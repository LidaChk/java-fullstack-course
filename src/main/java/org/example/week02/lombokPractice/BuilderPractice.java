package org.example.week02.lombokpractice;

import lombok.Builder;

public class BuilderPractice {

  public static void main(String[] args) {

    House myHouse = House.builder()
            .address("123 Main St")
            .rooms(4)
            .area(150.5)
            .price(250000.0)
            .build();

    System.out.println("House: " + myHouse);
  }

  @Builder
  public static class House {
    private String address;
    private int rooms;
    private double area;
    private double price;

//    Lombok generates builder pattern:
//    House(String address, int rooms, double area, double price) {
//      this.address = address;
//      this.rooms = rooms;
//      this.area = area;
//      this.price = price;
//    }
//
//    public static HouseBuilder builder() {
//      return new HouseBuilder();
//    }
//
//    public static class HouseBuilder {
//      private String address;
//      private int rooms;
//      private double area;
//      private double price;
//
//      HouseBuilder() {
//      }
//
//      public HouseBuilder address(String address) {
//        this.address = address;
//        return this;
//      }
//
//      public HouseBuilder rooms(int rooms) {
//        this.rooms = rooms;
//        return this;
//      }
//
//      public HouseBuilder area(double area) {
//        this.area = area;
//        return this;
//      }
//
//      public HouseBuilder price(double price) {
//        this.price = price;
//        return this;
//      }
//
//      public House build() {
//        return new House(this.address, this.rooms, this.area, this.price);
//      }

    public String toString() {
      return "House{" +
          "address='" + address + '\'' +
          ", rooms=" + rooms +
          ", area=" + area +
          ", price=" + price +
          '}';
    }
  }

}
