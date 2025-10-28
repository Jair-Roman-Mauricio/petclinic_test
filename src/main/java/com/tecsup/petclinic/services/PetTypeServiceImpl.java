package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import com.tecsup.petclinic.repositories.PetTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PetTypeServiceImpl implements PetTypeService {

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Override
    public PetTypeDTO create(PetTypeDTO petTypeDTO) {
        PetType petType = new PetType();
        petType.setName(petTypeDTO.getName());
        
        PetType savedPetType = petTypeRepository.save(petType);
        log.info("PetType created: " + savedPetType);
        
        return convertToDTO(savedPetType);
    }

    @Override
    public PetTypeDTO update(PetTypeDTO petTypeDTO) {
        PetType petType = new PetType();
        petType.setId(petTypeDTO.getId());
        petType.setName(petTypeDTO.getName());
        
        PetType updatedPetType = petTypeRepository.save(petType);
        log.info("PetType updated: " + updatedPetType);
        
        return convertToDTO(updatedPetType);
    }

    @Override
    public void delete(Integer id) throws PetTypeNotFoundException {
        Optional<PetType> petType = petTypeRepository.findById(id);
        if (petType.isPresent()) {
            petTypeRepository.delete(petType.get());
            log.info("PetType deleted with id: " + id);
        } else {
            throw new PetTypeNotFoundException("PetType not found with id: " + id);
        }
    }

    @Override
    public PetTypeDTO findById(Integer id) throws PetTypeNotFoundException {
        Optional<PetType> petType = petTypeRepository.findById(id);
        if (petType.isPresent()) {
            return convertToDTO(petType.get());
        } else {
            throw new PetTypeNotFoundException("PetType not found with id: " + id);
        }
    }

    @Override
    public List<PetTypeDTO> findAll() {
        List<PetType> petTypes = petTypeRepository.findAll();
        return petTypes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetTypeDTO> findByName(String name) {
        List<PetType> petTypes = petTypeRepository.findByName(name);
        return petTypes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PetTypeDTO convertToDTO(PetType petType) {
        PetTypeDTO dto = new PetTypeDTO();
        dto.setId(petType.getId());
        dto.setName(petType.getName());
        return dto;
    }
}
