# Домашнее задание №06 "Эмулятор банкомата"

## Условие:

Написать эмулятор АТМ (банкомата).

Объект класса АТМ должен уметь:
- принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
- выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
_Это задание не на алгоритмы, а на проектирование.
Поэтому оптимизировать выдачу не надо._
- выдавать сумму остатка денежных средств

## Решение:
1. Перечисление `ru.otus.hw06.Banknote` с возможными номиналами банкнот, т.к.
в реальном мире есть только ограниченное количество номиналов.
2. Ячейка банкнот `ru.otus.hw06.BanknoteCell` 
и ее реализация `ru.otus.hw06.BanknoteCellImpl`, которая имеет определенный при создании
номинал и хранит в себе банкноты данного номинала. Имеет методы для добавления банкнот,
извлечения и подсчета количества.
3. Хранилище банкнот `ru.otus.hw06.BanknotesStore` 
и его реализация `ru.otus.hw06.BanknotesStoreImpl`, оно содержит в себе ячейки под 
все номиналы банкнот и может принимать банкноты, выдавать коллекцию банкнот по сумме, 
выдавать баланс (сумму номиналов всех банкнот в хранилище). Для выдачи банкнот в хранилище
использован алгоритм с жадным выбором.
4. При невозможности выдать сумму генерируется исключение `ru.otus.hw06.ATMCashOutException`.
5. Банкомат `ru.otus.hw06.ATM` и его реализация `ru.otus.hw06.ATMImpl` 
имеет методы для загрузки банкнот, выдаче по сумме и получения
баланса. Все эти операции делегеруются хранилищу банкнот. 
Я думаю такое устройство достаточно
неплохо соответствует реальному устройству банкомата.
6. Для методов банкомата написан тест `ru.otus.hw06.ATMTest`.