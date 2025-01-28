package Mateus_Academia.Biblioteca_Virtual.services;

import Mateus_Academia.Biblioteca_Virtual.Utils.AuthorEntitiesBuilder;
import Mateus_Academia.Biblioteca_Virtual.Utils.BookEntitiesBuilder;
import Mateus_Academia.Biblioteca_Virtual.Utils.RenterEntitiesBuilder;
import Mateus_Academia.Biblioteca_Virtual.entities.Author;
import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.entities.Renter;
import Mateus_Academia.Biblioteca_Virtual.exceptions.BadRequestException;
import Mateus_Academia.Biblioteca_Virtual.repository.AuthorRepository;
import Mateus_Academia.Biblioteca_Virtual.repository.BookRepository;
import Mateus_Academia.Biblioteca_Virtual.repository.RenterRepository;
import Mateus_Academia.Biblioteca_Virtual.requests.AuthorPostRequestBody;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AuthorServiceTest {

    @InjectMocks
    private AuthorService authorService;
    @InjectMocks
    private RenterService renterService;

    @Mock
    private DateUtil dateUtil;

    @Mock
    private AuthorRepository authorRepositoryMock;
    @Mock
    private RenterRepository renterRepositoryMock;
    @Mock
    private BookRepository bookRepository;


    @BeforeEach
    void setUp() {
        BDDMockito.when(authorRepositoryMock.findAll())
                .thenReturn(List.of(AuthorEntitiesBuilder.authorBuilder()));

        BDDMockito.when(renterRepositoryMock.findAll())
                .thenReturn(List.of(RenterEntitiesBuilder.renterBuilder()));

        BDDMockito.when(authorRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AuthorEntitiesBuilder.authorBuilder()));

        BDDMockito.when(authorRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AuthorEntitiesBuilder.authorBuilder()));

        BDDMockito.when(authorRepositoryMock.save(ArgumentMatchers.any(Author.class)))
                .thenReturn(AuthorEntitiesBuilder.authorBuilder());

        BDDMockito.doNothing().when(authorRepositoryMock).delete(ArgumentMatchers.any(Author.class));
    }

    @Test
    @DisplayName("listAll: returns a list of all authors when successful")
    void listAll_ReturnsListOfAuthors_WhenSuccessful() {
        String expectedName = AuthorEntitiesBuilder.authorBuilder().getName();

        List<Author> authors = authorService.listAll();

        Assertions.assertThat(authors)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(authors.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException: returns author when successful")
    void findByIdOrThrowBadRequestException_ReturnsAuthor_WhenSuccessful() {
        Long expectedId = AuthorEntitiesBuilder.authorBuilder().getId();

        Author author = authorService.findByIdOrThrowBadRequestException(AuthorEntitiesBuilder.authorBuilder().getId());

        Assertions.assertThat(author).isNotNull();

        Assertions.assertThat(author.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName: returns a list of authors when successful")
    void findByName_ReturnsListOfAuthor_WhenSuccessful() {
        String expectedName = AuthorEntitiesBuilder.authorBuilder().getName();

        List<Author> authors = authorService.findByName("author");

        Assertions.assertThat(authors)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(authors.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName: throws an empty list when the author wasn't found")
    void findByName_ReturnsEmptyListOfAuthor_WhenAuthorIsNotFound() {
        BDDMockito.when(authorRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());
        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> authorService.findByName("author"))
                .withMessageContaining("Author not Found");
    }

    @Test
    @DisplayName("listAllBooksFromAuthor: returns the list of books from the specified author")
    void listAllBooksFromAuthor_ReturnsListOfBooksFromSpecifiedAuthor_WhenSuccessful() {
        Author author = AuthorEntitiesBuilder.authorBuilder();
        Book book = BookEntitiesBuilder.bookBuilder();
        author.setBooksMade(List.of(book));
        BDDMockito.when(authorRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(author));
        List<Book> booksFromAuthor = authorService.listAllBooksFromAuthor(
                ArgumentMatchers.anyLong());

        Assertions.assertThat(booksFromAuthor)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(booksFromAuthor.get(0).getName()).isEqualTo(book.getName());
    }

    @Test
    @DisplayName("save returns author when successful")
    void save_ReturnsAuthor_WhenSuccessful() {

        Author author = authorService.save(AuthorEntitiesBuilder.authorPostRequestBodyBuilder());
        Assertions.assertThat(author).isNotNull().isEqualTo(AuthorEntitiesBuilder.authorBuilder());
    }
    @Test
    @DisplayName("save returns author when successful")
    void save_ReturnsAuthor_WhenSuccessful_FirstAuthorRegistered() {
        BDDMockito.when(authorRepositoryMock.findAll())
                .thenReturn(Collections.emptyList());
        BDDMockito.when(renterRepositoryMock.findAll())
                .thenReturn(Collections.emptyList());

        Author author = authorService.save(AuthorEntitiesBuilder.authorPostRequestBodyBuilder());
        Assertions.assertThat(author).isNotNull().isEqualTo(AuthorEntitiesBuilder.authorBuilder());
    }

    @Test
    @DisplayName("Fails to save because of an equal to other Author's CPF.")
    void failsToSave_ThrowsBadRequestException_BecauseOfEqualToAnotherAuthorCpf() {
        AuthorPostRequestBody authorPostRequestBody = AuthorEntitiesBuilder.authorPostRequestBodyBuilder();
        Author author = authorService.save(AuthorEntitiesBuilder.authorPostRequestBodyBuilder());
        authorPostRequestBody.setCpf(author.getCpf());
        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> authorService.save(authorPostRequestBody))
                .withMessageContaining("the given author's CPF is already registered in the " +
                        "database. Verify the author of name " + AuthorEntitiesBuilder.authorBuilder().getName());
    }

    @Test
    @DisplayName("Fails to save because of an equal to other Renter's CPF.")
    void failsToSave_ThrowsBadRequestException_BecauseOfEqualToAnotherRenterCpf() {
        Renter renterMock = RenterEntitiesBuilder.renterBuilder();
        renterMock.setEmail("emailx@email.com");
        renterMock.setTelephone("999999999");
        BDDMockito.when(renterRepositoryMock.findAll())
                .thenReturn(List.of(renterMock));

        AuthorPostRequestBody authorPostRequestBody = AuthorEntitiesBuilder.authorPostRequestBodyBuilder();
        Renter renter = renterService.save(RenterEntitiesBuilder.renterPostRequestBodyBuilder());
        authorPostRequestBody.setCpf(RenterEntitiesBuilder.renterBuilder().getCpf());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> authorService.save(authorPostRequestBody))
                .withMessageContaining("the given author's CPF is already registered in the " +
                        "database. Verify the renter of name " + RenterEntitiesBuilder.renterPostRequestBodyBuilder().getName());
    }

    @Test
    @DisplayName("replace updates author when successful")
    void replace_UpdatesAuthor_WhenSuccessful() {

        Assertions.assertThatCode(() -> authorService.replace(AuthorEntitiesBuilder.authorPutRequestBodyBuilder()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete removes author when successful")
    void delete_RemovesAuthor_WhenSuccessful() {

        Assertions.assertThatCode(() -> authorService.delete(1))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("Fails to delete because the author still has registered books")
    void failsToDelete_ThrowBadRequestException_WhenAuthorStillHasRegisteredBooks() {
        Book book = BookEntitiesBuilder.bookBuilder();
        Author author = AuthorEntitiesBuilder.authorBuilder();
        author.setBooksMade(List.of(book));
        BDDMockito.when(authorRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(author));

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> authorService.delete(ArgumentMatchers.anyLong()))
                .withMessageContaining("The given author still has registered books!");
    }
}