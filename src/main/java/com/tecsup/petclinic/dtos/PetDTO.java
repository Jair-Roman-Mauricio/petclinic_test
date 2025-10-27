package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {

    private Integer id;
    private String name;
    private int typeId;
    private int ownerId;
    private LocalDate birthDate;
}
