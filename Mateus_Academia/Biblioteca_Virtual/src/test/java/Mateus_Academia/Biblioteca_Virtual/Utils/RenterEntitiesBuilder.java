package Mateus_Academia.Biblioteca_Virtual.Utils;

import Mateus_Academia.Biblioteca_Virtual.entities.Renter;
import Mateus_Academia.Biblioteca_Virtual.requests.RenterPostRequestBody;
import Mateus_Academia.Biblioteca_Virtual.requests.RenterPutRequestBody;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

public class RenterEntitiesBuilder {
    public static Renter renterBuilder(){
        return Renter.builder()
                .id(1L)
                .name("Renter_Test")
                .cpf("00100000000")
                .birthDate(Date.from(Instant.parse("2024-02-26T17:44:56.413Z")))
                .rentsMade(Collections.emptyList())
                .telephone("000000000")
                .email("email@email.com")
                .build();
    }
    public static RenterPostRequestBody renterPostRequestBodyBuilder(){
        return RenterPostRequestBody.builder()
                .name("Renter_Test")
                .cpf("00000000000")
                .birthDate(Date.from(Instant.parse("2024-02-26T17:44:56.413Z")))
                .telephone("000000000")
                .email("email@email.com")
                .build();
    }
    public static RenterPutRequestBody renterPutRequestBodyBuilder(){
        return RenterPutRequestBody.builder()
                .id(1L)
                .name("Renter_Test")
                .cpf("00000000000")
                .birthDate(Date.from(Instant.parse("2024-02-26T17:44:56.413Z")))
                .telephone("000000000")
                .email("email@email.com")
                .build();
    }
}
