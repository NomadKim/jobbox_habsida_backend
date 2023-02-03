package com.example.jobbox.repository;

import com.example.jobbox.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    @Query(value = "SELECT * FROM banners WHERE service_id =:id ORDER BY id DESC", nativeQuery = true)
    List<Banner> findBannersByServiceId(@Param("id") Long id);
}
