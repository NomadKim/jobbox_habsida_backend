package com.example.jobbox.repository;

import com.example.jobbox.model.JobPost;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    @EntityGraph(value = "JobPost.UsersFields")
    Optional<JobPost> findById(Long id);
}
