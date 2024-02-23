package Mateus_Academia.Biblioteca_Virtual.mapper;

import Mateus_Academia.Biblioteca_Virtual.entities.Renter;
import Mateus_Academia.Biblioteca_Virtual.requests.RenterPostRequestBody;
import Mateus_Academia.Biblioteca_Virtual.requests.RenterPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class RenterMapper {
    public static final RenterMapper INSTANCE = Mappers.getMapper(RenterMapper.class);

    public abstract Renter toRenter(RenterPostRequestBody renterPostRequestBody);

    public abstract Renter toRenter(RenterPutRequestBody renterPutRequestBody);
}
