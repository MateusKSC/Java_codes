package Mateus_Academia.Biblioteca_Virtual.requests;

import Mateus_Academia.Biblioteca_Virtual.entities.Author;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BookPostRequestBody {
    @NotEmpty(message = "The name cannot be empty!")
    private String name;

    @NotEmpty(message= "The isbn may not be empty!")
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3" +
            "})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:9" +
            "7[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$", message =
            "The ISBN's pattern is invalid!")
    private String isbn;

    @NotNull(message= "The date may not be empty!")
    private Date publicationDate;

}
