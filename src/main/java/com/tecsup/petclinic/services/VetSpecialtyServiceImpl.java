package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import com.tecsup.petclinic.repositories.VetRepository;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    @Autowired
    private VetSpecialtyRepository vetSpecialtyRepository;

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Override
    public VetSpecialtyDTO assignSpecialtyToVet(Integer vetId, Integer specialtyId) {
        VetSpecialty vetSpecialty = new VetSpecialty();
        vetSpecialty.setVetId(vetId);
        vetSpecialty.setSpecialtyId(specialtyId);
        
        VetSpecialty saved = vetSpecialtyRepository.save(vetSpecialty);
        log.info("Specialty assigned to vet: " + saved);
        
        VetSpecialtyDTO dto = new VetSpecialtyDTO();
        dto.setVetId(saved.getVetId());
        dto.setSpecialtyId(saved.getSpecialtyId());
        return dto;
    }

    @Override
    @Transactional
    public void removeSpecialtyFromVet(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
        List<VetSpecialty> vetSpecialties = vetSpecialtyRepository.findByVetId(vetId);
        boolean found = vetSpecialties.stream()
                .anyMatch(vs -> vs.getSpecialtyId().equals(specialtyId));
        
        if (found) {
            vetSpecialtyRepository.deleteByVetIdAndSpecialtyId(vetId, specialtyId);
            log.info("Specialty removed from vet: vetId=" + vetId + ", specialtyId=" + specialtyId);
        } else {
            throw new VetSpecialtyNotFoundException("VetSpecialty not found for vetId: " + vetId + " and specialtyId: " + specialtyId);
        }
    }

    @Override
    public List<SpecialtyDTO> findSpecialtiesByVet(Integer vetId) {
        List<VetSpecialty> vetSpecialties = vetSpecialtyRepository.findByVetId(vetId);
        return vetSpecialties.stream()
                .map(vs -> {
                    Specialty specialty = specialtyRepository.findById(vs.getSpecialtyId()).orElse(null);
                    if (specialty != null) {
                        SpecialtyDTO dto = new SpecialtyDTO();
                        dto.setId(specialty.getId());
                        dto.setName(specialty.getName());
                        dto.setOffice(specialty.getOffice());
                        dto.setHOpen(specialty.getHOpen());
                        dto.setHClose(specialty.getHClose());
                        return dto;
                    }
                    return null;
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }

    @Override
    public List<VetDTO> findVetsBySpecialty(Integer specialtyId) {
        List<VetSpecialty> vetSpecialties = vetSpecialtyRepository.findBySpecialtyId(specialtyId);
        return vetSpecialties.stream()
                .map(vs -> {
                    Vet vet = vetRepository.findById(vs.getVetId()).orElse(null);
                    if (vet != null) {
                        VetDTO dto = new VetDTO();
                        dto.setId(vet.getId());
                        dto.setFirstName(vet.getFirstName());
                        dto.setLastName(vet.getLastName());
                        return dto;
                    }
                    return null;
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }
}
