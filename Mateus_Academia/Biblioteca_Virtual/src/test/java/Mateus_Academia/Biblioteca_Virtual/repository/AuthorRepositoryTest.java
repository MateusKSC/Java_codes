package Mateus_Academia.Biblioteca_Virtual.repository;

import Mateus_Academia.Biblioteca_Virtual.entities.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Author Repository")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Creates and then saves an Author when successful")
    public void saveAuthorWhenSuccessful(){
        Author author = authorBuilder();
        Author savedAuthor = authorRepository.save(author);
        Assertions.assertThat(savedAuthor).isNotNull();
        Assertions.assertThat(savedAuthor.getId()).isNotNull();
        Assertions.assertThat(savedAuthor.getName()).isEqualTo(author.getName());
    }
    @Test
    @DisplayName("saves and then updates an Author when successful")
    public void updateAuthorWhenSuccessful(){
        Author author = authorBuilder();
        Author savedAuthor = authorRepository.save(author);
        savedAuthor.setName("Author_Updated");
        Author authorUpdated = authorRepository.save(savedAuthor);
        Assertions.assertThat(authorUpdated).isNotNull();
        Assertions.assertThat(authorUpdated.getId()).isNotNull();
        Assertions.assertThat(authorUpdated.getName()).isEqualTo(savedAuthor.getName());
    }
    @Test
    @DisplayName("saves and then deletes an Author when successful")
    public void deleteAuthorWhenSuccessful(){
        Author author = authorBuilder();
        Author savedAuthor = authorRepository.save(author);
        authorRepository.delete(savedAuthor);
        Optional<Author> optionalAuthor = authorRepository.findById(savedAuthor.getId());

        Assertions.assertThat(optionalAuthor).isEmpty();
    }
    @Test
    @DisplayName("finds an Author by name and then returns a list of Authors when successful")
    public void findAuthorByNameWhenSuccessful(){
        Author author = authorBuilder();
        Author savedAuthor = authorRepository.save(author);
        List<Author> authors = authorRepository.findByName(author.getName());

        Assertions.assertThat(authors)
                .isNotEmpty()
                .contains(savedAuthor);
    }
    @Test
    @DisplayName("tries to find an Author by name and then returns an empty list of Authors when unsuccessful")
    public void failsToFindAuthorByName(){
        Author author = authorBuilder();
        authorRepository.save(author);
        List<Author> authors = authorRepository.findByName("Different_Name");

        Assertions.assertThat(authors).isEmpty();
    }
    @Test
    @DisplayName("Tries to save an Author but throws ConstraintViolationException instead")
    public void failsToSaveAuthor_ThrowConstraintViolationException(){
        Author author = new Author();
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> authorRepository.save(author))
                .withMessageContaining("The name cannot be empty")
                .withMessageContaining("The birth year needs to be informed and must be greater than 0")
                .withMessageContaining("The cpf cannot be empty");
    }
    private Author authorBuilder(){
        return Author.builder()
                .name("Author_Test")
                .cpf("00000000000")
                .birthYear(2000)
                .booksMade(null)
                .build();
    }

}