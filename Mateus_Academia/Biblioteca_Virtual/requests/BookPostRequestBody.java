package Mateus_Academia.Biblioteca_Virtual.requests;

import Mateus_Academia.Biblioteca_Virtual.entities.Author;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BookPostRequestBody {
    @NotEmpty(message = "The name cannot be empty")
    private String name;

    @NotNull(message= "The isbn may not be empty")
    private int isbn;

    @NotNull(message= "The date may not be empty")
    private Date publicationDate;

}
