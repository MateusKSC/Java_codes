package Mateus_Academia.Biblioteca_Virtual.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="Rent")
public class Rent {
    @PrePersist
    public void setDateOfRentAndDevolution() {
        Date date1 = new Date();
        Date date2 = new Date(System.currentTimeMillis() + 86400000 * 2);
        this.dateOfRent = date1.toString();
        this.dateOfDevolution = date2.toString();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String dateOfRent;


    private String dateOfDevolution;

    @ManyToOne
    @JoinColumn(name = "renter_id", referencedColumnName = "id")
    private Renter renter;

    @OneToMany(targetEntity = Book.class)
    private List<Book> booksRented = new ArrayList<>();
    public void rentBooks(Book book) {
        this.booksRented.add(book);
    }
}

