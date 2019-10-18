# Выводы о работе разных сборщиков мусора

Все запускалось на BellSoft JDK 13.

## G1

Опции JVM:
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

Суммарно до OutOfMemory: 
* G1 Young Generation - 20 сборок, 173мс
* G1 Old Generation - 3 сборки, 146мс

## ConcurrentMarkSweep

Опции JVM:
    -Xms64m
    -Xmx64m
    -XX:+UseConcMarkSweepGC
    
По умолчанию в качестве сборщика для Young generation включается ParNewGC,
а для Old generation - ConcurrentMarkSweep.
    
### Результаты работы 
Сборщик | Номер минуты | Количество сборок | Время сборок, мс
------- | ------------ | ----------------- | ----------------
ParNew (young) | 1 | 2 |  63
ConcurrentMarkSweep (old) | 1 | 7 |  20480
ParNew (young) | 2 | 2 |  27
ConcurrentMarkSweep (old) | 2 | 20 |  21962
ParNew (young) | 3 | 0 |  0
ConcurrentMarkSweep (old) | 3 | 27 |  7965

Суммарно до OutOfMemory: 
* ParNew (young) - 4 сборок, 90мс
* ConcurrentMarkSweep (old) - 54 сборки, 50407мс

## Parallel

Опции JVM:
    -Xms64m
    -Xmx64m
    -XX:+UseParallelGC
    
### Результаты работы 
Сборщик | Номер минуты | Количество сборок | Время сборок, мс
------- | ------------ | ----------------- | ----------------
PS Scavenge (young) | 1 | 3 |  50
PS MarkSweep (old) | 1 | 1 |  75
PS Scavenge (young) | 2 | 0 |  0
PS MarkSweep (old) | 2 | 1 |  73
PS Scavenge (young) | 3 | 0 |  0
PS MarkSweep (old) | 3 | 0 |  0

Суммарно до OutOfMemory: 
* PS Scavenge (young) - 3 сборок, 50мс
* PS MarkSweep (old) - 2 сборки, 148мс

## Выводы
Измерения количества сборок и времени выполнения действительно подтверждают факты про сборщики мусора:
* **CMS** работает часто, но за короткие промежутки времени (малая задержка)
* **Parallel** работает редко и достаточно быстро (высокая производительность)
* **G1** работает и достаточно редко и сравнительно быстро 
(почти так же быстро, как и Parallel)

Измерения времени работы программы до OutOfMemory позволяют сделать вывод
что **G1** более "экономно" работает с памятью, поэтому при одинаковом
количестве Heap программа проработала на 2 минуты дольше.

Все это позволяет сделать вывод, что **G1** действительно стоит использовать
по умолчанию, если не нужны какие-то специфические особенности других GC.
