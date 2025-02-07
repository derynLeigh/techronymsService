package com.techronymsservice.repository;

import com.techronymsservice.model.GlossaryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlossaryRepository extends JpaRepository<GlossaryItem, Long> {
    List<GlossaryItem> findByTitleIgnoreCase(String title);
}
