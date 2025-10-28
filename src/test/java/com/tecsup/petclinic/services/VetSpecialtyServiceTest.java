package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    @Autowired
    private VetService vetService;

    @Autowired
    private SpecialtyService specialtyService;

    @Test
    public void testAssignSpecialtyToVet() {
        Integer VET_ID = 1;
        Integer SPECIALTY_ID = 1;

        VetSpecialtyDTO vetSpecialtyDTO = this.vetSpecialtyService.assignSpecialtyToVet(VET_ID, SPECIALTY_ID);

        log.info("SPECIALTY ASSIGNED TO VET: " + vetSpecialtyDTO.toString());

        assertNotNull(vetSpecialtyDTO);
        assertEquals(VET_ID, vetSpecialtyDTO.getVetId());
        assertEquals(SPECIALTY_ID, vetSpecialtyDTO.getSpecialtyId());
    }

    @Test
    public void testFindSpecialtiesByVet() {
        Integer VET_ID = 1;
        Integer SPECIALTY_ID = 2;

        this.vetSpecialtyService.assignSpecialtyToVet(VET_ID, SPECIALTY_ID);

        List<SpecialtyDTO> specialties = this.vetSpecialtyService.findSpecialtiesByVet(VET_ID);

        log.info("SPECIALTIES FOUND FOR VET " + VET_ID + ": " + specialties.size());

        assertTrue(specialties.size() >= 1);
    }

    @Test
    public void testFindVetsBySpecialty() {
        Integer VET_ID = 2;
        Integer SPECIALTY_ID = 1;

        this.vetSpecialtyService.assignSpecialtyToVet(VET_ID, SPECIALTY_ID);

        List<VetDTO> vets = this.vetSpecialtyService.findVetsBySpecialty(SPECIALTY_ID);

        log.info("VETS FOUND FOR SPECIALTY " + SPECIALTY_ID + ": " + vets.size());

        assertTrue(vets.size() >= 1);
    }

    @Test
    public void testRemoveSpecialtyFromVet() {
        Integer VET_ID = 3;
        Integer SPECIALTY_ID = 2;

        this.vetSpecialtyService.assignSpecialtyToVet(VET_ID, SPECIALTY_ID);
        log.info("SPECIALTY ASSIGNED TO VET FOR REMOVAL TEST");

        try {
            this.vetSpecialtyService.removeSpecialtyFromVet(VET_ID, SPECIALTY_ID);
            log.info("SPECIALTY REMOVED FROM VET");
            assertTrue(true);
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        List<SpecialtyDTO> specialties = this.vetSpecialtyService.findSpecialtiesByVet(VET_ID);
        boolean found = specialties.stream()
                .anyMatch(s -> s.getId().equals(SPECIALTY_ID));
        
        assertTrue(!found, "Specialty should be removed from vet");
    }
}
