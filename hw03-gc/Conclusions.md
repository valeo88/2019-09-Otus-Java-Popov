# Выводы о работе разных сборщиков мусора

## G1

Запускался на BellSoft JDK 13 с параметрами:
    -Xms64m
    -Xmx64m
    -XX:+UseG1GC

### Результаты работы 
Сборщик | Номер минуты | Количество сборок | Время сборок, мс
------- | ------------ | ----------------- | ----------------
G1 Young Generation |  1 | 4 |  41
G1 Old Generation |  1 | 0 |  0
G1 Young Generation |  2 | 4 |  31
G1 Old Generation |  2 | 0 |  0
G1 Young Generation |  3 | 4 |  39
G1 Old Generation |  3 | 0 |  0
G1 Young Generation |  4 | 5 |  42
G1 Old Generation |  4 | 2 |  107
G1 Young Generation |  5 | 3 |  20
G1 Old Generation |  5 | 1 |  39
G1 Young Generation (после OOM) |  6 | 5 |  24
G1 Old Generation (после OOM) |  6 | 7 |  314

Суммарно до OutOfMemory: 
* G1 Young Generation - 20 сборок, 173мс
* G1 Old Generation - 3 сборки, 146мс