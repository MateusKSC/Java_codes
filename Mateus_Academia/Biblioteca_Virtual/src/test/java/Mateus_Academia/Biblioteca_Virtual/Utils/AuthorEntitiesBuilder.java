package Mateus_Academia.Biblioteca_Virtual.Utils;

import Mateus_Academia.Biblioteca_Virtual.entities.Author;
import Mateus_Academia.Biblioteca_Virtual.requests.AuthorPostRequestBody;
import Mateus_Academia.Biblioteca_Virtual.requests.AuthorPutRequestBody;

import java.util.Collections;

public class AuthorEntitiesBuilder {
    public static Author authorBuilder(){
        return Author.builder()
                .id(1L)
                .name("Author_Test")
                .cpf("00002000000")
                .birthYear(2000)
                .booksMade(Collections.emptyList())
                .build();
    }
    public static  AuthorPostRequestBody authorPostRequestBodyBuilder(){
        return AuthorPostRequestBody.builder()
                .name("Author_Test")
                .cpf("10000000000")
                .birthYear(2000)
                .build();
    }
    public static  AuthorPutRequestBody authorPutRequestBodyBuilder(){
        return AuthorPutRequestBody.builder()
                .id(1L)
                .name("Author_Test")
                .cpf("00000000040")
                .birthYear(2000)
                .build();
    }
}
