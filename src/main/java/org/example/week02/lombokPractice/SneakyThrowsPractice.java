package org.example.week02.lombokpractice;

import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.SneakyThrows;

public class SneakyThrowsPractice {

  public static void main(String[] args) {

    String content = readFile("example.txt");
    System.out.println(content);

  }

  @SneakyThrows
  public static String readFile(String filePath) {
//  Lombok generates try-catch that trows RuntimeException
//  $ex -- cheating variable
//    try {
      return Files.readString(Paths.get(filePath));
//    } catch (Throwable $ex) {
//      throw $ex; -- cheating  variable, that erase types
//    }
  }
}
