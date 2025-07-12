package org.example.week02.arrayoperations;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ArrayOperationsTest {

  private static int[] testArray;

  private static Stream<Arguments> provideShiftMethods() {
    return Stream.of(
        Arguments.of((BiConsumer<int[], Integer>) ArrayOperations::shiftLeftSystemCopy),
        Arguments.of((BiConsumer<int[], Integer>) ArrayOperations::shiftLeftManualLoop));
  }

  @BeforeAll
  static void setUp() {
    testArray = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
  }

  @ParameterizedTest
  @MethodSource("provideShiftMethods")
  @DisplayName("Test illegal index")
  void testIllegalIndex(BiConsumer<int[], Integer> shiftMethod) {
    assertThrows(IllegalArgumentException.class, () -> shiftMethod.accept(testArray, -1));
    assertThrows(IllegalArgumentException.class, () -> shiftMethod.accept(testArray, testArray.length + 1));
  }

  @ParameterizedTest
  @MethodSource("provideShiftMethods")
  @DisplayName("Test shift left correctness")
  void testShiftLeftСorrectness(BiConsumer<int[], Integer> shiftMethod) {
    int[] copyOfTestArray = Arrays.copyOf(testArray, testArray.length);
    shiftMethod.accept(copyOfTestArray, 3);
    assertArrayEquals(new int[] { 4, 5, 6, 7, 8, 9, 10, 8, 9, 10 }, copyOfTestArray);
  }

  @ParameterizedTest
  @MethodSource("provideShiftMethods")
  @DisplayName("Test shift zero correctness")
  void testShiftZeroСorrectness(BiConsumer<int[], Integer> shiftMethod) {
    int[] copyOfTestArray = Arrays.copyOf(testArray, testArray.length);
    shiftMethod.accept(copyOfTestArray, 0);
    assertArrayEquals(testArray, copyOfTestArray);
  }

}
