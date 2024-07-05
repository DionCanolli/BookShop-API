package com.BookShop.BookShopAPI.entity;

import com.BookShop.BookShopAPI.enums.NationalityOrLanguage;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "authors")
public class Authors {

    @Id
    private String authorId;
    private String firstName;
    private String lastName;
    private String bio;
    private String authorEmail;
    private Date dateOfBirth;
    private NationalityOrLanguage nationality;

    public Authors(String firstName, String lastName, String bio, String authorEmail, Date dateOfBirth, NationalityOrLanguage nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.authorEmail = authorEmail;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }

    public Authors(String authorId, String firstName, String lastName, String bio, String authorEmail, Date dateOfBirth, NationalityOrLanguage nationality) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.authorEmail = authorEmail;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }

    public Authors() {
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public NationalityOrLanguage getNationality() {
        return nationality;
    }

    public void setNationality(NationalityOrLanguage nationality) {
        this.nationality = nationality;
    }
}
