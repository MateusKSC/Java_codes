package Mateus_Academia.Biblioteca_Virtual.services;

import Mateus_Academia.Biblioteca_Virtual.repository.RenterRepository;
import Mateus_Academia.Biblioteca_Virtual.entities.Author;
import Mateus_Academia.Biblioteca_Virtual.repository.AuthorRepository;
import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.entities.Renter;
import Mateus_Academia.Biblioteca_Virtual.requests.AuthorPostRequestBody;
import lombok.RequiredArgsConstructor;
import Mateus_Academia.Biblioteca_Virtual.exceptions.BadRequestException;
import Mateus_Academia.Biblioteca_Virtual.mapper.AuthorMapper;
import Mateus_Academia.Biblioteca_Virtual.requests.AuthorPutRequestBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final RenterRepository renterRepository;

    public List<Author> listAll() {
        return authorRepository.findAll();
    }

    public List<Author> findByName(String name) {
        List<Author> authors = authorRepository.findByName(name);
        if (authors.isEmpty()){
            throw new BadRequestException("Author not Found");
        }
        return authors;
    }

    public Author findByIdOrThrowBadRequestException(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Author not Found"));
    }
@Transactional
    public Author save(AuthorPostRequestBody authorPostRequestBody) {
        Author author = new Author();
        if(isCpfUnique(authorPostRequestBody.getCpf())){
            author = authorRepository.save(AuthorMapper.INSTANCE.toAuthor(authorPostRequestBody));
        }
        return author;
    }

    public void delete(long authorId) {
            Author author = findByIdOrThrowBadRequestException(authorId);
            if (author.getBooksMade().isEmpty()) {
                authorRepository.delete(findByIdOrThrowBadRequestException(authorId));
                log.info("The given author was successfully deleted");
            } else {
                throw new BadRequestException("The given author still has registered books!");
            }
    }

    public void replace(AuthorPutRequestBody authorPutRequestBody) {
        Author savedAuthor = findByIdOrThrowBadRequestException(authorPutRequestBody.getId());
        Author author = AuthorMapper.INSTANCE.toAuthor(authorPutRequestBody);
        author.setId(savedAuthor.getId());
        authorRepository.save(author);
    }

    public boolean isCpfUnique(String cpf){
        List<Author> authors = authorRepository.findAll();
        List<Renter> renters = renterRepository.findAll();
        if(!(authors.isEmpty())) {
            for (Author author : authors) {
                if (author.getCpf().equals(cpf)) {
                    throw new BadRequestException("the given author's CPF is already registered in the " +
                            "database. Verify the author of name " + author.getName());
                }
            }
        }
        else{
            return true;
        }
        if(!(renters.isEmpty())) {
            for (Renter renter : renters) {
                if (renter.getCpf().equals(cpf)) {
                    throw new BadRequestException("the given author's CPF is already registered in the " +
                            "database. Verify the renter of name " + renter.getName());
                }
            }
        }
        else{
            return true;
        }
        return true;
    }

    public List<Book> listAllBooks(long authorId) {
        Author author = findByIdOrThrowBadRequestException(authorId);
        List<Book> books = author.getBooksMade();
        return books;
    }
}
