package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Visit entity
 */
@Entity(name = "visits")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "pet_id")
    private Integer petId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "visit_date")
    private LocalDate visitDate;
    
    private String description;
}
