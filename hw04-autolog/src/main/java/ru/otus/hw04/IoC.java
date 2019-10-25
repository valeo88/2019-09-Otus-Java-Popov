package ru.otus.hw04;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/** Простой IoC-контейнер, создающий прокси-объекты только для классов,
 *  у которых есть методы аннотированные {@link Log}.
 *  Во всех остальных случаях создается обычный объект. */
public class IoC {

    static <T> T createInstance(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Constructor<T> constructor = clazz.getConstructor();
        // проверка что в интерфейсе класса есть методы аннотированные @Log
        for (Method method : Arrays.stream(clazz.getInterfaces())
                                    .flatMap(x -> Arrays.stream(x.getMethods()))
                                    .collect(Collectors.toSet())) {
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

        LoggedInvocationHandler(T instance){
            this.instance = instance;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (hasLogAnnotation(method)) {
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

    }
}
