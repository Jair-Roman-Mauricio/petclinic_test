package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    /**
     * Test: Create a new owner
     */
    @Test
    public void testCreateOwner() {
        String FIRST_NAME = "Carlos";
        String LAST_NAME = "Mendez";
        String ADDRESS = "123 Main St.";
        String CITY = "Springfield";
        String TELEPHONE = "6085551234";

        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setFirstName(FIRST_NAME);
        ownerDTO.setLastName(LAST_NAME);
        ownerDTO.setAddress(ADDRESS);
        ownerDTO.setCity(CITY);
        ownerDTO.setTelephone(TELEPHONE);

        OwnerDTO newOwnerDTO = this.ownerService.create(ownerDTO);

        log.info("OWNER CREATED: " + newOwnerDTO.toString());

        assertNotNull(newOwnerDTO.getId());
        assertEquals(FIRST_NAME, newOwnerDTO.getFirstName());
        assertEquals(LAST_NAME, newOwnerDTO.getLastName());
        assertEquals(ADDRESS, newOwnerDTO.getAddress());
        assertEquals(CITY, newOwnerDTO.getCity());
        assertEquals(TELEPHONE, newOwnerDTO.getTelephone());
    }

    /**
     * Test: Update an existing owner
     */
    @Test
    public void testUpdateOwner() {
        String FIRST_NAME = "John";
        String LAST_NAME = "Doe";
        String ADDRESS = "456 Oak Ave.";
        String CITY = "Madison";
        String TELEPHONE = "6085555678";

        String UP_FIRST_NAME = "John";
        String UP_LAST_NAME = "Smith";
        String UP_ADDRESS = "789 Elm St.";
        String UP_CITY = "Monona";
        String UP_TELEPHONE = "6085559999";

        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setFirstName(FIRST_NAME);
        ownerDTO.setLastName(LAST_NAME);
        ownerDTO.setAddress(ADDRESS);
        ownerDTO.setCity(CITY);
        ownerDTO.setTelephone(TELEPHONE);

        // ------------ Create ---------------
        log.info(">" + ownerDTO);
        OwnerDTO ownerDTOCreated = this.ownerService.create(ownerDTO);
        log.info(">>" + ownerDTOCreated);

        // ------------ Update ---------------
        // Prepare data for update
        ownerDTOCreated.setFirstName(UP_FIRST_NAME);
        ownerDTOCreated.setLastName(UP_LAST_NAME);
        ownerDTOCreated.setAddress(UP_ADDRESS);
        ownerDTOCreated.setCity(UP_CITY);
        ownerDTOCreated.setTelephone(UP_TELEPHONE);

        // Execute update
        OwnerDTO upgradeOwnerDTO = this.ownerService.update(ownerDTOCreated);
        log.info(">>>>" + upgradeOwnerDTO);

        //            EXPECTED              ACTUAL
        assertEquals(UP_FIRST_NAME, upgradeOwnerDTO.getFirstName());
        assertEquals(UP_LAST_NAME, upgradeOwnerDTO.getLastName());
        assertEquals(UP_ADDRESS, upgradeOwnerDTO.getAddress());
        assertEquals(UP_CITY, upgradeOwnerDTO.getCity());
        assertEquals(UP_TELEPHONE, upgradeOwnerDTO.getTelephone());
    }

    /**
     * Test: Find owner by ID
     */
    @Test
    public void testFindOwnerById() {
        Integer ID = 1;
        String FIRST_NAME_EXPECTED = "George";

        OwnerDTO owner = null;

        try {
            owner = this.ownerService.findById(ID);
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("OWNER FOUND: " + owner);

        assertEquals(FIRST_NAME_EXPECTED, owner.getFirstName());
    }

    /**
     * Test: Find all owners
     */
    @Test
    public void testFindAllOwners() {
        List<OwnerDTO> owners = this.ownerService.findAll();

        log.info("OWNERS FOUND: " + owners.size());

        // Verify that we have at least the initial 10 owners from test data
        assertTrue(owners.size() >= 10);
    }

    /**
     * Test: Delete an owner
     */
    @Test
    public void testDeleteOwner() {
        String FIRST_NAME = "Sarah";
        String LAST_NAME = "Johnson";
        String ADDRESS = "321 Pine St.";
        String CITY = "Windsor";
        String TELEPHONE = "6085554321";

        // ------------ Create ---------------
        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setFirstName(FIRST_NAME);
        ownerDTO.setLastName(LAST_NAME);
        ownerDTO.setAddress(ADDRESS);
        ownerDTO.setCity(CITY);
        ownerDTO.setTelephone(TELEPHONE);

        OwnerDTO newOwnerDTO = this.ownerService.create(ownerDTO);
        log.info("OWNER CREATED FOR DELETE: " + newOwnerDTO);

        // ------------ Delete ---------------
        try {
            this.ownerService.delete(newOwnerDTO.getId());
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------
        try {
            this.ownerService.findById(newOwnerDTO.getId());
            assertTrue(false);
        } catch (OwnerNotFoundException e) {
            assertTrue(true);
        }
    }
}
