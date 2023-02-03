package com.example.jobbox.repository;

import com.example.jobbox.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    @Query(value = "SELECT * FROM templates WHERE user_id = :user_id", nativeQuery = true)
    List<Template> getTemplateByUser(@Param("user_id") Long id);

    @Query(value = "SELECT * FROM templates WHERE is_default = true", nativeQuery = true)
    List<Template> getTemplateByIsDefault();
}
