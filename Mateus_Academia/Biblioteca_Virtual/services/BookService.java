package Mateus_Academia.Biblioteca_Virtual.services;

import Mateus_Academia.Biblioteca_Virtual.AuthorRepository;
import Mateus_Academia.Biblioteca_Virtual.BookRepository;
import Mateus_Academia.Biblioteca_Virtual.RentRepository;
import Mateus_Academia.Biblioteca_Virtual.entities.Author;
import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.entities.Rent;
import Mateus_Academia.Biblioteca_Virtual.entities.Renter;
import Mateus_Academia.Biblioteca_Virtual.exceptions.BadRequestException;
import Mateus_Academia.Biblioteca_Virtual.mapper.AuthorMapper;
import Mateus_Academia.Biblioteca_Virtual.mapper.BookMapper;
import Mateus_Academia.Biblioteca_Virtual.requests.BookPostRequestBody;
import Mateus_Academia.Biblioteca_Virtual.requests.BookPutRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    private final AuthorRepository authorRepository;

    @Autowired
    private final RentRepository rentRepository;

    private final AuthorService authorService;

    public List<Book> listAll() {
        return bookRepository.findAll();
    }
    public List<Book> findByName(String name) {
        List<Book> books = bookRepository.findByName(name);
        if (books.isEmpty()){
            throw new BadRequestException("Book not Found");
        }
        return books;
    }
    public Book findByIdOrThrowBadRequestException(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Book not Found"));
    }
@Transactional
    public Book save(BookPostRequestBody bookPostRequestBody, List<String> authorNames) {
        String lastAuthor = "";
        List<Author> authors = new ArrayList<>();
        Book book = new Book();
        book.setName(BookMapper.INSTANCE.toBook(bookPostRequestBody).getName());
        book.setIsbn(BookMapper.INSTANCE.toBook(bookPostRequestBody).getIsbn());
        book.setPublicationDate(BookMapper.INSTANCE.toBook(bookPostRequestBody).getPublicationDate());
        try {
            for (String authorName : authorNames) {
                lastAuthor = authorName;
                authors.add(authorService.findByName(authorName).get(0));
            }
        }catch (Exception e){
            throw new BadRequestException("The registration of the given book was not " +
                    "possible because the Author '" + lastAuthor + "' was not found! Try again.");
        }
        for (Author author : authors) {
            book.assignBook(author);
        }

        if(isIsbnUnique(bookPostRequestBody.getIsbn())){
            bookRepository.save(book);
        }

        return (book);
    }


    public void delete(long bookId) {
            Book book = findByIdOrThrowBadRequestException(bookId);
            if(isBookAvailable(book)) {
                bookRepository.delete(findByIdOrThrowBadRequestException(bookId));
            }
            log.info("The given book was successfully deleted");
    }
    public boolean isBookAvailable(Book book){
        boolean isAvailable = false;
        List<Rent> rents = rentRepository.findAll();
        for(Rent rent : rents){
            if(!(rent.getBooksRented().contains(book))){
                isAvailable = true;
            }else {
                throw new BadRequestException("The book " + book.getName() +
                        " is currently rented and therefore cannot be deleted.");
            }
        }
        return isAvailable;
    }
    public void replace(BookPutRequestBody bookPutRequestBody) {
        Book savedBook = findByIdOrThrowBadRequestException(bookPutRequestBody.getId());
        Book book = BookMapper.INSTANCE.toBook(bookPutRequestBody);
        book.setId(savedBook.getId());
        book.setAuthors(savedBook.getAuthors());
        bookRepository.save(book);
    }
    public boolean isIsbnUnique(int isbn){
        boolean unique = false;
        List<Book> books = bookRepository.findAll();
        if(!(books.isEmpty())) {
            for (Book book : books) {
                if (book.getIsbn() != isbn) {
                    unique = true;
                } else {
                    throw new BadRequestException("the given book's isbn is already registered in the " +
                            "database. Verify the book of name " + book.getName());
                }
            }
        }
        else{
            unique = true;
        }
        return unique;
    }


}
