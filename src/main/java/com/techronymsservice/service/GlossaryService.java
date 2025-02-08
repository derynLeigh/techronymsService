package com.techronymsservice.service;

import com.techronymsservice.model.GlossaryItem;
import com.techronymsservice.repository.GlossaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GlossaryService {
    private final GlossaryRepository glossaryRepository;
    private final ModelMapper mapper;

    public GlossaryService(GlossaryRepository repository, ModelMapper mapper) {
        this.glossaryRepository = repository;
        this.mapper = mapper;
    }
    public List<GlossaryItem> findAll() {
        return glossaryRepository.findAll();
    }
    public Optional<GlossaryItem> findById(Long id) {
        return glossaryRepository.findById(id);
    }
    public List<GlossaryItem> findByTitle(String title) {
        return glossaryRepository.findByTitleIgnoreCase(title);
    }
    public GlossaryItem create(GlossaryItem item) {
        return glossaryRepository.save(item);
    }
    public Optional<GlossaryItem> update(GlossaryItem item) {
        return glossaryRepository.findById(item.getId())
                .map(existingItem -> {
                    mapper.map(item, existingItem);
                    return glossaryRepository.save(existingItem);
                });
    }


    public void delete(Long id) {
        glossaryRepository.deleteById(id);
    }
}

