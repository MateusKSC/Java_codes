package Mateus_Academia.Biblioteca_Virtual.requests;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class AuthorPutRequestBody {
    private Long id;
    @NotEmpty(message = "The name cannot be empty")
    private String name;

    @NotNull(message= "The birth year may not be empty")
    private int birthYear;

    @NotEmpty(message = "The cpf cannot be empty")
    @Pattern(regexp = "^([0-9]{3}\\.?){3}-?[0-9]{2}$", message = "The given Author's CPF has an " +
            "invalid format.")
    private String cpf;
}
