package Mateus_Academia.Biblioteca_Virtual.Utils;

import Mateus_Academia.Biblioteca_Virtual.entities.Rent;

import java.time.Instant;
import java.util.Date;

public class RentEntitiesBuilder {
    public static Rent rentBuilder(){
        return Rent.builder()
                .id(1L)
                .dateOfRent(Date.from(Instant.parse("2024-02-26T17:44:56.413Z")))
                .dateOfDevolution(Date.from(Instant.parse("2024-02-26T17:44:56.413Z")))
                .build();
    }
}
