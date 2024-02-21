package Mateus_Academia.Biblioteca_Virtual;

import Mateus_Academia.Biblioteca_Virtual.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
   List<Author> findByName(String name);

}
