package com.example.MediaTracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "media_items")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String type;
    private Integer rating;
    private String status;

    public Media() {}

    public Media(String title, String type, Integer rating, String status) {
        this.title = title;
        this.type = type;
        this.rating = rating;
        this.status = status;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type;}

    public Integer getRating() { return rating; }

    public void setRating(Integer rating) { this.rating = rating; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
