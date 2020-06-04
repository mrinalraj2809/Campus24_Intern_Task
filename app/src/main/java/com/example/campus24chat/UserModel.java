package com.example.campus24chat;
public class UserModel {
    private String headline;
    private String firstname;
    private String lastname;
    private String username;
    private String profilepic;
    private String hobby1, hobby2, hobby3;
    private String hobby1Color, hobby2Color, hobby3Color;
    private String institute, about;

    private String Uid;

    public UserModel() {
    }

    public UserModel(String headline, String firstname, String lastname, String username, String profilepic, String hobby1, String hobby2, String hobby3, String hobby1Color, String hobby2Color, String hobby3Color, String institute, String about, String uid) {
        this.headline = headline;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.profilepic = profilepic;
        this.hobby1 = hobby1;
        this.hobby2 = hobby2;
        this.hobby3 = hobby3;
        this.hobby1Color = hobby1Color;
        this.hobby2Color = hobby2Color;
        this.hobby3Color = hobby3Color;
        this.institute = institute;
        this.about = about;
        Uid = uid;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getHobby1() {
        return hobby1;
    }

    public void setHobby1(String hobby1) {
        this.hobby1 = hobby1;
    }

    public String getHobby2() {
        return hobby2;
    }

    public void setHobby2(String hobby2) {
        this.hobby2 = hobby2;
    }

    public String getHobby3() {
        return hobby3;
    }

    public void setHobby3(String hobby3) {
        this.hobby3 = hobby3;
    }

    public String getHobby1Color() {
        return hobby1Color;
    }

    public void setHobby1Color(String hobby1Color) {
        this.hobby1Color = hobby1Color;
    }

    public String getHobby2Color() {
        return hobby2Color;
    }

    public void setHobby2Color(String hobby2Color) {
        this.hobby2Color = hobby2Color;
    }

    public String getHobby3Color() {
        return hobby3Color;
    }

    public void setHobby3Color(String hobby3Color) {
        this.hobby3Color = hobby3Color;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}