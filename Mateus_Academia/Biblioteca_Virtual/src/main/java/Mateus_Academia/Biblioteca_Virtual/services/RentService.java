package Mateus_Academia.Biblioteca_Virtual.services;

import Mateus_Academia.Biblioteca_Virtual.repository.BookRepository;
import Mateus_Academia.Biblioteca_Virtual.repository.RentRepository;
import Mateus_Academia.Biblioteca_Virtual.repository.RenterRepository;
import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.entities.Rent;
import Mateus_Academia.Biblioteca_Virtual.entities.Renter;
import Mateus_Academia.Biblioteca_Virtual.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentService {

    private final RentRepository rentRepository;
    private final RenterRepository renterRepository;
    private final RenterService renterService;
    private final BookService bookService;
    private final BookRepository bookRepository;

    public List<Rent> listAll() {
        return rentRepository.findAll();
    }

    public Rent findByIdOrThrowBadRequestException(long id) {
        return rentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Rent not Found"));
    }
@Transactional
    public Rent save(String renterName, List<String> bookNames) {
        String lastBook = "";
        Rent rent = new Rent();
        Renter renter = renterService.findByName(renterName).get(0);
        List<Book> books = new ArrayList<>();
    try {
        for (String bookName : bookNames) {
            lastBook = bookName;
            books.add(bookService.findByName(bookName).get(0));
        }
    }catch (Exception e){
        throw new BadRequestException("The registration of the given rent was not " +
                "possible because the Book '" + lastBook + "' was not found! Try again.");
    }
    for (Book book : books) {
        if(isBookAvailable(book)) {
            rent.rentBooks(book);
        }
        else{
            throw new BadRequestException("The book " + book.getName() +
                    " has already been rented! All books requested before" +
                    " this one were successfully rented");
        }
    }
        renter.assignRent(rent);
        rentRepository.save(rent);
        renterRepository.save(renter);
        return (rent);
    }

    public void delete(long id) {
        Rent rent = findByIdOrThrowBadRequestException(id);
        rent.setId(id);
        rent.setBooksRented(null);
        rentRepository.save(rent);
        rentRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public boolean isBookAvailable(Book book){
        boolean isAvailable = false;
        List<Rent> rents = rentRepository.findAll();
        if(!(rents.isEmpty())) {
            for (Rent rent : rents) {
                isAvailable = !(rent.getBooksRented().contains(book));
            }
        }
        else{
            isAvailable = true;
        }
        return isAvailable;
    }
    public List<Book> listAllAvailableBooksForRent() {
        List<Book> allBooks = bookRepository.findAll();
        List<Book> availableBooks = new ArrayList<>();
        for(Book book : allBooks){
            if(isBookAvailable(book)){
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }
    public List<Book> listAllRentedBooks() {
        List<Book> rentedBooks= new ArrayList<>();
        List<Rent> rents = rentRepository.findAll();
        for(Rent rent : rents){
            rentedBooks.addAll(rent.getBooksRented());
        }
        return rentedBooks;
    }
}
