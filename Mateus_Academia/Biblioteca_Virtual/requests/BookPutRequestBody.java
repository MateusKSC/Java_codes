package Mateus_Academia.Biblioteca_Virtual.requests;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class BookPutRequestBody {
    private Long id;

    @NotEmpty(message = "The name cannot be empty")
    private String name;

    @NotNull(message= "The isbn may not be empty")
    private int isbn;

    @NotNull(message= "The date may not be empty")
    private Date publicationDate;
}
