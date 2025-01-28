package Mateus_Academia.Biblioteca_Virtual.controllers;

import Mateus_Academia.Biblioteca_Virtual.Utils.AuthorEntitiesBuilder;
import Mateus_Academia.Biblioteca_Virtual.Utils.BookEntitiesBuilder;
import Mateus_Academia.Biblioteca_Virtual.entities.Author;
import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.requests.AuthorPostRequestBody;
import Mateus_Academia.Biblioteca_Virtual.requests.AuthorPutRequestBody;
import Mateus_Academia.Biblioteca_Virtual.services.AuthorService;
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
class AuthorControllerTest {
    @InjectMocks
    private AuthorController authorController;


    @Mock
    private AuthorService authorServiceMock;


    @BeforeEach
    void setUp() {
        BDDMockito.when(authorServiceMock.listAll())
                .thenReturn(List.of(AuthorEntitiesBuilder.authorBuilder()));

        BDDMockito.when(authorServiceMock.listAllBooksFromAuthor(ArgumentMatchers.anyLong()))
                .thenReturn(List.of(BookEntitiesBuilder.bookBuilder()));

        BDDMockito.when(authorServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AuthorEntitiesBuilder.authorBuilder());

        BDDMockito.when(authorServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AuthorEntitiesBuilder.authorBuilder()));

        BDDMockito.when(authorServiceMock.save(ArgumentMatchers.any(AuthorPostRequestBody.class)))
                .thenReturn(AuthorEntitiesBuilder.authorBuilder());

        BDDMockito.doNothing().when(authorServiceMock).replace(ArgumentMatchers.any(AuthorPutRequestBody.class));

        BDDMockito.doNothing().when(authorServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("list: returns a list of all authors when successful")
    void list_ReturnsListOfAuthors_WhenSuccessful() {
        String expectedName = AuthorEntitiesBuilder.authorBuilder().getName();

        List<Author> authors = authorController.list().getBody();

        Assertions.assertThat(authors)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(authors.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById: returns author when successful")
    void findById_ReturnsAuthor_WhenSuccessful() {
        Long expectedId = AuthorEntitiesBuilder.authorBuilder().getId();

        Author author = authorController.findById(AuthorEntitiesBuilder.authorBuilder().getId()).getBody();

        Assertions.assertThat(author).isNotNull();

        Assertions.assertThat(author.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName: returns a list of authors when successful")
    void findByName_ReturnsListOfAuthor_WhenSuccessful() {
        String expectedName = AuthorEntitiesBuilder.authorBuilder().getName();

        List<Author> authors = authorController.findByName("author").getBody();

        Assertions.assertThat(authors)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(authors.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName: returns an empty list when the author wasn't found")
    void findByName_ReturnsEmptyListOfAuthor_WhenAuthorIsNotFound() {
        BDDMockito.when(authorServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Author> authors = authorController.findByName("author").getBody();

        Assertions.assertThat(authors)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("listAllBooksFromAuthor: returns the list of books from the specified author")
    void listAllBooksFromAuthor_ReturnsListOfBooksFromSpecifiedAuthor_WhenSuccessful() {
        String expectedName = BookEntitiesBuilder.bookBuilder().getName();

        ResponseEntity<List<Book>> booksFromAuthor = authorController.listAllBooksFromAuthor(
                ArgumentMatchers.anyLong());

        Assertions.assertThat(booksFromAuthor.getBody())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(booksFromAuthor.getBody().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("save returns author when successful")
    void save_ReturnsAuthor_WhenSuccessful() {

        Author author = authorController.save(AuthorEntitiesBuilder.authorPostRequestBodyBuilder()).getBody();

        Assertions.assertThat(author).isNotNull().isEqualTo(AuthorEntitiesBuilder.authorBuilder());

    }
    @Test
    @DisplayName("delete removes author when successful")
    void delete_RemovesAuthor_WhenSuccessful() {

        Assertions.assertThatCode(() -> authorController.delete(1))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = authorController.delete(1);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}