package Mateus_Academia.Biblioteca_Virtual.repository;

import Mateus_Academia.Biblioteca_Virtual.entities.Renter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Renter Repository")
class RenterRepositoryTest {
    @Autowired
    private RenterRepository renterRepository;

    @Test
    @DisplayName("Creates and then saves a Renter when successful")
    public void saveRenterWhenSuccessful() {
        Renter renter = renterBuilder();
        Renter savedRenter = renterRepository.save(renter);
        Assertions.assertThat(savedRenter).isNotNull();
        Assertions.assertThat(savedRenter.getId()).isNotNull();
        Assertions.assertThat(savedRenter.getName()).isEqualTo(renter.getName());
    }
    @Test
    @DisplayName("saves and then updates a Renter when successful")
    public void updateRenterWhenSuccessful(){
        Renter renter = renterBuilder();
        Renter savedRenter = renterRepository.save(renter);
        savedRenter.setName("Renter_Updated");
        Renter renterUpdated = renterRepository.save(savedRenter);
        Assertions.assertThat(renterUpdated).isNotNull();
        Assertions.assertThat(renterUpdated.getId()).isNotNull();
        Assertions.assertThat(renterUpdated.getName()).isEqualTo(savedRenter.getName());
    }
    @Test
    @DisplayName("saves and then deletes a Renter when successful")
    public void deleteRenterWhenSuccessful(){
        Renter renter = renterBuilder();
        Renter savedRenter = renterRepository.save(renter);
        renterRepository.delete(savedRenter);
        Optional<Renter> optionalRenter = renterRepository.findById(savedRenter.getId());

        Assertions.assertThat(optionalRenter).isEmpty();
    }
    @Test
    @DisplayName("finds a Renter by name and then returns a list of Renters when successful")
    public void findRenterByNameWhenSuccessful(){
        Renter renter = renterBuilder();
        Renter savedRenter = renterRepository.save(renter);
        List<Renter> renters = renterRepository.findByName(renter.getName());

        Assertions.assertThat(renters)
                .isNotEmpty()
                .contains(savedRenter);
    }
    @Test
    @DisplayName("tries to find an Renter by name and then returns an empty list of Renters when unsuccessful")
    public void failsToFindRenterByName(){
        Renter renter = renterBuilder();
        renterRepository.save(renter);
        List<Renter> renters = renterRepository.findByName("Different_Name");

        Assertions.assertThat(renters).isEmpty();
    }
    @Test
    @DisplayName("Tries to save an Renter but throws ConstraintViolationException instead")
    public void failsToSaveRenter_ThrowConstraintViolationException_EmptyField(){
        Renter renter = new Renter();
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> renterRepository.save(renter))
                .withMessageContaining("The name cannot be empty!")
                .withMessageContaining("The telephone number cannot be empty!")
                .withMessageContaining("The email cannot be empty!")
                .withMessageContaining("The birth date cannot be empty!")
                .withMessageContaining("The cpf cannot be empty!");
    }

    @Test
    @DisplayName("Tries to save a Renter but fails because of invalid phone " +
            "number pattern")
    public void failsToSaveRenter_ThrowConstraintViolationException_InvalidPhoneFormat(){
        Renter renter = renterBuilder();
        renter.setTelephone("Invalid_Telephone");
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> renterRepository.save(renter))
                .withMessageContaining("The phone number should be composed of exactly 9 numbers!");
    }

    @Test
    @DisplayName("Tries to save a Renter but fails because of invalid email pattern")
    public void failsToSaveRenter_ThrowConstraintViolationException_InvalidEmailFormat(){
        Renter renter = renterBuilder();
        renter.setEmail("Invalid_Email");
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> renterRepository.save(renter))
                .withMessageContaining("The given Renter's email has an invalid format");
    }



    private Renter renterBuilder() {
        Date date = new Date();
        return Renter.builder()
                .name("Renter_Test")
                .cpf("00000000000")
                .birthDate(date)
                .email("email@email.com")
                .telephone("000000000")
                .rentsMade(null)
                .build();
    }
}