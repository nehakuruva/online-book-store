package com.booknest.store;
import jakarta.persistence.*;
@Entity
public class Book {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 public String title; public String author; public String category; public double price;
 public double rating; public String coverUrl; public String description;
 public Book() {}
 public Book(String title,String author,String category,double price,double rating,String coverUrl,String description){
  this.title=title;this.author=author;this.category=category;this.price=price;this.rating=rating;this.coverUrl=coverUrl;this.description=description;
 }
}
