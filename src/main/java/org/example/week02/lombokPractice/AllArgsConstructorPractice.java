package org.example.week02.lombokpractice;

import lombok.AllArgsConstructor;

public class AllArgsConstructorPractice {

  public static void main(String[] args) {
    Book myBook = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1178, 25.99);

    System.out.println("Book: " + myBook);
  }

  @AllArgsConstructor
  public static class Book {
    private String title;
    private String author;
    private int pages;
    private double price;

//    Lombok generates constructor with args for  all fields:
//    public Book(String title, String author, int pages, double price) {
//      this.title = title;
//      this.author = author;
//      this.pages = pages;
//      this.price = price;
//    }

    public String toString() {
      return "Book{" +
              "title='" + title + '\'' +
              ", author='" + author + '\'' +
              ", pages=" + pages +
              ", price=" + price +
              '}';
    }
  }
}
