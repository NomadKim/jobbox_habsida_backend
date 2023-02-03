package com.example.jobbox.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "categories")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer sort;
    private Boolean isActive;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File image;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
    private List<Service> services;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getSort() {
        return sort;
    }

    public Boolean getActive() {
        return isActive;
    }

    public String getCreated_at() {
        return String.valueOf(created_at);
    }

    public String getUpdated_at() {
        return String.valueOf(updated_at);
    }

    public File getImage() {
        return image;
    }

    public List<Service> getServices() {
        return services;
    }
}
