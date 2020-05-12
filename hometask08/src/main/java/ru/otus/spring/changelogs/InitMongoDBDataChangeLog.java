package ru.otus.spring.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.Collections;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Genre historicalBooks;
    private Genre militaryBooks;
    private Genre russianClassic;
    private Genre computerScience;
    private Genre philosophy;

    private Author karamzin;
    private Author pikul;
    private Author pushkin;
    private Author martin;
    private Author dostoevsky;

    @ChangeSet(order = "000", id = "dropDB", author = "yurgenMU", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initGenres", author = "yurgenMU", runAlways = true)
    public void initGenres(MongoTemplate template) {
        historicalBooks = template.save(new Genre("1", "Historical books"));
        militaryBooks = template.save(new Genre("2", "Military books"));
        russianClassic = template.save(new Genre("3", "Russian classic"));
        computerScience = template.save(new Genre("4", "Computer science"));
        philosophy = template.save(new Genre("5", "Philosophy"));
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "yurgenMU", runAlways = true)
    public void initStudents(MongoTemplate template) {
        karamzin = template.save(new Author("1", "Nikolai Karamzin"));
        pikul = template.save(new Author("2", "Valentin Pikul"));
        pushkin = template.save(new Author("3", "Alexander Pushkin"));
        martin = template.save(new Author("4", "Robert Martin"));
        dostoevsky = template.save(new Author("5", "Fyodor Dostoevsky"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "yurgenMU", runAlways = true)
    public void initTeachers(MongoTemplate template) {
        var pq17 = new Book("1", "Requiem to PQ-17", pikul, List.of(historicalBooks, militaryBooks));
        template.save(pq17);
        var history = new Book("2", "History of the Russian State", karamzin,
                Collections.singletonList(historicalBooks));
        template.save(history);
        var cleanCode = new Book("3", "Clean Code", martin,
                Collections.singletonList(computerScience));
        template.save(cleanCode);
        var idiot = new Book("4", "The Idiot", dostoevsky, List.of(russianClassic, philosophy));
        template.save(idiot);
        var godunov = new Book("5", "Boris Godunov", pushkin, List.of(russianClassic, historicalBooks));
        template.save(godunov);
        var onegin = new Book("6", "Eugene Onegin", pushkin,
                Collections.singletonList(russianClassic));
        template.save(onegin);
    }
}
