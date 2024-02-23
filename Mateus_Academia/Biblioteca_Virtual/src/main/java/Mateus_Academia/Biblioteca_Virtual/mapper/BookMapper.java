package Mateus_Academia.Biblioteca_Virtual.mapper;

import Mateus_Academia.Biblioteca_Virtual.entities.Book;
import Mateus_Academia.Biblioteca_Virtual.requests.BookPostRequestBody;
import Mateus_Academia.Biblioteca_Virtual.requests.BookPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class BookMapper {
    public static final BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    public abstract Book toBook(BookPostRequestBody bookPostRequestBody);

    public abstract Book toBook(BookPutRequestBody bookPutRequestBody);
}
