package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class SpecialtyServiceTest {

    @Autowired
    private SpecialtyService specialtyService;

    @Test
    public void testCreateSpecialty() {
        String NAME = "Cardiology";
        String OFFICE = "Room 101";
        Integer H_OPEN = 8;
        Integer H_CLOSE = 18;

        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setName(NAME);
        specialtyDTO.setOffice(OFFICE);
        specialtyDTO.setHOpen(H_OPEN);
        specialtyDTO.setHClose(H_CLOSE);

        SpecialtyDTO newSpecialtyDTO = this.specialtyService.create(specialtyDTO);

        log.info("SPECIALTY CREATED: " + newSpecialtyDTO.toString());

        assertNotNull(newSpecialtyDTO.getId());
        assertEquals(NAME, newSpecialtyDTO.getName());
        assertEquals(OFFICE, newSpecialtyDTO.getOffice());
        assertEquals(H_OPEN, newSpecialtyDTO.getHOpen());
        assertEquals(H_CLOSE, newSpecialtyDTO.getHClose());
    }

    @Test
    public void testUpdateSpecialty() {
        String NAME = "Surgery";
        String OFFICE = "Room 201";
        Integer H_OPEN = 9;
        Integer H_CLOSE = 17;

        String UP_NAME = "Advanced Surgery";
        String UP_OFFICE = "Room 202";
        Integer UP_H_OPEN = 10;
        Integer UP_H_CLOSE = 16;

        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setName(NAME);
        specialtyDTO.setOffice(OFFICE);
        specialtyDTO.setHOpen(H_OPEN);
        specialtyDTO.setHClose(H_CLOSE);

        log.info(">" + specialtyDTO);
        SpecialtyDTO specialtyDTOCreated = this.specialtyService.create(specialtyDTO);
        log.info(">>" + specialtyDTOCreated);

        specialtyDTOCreated.setName(UP_NAME);
        specialtyDTOCreated.setOffice(UP_OFFICE);
        specialtyDTOCreated.setHOpen(UP_H_OPEN);
        specialtyDTOCreated.setHClose(UP_H_CLOSE);

        SpecialtyDTO upgradeSpecialtyDTO = this.specialtyService.update(specialtyDTOCreated);
        log.info(">>>>" + upgradeSpecialtyDTO);

        assertEquals(UP_NAME, upgradeSpecialtyDTO.getName());
        assertEquals(UP_OFFICE, upgradeSpecialtyDTO.getOffice());
        assertEquals(UP_H_OPEN, upgradeSpecialtyDTO.getHOpen());
        assertEquals(UP_H_CLOSE, upgradeSpecialtyDTO.getHClose());
    }

    @Test
    public void testFindSpecialtyById() {
        Integer ID = 1;
        String NAME_EXPECTED = "Radiology";

        SpecialtyDTO specialty = null;

        try {
            specialty = this.specialtyService.findById(ID);
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("SPECIALTY FOUND: " + specialty);

        assertEquals(NAME_EXPECTED, specialty.getName());
    }

    @Test
    public void testFindAllSpecialties() {
        List<SpecialtyDTO> specialties = this.specialtyService.findAll();

        log.info("SPECIALTIES FOUND: " + specialties.size());

        assertTrue(specialties.size() >= 3);
    }

    @Test
    public void testDeleteSpecialty() {
        String NAME = "Dentistry";
        String OFFICE = "Room 301";
        Integer H_OPEN = 8;
        Integer H_CLOSE = 16;

        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setName(NAME);
        specialtyDTO.setOffice(OFFICE);
        specialtyDTO.setHOpen(H_OPEN);
        specialtyDTO.setHClose(H_CLOSE);

        SpecialtyDTO newSpecialtyDTO = this.specialtyService.create(specialtyDTO);
        log.info("SPECIALTY CREATED FOR DELETE: " + newSpecialtyDTO);

        try {
            this.specialtyService.delete(newSpecialtyDTO.getId());
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        try {
            this.specialtyService.findById(newSpecialtyDTO.getId());
            assertTrue(false);
        } catch (SpecialtyNotFoundException e) {
            assertTrue(true);
        }
    }
}
