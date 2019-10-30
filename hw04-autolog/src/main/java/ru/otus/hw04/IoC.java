package ru.otus.hw04;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/** Простой IoC-контейнер, создающий прокси-объекты только для классов,
 *  у которых есть методы аннотированные {@link Log}.
 *  Во всех остальных случаях создается обычный объект. */
public class IoC {

    static <T> T createInstance(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Constructor<T> constructor = clazz.getConstructor();
        // проверка что в классе есть методы аннотированные @Log
        for (Method method : clazz.getMethods()) {
            if (hasLogAnnotation(method)) {
                InvocationHandler handler = new LoggedInvocationHandler(constructor.newInstance());
                return (T) Proxy.newProxyInstance(IoC.class.getClassLoader(),
                        clazz.getInterfaces(), handler);
            }
        }
        // методов с аннотацией нет - возвращаем обычный объект
        return constructor.newInstance();
    }

    /** Проверка на наличие у метода аннотации {@link Log}. */
    private static boolean hasLogAnnotation(Method method) {
        return method.getDeclaredAnnotation(Log.class)!=null;
    }

    /** Реализация InvocationHandler, в котором для методов,
     *  аннотированных {@link Log} будет выполнятся логирование аргументов. */
    static class LoggedInvocationHandler<T> implements InvocationHandler {
        private final T instance;
        private final Set<Method> methodsWithLog;

        LoggedInvocationHandler(T instance){
            this.instance = instance;
            // заполняем множество методов интерфейсов, реализации которых помечены @Log
            // берутся именно методы интерфейсов, потому что в invoke приходит метод из интерфейса
            methodsWithLog = Arrays.stream(this.instance.getClass().getMethods())
                    .filter(IoC::hasLogAnnotation)
                    .flatMap(method -> {
                        return Arrays.stream(this.instance.getClass().getInterfaces())
                                .flatMap(intf -> Arrays.stream(intf.getMethods()))
                                .filter(method1 -> method.getName().equals(method1.getName())
                                && method.getReturnType().equals(method1.getReturnType())
                                && equalParamTypes(method.getParameterTypes(), method1.getParameterTypes()));
                    })
                    .collect(Collectors.toSet());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methodsWithLog.contains(method)) {
                System.out.println(String.format("executed method: %s, params: %s", method.getName(),
                        Arrays.stream(args).map(Object::toString).collect(Collectors.joining(","))));
            }
            return method.invoke(instance, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "clazz=" + this.instance.getClass() +
                    '}';
        }

        // сравнение типов параметров взято из java.lang.reflect.Executable
        private boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
            if (params1.length == params2.length) {
                for (int i = 0; i < params1.length; i++) {
                    if (params1[i] != params2[i])
                        return false;
                }
                return true;
            }
            return false;
        }

    }

}
