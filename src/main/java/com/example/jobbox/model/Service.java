package com.example.jobbox.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "services")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String phone;
    private String site;
    private String address;
    private String time;
    private String description;
    private Integer sort;
    private Boolean isActive;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File image;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Float longitude;
    private Float latitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "service", cascade = CascadeType.ALL)
    private List<Banner> banners;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPhone() {
        return phone;
    }

    public String getSite() {
        return site;
    }

    public String getAddress() {
        return address;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public Integer getSort() {
        return sort;
    }

    public Boolean getActive() {
        return isActive;
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

    public Float getLongitude() {
        return longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Category getCategory() {
        return category;
    }

    public List<Banner> getBanners() {
        return banners;
    }
}
