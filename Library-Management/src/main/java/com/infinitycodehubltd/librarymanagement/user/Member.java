//package com.infinitycodehubltd.librarymanagement.user;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table
//public class Member {
//    @Id
//    @SequenceGenerator(
//            name = "member_sequence",
//            sequenceName = "member_sequence",
//            allocationSize = 1
//    )
//
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "member_sequence"
//    )
//
//    private long id;
//    private String name;
//    @Column(unique = true)
//    private String email;
//    private String phone;
//    private String address;
//    private String joined_date;
//    private String password;
//
//    public Member() {
//    }
//
//    public Member(String name,  String email, String phone, String address, String joined_date, long id, String password) {
//        this.name = name;
//
//        this.email = email;
//        this.phone = phone;
//        this.address = address;
//        this.joined_date = joined_date;
//        this.id = id;
//        this.password = password;
//    }
//
//    public Member(long id, String name, String email, String phone, String address, String joined_date, String password) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.phone = phone;
//        this.address = address;
//        this.joined_date = joined_date;
//        this.password = password;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//
//
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getJoined_date() {
//        return joined_date;
//    }
//
//    public void setJoined_date(String joined_date) {
//        this.joined_date = joined_date;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @Override
//    public String toString() {
//        return "Member{" +
//                "name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", phone='" + phone + '\'' +
//                ", address='" + address + '\'' +
//                ", joined_date='" + joined_date + '\'' +
//                ", mid=" + id +
//                ",password=" + password+
//                '}';
//    }
//}


package com.infinitycodehubltd.librarymanagement.user;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {

    public enum Role {
        ADMIN, STAFF, USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_sequence")
    @SequenceGenerator(name = "member_sequence", sequenceName = "member_sequence", allocationSize = 1)
    private long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String phone;
    private String address;
    private String joined_date;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member() {}

    public Member(String name, String email, String phone, String address, String joined_date, String password, Role role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.joined_date = joined_date;
        this.password = password;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", joined_date='" + joined_date + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }


    // Getters and Setters
}


