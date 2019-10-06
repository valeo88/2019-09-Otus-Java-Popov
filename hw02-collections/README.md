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
* Создан модуль hw02-collections с реализацией DIYArrayList
* Создан класс ru.otus.hw02.DIYArrayListDemo для проверки методов из задания
* Модуль собирается в исполняемый jar