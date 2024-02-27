package Mateus_Academia.Biblioteca_Virtual.controllers;

import Mateus_Academia.Biblioteca_Virtual.entities.Author;
import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.requests.BookPostRequestBody;
import Mateus_Academia.Biblioteca_Virtual.requests.BookPutRequestBody;
import Mateus_Academia.Biblioteca_Virtual.services.BookService;
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
@RequestMapping("books")
@Log4j2
@RequiredArgsConstructor
public class BookController {

    private final DateUtil dateUtil;
    private final BookService bookService;


    @GetMapping
    public ResponseEntity<List<Book>> list() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(bookService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> findById(@PathVariable long id) {
        return ResponseEntity.ok(bookService.findByIdOrThrowBadRequestException(id));
    }
    @GetMapping(path = "/find")
    public ResponseEntity<List<Book>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(bookService.findByName(name));
    }
    @GetMapping(path = "/{bookId}/authors")
    public ResponseEntity<List<Author>> listAllAuthors(@PathVariable long bookId) {
        return ResponseEntity.ok(bookService.listAllAuthors(bookId));
    }

    @PostMapping(path = "/{authorNames}")
    public ResponseEntity<Book> save(
            @RequestBody @Valid BookPostRequestBody bookPostRequestBody, @PathVariable List<String> authorNames) {
        return new ResponseEntity<>(bookService.save(bookPostRequestBody,authorNames), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity<Void> delete(@PathVariable long bookId) {
        bookService.delete(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid BookPutRequestBody bookPutRequestBody) {
        bookService.replace(bookPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
