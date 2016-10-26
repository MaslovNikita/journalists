package model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Bean of user
 */
public class User implements Serializable{
    private int id;
    private String name;
    private String surname;
    private Date birthdayDay;
    private short gender;
    private String aboutSelf;
    private String telephoneNumber;
    private String email;
    private boolean avatar;

    public User() {
        this.id = -1;
    }

    public User(final int id, final String name, final String surname, final Date birthdayDay, final short gender, final String aboutSelf, final String telephoneNumber, final String email,final boolean avatar) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthdayDay = birthdayDay;
        this.gender = gender;
        this.aboutSelf = aboutSelf;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.avatar = avatar;
    }

    public void set(User user){
        this.id = user.id;
        this.name = user.name;
        this.surname = user.surname;
        this.birthdayDay = user.birthdayDay;
        this.gender = user.gender;
        this.aboutSelf = user.aboutSelf;
        this.telephoneNumber = user.telephoneNumber;
        this.email = user.email;
        this.avatar = user.avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public Date getBirthdayDay() {
        return birthdayDay;
    }

    public void setBirthdayDay(final Date birthdayDay) {
        this.birthdayDay = birthdayDay;
    }

    public short getGender() {
        return gender;
    }

    public void setGender(final short gender) {
        this.gender = gender;
    }

    public String getAboutSelf() {
        return aboutSelf;
    }

    public void setAboutSelf(final String aboutSelf) {
        this.aboutSelf = aboutSelf;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(final String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public boolean isAvatar() {
        return avatar;
    }

    public void setAvatar(final boolean avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthdayDay=" + birthdayDay +
                ", gender=" + gender +
                ", aboutSelf='" + aboutSelf + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", avatar=" + avatar +
                '}';
    }
}
