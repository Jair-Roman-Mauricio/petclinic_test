package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpecialtyServiceImpl implements SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Override
    public SpecialtyDTO create(SpecialtyDTO specialtyDTO) {
        Specialty specialty = new Specialty();
        specialty.setName(specialtyDTO.getName());
        specialty.setOffice(specialtyDTO.getOffice());
        specialty.setHOpen(specialtyDTO.getHOpen());
        specialty.setHClose(specialtyDTO.getHClose());
        
        Specialty savedSpecialty = specialtyRepository.save(specialty);
        log.info("Specialty created: " + savedSpecialty);
        
        return convertToDTO(savedSpecialty);
    }

    @Override
    public SpecialtyDTO update(SpecialtyDTO specialtyDTO) {
        Specialty specialty = new Specialty();
        specialty.setId(specialtyDTO.getId());
        specialty.setName(specialtyDTO.getName());
        specialty.setOffice(specialtyDTO.getOffice());
        specialty.setHOpen(specialtyDTO.getHOpen());
        specialty.setHClose(specialtyDTO.getHClose());
        
        Specialty updatedSpecialty = specialtyRepository.save(specialty);
        log.info("Specialty updated: " + updatedSpecialty);
        
        return convertToDTO(updatedSpecialty);
    }

    @Override
    public void delete(Integer id) throws SpecialtyNotFoundException {
        Optional<Specialty> specialty = specialtyRepository.findById(id);
        if (specialty.isPresent()) {
            specialtyRepository.delete(specialty.get());
            log.info("Specialty deleted with id: " + id);
        } else {
            throw new SpecialtyNotFoundException("Specialty not found with id: " + id);
        }
    }

    @Override
    public SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException {
        Optional<Specialty> specialty = specialtyRepository.findById(id);
        if (specialty.isPresent()) {
            return convertToDTO(specialty.get());
        } else {
            throw new SpecialtyNotFoundException("Specialty not found with id: " + id);
        }
    }

    @Override
    public List<SpecialtyDTO> findAll() {
        List<Specialty> specialties = specialtyRepository.findAll();
        return specialties.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpecialtyDTO> findByName(String name) {
        List<Specialty> specialties = specialtyRepository.findByName(name);
        return specialties.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SpecialtyDTO convertToDTO(Specialty specialty) {
        SpecialtyDTO dto = new SpecialtyDTO();
        dto.setId(specialty.getId());
        dto.setName(specialty.getName());
        dto.setOffice(specialty.getOffice());
        dto.setHOpen(specialty.getHOpen());
        dto.setHClose(specialty.getHClose());
        return dto;
    }
}
