package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for Vet operations
 */
@Service
@Slf4j
public class VetServiceImpl implements VetService {

    @Autowired
    private VetRepository vetRepository;

    @Override
    public VetDTO create(VetDTO vetDTO) {
        Vet vet = new Vet();
        vet.setFirstName(vetDTO.getFirstName());
        vet.setLastName(vetDTO.getLastName());
        
        Vet savedVet = vetRepository.save(vet);
        log.info("Vet created: " + savedVet);
        
        return convertToDTO(savedVet);
    }

    @Override
    public VetDTO update(VetDTO vetDTO) {
        Vet vet = new Vet();
        vet.setId(vetDTO.getId());
        vet.setFirstName(vetDTO.getFirstName());
        vet.setLastName(vetDTO.getLastName());
        
        Vet updatedVet = vetRepository.save(vet);
        log.info("Vet updated: " + updatedVet);
        
        return convertToDTO(updatedVet);
    }

    @Override
    public void delete(Integer id) throws VetNotFoundException {
        Optional<Vet> vet = vetRepository.findById(id);
        if (vet.isPresent()) {
            vetRepository.delete(vet.get());
            log.info("Vet deleted with id: " + id);
        } else {
            throw new VetNotFoundException("Vet not found with id: " + id);
        }
    }

    @Override
    public VetDTO findById(Integer id) throws VetNotFoundException {
        Optional<Vet> vet = vetRepository.findById(id);
        if (vet.isPresent()) {
            return convertToDTO(vet.get());
        } else {
            throw new VetNotFoundException("Vet not found with id: " + id);
        }
    }

    @Override
    public List<VetDTO> findAll() {
        List<Vet> vets = vetRepository.findAll();
        return vets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VetDTO> findByFirstName(String firstName) {
        List<Vet> vets = vetRepository.findByFirstName(firstName);
        return vets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VetDTO> findByLastName(String lastName) {
        List<Vet> vets = vetRepository.findByLastName(lastName);
        return vets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private VetDTO convertToDTO(Vet vet) {
        VetDTO dto = new VetDTO();
        dto.setId(vet.getId());
        dto.setFirstName(vet.getFirstName());
        dto.setLastName(vet.getLastName());
        return dto;
    }
}
