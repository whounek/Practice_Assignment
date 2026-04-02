package app;

import jakarta.persistence.*;

@Entity
@Table(name = "dictionary", indexes = {
        @Index(name = "idx_key", columnList = "wordKey"),
        @Index(name = "idx_val", columnList = "wordValue")
})
public class DictionaryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dictType;
    private String wordKey;
    private String wordValue;

    public DictionaryEntry() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDictType() { return dictType; }
    public void setDictType(String dictType) { this.dictType = dictType; }

    public String getWordKey() { return wordKey; }
    public void setWordKey(String wordKey) { this.wordKey = wordKey; }

    public String getWordValue() { return wordValue; }
    public void setWordValue(String wordValue) { this.wordValue = wordValue; }
}