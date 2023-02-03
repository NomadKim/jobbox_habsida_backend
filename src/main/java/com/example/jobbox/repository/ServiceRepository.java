package com.example.jobbox.repository;

import com.example.jobbox.model.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Query("SELECT s FROM Service s WHERE s.title LIKE %?1%")
    Page<Service> searchWithPagination(String title, Pageable pageable);

    @Query(value = "SELECT * FROM services WHERE category_id =:id ORDER BY id DESC", nativeQuery = true)
    List<Service> findServicesByCategoryId(Long id);

    @Query(value = "SELECT * FROM services WHERE category_id =:id", nativeQuery = true)
    Page<Service> findByCategoryIdWithPagination(Long id, Pageable pageable);
}
