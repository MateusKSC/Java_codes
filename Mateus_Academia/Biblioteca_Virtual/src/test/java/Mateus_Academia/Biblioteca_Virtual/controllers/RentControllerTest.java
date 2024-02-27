package Mateus_Academia.Biblioteca_Virtual.controllers;

import Mateus_Academia.Biblioteca_Virtual.Utils.BookEntitiesBuilder;
import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.entities.Rent;
import Mateus_Academia.Biblioteca_Virtual.services.RentService;
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

import java.util.Date;
import java.util.List;

import static Mateus_Academia.Biblioteca_Virtual.Utils.RentEntitiesBuilder.rentBuilder;

@ExtendWith(SpringExtension.class)
class RentControllerTest {
    @InjectMocks
    private RentController rentController;
    @Mock
    private DateUtil dateUtil;
    @Mock
    private RentService rentServiceMock;

    @BeforeEach
    void setUp(){
        BDDMockito.when(rentServiceMock.listAll())
                .thenReturn(List.of(rentBuilder()));

        BDDMockito.when(rentServiceMock.listAllAvailableBooksForRent())
                .thenReturn(List.of(BookEntitiesBuilder.bookBuilder()));

        BDDMockito.when(rentServiceMock.listAllRentedBooks())
                .thenReturn(List.of(BookEntitiesBuilder.bookBuilder()));

        BDDMockito.when(rentServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(rentBuilder());

        BDDMockito.when(rentServiceMock.save(ArgumentMatchers.any(),ArgumentMatchers.any()))
                .thenReturn(rentBuilder());
        BDDMockito.doNothing().when(rentServiceMock).replace(ArgumentMatchers.anyLong());

        BDDMockito.doNothing().when(rentServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("list: returns a list of all rents when successful")
    void list_ReturnsListOfRents_WhenSuccessful(){
        Date expectedDateOfRent = rentBuilder().getDateOfRent();

        List<Rent> rents = rentController.list().getBody();

        Assertions.assertThat(rents)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(rents.get(0).getDateOfRent()).isEqualTo(expectedDateOfRent);
    }
    @Test
    @DisplayName("listAllAvailableBooksForRent: returns a list of books successful")
    void listAllAvailableBooksForRent_ReturnsListOfBooks_WhenSuccessful(){
        ResponseEntity<List<Book>> books = rentController.listAllAvailableBooksForRent();
        Assertions.assertThat(books.getBody())
                        .isNotNull()
                        .isNotEmpty()
                        .hasSize(1);

        Assertions.assertThat(books.getBody().get(0).getName()).isEqualTo(
                BookEntitiesBuilder.bookBuilder().getName());
    }
    @Test
    @DisplayName("listAllRentedBooks: returns a list of books successful")
    void listAllRentedBooks_ReturnsListOfBooks_WhenSuccessful(){
        ResponseEntity<List<Book>> books = rentController.listAllRentedBooks();
        Assertions.assertThat(books.getBody())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(books.getBody().get(0).getName()).isEqualTo(
                BookEntitiesBuilder.bookBuilder().getName());
    }

    @Test
    @DisplayName("findById: returns rent when successful")
    void findById_ReturnsRent_WhenSuccessful(){
        Long expectedId = rentBuilder().getId();

        Rent rent = rentController.findById(rentBuilder().getId()).getBody();

        Assertions.assertThat(rent).isNotNull();

        Assertions.assertThat(rent.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save returns rent when successful")
    void save_ReturnsRent_WhenSuccessful(){
        Rent rent = rentController.save(ArgumentMatchers.any(),ArgumentMatchers.any()).getBody();

        Assertions.assertThat(rent).isNotNull().isEqualTo(rentBuilder());
    }

    @Test
    @DisplayName("replace updates rent when successful")
    void replace_UpdatesRent_WhenSuccessful(){

        Assertions.assertThatCode(() ->rentController.replace(rentBuilder().getId()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = rentController.replace(rentBuilder().getId());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes rent when successful")
    void delete_RemovesRent_WhenSuccessful(){

        Assertions.assertThatCode(() ->rentController.delete(1))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = rentController.delete(1);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}