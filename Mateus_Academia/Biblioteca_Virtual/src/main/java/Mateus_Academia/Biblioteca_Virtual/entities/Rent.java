package Mateus_Academia.Biblioteca_Virtual.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
        this.dateOfRent = date1;
        this.dateOfDevolution = date2;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Date dateOfRent;


    private Date dateOfDevolution;

    @OneToMany(targetEntity = Book.class)
    private List<Book> booksRented = new ArrayList<>();
    public void rentBooks(Book book) {
        this.booksRented.add(book);
    }
}

