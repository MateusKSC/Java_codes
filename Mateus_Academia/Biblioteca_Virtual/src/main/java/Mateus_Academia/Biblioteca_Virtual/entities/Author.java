package Mateus_Academia.Biblioteca_Virtual.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="Author")
public class Author{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The name cannot be empty!")
    private String name;

    @Range(min=1, message = "The birth year needs to be informed and must be greater than 0!")
    private int birthYear;

    @NotEmpty(message = "The cpf cannot be empty")
    @Pattern(regexp = "^([0-9]{3}\\.?){3}-?[0-9]{2}$", message = "The given Author's CPF has an " +
            "invalid format!")
    private String cpf;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnoreProperties("authors")
    private List<Book> booksMade = new ArrayList<>();
}