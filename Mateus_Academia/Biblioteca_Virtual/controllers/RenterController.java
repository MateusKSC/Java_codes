package Mateus_Academia.Biblioteca_Virtual.controllers;

import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.entities.Renter;
import Mateus_Academia.Biblioteca_Virtual.requests.RenterPostRequestBody;
import Mateus_Academia.Biblioteca_Virtual.requests.RenterPutRequestBody;
import Mateus_Academia.Biblioteca_Virtual.services.RenterService;
import Mateus_Academia.Biblioteca_Virtual.utilities.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("renters")
@Log4j2
@RequiredArgsConstructor
public class RenterController {

    private final DateUtil dateUtil;
    private final RenterService renterService;

    @GetMapping
    public ResponseEntity<List<Renter>> list() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(renterService.listAll());
    }

    @GetMapping(path = "/{renterId}")
    public ResponseEntity<Renter> findById(@PathVariable long renterId) {
        return ResponseEntity.ok(renterService.findByIdOrThrowBadRequestException(renterId));
    }
    @GetMapping(path = "/find")
    public ResponseEntity<List<Renter>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(renterService.findByName(name));
    }
    @GetMapping(path = "/{renterId}/books")
    public ResponseEntity<List<Book>> listAllRentedBooksByRenter(@PathVariable long renterId) {
        return ResponseEntity.ok(renterService.listAllRentedBooksByRenter(renterId));
    }

    @PostMapping
    public ResponseEntity<Renter> save(@RequestBody @Valid RenterPostRequestBody renterPostRequestBody) {
        return new ResponseEntity<>(renterService.save(renterPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{renterId}")
    public ResponseEntity<Void> delete(@PathVariable long renterId) {
        renterService.delete(renterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid  RenterPutRequestBody renterPutRequestBody) {
        renterService.replace(renterPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
