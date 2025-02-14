package Mateus_Academia.Biblioteca_Virtual.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Builder
public class RenterPutRequestBody {
    private Long id;
    @NotEmpty(message = "The name cannot be empty!")
    private String name;

    @NotEmpty(message = "The telephone number cannot be empty!")
    @Pattern(regexp="(^$|[0-9]{9})", message = "The phone number should be composed of exactly 9 numbers!" )
    private String telephone;

    @NotEmpty(message = "The email cannot be empty!")
    @Pattern(regexp = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$",
            message = "The given Renter's email has an invalid format!")
    private String email;

    @NotNull(message = "The birth date cannot be empty!")
    private Date birthDate;

    @NotEmpty(message = "The cpf cannot be empty!")
    @Pattern(regexp = "^([0-9]{3}\\.?){3}-?[0-9]{2}$", message = "The given Renter's CPF has an " +
            "invalid format!")
    private String cpf;
}
