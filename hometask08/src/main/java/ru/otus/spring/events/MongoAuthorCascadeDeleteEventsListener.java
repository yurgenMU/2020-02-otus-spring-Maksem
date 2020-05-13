package ru.otus.spring.events;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repository.BookRepository;

@Component
public class MongoAuthorCascadeDeleteEventsListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;

    public MongoAuthorCascadeDeleteEventsListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Author> event) {
        super.onAfterDelete(event);
        var source = event.getSource();
        var id = source.get("_id").toString();
        bookRepository.findAllByAuthor(new Author(id, null)).forEach(bookRepository::delete);
    }
}
