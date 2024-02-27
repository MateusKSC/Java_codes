package Mateus_Academia.Biblioteca_Virtual.repository;

import Mateus_Academia.Biblioteca_Virtual.entities.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {

}
