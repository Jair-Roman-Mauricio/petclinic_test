package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Slf4j
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @Test
    public void testCreateVisit() {
        Integer PET_ID = 1;
        LocalDate VISIT_DATE = LocalDate.of(2024, 1, 15);
        String DESCRIPTION = "Annual checkup";

        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setPetId(PET_ID);
        visitDTO.setVisitDate(VISIT_DATE);
        visitDTO.setDescription(DESCRIPTION);

        VisitDTO newVisitDTO = this.visitService.create(visitDTO);

        log.info("VISIT CREATED: " + newVisitDTO.toString());

        assertNotNull(newVisitDTO.getId());
        assertEquals(PET_ID, newVisitDTO.getPetId());
        assertEquals(VISIT_DATE, newVisitDTO.getVisitDate());
        assertEquals(DESCRIPTION, newVisitDTO.getDescription());
    }

    @Test
    public void testUpdateVisit() {
        Integer PET_ID = 2;
        LocalDate VISIT_DATE = LocalDate.of(2024, 2, 10);
        String DESCRIPTION = "Vaccination";

        Integer UP_PET_ID = 3;
        LocalDate UP_VISIT_DATE = LocalDate.of(2024, 2, 15);
        String UP_DESCRIPTION = "Vaccination and checkup";

        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setPetId(PET_ID);
        visitDTO.setVisitDate(VISIT_DATE);
        visitDTO.setDescription(DESCRIPTION);

        log.info(">" + visitDTO);
        VisitDTO visitDTOCreated = this.visitService.create(visitDTO);
        log.info(">>" + visitDTOCreated);

        visitDTOCreated.setPetId(UP_PET_ID);
        visitDTOCreated.setVisitDate(UP_VISIT_DATE);
        visitDTOCreated.setDescription(UP_DESCRIPTION);

        VisitDTO upgradeVisitDTO = this.visitService.update(visitDTOCreated);
        log.info(">>>>" + upgradeVisitDTO);

        assertEquals(UP_PET_ID, upgradeVisitDTO.getPetId());
        assertEquals(UP_VISIT_DATE, upgradeVisitDTO.getVisitDate());
        assertEquals(UP_DESCRIPTION, upgradeVisitDTO.getDescription());
    }

    @Test
    public void testFindVisitById() {
        Integer PET_ID = 1;
        LocalDate VISIT_DATE = LocalDate.of(2024, 3, 20);
        String DESCRIPTION = "Emergency visit";

        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setPetId(PET_ID);
        visitDTO.setVisitDate(VISIT_DATE);
        visitDTO.setDescription(DESCRIPTION);

        VisitDTO createdVisit = this.visitService.create(visitDTO);

        VisitDTO visit = null;
        try {
            visit = this.visitService.findById(createdVisit.getId());
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("VISIT FOUND: " + visit);

        assertEquals(DESCRIPTION, visit.getDescription());
    }

    @Test
    public void testFindVisitsByPet() {
        Integer PET_ID = 1;

        List<VisitDTO> visits = this.visitService.findByPetId(PET_ID);

        log.info("VISITS FOUND FOR PET " + PET_ID + ": " + visits.size());

        assertTrue(visits.size() >= 0);
    }

    @Test
    public void testDeleteVisit() {
        Integer PET_ID = 5;
        LocalDate VISIT_DATE = LocalDate.of(2024, 4, 5);
        String DESCRIPTION = "Surgery";

        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setPetId(PET_ID);
        visitDTO.setVisitDate(VISIT_DATE);
        visitDTO.setDescription(DESCRIPTION);

        VisitDTO newVisitDTO = this.visitService.create(visitDTO);
        log.info("VISIT CREATED FOR DELETE: " + newVisitDTO);

        try {
            this.visitService.delete(newVisitDTO.getId());
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        try {
            this.visitService.findById(newVisitDTO.getId());
            assertTrue(false);
        } catch (VisitNotFoundException e) {
            assertTrue(true);
        }
    }
}
