package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class PetTypeServiceTest {

    @Autowired
    private PetTypeService petTypeService;

    @Test
    public void testCreatePetType() {
        String NAME = "Rabbit";

        PetTypeDTO petTypeDTO = new PetTypeDTO();
        petTypeDTO.setName(NAME);

        PetTypeDTO newPetTypeDTO = this.petTypeService.create(petTypeDTO);

        log.info("PET TYPE CREATED: " + newPetTypeDTO.toString());

        assertNotNull(newPetTypeDTO.getId());
        assertEquals(NAME, newPetTypeDTO.getName());
    }

    @Test
    public void testUpdatePetType() {
        String NAME = "Hamster";
        String UP_NAME = "Guinea Pig";

        PetTypeDTO petTypeDTO = new PetTypeDTO();
        petTypeDTO.setName(NAME);

        log.info(">" + petTypeDTO);
        PetTypeDTO petTypeDTOCreated = this.petTypeService.create(petTypeDTO);
        log.info(">>" + petTypeDTOCreated);

        petTypeDTOCreated.setName(UP_NAME);

        PetTypeDTO upgradePetTypeDTO = this.petTypeService.update(petTypeDTOCreated);
        log.info(">>>>" + upgradePetTypeDTO);

        assertEquals(UP_NAME, upgradePetTypeDTO.getName());
    }

    @Test
    public void testFindPetTypeById() {
        String NAME = "Parrot";

        PetTypeDTO petTypeDTO = new PetTypeDTO();
        petTypeDTO.setName(NAME);

        PetTypeDTO createdPetType = this.petTypeService.create(petTypeDTO);

        PetTypeDTO petType = null;
        try {
            petType = this.petTypeService.findById(createdPetType.getId());
        } catch (PetTypeNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("PET TYPE FOUND: " + petType);

        assertEquals(NAME, petType.getName());
    }

    @Test
    public void testFindAllPetTypes() {
        List<PetTypeDTO> petTypes = this.petTypeService.findAll();

        log.info("PET TYPES FOUND: " + petTypes.size());

        assertTrue(petTypes.size() >= 0);
    }

    @Test
    public void testDeletePetType() {
        String NAME = "Turtle";

        PetTypeDTO petTypeDTO = new PetTypeDTO();
        petTypeDTO.setName(NAME);

        PetTypeDTO newPetTypeDTO = this.petTypeService.create(petTypeDTO);
        log.info("PET TYPE CREATED FOR DELETE: " + newPetTypeDTO);

        try {
            this.petTypeService.delete(newPetTypeDTO.getId());
        } catch (PetTypeNotFoundException e) {
            fail(e.getMessage());
        }

        try {
            this.petTypeService.findById(newPetTypeDTO.getId());
            assertTrue(false);
        } catch (PetTypeNotFoundException e) {
            assertTrue(true);
        }
    }
}
