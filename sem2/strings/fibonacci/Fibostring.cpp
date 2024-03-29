﻿#include <string>

// Строка Фибоначчи
// Строка получаемая из реккурентного соотношения S(n) = S(n - 1) + S(n - 2) n >= 2, где + это конкатенация
// (Также существует определение через морфизм h(x) = xy, h(y) = x => S(n) = h^n(y)
// При чем строится строка на основе алфавита из двух символов (x, y):
// S(0) = y, S(1) = x, S(2) = xy, ...

// Строка Фибоначчи отличается лишь своими интересными свойствами:
//    - Длины строк соответствуют значениям чисел Фибоначчи, что очевидно ведь для длин конкатенация означает сложение
//
//	  - Все предыдущие значения являются префиксами текущего (кроме нулевого), так как по формуле мы начинаем с S(n - 1)
//
//    - Не существует такой строки S(n + 2), что она не только S(n + 1) + S(n), но и S(n) + S(n + 1):
//      Это доказывается по индукции, достаточно произвести декомпозицию соотношений:
//      S(n + 1) + S(n) => S(n) + S(n - 1) + S(n) и S(n) + S(n + 1) => S(n) + S(n) + S(n - 1)
//      Если продолжать до значений S(0) и S(1), то можно подтвердить ассиметрию
//      Также, если просто посмотреть на строку, то можно заметить, что будет получаться S(n) + S(n + 1), но с инверсией двух последних знаков
//
//    - Для n >= 3 значения S(n - 2), S(n - 4), ..., S(2 - (n % 2)), являются бордерами:
//      Известно, что все предыдущие строки являются префиксами, также известно, что S(n) = S(n - 1) + S(n - 2), S(n - 2) = S(n - 3) + S(n - 4), ...
//   
//    - В строке Фибоначчи отсутствуют посдстроки из более чем двух х идущих подряд, либо одного у:
//      Снова доказываем по индукции, при переходе от начальных состояний S(0) и S(1) становится понятно, 
//      что изначально таких подстрок нет и они могут появиться лишь в месте конкатенации:
//      - строка заканчивающаяся на х + строка начинающаяся на xx - невозможно, из-за свойства префиксов 
//      - строка заканчивается на хх + строка начинающаяся на х - невозможно, из-за свойства бордеров
//      Аналогично для y (y + y, yy + _)

// В данной реализации создан класс который выводит первые n чисел бесконечной обобщенной строки Фибоначчи, что содержит как префиксы все строки Фибоначчи
// Так как мы знаем длину строки S(n - 2), а также то, что она префикс, то можем достраивать префикс нашей строки S(n - 1) к ней же

class FiboString
{
private:
	char zeroChar;
	char oneChar;

public:
	FiboString()
	{
		zeroChar = '0';
		oneChar = '1';
	}

	FiboString(const char zeroChar, const char oneChar)
	{
		this->zeroChar = zeroChar;
		this->oneChar = oneChar;
	}

	std::string GetFirstNCharacters(const int length)
	{
		std::string fibostring = "xy";

		if (length < 2)
		{
			return fibostring.substr(0, length);
		}

		int offset = 0;
		int prevSize = 1;
		int currentSize = 2;

		while (fibostring.length() < length)
		{
			if (offset == prevSize)
			{
				prevSize = currentSize;
				currentSize = prevSize + offset;
				offset = 0;
			}

			fibostring += fibostring[offset];
			offset++;
		}

		return fibostring;
	}
};
