# Script to create service implementations
$services = @(
    @{Name="Specialty"; Fields=@("name","office","hOpen","hClose")},
    @{Name="Visit"; Fields=@("petId","visitDate","description")},
    @{Name="PetType"; Fields=@("name")}
)

foreach ($svc in $services) {
    $name = $svc.Name
    $impl = @"
package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.${name}DTO;
import com.tecsup.petclinic.entities.$name;
import com.tecsup.petclinic.exceptions.${name}NotFoundException;
import com.tecsup.petclinic.repositories.${name}Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ${name}ServiceImpl implements ${name}Service {
    @Autowired
    private ${name}Repository repository;

    @Override
    public ${name}DTO create(${name}DTO dto) {
        $name entity = convertToEntity(dto);
        $name saved = repository.save(entity);
        log.info("$name created: " + saved);
        return convertToDTO(saved);
    }

    @Override
    public ${name}DTO update(${name}DTO dto) {
        $name entity = convertToEntity(dto);
        entity.setId(dto.getId());
        $name updated = repository.save(entity);
        log.info("$name updated: " + updated);
        return convertToDTO(updated);
    }

    @Override
    public void delete(Integer id) throws ${name}NotFoundException {
        Optional<$name> entity = repository.findById(id);
        if (entity.isPresent()) {
            repository.delete(entity.get());
            log.info("$name deleted with id: " + id);
        } else {
            throw new ${name}NotFoundException("$name not found with id: " + id);
        }
    }

    @Override
    public ${name}DTO findById(Integer id) throws ${name}NotFoundException {
        Optional<$name> entity = repository.findById(id);
        if (entity.isPresent()) {
            return convertToDTO(entity.get());
        } else {
            throw new ${name}NotFoundException("$name not found with id: " + id);
        }
    }

    @Override
    public List<${name}DTO> findAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ${name}DTO convertToDTO($name entity) {
        ${name}DTO dto = new ${name}DTO();
        dto.setId(entity.getId());
        return dto;
    }

    private $name convertToEntity(${name}DTO dto) {
        $name entity = new $name();
        return entity;
    }
}
"@
    $impl | Out-File -FilePath "src\main\java\com\tecsup\petclinic\services\${name}ServiceImpl.java" -Encoding UTF8
}
