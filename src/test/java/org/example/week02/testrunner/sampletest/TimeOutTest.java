package org.example.week02.testrunner.sampletest;

import org.example.week02.testrunner.annotation.Test;

public class TimeOutTest {

  @Test(timeout = 1000, description = "Test failed with timeout")
  public void timeOutTest() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Test(timeout = 1000, description = "Test with timeout passed")
  public void timeOutPassedTest() {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
