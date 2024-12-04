package com.example.classwork;

public class Book {
    private String title;
    private String author;
    private String imagePath;

    public Book(String title, String author, String imagePath) {
        this.title = title;
        this.author = author;
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
