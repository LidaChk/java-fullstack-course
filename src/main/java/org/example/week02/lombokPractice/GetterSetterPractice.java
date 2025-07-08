package org.example.week02.lombokpractice;

import lombok.Getter;
import lombok.Setter;

public class GetterSetterPractice {

  public static void main(String[] args) {
    Product product = new Product("Laptop", 1200.00, 10, "Powerful computing device");

    System.out.println("Initial Product: " + product);

    product.setName("Gaming Laptop");
    product.setQuantity(5);


    System.out.println("Updated Product Name: " + product.getName());
    System.out.println("Updated Product Quantity: " + product.quantity);
    System.out.println("Product Price: " + product.getPrice());

    System.out.println("Final Product: " + product);
  }

  public static class Product {
    @Getter
    @Setter
    private String name; // getName()  setName()

    @Getter
    private double price; // getPrice()

    @Setter
    private int quantity; // setQuantity()

    private String description; // no getter no setter



    public Product(String name, double price, int quantity, String description) {
      this.name = name;
      this.price = price;
      this.quantity = quantity;
      this.description = description;
    }

//    Lombok genegates getters and setters:
//    public String getName() {
//      return this.name;
//    }
//
//    public void setName(String name) {
//      this.name = name;
//    }
//
//    public double getPrice() {
//      return this.price;
//    }
//
//    public void setQuantity(int quantity) {
//      this.quantity = quantity;
//    }

    public String toString() {
      return "Product{" +
          "name='" + name + '\'' +
          ", price=" + price +
          ", quantity=" + quantity +
          ", description='" + description + '\'' +
          '}';
    }
  }
}
