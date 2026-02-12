import java.io.*;
import java.util.*;

public class FileDictionary implements Dictionary {
    private final String name;
    private final String filePath;
    private final String regex;
    private final Map<String, String> dictionary = new HashMap<>();

    public FileDictionary(String name, String filePath, String regex) {
        this.name = name;
        this.filePath = filePath;
        this.regex = regex;
        load();
    }

    private void load() {
        File file = new File(filePath);
        if (!file.exists()) return;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("-", 2);
                if (parts.length == 2) {
                    dictionary.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    @Override
    public void save() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                writer.println(entry.getKey() + " - " + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    @Override
    public String getName() { return name; }

    @Override
    public String getRegex() { return regex; }

    @Override
    public Map<String, String> getEntries() { return dictionary; }

    @Override
    public void addEntry(String key, String value) {
        dictionary.put(key, value);
        save();
    }

    @Override
    public void deleteEntry(String key) {
        dictionary.remove(key);
        save();
    }

    @Override
    public String findEntry(String key) {
        return dictionary.get(key);
    }
}
