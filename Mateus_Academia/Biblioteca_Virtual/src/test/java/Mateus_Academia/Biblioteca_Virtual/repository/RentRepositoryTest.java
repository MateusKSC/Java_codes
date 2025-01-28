package Mateus_Academia.Biblioteca_Virtual.repository;

import Mateus_Academia.Biblioteca_Virtual.Utils.RentEntitiesBuilder;
import Mateus_Academia.Biblioteca_Virtual.entities.Rent;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Rent Repository")
@Log4j2
class RentRepositoryTest {
    @Autowired
    private RentRepository rentRepository;

    @Test
    @DisplayName("Creates and then saves a Rent when successful")
    public void saveRentWhenSuccessful() {
        Rent rent = new Rent();
        Rent savedRent = rentRepository.save(rent);
        Assertions.assertThat(savedRent).isNotNull();
        Assertions.assertThat(savedRent.getId()).isNotNull();
        Assertions.assertThat(savedRent.getDateOfRent()).isEqualTo(
                rent.getDateOfRent());
    }

    @Test
    @DisplayName("saves and then updates a Rent when successful")
    public void updateRentWhenSuccessful(){
        Rent rent = RentEntitiesBuilder.rentBuilder();
        Rent toBeUpdatedRent = RentEntitiesBuilder.rentBuilder();
        Date differentDate = new Date(System.currentTimeMillis() + 86400000 * 2);
        rentRepository.save(rent);
        toBeUpdatedRent.setId(rent.getId());
        toBeUpdatedRent.setDateOfRent(differentDate);
        Rent updatedRent = rentRepository.save(toBeUpdatedRent);

        Assertions.assertThat(updatedRent).isNotNull();
        Assertions.assertThat(updatedRent.getId()).isNotNull();
        Assertions.assertThat(updatedRent.getId()).isEqualTo(rent.getId());
        Assertions.assertThat(updatedRent.getDateOfRent()).isNotEqualTo(rent.getDateOfRent());

    }

    @Test
    @DisplayName("saves and then deletes a Rent when successful")
    public void deleteRentWhenSuccessful(){
        Rent rent = RentEntitiesBuilder.rentBuilder();
        Rent savedRent = rentRepository.save(rent);
        rentRepository.delete(savedRent);
        Optional<Rent> optionalRent = rentRepository.findById(savedRent.getId());

        Assertions.assertThat(optionalRent).isEmpty();
    }
}