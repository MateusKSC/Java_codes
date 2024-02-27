package Mateus_Academia.Biblioteca_Virtual.controllers;

import Mateus_Academia.Biblioteca_Virtual.Utils.BookEntitiesBuilder;
import Mateus_Academia.Biblioteca_Virtual.Utils.RenterEntitiesBuilder;
import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.entities.Renter;
import Mateus_Academia.Biblioteca_Virtual.requests.RenterPostRequestBody;
import Mateus_Academia.Biblioteca_Virtual.requests.RenterPutRequestBody;
import Mateus_Academia.Biblioteca_Virtual.services.RenterService;
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
class RenterControllerTest {
    @InjectMocks
    private RenterController renterController;

    @Mock
    private DateUtil dateUtil;

    @Mock
    private RenterService renterServiceMock;


    @BeforeEach
    void setUp() {
        BDDMockito.when(renterServiceMock.listAll())
                .thenReturn(List.of(RenterEntitiesBuilder.renterBuilder()));

        BDDMockito.when(renterServiceMock.listAllRentedBooksByRenter(ArgumentMatchers.anyLong()))
                .thenReturn(List.of(BookEntitiesBuilder.bookBuilder()));

        BDDMockito.when(renterServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(RenterEntitiesBuilder.renterBuilder());

        BDDMockito.when(renterServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(RenterEntitiesBuilder.renterBuilder()));

        BDDMockito.when(renterServiceMock.save(ArgumentMatchers.any(RenterPostRequestBody.class)))
                .thenReturn(RenterEntitiesBuilder.renterBuilder());

        BDDMockito.doNothing().when(renterServiceMock).replace(ArgumentMatchers.any(RenterPutRequestBody.class));

        BDDMockito.doNothing().when(renterServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("list: returns a list of all renters when successful")
    void list_ReturnsListOfRenters_WhenSuccessful() {
        String expectedName = RenterEntitiesBuilder.renterBuilder().getName();

        List<Renter> renters = renterController.list().getBody();

        Assertions.assertThat(renters)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(renters.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAllAuthors: returns a list of Authors when successful")
    void listAllRentedBooksByRenter_ReturnsListOfAuthors_WhenSuccessful() {
        ResponseEntity<List<Book>> books = renterController.listAllRentedBooksByRenter(ArgumentMatchers.anyLong());
        Assertions.assertThat(books.getBody())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(books.getBody().get(0).getName()).isEqualTo(
                BookEntitiesBuilder.bookBuilder().getName());
    }

    @Test
    @DisplayName("findById: returns renter when successful")
    void findById_ReturnsRenter_WhenSuccessful() {
        Long expectedId = RenterEntitiesBuilder.renterBuilder().getId();

        Renter renter = renterController.findById(RenterEntitiesBuilder.renterBuilder().getId()).getBody();

        Assertions.assertThat(renter).isNotNull();

        Assertions.assertThat(renter.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName: returns a list of renters when successful")
    void findByName_ReturnsListOfRenter_WhenSuccessful() {
        String expectedName = RenterEntitiesBuilder.renterBuilder().getName();

        List<Renter> renters = renterController.findByName("renter").getBody();

        Assertions.assertThat(renters)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(renters.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName: returns an empty list when the renter wasn't found")
    void findByName_ReturnsEmptyListOfRenter_WhenRenterIsNotFound() {
        BDDMockito.when(renterServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Renter> renters = renterController.findByName("renter").getBody();

        Assertions.assertThat(renters)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns renter when successful")
    void save_ReturnsRenter_WhenSuccessful() {

        Renter renter = renterController.save(RenterEntitiesBuilder.renterPostRequestBodyBuilder()).getBody();

        Assertions.assertThat(renter).isNotNull().isEqualTo(RenterEntitiesBuilder.renterBuilder());

    }

    @Test
    @DisplayName("replace updates renter when successful")
    void replace_UpdatesRenter_WhenSuccessful() {

        Assertions.assertThatCode(() -> renterController.replace(RenterEntitiesBuilder.renterPutRequestBodyBuilder()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = renterController.replace(RenterEntitiesBuilder.renterPutRequestBodyBuilder());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes renter when successful")
    void delete_RemovesRenter_WhenSuccessful() {

        Assertions.assertThatCode(() -> renterController.delete(1))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = renterController.delete(1);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}