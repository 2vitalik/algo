class Version:
    def __init__(self, value: object, prev_ver: int):
        self.value = value        # значение в вершине стека
        self.prev_ver = prev_ver  # индекс предыдущей версии

    def copy(self):
        return Version(self.value, self.prev_ver)

class PersistentStack:
    def __init__(self):
        self.versions = list()  # версии стека
    
    def push(self, ver: int, value: object):
        # Добавим value в стек версии ver, получив новую версию
        self.versions.append(Version(value, ver))
    
    def pop(self, ver: int) -> object:
        curr = self.versions[ver]  # текущая версия
        prev = self.versions[curr.prev_ver]
        # Копируем предыдущую версию и тем самым
        # получаем новый стек без последнего элемента:
        self.versions.append(prev.copy())
        return curr.value  # возвращаем значение в стеке `ver`

    def print(self):
        print("[ ", end="")
        for i in range(0, len(self.versions) - 1):
            print(f'[value: {self.versions[i].value}, prev_ver: {self.versions[i].prev_ver}]', end=", ")
        if len(self.versions) > 0:
            print(f'[value: {self.versions[-1].value}, prev_ver: {self.versions[-1].prev_ver}]', end="")
        print(" ]")

def PersistentStackDemo():
    ps = PersistentStack()
    ps.push(0, 1)
    ps.push(1, 2)
    ps.print()
    print(ps.pop(1))
    ps.print()

PersistentStackDemo()

# Resources: this code is from KhNURE course called: 
# "Additional chapters of Algorithms and Data Structure"
# I've fixed some syntax errors and added code to demonstrate the DataStructure
