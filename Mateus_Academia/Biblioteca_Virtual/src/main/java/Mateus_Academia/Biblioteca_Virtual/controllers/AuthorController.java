package Mateus_Academia.Biblioteca_Virtual.controllers;

import Mateus_Academia.Biblioteca_Virtual.requests.AuthorPostRequestBody;
import Mateus_Academia.Biblioteca_Virtual.requests.AuthorPutRequestBody;
import Mateus_Academia.Biblioteca_Virtual.services.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import Mateus_Academia.Biblioteca_Virtual.entities.*;
import Mateus_Academia.Biblioteca_Virtual.utilities.DateUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("authors")
@Log4j2
@RequiredArgsConstructor
public class AuthorController {

    private final DateUtil dateUtil;
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> list() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(authorService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Author> findById(@PathVariable long id) {
        return ResponseEntity.ok(authorService.findByIdOrThrowBadRequestException(id));
    }
    @GetMapping(path = "/find")
    public ResponseEntity<List<Author>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(authorService.findByName(name));
    }
    @GetMapping(path = "/{authorId}/books")
    public ResponseEntity<List<Book>> listAllBooksFromAuthor(@PathVariable long authorId) {
        return ResponseEntity.ok(authorService.listAllBooksFromAuthor(authorId));
    }

    @PostMapping
    public ResponseEntity<Author> save(@RequestBody @Valid AuthorPostRequestBody authorPostRequestBody) {
        return new ResponseEntity<>(authorService.save(authorPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{authorId}")
    public ResponseEntity<Void> delete(@PathVariable long authorId) {
        authorService.delete(authorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid  AuthorPutRequestBody authorPutRequestBody) {
        authorService.replace(authorPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
