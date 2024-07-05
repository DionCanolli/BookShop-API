package com.BookShop.BookShopAPI.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "genres")
public class Genres {
    @Id
    private String genreId;
    private String name;

    public Genres(String genreId, String name) {
        this.genreId = genreId;
        this.name = name;
    }

    public Genres(String name) {
        this.name = name;
    }

    public Genres() {
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
