package com.techronymsservice.controller;

import com.techronymsservice.dto.GlossaryDTO;
import com.techronymsservice.model.GlossaryItem;
import com.techronymsservice.model.GlossaryItemScope;
import com.techronymsservice.model.GlossaryItemType;
import com.techronymsservice.service.GlossaryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/glossary")
public class GlossaryController {
    private final GlossaryService glossaryService;
    private final ModelMapper mapper;
    public GlossaryController(GlossaryService glossaryService, ModelMapper mapper) {
        this.glossaryService = glossaryService;
        this.mapper = mapper;
    }

    @GetMapping("/findAll")
    public List<GlossaryDTO> getAllItems() {
        List<GlossaryItem> items = glossaryService.findAll();
        return items.stream()
                .map(item -> mapper.map(item, GlossaryDTO.class))
                .toList();
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<GlossaryDTO> getGlossaryById(@PathVariable Long id) {
        Optional<GlossaryItem> existingItem = glossaryService.findById(id);
        return existingItem.map(item -> ResponseEntity.ok().body(mapper.map(item, GlossaryDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByTitle/{title}")
    public List<GlossaryItem> getGlossaryByTitle(@PathVariable String title) {
        return glossaryService.findByTitle(title);
    }

    @PostMapping("/create")
    public GlossaryItem createGlossaryItem(@Valid @RequestBody GlossaryDTO glossaryDTO) {
        GlossaryItem glossaryItem = mapper.map(glossaryDTO, GlossaryItem.class);
        GlossaryItem savedGlossaryItem = glossaryService.create(glossaryItem);
        return mapper.map(savedGlossaryItem, GlossaryItem.class);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<GlossaryDTO> editGlossaryItem(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        log.info("Editing glossary item with id {}", id);
        log.info("Update payload: {}", updates);

       Optional<GlossaryItem> optionalItem = glossaryService.findById(id);
       if (optionalItem.isEmpty()) {
           log.warn("Glossary item with id {} not found", id);
           return ResponseEntity.notFound().build();
       }

       GlossaryItem item = optionalItem.get();
       log.info("Glossary item found: {}", item);

       updates.forEach((key, value) -> {
           switch (key) {
               case "title" -> item.setTitle((String) value);
               case "definition" -> item.setDefinition((String) value);
               case "furtherReading" -> item.setFurtherReading((String) value);
               default -> log.warn("Unrecognized field {}", key);
           }
       });

       Optional<GlossaryItem> updatedItem = glossaryService.update(item);
       if (updatedItem.isPresent()) {
           log.info("Glossary item updated: {}", updatedItem);
           return ResponseEntity.ok().body(mapper.map(updatedItem.get(), GlossaryDTO.class));
       } else {
           log.error("Glossary item could not be updated");
           return ResponseEntity.internalServerError().build();
       }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (glossaryService.findById(id).isPresent()) {
            glossaryService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
