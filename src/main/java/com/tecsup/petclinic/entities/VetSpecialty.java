package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * VetSpecialty entity - represents the many-to-many relationship
 */
@Entity(name = "vet_specialties")
@NoArgsConstructor
@Data
@AllArgsConstructor
@IdClass(VetSpecialty.VetSpecialtyId.class)
public class VetSpecialty {

    @Id
    @Column(name = "vet_id")
    private Integer vetId;
    
    @Id
    @Column(name = "specialty_id")
    private Integer specialtyId;

    /**
     * Composite key class
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VetSpecialtyId implements Serializable {
        private Integer vetId;
        private Integer specialtyId;
    }
}
