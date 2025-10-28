package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.repositories.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for Owner operations
 */
@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public OwnerDTO create(OwnerDTO ownerDTO) {
        Owner owner = new Owner();
        owner.setFirstName(ownerDTO.getFirstName());
        owner.setLastName(ownerDTO.getLastName());
        owner.setAddress(ownerDTO.getAddress());
        owner.setCity(ownerDTO.getCity());
        owner.setTelephone(ownerDTO.getTelephone());
        
        Owner savedOwner = ownerRepository.save(owner);
        log.info("Owner created: " + savedOwner);
        
        return convertToDTO(savedOwner);
    }

    @Override
    public OwnerDTO update(OwnerDTO ownerDTO) {
        Owner owner = new Owner();
        owner.setId(ownerDTO.getId());
        owner.setFirstName(ownerDTO.getFirstName());
        owner.setLastName(ownerDTO.getLastName());
        owner.setAddress(ownerDTO.getAddress());
        owner.setCity(ownerDTO.getCity());
        owner.setTelephone(ownerDTO.getTelephone());
        
        Owner updatedOwner = ownerRepository.save(owner);
        log.info("Owner updated: " + updatedOwner);
        
        return convertToDTO(updatedOwner);
    }

    @Override
    public void delete(Integer id) throws OwnerNotFoundException {
        Optional<Owner> owner = ownerRepository.findById(id);
        if (owner.isPresent()) {
            ownerRepository.delete(owner.get());
            log.info("Owner deleted with id: " + id);
        } else {
            throw new OwnerNotFoundException("Owner not found with id: " + id);
        }
    }

    @Override
    public OwnerDTO findById(Integer id) throws OwnerNotFoundException {
        Optional<Owner> owner = ownerRepository.findById(id);
        if (owner.isPresent()) {
            return convertToDTO(owner.get());
        } else {
            throw new OwnerNotFoundException("Owner not found with id: " + id);
        }
    }

    @Override
    public List<OwnerDTO> findAll() {
        List<Owner> owners = ownerRepository.findAll();
        return owners.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OwnerDTO> findByFirstName(String firstName) {
        List<Owner> owners = ownerRepository.findByFirstName(firstName);
        return owners.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OwnerDTO> findByLastName(String lastName) {
        List<Owner> owners = ownerRepository.findByLastName(lastName);
        return owners.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OwnerDTO> findByCity(String city) {
        List<Owner> owners = ownerRepository.findByCity(city);
        return owners.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OwnerDTO convertToDTO(Owner owner) {
        OwnerDTO dto = new OwnerDTO();
        dto.setId(owner.getId());
        dto.setFirstName(owner.getFirstName());
        dto.setLastName(owner.getLastName());
        dto.setAddress(owner.getAddress());
        dto.setCity(owner.getCity());
        dto.setTelephone(owner.getTelephone());
        return dto;
    }
}
