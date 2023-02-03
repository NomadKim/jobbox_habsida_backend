package com.example.jobbox.model;

import com.example.jobbox.model.enums.EVisa;
import com.example.jobbox.model.enums.Gender;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String birthday;
    private Gender gender;
    private String phone;
    private EVisa visa;
    private String aboutMe;
    private Boolean hasCar;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Profile() {
    }

    public Profile(String name, String birthday, Gender gender, String phone, EVisa visa, String aboutMe, Boolean hasCar) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.visa = visa;
        this.aboutMe = aboutMe;
        this.hasCar = hasCar;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EVisa getVisa() {
        return visa;
    }

    public void setVisa(EVisa visa) {
        this.visa = visa;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Boolean hasCar() {
        return hasCar;
    }

    public void setCar(Boolean hasCar) {
        this.hasCar = hasCar;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
