package Mateus_Academia.Biblioteca_Virtual.repository;

import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Book Repository")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Creates and then saves a Book when successful")
    public void saveBookWhenSuccessful(){
        Book book = bookBuilder();
        Book savedBook = bookRepository.save(book);
        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getId()).isNotNull();
        Assertions.assertThat(savedBook.getName()).isEqualTo(book.getName());
    }
    @Test
    @DisplayName("saves and then updates a Book when successful")
    public void updateBookWhenSuccessful(){
        Book book = bookBuilder();
        Book savedBook = bookRepository.save(book);
        savedBook.setName("Book_Updated");
        Book bookUpdated = bookRepository.save(savedBook);
        Assertions.assertThat(bookUpdated).isNotNull();
        Assertions.assertThat(bookUpdated.getId()).isNotNull();
        Assertions.assertThat(bookUpdated.getName()).isEqualTo(savedBook.getName());
    }
    @Test
    @DisplayName("saves and then deletes a Book when successful")
    public void deleteBookWhenSuccessful(){
        Book book = bookBuilder();
        Book savedBook = bookRepository.save(book);
        bookRepository.delete(savedBook);
        Optional<Book> optionalBook = bookRepository.findById(savedBook.getId());

        Assertions.assertThat(optionalBook).isEmpty();
    }
    @Test
    @DisplayName("finds a Book by name and then returns a list of Books when successful")
    public void findBookByNameWhenSuccessful(){
        Book book = bookBuilder();
        Book savedBook = bookRepository.save(book);
        List<Book> books = bookRepository.findByName(book.getName());

        Assertions.assertThat(books)
                .isNotEmpty()
                .contains(savedBook);
    }
    @Test
    @DisplayName("tries to find a Book by name and then returns an empty list of Books when unsuccessful")
    public void failsToFindBookByName(){
        Book book = bookBuilder();
        bookRepository.save(book);
        List<Book> books = bookRepository.findByName("Different_Name");

        Assertions.assertThat(books).isEmpty();
    }
    @Test
    @DisplayName("Tries to save a Book but fails because of empty input values")
    public void failsToSaveBook_ThrowConstraintViolationException_EmptyValues(){
        Book book = new Book();
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> bookRepository.save(book))
                .withMessageContaining("The name cannot be empty!")
                .withMessageContaining("The isbn may not be empty!")
                .withMessageContaining("The date may not be empty!");
    }
    @Test
    @DisplayName("Tries to save a Book but fails because of invalid ISBN pattern")
    public void failsToSaveBook_ThrowConstraintViolationException_InvalidIsbnFormat(){
        Book book = new Book();
        book.setIsbn("invalid isbn");
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> bookRepository.save(book))
                .withMessageContaining("The ISBN's pattern is invalid!");
    }
    private Book bookBuilder(){
        Date date = new Date();
        return Book.builder()
                .name("Book_Test")
                .publicationDate(date)
                .authors(null)
                .isbn("9780596520687")
                .build();
    }
}