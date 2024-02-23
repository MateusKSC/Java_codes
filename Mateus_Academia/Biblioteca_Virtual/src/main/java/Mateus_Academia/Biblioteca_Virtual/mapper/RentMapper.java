package Mateus_Academia.Biblioteca_Virtual.mapper;

import Mateus_Academia.Biblioteca_Virtual.entities.Rent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class RentMapper {
    public static final RentMapper INSTANCE = Mappers.getMapper(RentMapper.class);

}
