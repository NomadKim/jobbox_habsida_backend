package com.example.jobbox.model;

import com.example.jobbox.model.enums.ERole;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ERole name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }

    public Long
    getId() {
        return id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public String getCreated_at() {
        return String.valueOf(created_at);
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return String.valueOf(updated_at);
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
