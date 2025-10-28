package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import java.util.List;

public interface VetSpecialtyService {
    VetSpecialtyDTO assignSpecialtyToVet(Integer vetId, Integer specialtyId);
    void removeSpecialtyFromVet(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;
    List<SpecialtyDTO> findSpecialtiesByVet(Integer vetId);
    List<VetDTO> findVetsBySpecialty(Integer specialtyId);
}
