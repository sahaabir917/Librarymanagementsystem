package com.infinitycodehubltd.librarymanagement.user;

import jakarta.persistence.*;

@Entity
@Table
public class Member {
    @Id
    @SequenceGenerator(
            name = "member_sequence",
            sequenceName = "member_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "member_sequence"
    )

    private long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String phone;
    private String address;
    private String joined_date;

    public Member() {
    }

    public Member(String name,  String email, String phone, String address, String joined_date, long id) {
        this.name = name;

        this.email = email;
        this.phone = phone;
        this.address = address;
        this.joined_date = joined_date;
        this.id = id;
    }

    public Member(long id, String name, String email, String phone, String address, String joined_date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.joined_date = joined_date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJoined_date() {
        return joined_date;
    }

    public void setJoined_date(String joined_date) {
        this.joined_date = joined_date;
    }


    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", joined_date='" + joined_date + '\'' +
                ", mid=" + id +
                '}';
    }
}
