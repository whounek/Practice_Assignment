import java.util.Map;

public interface Dictionary {
    String getName();
    String getRegex();
    Map<String, String> getEntries();
    void addEntry(String key, String value);
    void deleteEntry(String key);
    String findEntry(String key);
    void save();
}
