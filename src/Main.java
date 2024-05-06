import java.util.HashMap;
import java.util.Scanner;

class Main {
    public static String calc(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неправильный формат ввода"); // Неправильный формат ввода
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        if (operand1.isEmpty() || operator.isEmpty() || operand2.isEmpty()) {
            throw new IllegalArgumentException("Пустая строка"); // Пустая строка
        }

        if (!isRomanNumber(operand1) && !isNumber(operand1)) {
            throw new IllegalArgumentException("Неверный формат числа"); // Неверный формат числа
        }

        if (!isRomanNumber(operand2) && !isNumber(operand2)) {
            throw new IllegalArgumentException("Неверный формат числа"); // Неверный формат числа
        }

        int num1;
        int num2;
        if (isRomanNumber(operand1)) {
            num1 = convertToNumber(operand1);
            num2 = convertToNumber(operand2);
        } else {
            num1 = Integer.parseInt(operand1);
            num2 = Integer.parseInt(operand2);
        }

        if ((num1 < 1 || num1 > 10) || (num2 < 1 || num2 > 10)) {
            throw new IllegalArgumentException("Числа не в диапазоне от 1 до 10"); // Числа не в диапазоне от 1 до 10
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new IllegalArgumentException("Деление на ноль"); // Деление на ноль
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Неправильный оператор"); // Неправильный оператор
        }

        if (isRomanNumber(operand1) && isRomanNumber(operand2)) {
            return convertToRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    private static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isRomanNumber(String str) {
        String romanNumeralPattern = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
        return str.matches(romanNumeralPattern);
    }

    private static int convertToNumber(String roman) {
        HashMap<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int result = 0;
        int prevValue = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            int curValue = romanMap.get(roman.charAt(i));
            if (curValue < prevValue) {
                result -= curValue;
            } else {
                result += curValue;
            }
            prevValue = curValue;
        }
        return result;
    }

    private static String convertToRoman(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Результат меньше единицы");
        }
        if (number > 3999) {
            throw new IllegalArgumentException("Результат больше 3999");
        }

        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanNumerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder result = new StringBuilder();
        int i = 0;
        while (number > 0) {
            if (number - arabicValues[i] >= 0) {
                result.append(romanNumerals[i]);
                number -= arabicValues[i];
            } else {
                i++;
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение:");
        String input = scanner.nextLine();
        System.out.println(calc(input));
        scanner.close();
    }
}