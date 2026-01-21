import java.io.*;
import java.util.*;

public class Main {

    private static Map<String, String> latinDict = new HashMap<>();
    private static Map<String, String> digitDict = new HashMap<>();

    private static String latinPath = "dictionary_latin.txt";
    private static String digitPath = "dictionary_digit.txt";

    private static Map<String, String> currentDict;
    private static String currentPath;
    private static String currentRegex;
    private static String currentDescription;

    public static void main(String[] args) {
        loadDictionary(latinPath, latinDict);
        loadDictionary(digitPath, digitDict);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("ГЛАВНОЕ МЕНЮ");
            System.out.println("1. Работа со словарем 1 (4 латинские буквы)");
            System.out.println("2. Работа со словарем 2 (5 цифр)");
            System.out.println("3. выход");

            String choice = scanner.nextLine().trim();

            if (choice.equals("3")) {
                break;
            } else if (choice.equals("1")) {
                currentDict = latinDict;
                currentPath = latinPath;
                currentDescription = "4 латинские буквы";
                currentRegex = "[a-zA-Z]{4}";
                workWithDictionary();
            } else if (choice.equals("2")) {
                currentDict = digitDict;
                currentPath = digitPath;
                currentDescription = "5 цирф";
                currentRegex = "[0-9]{5}";
                workWithDictionary();
            } else {
                System.out.println("Такого выбора нет, пожалуйста, выберите номер из списка");
            }
        }
    }

    private static void workWithDictionary() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("СЛОВАРЬ: " + currentDescription);
            System.out.println("1. Просмотр всех записей");
            System.out.println("2. Найти запись");
            System.out.println("3. Добавить запись");
            System.out.println("4. Удалить запись");
            System.out.println("5. СМЕНИТЬ СЛОВАРЬ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": printDictionary(); break;
                case "2": findEntry(); break;
                case "3": addEntry(); break;
                case "4": delEntry(); break;
                case "5": return;
                default: System.out.println("Такого выбора нет, пожалуйста, выберите номер из списка");
            }
        }
    }

    private static void loadDictionary(String Path, Map<String, String>dict) {
        File file = new File(Path);
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("-", 2);
                if (parts.length == 2) {
                    dict.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения файла" + e.getMessage());
        }
    }

    public static void saveDictionary() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(currentPath))) {
            for (Map.Entry<String, String> entry : currentDict.entrySet()) {
                writer.println(entry.getKey() + " - " + entry.getValue());
            }
            System.out.println("Словарь сохранен на диск");
        } catch (IOException e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    private static void printDictionary() {
        if (currentDict.isEmpty()) {
            System.out.println("Словарь пуст");
        } else {
            int count = 1;
            List<String> keys = new ArrayList<>(currentDict.keySet());
            Collections.sort(keys);

            for (String key : keys) {
                System.out.println(count++ + ". " + key + " - " + currentDict.get(key));
            }
        }
    }

    private static void findEntry() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ключ для поиска: ");
        String key = scanner.nextLine().trim();

        if (currentDict.containsKey(key)) {
            System.out.println("Найдено:" + currentDict.get(key));
        } else {
            System.out.println("Ключ не найден");
        }
    }

    private static void addEntry() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите новый ключ (" + currentDescription + "):");
        String key = scanner.nextLine().trim();

        if (!key.matches(currentRegex)) {
            System.out.println("Ошибка. Ключ должен содержать только " + currentDescription);
            return;
        }

        if (currentDict.containsKey(key)) {
            System.out.println("Такой ключ уже существует");
            return;
        }

        System.out.println("Введите значение: ");
        String value = scanner.nextLine().trim();

        currentDict.put (key, value);
        System.out.println("Запись добавлена");
        saveDictionary();
    }

    private static void delEntry() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ключ для удаления");
        String key = scanner.nextLine().trim();

        if (currentDict.containsKey(key)) {
            currentDict.remove(key);
            System.out.println("Запись удалена");
            saveDictionary();
        } else {
            System.out.println("Ключ не найден");
        }
    }
}
