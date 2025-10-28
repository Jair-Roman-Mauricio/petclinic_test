package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    /**
     * Test: Create a new vet
     */
    @Test
    public void testCreateVet() {
        String FIRST_NAME = "John";
        String LAST_NAME = "Smith";

        VetDTO vetDTO = new VetDTO();
        vetDTO.setFirstName(FIRST_NAME);
        vetDTO.setLastName(LAST_NAME);

        VetDTO newVetDTO = this.vetService.create(vetDTO);

        log.info("VET CREATED: " + newVetDTO.toString());

        assertNotNull(newVetDTO.getId());
        assertEquals(FIRST_NAME, newVetDTO.getFirstName());
        assertEquals(LAST_NAME, newVetDTO.getLastName());
    }

    /**
     * Test: Update an existing vet
     */
    @Test
    public void testUpdateVet() {
        String FIRST_NAME = "Michael";
        String LAST_NAME = "Johnson";

        String UP_FIRST_NAME = "Michael";
        String UP_LAST_NAME = "Anderson";

        VetDTO vetDTO = new VetDTO();
        vetDTO.setFirstName(FIRST_NAME);
        vetDTO.setLastName(LAST_NAME);

        // ------------ Create ---------------
        log.info(">" + vetDTO);
        VetDTO vetDTOCreated = this.vetService.create(vetDTO);
        log.info(">>" + vetDTOCreated);

        // ------------ Update ---------------
        // Prepare data for update
        vetDTOCreated.setFirstName(UP_FIRST_NAME);
        vetDTOCreated.setLastName(UP_LAST_NAME);

        // Execute update
        VetDTO upgradeVetDTO = this.vetService.update(vetDTOCreated);
        log.info(">>>>" + upgradeVetDTO);

        //            EXPECTED           ACTUAL
        assertEquals(UP_FIRST_NAME, upgradeVetDTO.getFirstName());
        assertEquals(UP_LAST_NAME, upgradeVetDTO.getLastName());
    }

    /**
     * Test: Find vet by ID
     */
    @Test
    public void testFindVetById() {
        Integer ID = 1;
        String FIRST_NAME_EXPECTED = "James";

        VetDTO vet = null;

        try {
            vet = this.vetService.findById(ID);
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("VET FOUND: " + vet);

        assertEquals(FIRST_NAME_EXPECTED, vet.getFirstName());
    }

    /**
     * Test: Find all vets
     */
    @Test
    public void testFindAllVets() {
        int SIZE_EXPECTED = 6; // Based on test data

        List<VetDTO> vets = this.vetService.findAll();

        log.info("VETS FOUND: " + vets.size());

        assertEquals(SIZE_EXPECTED, vets.size());
    }

    /**
     * Test: Delete a vet
     */
    @Test
    public void testDeleteVet() {
        String FIRST_NAME = "Robert";
        String LAST_NAME = "Williams";

        // ------------ Create ---------------
        VetDTO vetDTO = new VetDTO();
        vetDTO.setFirstName(FIRST_NAME);
        vetDTO.setLastName(LAST_NAME);

        VetDTO newVetDTO = this.vetService.create(vetDTO);
        log.info("VET CREATED FOR DELETE: " + newVetDTO);

        // ------------ Delete ---------------
        try {
            this.vetService.delete(newVetDTO.getId());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------
        try {
            this.vetService.findById(newVetDTO.getId());
            assertTrue(false);
        } catch (VetNotFoundException e) {
            assertTrue(true);
        }
    }
}
