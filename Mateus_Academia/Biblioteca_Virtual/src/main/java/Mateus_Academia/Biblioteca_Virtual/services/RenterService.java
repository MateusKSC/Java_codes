package Mateus_Academia.Biblioteca_Virtual.services;

import Mateus_Academia.Biblioteca_Virtual.repository.AuthorRepository;
import Mateus_Academia.Biblioteca_Virtual.repository.RenterRepository;
import Mateus_Academia.Biblioteca_Virtual.entities.Author;
import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.entities.Rent;
import Mateus_Academia.Biblioteca_Virtual.entities.Renter;
import Mateus_Academia.Biblioteca_Virtual.exceptions.BadRequestException;
import Mateus_Academia.Biblioteca_Virtual.mapper.RenterMapper;
import Mateus_Academia.Biblioteca_Virtual.requests.RenterPostRequestBody;
import Mateus_Academia.Biblioteca_Virtual.requests.RenterPutRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class RenterService {

    private final RenterRepository renterRepository;
    private final AuthorRepository authorRepository;

    public List<Renter> listAll() {
        return renterRepository.findAll();
    }
    public List<Renter> findByName(String name) {
        List<Renter> renters = renterRepository.findByName(name);
        if (renters.isEmpty()){
            throw new BadRequestException("Renter not Found");
        }
        return renters;
    }
    public Renter findByIdOrThrowBadRequestException(long renterId) {
        return renterRepository.findById(renterId)
                .orElseThrow(() -> new BadRequestException("The given Renter was not Found"));
    }
@Transactional
    public Renter save(RenterPostRequestBody renterPostRequestBody) {
        Renter renter = new Renter();
        if(isCpfUnique(renterPostRequestBody.getCpf()) && isEmailUnique(
                renterPostRequestBody.getEmail()) && isTelephoneUnique(
                renterPostRequestBody.getTelephone())){
            renter = renterRepository.save(RenterMapper.INSTANCE.toRenter(renterPostRequestBody));
        }
        return renter;
    }


    public void delete(long renterId) {
        Renter renter = findByIdOrThrowBadRequestException(renterId);
        if(renter.getRentsMade().isEmpty()) {
            renterRepository.delete(findByIdOrThrowBadRequestException(renterId));
            log.info("The given renter was successfully deleted");
        }
        else{
            throw new BadRequestException("The given renter still has pending rents!");
        }
    }
    public void replace(RenterPutRequestBody renterPutRequestBody) {
        Renter savedRenter = findByIdOrThrowBadRequestException(renterPutRequestBody.getId());
        Renter renter = RenterMapper.INSTANCE.toRenter(renterPutRequestBody);
        renter.setId(savedRenter.getId());
        renterRepository.save(renter);
    }

    public List<Book> listAllRentedBooksByRenter(long renterId) {
        Renter renter = findByIdOrThrowBadRequestException(renterId);
        Rent rent = renter.getRentsMade().get(0);
        List<Book> books = rent.getBooksRented();
        return books;
    }
    public boolean isCpfUnique(String cpf){
        boolean authorCpfUnique = false;
        boolean renterCpfUnique = false;
        List<Author> authors = authorRepository.findAll();
        List<Renter> renters = renterRepository.findAll();
        if(!(renters.isEmpty())) {
            for (Renter renter : renters) {
                if (renter.getCpf().equals(cpf)) {
                    throw new BadRequestException("the given renter's CPF is already registered in the " +
                            "database. Verify the renter of name " + renter.getName());
                }
                else{
                    renterCpfUnique = true;
                }
            }
        }
        else{
            renterCpfUnique = true;
        }
        if(!(authors.isEmpty())) {
            for (Author author : authors) {
                if (author.getCpf().equals(cpf)) {
                    throw new BadRequestException("the given renter's CPF is already registered in the " +
                            "database. Verify the author of name " + author.getName());
                }
                else{
                    authorCpfUnique = true;
                }
            }
        }
        else{
            authorCpfUnique = true;
        }
        return (authorCpfUnique && renterCpfUnique);
    }
    public boolean isEmailUnique(String email){
        boolean unique = false;
        List<Renter> renters = renterRepository.findAll();
        if(!(renters.isEmpty())) {
            for (Renter renter : renters) {
                if (!(renter.getEmail().equals(email))) {
                    unique = true;
                } else {
                    throw new BadRequestException("the given renter's email is already registered in the " +
                            "database. Verify the renter of name " + renter.getName());
                }
            }
        }
        else{
            unique = true;
        }
        return unique;
    }
    public boolean isTelephoneUnique(String telephone){
        boolean unique = false;
        List<Renter> renters = renterRepository.findAll();
        if(!(renters.isEmpty())) {
            for (Renter renter : renters) {
                if (!(renter.getTelephone().equals(telephone))) {
                    unique = true;
                } else {
                    throw new BadRequestException("the given renter's telephone is already registered in the " +
                            "database. Verify the renter of name " + renter.getName());
                }
            }
        }
        else{
            unique = true;
        }
        return unique;
    }
}
