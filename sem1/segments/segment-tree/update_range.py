# модификации вида "Добавь от i до j включительно", индексация начинается с 1 вроде бы)))
def update(left, right: int, add_value: int,
           v: int, tree_left: int, tree_right: int):
    if left > right:
        return
    if left == tree_left and right == tree_right:
        tree[v] += add_value
        return

    tree_middle = (tree_left + tree_right) // 2
    
    update(left, min(right, tree_middle), add_value,
           v * 2 + 1, tree_left, tree_middle),
    update(max(left, tree_middle + 1), right, add_value,
           v * 2 + 2, tree_middle + 1, tree_right)


# Запрос узнать значение элемента
def get(index: int, v: int, tree_left: int, tree_right: int) -> int:
    if tree_left == tree_right:
        return tree[v]

    tree_middle = (tree_left + tree_right) // 2

    if index <= tree_middle:
        return tree[v] + get(index, v * 2 + 1,
                             tree_left, tree_middle)
    else:
        return tree[v] + get(index, v * 2 + 2,
                             tree_middle + 1, tree_right)
