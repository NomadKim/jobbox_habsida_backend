package com.example.jobbox.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "banners")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer sort;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File image;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getSort() {
        return sort;
    }

    public File getImage() {
        return image;
    }

    public String getCreated_at() {
        return String.valueOf(created_at);
    }

    public String getUpdated_at() {
        return String.valueOf(updated_at);
    }

    public Service getService() {
        return service;
    }
}
