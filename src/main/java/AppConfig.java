import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public Dictionary latinDictionary() {
        return new FileDictionary("4 латинские буквы", "dictionary_latin.txt", "[a-zA-Z]{4}");
    }

    @Bean
    public Dictionary digitDictionary() {
        return new FileDictionary("5 цифр", "dictionary_digit.txt", "[0-9]{5}");
    }

    @Bean
    public ConsoleRunner consoleRunner(List<Dictionary> dictionaries) {
        return new ConsoleRunner(dictionaries);
    }
}
