package com.BookShop.BookShopAPI.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class JWTToken {

    @Id
    private String jwtId;
    private String value;

    public JWTToken(String value) {
        this.value = value;
    }

    public JWTToken() {
    }

    public String getJwtId() {
        return jwtId;
    }

    public void setJwtId(String jwtId) {
        this.jwtId = jwtId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
