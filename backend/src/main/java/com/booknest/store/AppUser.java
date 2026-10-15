package com.booknest.store;
import jakarta.persistence.*;
@Entity @Table(name="app_users")
public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(unique=true,nullable=false) public String email;
 public String name;
 @Column(nullable=false) public String password;
 public AppUser() {}
}
