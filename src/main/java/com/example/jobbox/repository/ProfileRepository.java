package com.example.jobbox.repository;

import com.example.jobbox.model.Profile;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query(value = "select profiles from Profile profiles " +
            "inner join User user on user.id = profiles.user.id " +
            "where profiles.phone in :phoneNumbers")
    List<Profile> getProfilesByPhoneNumber(List<String> phoneNumbers);
}
