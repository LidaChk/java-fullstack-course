package org.example.week02.testrunner.sampletest;

import org.example.week02.testrunner.annotation.AfterEach;
import org.example.week02.testrunner.annotation.BeforeEach;

public class BeforeEachAfterEachTest extends BaseSampleTest {

  @BeforeEach
  void setUp() {
    System.out.println("BeforeEach");
  }

  @AfterEach
  void tearDown() {
    System.out.println("AfterEach");
  }

}
