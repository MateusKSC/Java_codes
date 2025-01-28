package Mateus_Academia.Biblioteca_Virtual.Client;

import Mateus_Academia.Biblioteca_Virtual.entities.Author;
import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.entities.Renter;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Log4j2
public class Client {
    public static void main(String[] args) {
        int option = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Welcome to the library!!
                1: Register an Author;
                2: Register a Book;
                3: Register a Renter;          
                4: Delete an Author;          
                5: Delete a Book;          
                6: Delete a Renter;          
                4: Delete a Book;          
                4: Delete a Book;          
                5: Find an Author By Id;          
                6: Find an Author By Name;          
                7: Find a Book By Id;          
                8: Find a Book By Name;          
                8: List All of the Books made by an Author;          
                9: List All Rented Books From a Renter;          
                10: List All Books Available for Rent;          
                11: List All Rented Books;          
                12: Leave.
                """);
        option = scanner.nextInt();
        switch (option){
            case 1:
                registerAuthor();
                break;
            case 2:
                registerBook();
                break;
            case 3:
                registerRenter();
                break;
            case 4:
                deleteAuthor();
                break;
            case 5:
                deleteBook();
                break;
            case 6:
                deleteRenter();
                break;
            case 7:
                findAuthorById();
                break;
            case 8:
                findAuthorByName();
            break;
            case 9:
                findBookById();
                break;
            case 82:
                findBookByName();
                break;
        }
    }
    public static void registerAuthor(){
        String authorName = "";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Tell me the name of the author");
        authorName = scanner.nextLine();
        Author author = Author.builder().name(authorName).build();
        ResponseEntity<Author> authorSaved = new RestTemplate().exchange("http://localhost:8080/authors/",
                HttpMethod.POST,
                new HttpEntity<>(author, createJsonHeader()),
                Author.class);
        log.info("saved Author {}", authorSaved);
    }
    public static void deleteAuthor(){
        String bookName = "";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Tell me the name of the book that will be deleted");
        bookName = scanner.nextLine();
        ResponseEntity<Void> authorDeleted = new RestTemplate().exchange("http://localhost:8080/authors/" +
                        bookName,
                HttpMethod.DELETE,
                null,
                Void.class);
    }
    public static void registerBook(){
        String bookName = "";
        String deliveryAuthorNames = "";
        String userString = "";
        List<String> authorNames = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Tell me the name of the book");
        bookName = scanner.nextLine();
        Book book = new Book();
        book.setName(bookName);
        while(!(authorNames.contains("stop"))) {
            System.out.println("Tell me the at least one of the names of each of the the book's authors");
            userString = scanner.nextLine();
            if (userString.equals("stop")) {
                break;
            }
            else {
                authorNames.add(userString);
            }
        }
        if(!(authorNames.isEmpty())){
            deliveryAuthorNames = String.join(",", authorNames);
            Book object = new RestTemplate().postForObject("http://localhost:8080/books/{authorNames}", book, Book.class, deliveryAuthorNames);
            log.info("saved book {}", object);
        }
        else {
            System.out.println("You have to inform th system at least one author name for each book" +
                    " registering attempt!");
        }
    }
    public static void deleteBook(){
        String bookName = "";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Tell me the name of the book that will be deleted");
        bookName = scanner.nextLine();
        ResponseEntity<Void> authorDeleted = new RestTemplate().exchange("http://localhost:8080/books/" +
                        bookName,
                HttpMethod.DELETE,
                null,
                Void.class);
    }
    public static void registerRenter(){
        String renterName = "";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Tell me the name of the renter");
        renterName = scanner.nextLine();
        Renter renter = Renter.builder().name(renterName).build();
        ResponseEntity<Renter> renterSaved = new RestTemplate().exchange("http://localhost:8080/renters/",
                HttpMethod.POST,
                new HttpEntity<>(renter, createJsonHeader()),
                Renter.class);
        log.info("saved renter {}", renterSaved);
    }
    public static void deleteRenter(){
        String renterName = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tell me the name of the renter that will be deleted");
        renterName = scanner.nextLine();
        ResponseEntity<Void> renterDeleted = new RestTemplate().exchange("http://localhost:8080/renters/" +
                        renterName,
                HttpMethod.DELETE,
                null,
                Void.class);
        log.info(renterDeleted);
    }
    public static void findAuthorById(){
        Long authorId;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tell me the Id of the Author you wish to search.");
        authorId = scanner.nextLong();
        ResponseEntity<Author> entity = new RestTemplate().getForEntity("http" +
                "://localhost:8080/authors/{authorId}", Author.class,authorId);
        log.info(entity);
    }
    public static void findAuthorByName(){
        String authorName = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tell me the name of the author you wish to search.");
        authorName = scanner.nextLine();
        List<Author> authors = new RestTemplate().getForObject("http://localhost:8080/authors/" +
                "find?name={authorName}", List.class,authorName);
        log.info(authors);
    }
    public static void findBookById(){
        Long bookId;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tell me the Id of the book you wish to search.");
        bookId = scanner.nextLong();
        ResponseEntity<Book> entity = new RestTemplate().getForEntity("http" +
                "://localhost:8080/books/{bookId}", Book.class,bookId);
        log.info(entity);
    }
    public static void findBookByName(){
        String bookName = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tell me the name of the book you wish to search.");
        bookName = scanner.nextLine();
        List<Author> books = new RestTemplate().getForObject("http://localhost:8080/books/find?name=" +
                "{bookName}", List.class,bookName);
        log.info(books);
    }
    public void firstFunc(){
        ResponseEntity<Author> entity = new RestTemplate().getForEntity("http://localhost:8080/authors/{id}", Author.class, 2);
        log.info(entity);

        Author object = new RestTemplate().getForObject("http://localhost:8080/authors/{id}", Author.class, 2);

        log.info(object);

        Author[] animes = new RestTemplate().getForObject("http://localhost:8080/authors/all", Author[].class);

        log.info(Arrays.toString(animes));
        ResponseEntity<List<Author>> exchange = new RestTemplate().exchange("http://localhost:8080/authors/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Author>>() {});
        log.info(exchange.getBody());

//        Anime kingdom = Anime.builder().name("kingdom").build();
//        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", kingdom, Anime.class);
//        log.info("saved anime {}", kingdomSaved);

        Author samuraiChamploo = Author.builder().name("Samurai Champloo").build();
        ResponseEntity<Author> samuraiChamplooSaved = new RestTemplate().exchange("http://localhost:8080/authors/",
                HttpMethod.POST,
                new HttpEntity<>(samuraiChamploo, createJsonHeader()),
                Author.class);

        log.info("saved anime {}", samuraiChamplooSaved);

        Author animeToBeUpdated = samuraiChamplooSaved.getBody();
        animeToBeUpdated.setName("Samurai Champloo 2");

        ResponseEntity<Void> samuraiChamplooUpdated = new RestTemplate().exchange("http://localhost:8080/authors/",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                Void.class);

        log.info(samuraiChamplooUpdated);

        ResponseEntity<Void> samuraiChamplooDeleted = new RestTemplate().exchange("http://localhost:8080/authors/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                animeToBeUpdated.getId());

        log.info(samuraiChamplooDeleted);
    }
    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}