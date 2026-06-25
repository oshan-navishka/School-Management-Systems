package com.example.AAD_Task_II.service.impl;

import com.example.AAD_Task_II.dto.SectionDTO;
import com.example.AAD_Task_II.entity.Section;
import com.example.AAD_Task_II.repository.SectionRepository;
import com.example.AAD_Task_II.service.SectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;

    public SectionServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    public void saveSection(SectionDTO sectionDTO) {
        log.info("Saving section: {}", sectionDTO.getSectionName());
        try {
            if (sectionDTO.getSectionName() == null || sectionDTO.getSectionName().isEmpty()) {
                throw new RuntimeException("Section name cannot be empty");
            }
            Section section = new Section();
            section.setSectionName(sectionDTO.getSectionName());
            sectionRepository.save(section);
            log.info("Section saved successfully");
        } catch (Exception e) {
            log.error("Error saving section: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateSection(SectionDTO sectionDTO) {
        log.info("Updating section with ID: {}", sectionDTO.getSectionId());
        try {
            if (sectionDTO.getSectionName() == null || sectionDTO.getSectionName().isEmpty()) {
                throw new RuntimeException("Section name cannot be empty");
            }
            Optional<Section> sectionOptional = sectionRepository.findById(sectionDTO.getSectionId());
            if (sectionOptional.isEmpty()) {
                throw new RuntimeException("Section not found with ID: " + sectionDTO.getSectionId());
            }
            Section section = sectionOptional.get();
            section.setSectionName(sectionDTO.getSectionName());
            sectionRepository.save(section);
            log.info("Section updated successfully");
        } catch (Exception e) {
            log.error("Error updating section: {}", e.getMessage());
            throw e;
        }
    }
}
