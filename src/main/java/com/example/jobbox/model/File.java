package com.example.jobbox.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String originalTitle;
    private String type;

    public File() {
    }

    public File(String path, LocalDateTime created_at, LocalDateTime updated_at, String originalTitle, String type) {
        this.path = path;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.originalTitle = originalTitle;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
