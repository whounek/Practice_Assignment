package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WebController {

    @Autowired
    private DictionaryRepository repo;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String search) {
        List<DictionaryEntry> allRecords = repo.findAll();
        List<DictionaryEntry> filtered = new ArrayList<>();

        for (DictionaryEntry entry : allRecords) {
            if (search == null || search.isEmpty() ||
                    entry.getWordKey().toLowerCase().contains(search.toLowerCase()) ||
                    entry.getWordValue().toLowerCase().contains(search.toLowerCase())) {
                filtered.add(entry);
            }
        }

        StringBuilder latinHtml = new StringBuilder();
        StringBuilder digitHtml = new StringBuilder();

        for (DictionaryEntry e : filtered) {
            String item = "<li>" + e.getWordKey() + " ➔ " + e.getWordValue() +
                    " <button onclick='deleteRecord(" + e.getId() + ")'>Удалить</button></li>";

            if (e.getDictType().equals("LATIN")) latinHtml.append(item);
            if (e.getDictType().equals("DIGIT")) digitHtml.append(item);
        }

        return "<html><head><meta charset='UTF-8'><title>Словари</title></head><body style='font-family: Arial; padding: 20px;'>" +
                "<h1>Управление словарями</h1>" +

                "<h3>Добавить запись</h3>" +
                "<form method='POST' action='/add'>" +
                "<select name='dictType'><option value='LATIN'>4 латинские</option><option value='DIGIT'>5 цифр</option></select> " +
                "<input type='text' name='wordKey' placeholder='Ключ' required> " +
                "<input type='text' name='wordValues' placeholder='Значения (через запятую)' required> " +
                "<button type='submit'>Добавить</button>" +
                "</form><hr>" +

                "<h3>Поиск</h3>" +
                "<form method='GET' action='/'>" +
                "<input type='text' name='search' placeholder='Поиск...'> " +
                "<button type='submit'>Найти</button> <a href='/'>Сброс</a>" +
                "</form><hr>" +

                "<h3>Словарь 1 (Латынь)</h3><ul>" + latinHtml.toString() + "</ul>" +
                "<h3>Словарь 2 (Цифры)</h3><ul>" + digitHtml.toString() + "</ul>" +

                "<script>" +
                "function deleteRecord(id) { if(confirm('Удалить?')) { fetch('/delete/' + id, {method: 'DELETE'}).then(() => window.location.reload()); } }" +
                "</script>" +
                "</body></html>";
    }

    @PostMapping("/add")
    public void add(@RequestParam String dictType, @RequestParam String wordKey, @RequestParam String wordValues, jakarta.servlet.http.HttpServletResponse response) throws Exception {
        if ((dictType.equals("LATIN") && wordKey.matches("[a-zA-Z]{4}")) ||
                (dictType.equals("DIGIT") && wordKey.matches("[0-9]{5}"))) {

            String[] values = wordValues.split(",");
            for (String val : values) {
                if (!val.trim().isEmpty()) {
                    DictionaryEntry entry = new DictionaryEntry();
                    entry.setDictType(dictType);
                    entry.setWordKey(wordKey.trim());
                    entry.setWordValue(val.trim());
                    repo.save(entry);
                }
            }
        }
        response.sendRedirect("/");
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "ok";
    }
}
