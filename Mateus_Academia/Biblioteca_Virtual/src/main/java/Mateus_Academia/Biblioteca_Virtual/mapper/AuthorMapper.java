package Mateus_Academia.Biblioteca_Virtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import Mateus_Academia.Biblioteca_Virtual.entities.Author;
import Mateus_Academia.Biblioteca_Virtual.requests.AuthorPostRequestBody;
import Mateus_Academia.Biblioteca_Virtual.requests.AuthorPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class AuthorMapper {
    public static final AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    public abstract Author toAuthor(AuthorPostRequestBody authorPostRequestBody);

    public abstract Author toAuthor(AuthorPutRequestBody authorPutRequestBody);
}
