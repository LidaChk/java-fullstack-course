package org.example.week02.lombokpractice;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class RequiredArgsConstructorPractice {

    public static void main(String[] args) {
        Car myCar = new Car("Toyota", "Camry", "123");

        System.out.println("Car: " + myCar);
    }

    @RequiredArgsConstructor
    public static class Car {
        private final String brand;
        private final String model;
        @NonNull
        private String registrationNumber;
        private String color;


//        Lombok generates constructor with args for required and non-null fields:
//        public Car(String brand, String model, @NonNull String registrationNumber) {
//            if (registrationNumber == null) {
//                throw new NullPointerException("registrationNumber is marked non-null but is null");
//            } else {
//                this.brand = brand;
//                this.model = model;
//                this.registrationNumber = registrationNumber;
//            }
//        }

        public String toString() {
            return "Car{" +
                    "brand='" + brand + '\'' +
                    ", model='" + model + '\'' +
                    ", color='" + color + '\'' +
                    ", registrationNumber='" + registrationNumber + '\'' +
                    '}';
        }
    }
}
