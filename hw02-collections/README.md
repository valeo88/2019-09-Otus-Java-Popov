# Домашнее задание №02 "DIY ArrayList"
## Постановка
Написать свою реализацию ArrayList на основе массива.
class DIYArrayList<T> implements List<T>{...}

Проверить, что на ней работают методы из java.util.Collections:
* addAll(Collection<? super T> c, T... elements)
* static <T> void copy(List<? super T> dest, List<? extends T> src)
* static <T> void sort(List<T> list, Comparator<? super T> c)

1) Проверяйте на коллекциях с 20 и больше элементами.
2) DIYArrayList должен имплементировать ТОЛЬКО ОДИН интерфейс - List.
3) Если метод не имплементирован, то он должен выбрасывать исключение UnsupportedOperationException.

## Решение
* Создан модуль hw02-collections, подключен JUnit
* Создан класс ru.otus.hw02.DIYArrayList с реализацией интерфейса List<T> на основе массива
* Создан класс теста ru.otus.hw02.DIYArrayListTest для тестирования методов из задания