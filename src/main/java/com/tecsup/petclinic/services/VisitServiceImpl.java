package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.repositories.VisitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitRepository visitRepository;

    @Override
    public VisitDTO create(VisitDTO visitDTO) {
        Visit visit = new Visit();
        visit.setPetId(visitDTO.getPetId());
        visit.setVisitDate(visitDTO.getVisitDate());
        visit.setDescription(visitDTO.getDescription());
        
        Visit savedVisit = visitRepository.save(visit);
        log.info("Visit created: " + savedVisit);
        
        return convertToDTO(savedVisit);
    }

    @Override
    public VisitDTO update(VisitDTO visitDTO) {
        Visit visit = new Visit();
        visit.setId(visitDTO.getId());
        visit.setPetId(visitDTO.getPetId());
        visit.setVisitDate(visitDTO.getVisitDate());
        visit.setDescription(visitDTO.getDescription());
        
        Visit updatedVisit = visitRepository.save(visit);
        log.info("Visit updated: " + updatedVisit);
        
        return convertToDTO(updatedVisit);
    }

    @Override
    public void delete(Integer id) throws VisitNotFoundException {
        Optional<Visit> visit = visitRepository.findById(id);
        if (visit.isPresent()) {
            visitRepository.delete(visit.get());
            log.info("Visit deleted with id: " + id);
        } else {
            throw new VisitNotFoundException("Visit not found with id: " + id);
        }
    }

    @Override
    public VisitDTO findById(Integer id) throws VisitNotFoundException {
        Optional<Visit> visit = visitRepository.findById(id);
        if (visit.isPresent()) {
            return convertToDTO(visit.get());
        } else {
            throw new VisitNotFoundException("Visit not found with id: " + id);
        }
    }

    @Override
    public List<VisitDTO> findAll() {
        List<Visit> visits = visitRepository.findAll();
        return visits.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitDTO> findByPetId(Integer petId) {
        List<Visit> visits = visitRepository.findByPetId(petId);
        return visits.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private VisitDTO convertToDTO(Visit visit) {
        VisitDTO dto = new VisitDTO();
        dto.setId(visit.getId());
        dto.setPetId(visit.getPetId());
        dto.setVisitDate(visit.getVisitDate());
        dto.setDescription(visit.getDescription());
        return dto;
    }
}
