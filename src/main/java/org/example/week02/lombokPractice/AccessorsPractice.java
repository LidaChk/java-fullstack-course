package org.example.week02.lombokpractice;

import lombok.Setter;
import lombok.experimental.Accessors;


public class AccessorsPractice {

  public static void main(String[] args) {

    Configuration config = new Configuration()
        .setHost("localhost")
        .setPort(8080)
        .setSecure(true);

    System.out.println("Configuration: " + config);
  }

  @Setter
  @Accessors(chain = true)
  public static class Configuration {
    private String host;
    private int port;
    private boolean secure;

//    Lombok modifies the setters so that they can be used in a functional style (fluent API):
//    public Configuration setHost(String host) {
//      this.host = host;
//      return this;
//    }
//
//    public Configuration setPort(int port) {
//      this.port = port;
//      return this;
//    }
//
//    public Configuration setSecure(boolean secure) {
//      this.secure = secure;
//      return this;
//    }

    public String toString() {
      return "Configuration{" +
              "host='" + host + '\'' +
              ", port=" + port +
              ", secure=" + secure +
              '}';
    }
  }
}
