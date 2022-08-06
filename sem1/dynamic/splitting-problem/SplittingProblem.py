# Описание задачи:
# Дан стержень длины n и таблица цен для стержней длины i=1..n
# Найти максимальную прибыль, которую можно получить, 
# если имеющийся стержень можно разрезать на стержни 
# меньшей длины (длины стержней - целые числа)

def cut(n: int, prices: list, splits: list):
    results = [None] * (n + 1)
    results[0] = 0
    for j in range(1, n + 1):
        result = -1
        for i in range(1, j + 1):
            candidate = prices[i - 1] + results[j - i]
            if candidate > result:
                result = candidate
                splits[j] = i
        results[j] = result
    return results[n]

def solve(n: int, prices: list):
    splits = [None] * (n + 1)  # массив оптимальных размеров первой части

    print(f'Total price: {cut(n, prices, splits)}')
    size = n
    while size > 0:
        print(splits[size])
        size -= splits[size]

solve(9, [1, 5, 8, 9, 10, 17, 17, 20, 24, 30])
solve(10, [1, 5, 8, 9, 10, 17, 17, 20, 24, 30])

# Источник:
# За основу решения был взят код из курса "Дополнительные разделы Алгоритмов и Структур Данных"
# Харьковского Национального Университета Радиоэлектроники.
