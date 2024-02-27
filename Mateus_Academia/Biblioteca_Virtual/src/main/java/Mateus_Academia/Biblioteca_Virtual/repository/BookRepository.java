package Mateus_Academia.Biblioteca_Virtual.repository;

import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

   List<Book> findByName(String name);

}
