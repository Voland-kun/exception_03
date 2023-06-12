package hw03;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class hw03 {

    // Ввод в одну строку с последующим её парсингом делать не стал, сам подсчёт количества элементов не представляет
    // какой-либо сложности, но порядок имени фамилии отчества и вычленение заведомо ошибочного ввода в виде например
    // ввода вместо номера телефона строки из одних букв другое дело.
    public static void main(String[] args) {
        String a = stringCheck("фамилию");
        String b = stringCheck("имя");
        String c = stringCheck("отчество");
        String d = dateCheck("дату рождения в формате dd.MM.yyyy");
        String e = phoneCheck("номер телефона только цифры");
        String f = genderCheck("пол (m или f)");
        String res = "<" + a + ">" + "<" + b + ">" + "<" + c + ">" + "<" + d + ">" + "<" + e + ">" + "<" + f + ">" + "\n";
        fileWrite(a, res);
    }

    public static String userInput(String str) {
        Scanner sc = new Scanner(System.in);
        System.out.printf("Введите " + str + ":\n");
        String result = sc.nextLine();
        return result;
    }

    public static String stringCheck(String str) {
        String result;
        while (true) {
            String inStr = userInput(str);
            try {
                if (inStr != null && inStr.chars().allMatch(Character::isLetter)) {
                    result = inStr;
                    break;
                } else {
                    throw new InvalidInputException(1);
                }
            } catch (InvalidInputException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String dateCheck(String str) {
        String result;
        SimpleDateFormat userDate = new SimpleDateFormat();
        while (true) {
            String inStr = userInput(str);
            try {
                userDate.applyPattern("dd.MM.yyyy");
                Date bdDate = userDate.parse(inStr);
                result = inStr;
                break;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String phoneCheck(String str) {
        String result;
        while (true) {
            String inStr = userInput(str);
            try {
                if (inStr.length() == 11 && inStr.chars().allMatch(Character::isDigit)) {
                    result = inStr;
                    break;
                } else {
                    throw new InvalidInputException(2);
                }
            } catch (InvalidInputException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String genderCheck(String str) {
        String result;
        while (true) {
            String inStr = userInput(str);
            try {
                if (inStr.toLowerCase().equals("f") || inStr.toLowerCase().equals("m")) {
                    result = inStr.toLowerCase();
                    break;
                } else {
                    throw new InvalidInputException(3);
                }
            } catch (InvalidInputException e) {
                e.printStackTrace();
            }
        }
        return result;

    }
    public static void fileWrite(String surename, String text) {
        String filePath = surename + ".txt";
        //Насколько я понял при создании файлов/стримов в блоке try закрывать отдельно в блоке finаlly не обязательно?
        //Проверку на полное совпадение вносимой строки с содержащимися в файле для обработки дублей тоже не стал
        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            fileWriter.append(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class InvalidInputException extends Exception{

    private int id;

    public InvalidInputException(int x) {
        id = x;
    }

    public String toString() {
        String desc = "";
        switch (id) {
            case (1):
                desc = "Должно содержать только буквы";
                break;
            case (2):
                desc = "Некорректный номер телефона";
                break;
            case (3):
                desc = "Некорректный пол";
                break;
            default:
                break;
        }
        return "Некорректный ввод InvalidInputException[" + id + "]: " + desc;
    }
}
