package Mateus_Academia.Biblioteca_Virtual.controllers;

import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.entities.Rent;
import Mateus_Academia.Biblioteca_Virtual.services.RentService;
import Mateus_Academia.Biblioteca_Virtual.utilities.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("rents")
@Log4j2
@RequiredArgsConstructor
public class RentController {

    private final DateUtil dateUtil;
    private final RentService rentService;


    @GetMapping
    public ResponseEntity<List<Rent>> list() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(rentService.listAll());
    }

    @GetMapping(path = "/{rentId}")
    public ResponseEntity<Rent> findById(@PathVariable long rentId) {
        return ResponseEntity.ok(rentService.findByIdOrThrowBadRequestException(rentId));
    }

    @GetMapping(path = "/rentedBooks")
    public ResponseEntity<List<Book>> listAllRentedBooks() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(rentService.listAllRentedBooks());
    }

    @GetMapping(path = "/availableBooks")
    public ResponseEntity listAllAvailableBooksForRent() {
        return ResponseEntity.ok(rentService.listAllAvailableBooksForRent());
    }

    @PostMapping(path = "/{renterName}/{bookNames}")
    public ResponseEntity<Rent> save(@PathVariable String renterName, @PathVariable List<String> bookNames) {
        return new ResponseEntity<>(rentService.save(renterName, bookNames), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{rentId}")
    public ResponseEntity<Void> replace(@PathVariable long rentId) {
        rentService.replace(rentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{rentId}")
    public ResponseEntity<Void> delete(@PathVariable long rentId) {
        rentService.delete(rentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
