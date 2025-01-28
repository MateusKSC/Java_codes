package Mateus_Academia.Biblioteca_Virtual;

import Mateus_Academia.Biblioteca_Virtual.entities.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RenterRepository extends JpaRepository<Renter, Long> {
   List<Renter> findByName(String name);

}
