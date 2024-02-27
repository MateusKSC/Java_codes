package Mateus_Academia.Biblioteca_Virtual.controllers;

import Mateus_Academia.Biblioteca_Virtual.Utils.AuthorEntitiesBuilder;
import Mateus_Academia.Biblioteca_Virtual.Utils.BookEntitiesBuilder;
import Mateus_Academia.Biblioteca_Virtual.entities.Author;
import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.requests.BookPutRequestBody;
import Mateus_Academia.Biblioteca_Virtual.services.BookService;
import Mateus_Academia.Biblioteca_Virtual.utilities.DateUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class BookControllerTest {
    @InjectMocks
    private BookController bookController;
    @Mock
    private DateUtil dateUtil;
    @Mock
    private BookService bookServiceMock;

    @BeforeEach
    void setUp(){
        BDDMockito.when(bookServiceMock.listAll())
                .thenReturn(List.of(BookEntitiesBuilder.bookBuilder()));

        BDDMockito.when(bookServiceMock.listAllAuthors(ArgumentMatchers.anyLong()))
                .thenReturn(List.of(AuthorEntitiesBuilder.authorBuilder()));

        BDDMockito.when(bookServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(BookEntitiesBuilder.bookBuilder());

        BDDMockito.when(bookServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(BookEntitiesBuilder.bookBuilder()));

        BDDMockito.when(bookServiceMock.save(ArgumentMatchers.any(),ArgumentMatchers.any()))
                .thenReturn(BookEntitiesBuilder.bookBuilder());
        BDDMockito.doNothing().when(bookServiceMock).replace(ArgumentMatchers.any(BookPutRequestBody.class));

        BDDMockito.doNothing().when(bookServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("list: returns a list of all books when successful")
    void list_ReturnsListOfBooks_WhenSuccessful(){
        String expectedName = BookEntitiesBuilder.bookBuilder().getName();

        List<Book> books = bookController.list().getBody();

        Assertions.assertThat(books)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(books.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("listAllAuthors: returns a list of Authors when successful")
    void listAllAuthors_ReturnsListOfAuthors_WhenSuccessful(){
        ResponseEntity<List<Author>> authors = bookController.listAllAuthors(ArgumentMatchers.anyLong());
        Assertions.assertThat(authors.getBody())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(authors.getBody().get(0).getName()).isEqualTo(
                AuthorEntitiesBuilder.authorBuilder().getName());
    }

    @Test
    @DisplayName("findById: returns book when successful")
    void findById_ReturnsBook_WhenSuccessful(){
        Long expectedId = BookEntitiesBuilder.bookBuilder().getId();

        Book book = bookController.findById(BookEntitiesBuilder.bookBuilder().getId()).getBody();

        Assertions.assertThat(book).isNotNull();

        Assertions.assertThat(book.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName: returns a list of books when successful")
    void findByName_ReturnsListOfBook_WhenSuccessful(){
        String expectedName = BookEntitiesBuilder.bookBuilder().getName();

        List<Book> books = bookController.findByName("book").getBody();
        Assertions.assertThat(books)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(books.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName: returns an empty list when the book wasn't found")
    void findByName_ReturnsEmptyListOfBook_WhenBookIsNotFound(){
        BDDMockito.when(bookServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Book> books = bookController.findByName("book").getBody();

        Assertions.assertThat(books)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns book when successful")
    void save_ReturnsBook_WhenSuccessful(){
        Book book = bookController.save(BookEntitiesBuilder.bookPostRequestBodyBuilder(),
                Collections.emptyList()).getBody();

        Assertions.assertThat(book).isNotNull().isEqualTo(BookEntitiesBuilder.bookBuilder());
    }

    @Test
    @DisplayName("replace updates book when successful")
    void replace_UpdatesBook_WhenSuccessful(){

        Assertions.assertThatCode(() ->bookController.replace(BookEntitiesBuilder.bookPutRequestBodyBuilder()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = bookController.replace(BookEntitiesBuilder.bookPutRequestBodyBuilder());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes book when successful")
    void delete_RemovesBook_WhenSuccessful(){

        Assertions.assertThatCode(() ->bookController.delete(1))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = bookController.delete(1);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}