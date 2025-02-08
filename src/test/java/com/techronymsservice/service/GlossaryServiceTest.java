package com.techronymsservice.service;

import com.techronymsservice.model.GlossaryItem;
import com.techronymsservice.repository.GlossaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GlossaryServiceTest {

    @Mock
    private GlossaryRepository glossaryRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private GlossaryService glossaryService;

    GlossaryItem glossaryItem = new GlossaryItem("MTA", "Meaningless test abbreviation");

    @Test
    void testCreateGlossaryItem() {

        GlossaryItem savedGlossaryItem = spy(glossaryItem);
        savedGlossaryItem.setId(1L);
        savedGlossaryItem.setCreated(Instant.now());
        savedGlossaryItem.setLastUpdated(Instant.now());

        when(glossaryRepository.save(any(GlossaryItem.class))).thenReturn(savedGlossaryItem);

        GlossaryItem result = glossaryService.create(glossaryItem);

        assertNotNull(result);
        assertEquals(savedGlossaryItem.getId(), result.getId());
        assertEquals(savedGlossaryItem.getTitle(), result.getTitle());
        assertEquals(savedGlossaryItem.getDefinition(), result.getDefinition());
        assertEquals(savedGlossaryItem.getCreated(), result.getCreated());
        assertNotNull(result.getLastUpdated());
        assertNotNull(result.getLastUpdated());
        verify(glossaryRepository).save(any(GlossaryItem.class));
    }

    @Test
    void testUpdateExistingItem() {
        // Arrange
        GlossaryItem existingItem = glossaryItem;
        existingItem.setId(2L);

        GlossaryItem updatedItem = glossaryItem;
        updatedItem.setId(2L);
        updatedItem.setTitle("ATA");
        updatedItem.setDefinition("Another Test Acronym");

        when(glossaryRepository.findById(2L)).thenReturn(Optional.of(existingItem));
        when(glossaryRepository.save(any(GlossaryItem.class))).thenReturn(updatedItem);

        // Mock the mapper behavior
        doNothing().when(mapper).map(updatedItem, existingItem);

        // Act
        Optional<GlossaryItem> result = glossaryService.update(updatedItem);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("ATA", result.get().getTitle());
        assertEquals("Another Test Acronym", result.get().getDefinition());
        verify(glossaryRepository).findById(2L);
        verify(glossaryRepository).save(any(GlossaryItem.class));
    }

    @Test
    void testUpdateNonExistingItem(){
        //Arrange
        GlossaryItem updatedItem = new GlossaryItem();
        updatedItem.setId(1L);
        updatedItem.setTitle("New Title");

        when(glossaryRepository.findById(1L)).thenReturn(Optional.empty());

        //Act
        Optional<GlossaryItem> result = glossaryService.update(updatedItem);

        //Assert
        assertFalse(result.isPresent());
        verify(glossaryRepository).findById(1L);
        verify(glossaryRepository, never()).save(any(GlossaryItem.class));
    }
}
