import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleRunner {

    private final List<Dictionary> dictionaries;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public ConsoleRunner(List<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }

    public void run() {
        while (true) {
            System.out.println("\nГЛАВНОЕ МЕНЮ");
            for (int i = 0; i < dictionaries.size(); i++) {
                System.out.println((i + 1) + ". " + dictionaries.get(i).getName());
            }
            System.out.println((dictionaries.size() + 1) + ". Выход");

            String choice = scanner.nextLine().trim();
            try {
                int index = Integer.parseInt(choice) - 1;
                if (index == dictionaries.size()) return;
                if (index >= 0 && index < dictionaries.size()) {
                    workWithDictionary(dictionaries.get(index));
                } else {
                    System.out.println("Неверный выбор.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите число.");
            }
        }
    }

    private void workWithDictionary(Dictionary dict) {
        while (true) {
            System.out.println("\nСЛОВАРЬ: " + dict.getName());
            System.out.println("1. Просмотр всех записей");
            System.out.println("2. Найти запись");
            System.out.println("3. Добавить запись");
            System.out.println("4. Удалить запись");
            System.out.println("5. Назад");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    dict.getEntries().forEach((k, v) -> System.out.println(k + " - " + v));
                    break;
                case "2":
                    System.out.println("Введите ключ:");
                    String key = scanner.nextLine().trim();
                    String found = dict.findEntry(key);
                    System.out.println(found != null ? "Найдено: " + found : "Не найдено");
                    break;
                case "3":
                    System.out.println("Введите ключ (" + dict.getRegex() + "):");
                    String newKey = scanner.nextLine().trim();
                    if (!newKey.matches(dict.getRegex())) {
                        System.out.println("Ошибка формата ключа.");
                        break;
                    }
                    System.out.println("Введите значение:");
                    String value = scanner.nextLine().trim();
                    dict.addEntry(newKey, value);
                    System.out.println("Добавлено.");
                    break;
                case "4":
                    System.out.println("Введите ключ для удаления:");
                    String delKey = scanner.nextLine().trim();
                    dict.deleteEntry(delKey);
                    System.out.println("Удалено.");
                    break;
                case "5": return;
                default: System.out.println("Неверный выбор.");
            }
        }
    }
}
