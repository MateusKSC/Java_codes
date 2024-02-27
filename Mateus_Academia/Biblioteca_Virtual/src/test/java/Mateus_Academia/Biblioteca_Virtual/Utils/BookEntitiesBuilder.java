package Mateus_Academia.Biblioteca_Virtual.Utils;

import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.requests.BookPostRequestBody;
import Mateus_Academia.Biblioteca_Virtual.requests.BookPutRequestBody;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

public class BookEntitiesBuilder {
    public static  Book bookBuilder(){
        return Book.builder()
                .id(1L)
                .name("Book_Test")
                .publicationDate(Date.from(Instant.parse("2024-02-26T17:44:56.413Z")))
                .isbn("9780596520687")
                .authors(Collections.emptyList())
                .build();
    }
    public static  BookPostRequestBody bookPostRequestBodyBuilder(){
        return BookPostRequestBody.builder()
                .name("Book_Test")
                .publicationDate(Date.from(Instant.parse("2024-02-26T17:44:56.413Z")))
                .isbn("9780596520687")
                .build();
    }
    public static  BookPutRequestBody bookPutRequestBodyBuilder(){
        return BookPutRequestBody.builder()
                .id(1L)
                .name("Book_Test")
                .publicationDate(Date.from(Instant.parse("2024-02-26T17:44:56.413Z")))
                .isbn("9780596520687")
                .build();
    }
}
