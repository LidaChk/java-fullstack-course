package org.example.week01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

@DisplayName("List Implementation Tests - CustomList vs ArrayList")
class ListImplementationTest {

  private <T> Stream<DynamicTest> createTestsForEachList(String testName, Integer capacity,
      Consumer<List<T>> test) {
    List<Supplier<List<T>>> listFactories = new ArrayList<>();

    if (capacity == null) {
        listFactories.add(ArrayList::new);
        listFactories.add(CustomList::new);
    } else {
        listFactories.add(() -> new ArrayList<>(capacity));
        listFactories.add(() -> new CustomList<>(capacity));
    }

    Stream.Builder<DynamicTest> testBuilder = Stream.builder();

      for (Supplier<List<T>> factory : listFactories) {

      final String factoryName = factory.get().getClass().getSimpleName();

      testBuilder.add(DynamicTest.dynamicTest(testName + " [" + factoryName + "]", () -> {
        List<T> list = factory.get();
        test.accept(list);
      }));
    }
    return testBuilder.build();
  }

  @Nested
  @DisplayName("Constructor operations")
  class ConstructorTests {
    @TestFactory
    @DisplayName("Default constructor should create empty list")
    Stream<DynamicTest> defaultConstructorShouldCreateEmptyList() {
      return createTestsForEachList("Default constructor should create empty list", null,
          (List<String> list) -> {
            assertEquals(0, list.size());
            assertTrue(list.isEmpty());
          });
    }

    @TestFactory
    @DisplayName("Constructor with capacity should create empty list")
    Stream<DynamicTest> constructorWithCapacityShouldCreateEmptyList() {
      return createTestsForEachList("Constructor with capacity should create empty list", 10,
          (List<String> list) -> {
            assertEquals(0, list.size());
            assertTrue(list.isEmpty());
          });
    }

    @TestFactory
    @DisplayName("Constructor with zero capacity should work")
    Stream<DynamicTest> constructorWithZeroCapacityShouldWork() {
      return createTestsForEachList("Constructor with zero capacity should work", 0,
          (List<String> list) -> {
            assertEquals(0, list.size());
            assertTrue(list.isEmpty());
            list.add("element");
            assertEquals(1, list.size());
          });
    }

    @Test
    @DisplayName("Constructor with negative capacity should throw IllegalArgumentException")
    void constructorWithNegativeCapacityShouldThrowException() {
      assertThrows(IllegalArgumentException.class, () -> new CustomList<>(-1));
      assertThrows(IllegalArgumentException.class, () -> new ArrayList<>(-1));
      assertThrows(IllegalArgumentException.class, () -> new CustomList<>(-10));
      assertThrows(IllegalArgumentException.class, () -> new ArrayList<>(-10));
    }
  }

  @Nested
  @DisplayName("Size and Empty operations")
  class SizeAndEmptyTests {
    @TestFactory
    @DisplayName("size() should return 0 for empty list")
    Stream<DynamicTest> sizeShouldReturnZeroForEmptyList() {
      return createTestsForEachList("size() should return 0 for empty list", null,
          (List<String> list) -> assertEquals(0, list.size()));
    }

    @TestFactory
    @DisplayName("size() should return correct number after adding elements")
    Stream<DynamicTest> sizeShouldReturnCorrectNumberAfterAdding() {
      return createTestsForEachList(
          "size() should return correct number after adding elements", null,
          (List<String> list) -> {
            list.add("first");
            assertEquals(1, list.size());
            list.add("second");
            assertEquals(2, list.size());
            list.add("third");
            assertEquals(3, list.size());
          });
    }

    @TestFactory
    @DisplayName("isEmpty() should return true for new list")
    Stream<DynamicTest> isEmptyShouldReturnTrueForNewList() {
      return createTestsForEachList("isEmpty() should return true for new list", null,
          (List<String> list) -> assertTrue(list.isEmpty()));
    }

    @TestFactory
    @DisplayName("isEmpty() should return false after adding element")
    Stream<DynamicTest> isEmptyShouldReturnFalseAfterAddingElement() {
      return createTestsForEachList("isEmpty() should return false after adding element", null,
          (List<String> list) -> {
            list.add("element");
            assertFalse(list.isEmpty());
          });
    }

    @TestFactory
    @DisplayName("isEmpty() should return true after clear()")
    Stream<DynamicTest> isEmptyShouldReturnTrueAfterClear() {
      return createTestsForEachList("isEmpty() should return true after clear()", null,
          (List<String> list) -> {
            list.add("element");
            list.clear();
            assertTrue(list.isEmpty());
          });
    }
  }

  @Nested
  @DisplayName("Add operations")
  class AddTests {
    @TestFactory
    @DisplayName("add() should return true when adding element")
    Stream<DynamicTest> addShouldReturnTrueWhenAddingElement() {
      return createTestsForEachList("add() should return true when adding element", null,
          (List<String> list) -> assertTrue(list.add("element")));
    }

    @TestFactory
    @DisplayName("add() should increase size by 1")
    Stream<DynamicTest> addShouldIncreaseSizeByOne() {
      return createTestsForEachList("add() should increase size by 1", null,
          (List<String> list) -> {
            int initialSize = list.size();
            list.add("element");
            assertEquals(initialSize + 1, list.size());
          });
    }

    @TestFactory
    @DisplayName("add() should allow adding null values")
    Stream<DynamicTest> addShouldAllowAddingNullValues() {
      return createTestsForEachList("add() should allow adding null values", null,
          (List<String> list) -> {
            assertTrue(list.add(null));
            assertEquals(1, list.size());
          });
    }

    @TestFactory
    @DisplayName("add() should allow adding duplicate elements")
    Stream<DynamicTest> addShouldAllowAddingDuplicateElements() {
      return createTestsForEachList("add() should allow adding duplicate elements", null,
          (List<String> list) -> {
            list.add("duplicate");
            list.add("duplicate");
            assertEquals(2, list.size());
          });
    }

    @TestFactory
    @DisplayName("add() should maintain insertion order")
    Stream<DynamicTest> addShouldMaintainInsertionOrder() {
      return createTestsForEachList("add() should maintain insertion order", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));
            assertEquals("third", list.get(2));
          });
    }
  }

  @Nested
  @DisplayName("Add with index operations")
  class AddWithIndexTests {
    @TestFactory
    @DisplayName("add(index, element) should insert element at beginning")
    Stream<DynamicTest> addWithIndexShouldInsertElementAtBeginning() {
      return createTestsForEachList("add(index, element) should insert element at beginning",
          null, (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            list.add(0, "new_first");
            assertEquals(4, list.size());
            assertEquals("new_first", list.get(0));
            assertEquals("first", list.get(1));
            assertEquals("second", list.get(2));
            assertEquals("third", list.get(3));
          });
    }

    @TestFactory
    @DisplayName("add(index, element) should insert element at middle")
    Stream<DynamicTest> addWithIndexShouldInsertElementAtMiddle() {
      return createTestsForEachList("add(index, element) should insert element at middle", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            list.add(1, "new_middle");
            assertEquals(4, list.size());
            assertEquals("first", list.get(0));
            assertEquals("new_middle", list.get(1));
            assertEquals("second", list.get(2));
            assertEquals("third", list.get(3));
          });
    }

    @TestFactory
    @DisplayName("add(index, element) should insert element at end")
    Stream<DynamicTest> addWithIndexShouldInsertElementAtEnd() {
      return createTestsForEachList("add(index, element) should insert element at end", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            list.add(3, "new_last");
            assertEquals(4, list.size());
            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));
            assertEquals("third", list.get(2));
            assertEquals("new_last", list.get(3));
          });
    }

    @TestFactory
    @DisplayName("add(index, element) should insert element in empty list")
    Stream<DynamicTest> addWithIndexShouldInsertElementInEmptyList() {
      return createTestsForEachList("add(index, element) should insert element in empty list",
          null, (List<String> list) -> {
            list.add(0, "only_element");
            assertEquals(1, list.size());
            assertEquals("only_element", list.get(0));
          });
    }

    @TestFactory
    @DisplayName("add(index, element) should increase size by 1")
    Stream<DynamicTest> addWithIndexShouldIncreaseSizeByOne() {
      return createTestsForEachList("add(index, element) should increase size by 1", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            int originalSize = list.size();
            list.add(1, "inserted");
            assertEquals(originalSize + 1, list.size());
          });
    }

    @TestFactory
    @DisplayName("add(index, element) should shift subsequent elements to the right")
    Stream<DynamicTest> addWithIndexShouldShiftSubsequentElementsToRight() {
      return createTestsForEachList(
          "add(index, element) should shift subsequent elements to the right", null,
          (List<String> list) -> {
            list.add("element0");
            list.add("element1");
            list.add("element2");
            list.add("element3");
            list.add(2, "inserted");
            assertEquals("element0", list.get(0));
            assertEquals("element1", list.get(1));
            assertEquals("inserted", list.get(2));
            assertEquals("element2", list.get(3));
            assertEquals("element3", list.get(4));
          });
    }

    @TestFactory
    @DisplayName("add(index, element) should allow adding null values")
    Stream<DynamicTest> addWithIndexShouldAllowAddingNullValues() {
      return createTestsForEachList("add(index, element) should allow adding null values", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add(1, null);
            assertEquals(3, list.size());
            assertEquals("first", list.get(0));
            assertNull(list.get(1));
            assertEquals("second", list.get(2));
          });
    }

    @TestFactory
    @DisplayName("add(index, element) should allow adding duplicate elements")
    Stream<DynamicTest> addWithIndexShouldAllowAddingDuplicateElements() {
      return createTestsForEachList("add(index, element) should allow adding duplicate elements",
          null, (List<String> list) -> {
            list.add("duplicate");
            list.add("other");
            list.add(1, "duplicate");
            assertEquals(3, list.size());
            assertEquals("duplicate", list.get(0));
            assertEquals("duplicate", list.get(1));
            assertEquals("other", list.get(2));
          });
    }

    @TestFactory
    @DisplayName("add(index, element) should throw IndexOutOfBoundsException for negative index")
    Stream<DynamicTest> addWithIndexShouldThrowExceptionForNegativeIndex() {
      return createTestsForEachList(
          "add(index, element) should throw IndexOutOfBoundsException for negative index", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "element"));
          });
    }

    @TestFactory
    @DisplayName("add(index, element) should throw IndexOutOfBoundsException for index > size")
    Stream<DynamicTest> addWithIndexShouldThrowExceptionForIndexGreaterThanSize() {
      return createTestsForEachList(
          "add(index, element) should throw IndexOutOfBoundsException for index > size", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            assertThrows(IndexOutOfBoundsException.class, () -> list.add(3, "element"));
            assertThrows(IndexOutOfBoundsException.class, () -> list.add(100, "element"));
          });
    }

    @TestFactory
    @DisplayName("add(index, element) should work with index equal to size (append)")
    Stream<DynamicTest> addWithIndexShouldWorkWithIndexEqualToSize() {
      return createTestsForEachList(
          "add(index, element) should work with index equal to size (append)", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            int size = list.size();
            list.add(size, "appended");
            assertEquals(size + 1, list.size());
            assertEquals("appended", list.get(size));
          });
    }

    @TestFactory
    @DisplayName("add(index, element) should handle multiple insertions correctly")
    Stream<DynamicTest> addWithIndexShouldHandleMultipleInsertionsCorrectly() {
      return createTestsForEachList(
          "add(index, element) should handle multiple insertions correctly", null,
          (List<String> list) -> {
            list.add("a");
            list.add("b");
            list.add("c");
            list.add(1, "x"); // a, x, b, c
            list.add(3, "y"); // a, x, b, y, c
            list.add(0, "z"); // z, a, x, b, y, c
            assertEquals(6, list.size());
            assertEquals("z", list.get(0));
            assertEquals("a", list.get(1));
            assertEquals("x", list.get(2));
            assertEquals("b", list.get(3));
            assertEquals("y", list.get(4));
            assertEquals("c", list.get(5));
          });
    }

    @TestFactory
    @DisplayName("add(index, element) should work after clear()")
    Stream<DynamicTest> addWithIndexShouldWorkAfterClear() {
      return createTestsForEachList("add(index, element) should work after clear()", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.clear();
            list.add(0, "new_element");
            assertEquals(1, list.size());
            assertEquals("new_element", list.get(0));
          });
    }
  }

  @Nested
  @DisplayName("Get operations")
  class GetTests {
    @TestFactory
    @DisplayName("get() should return correct element at valid index")
    Stream<DynamicTest> getShouldReturnCorrectElementAtValidIndex() {
      return createTestsForEachList("get() should return correct element at valid index", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));
            assertEquals("third", list.get(2));
          });
    }

    @TestFactory
    @DisplayName("get() should throw IndexOutOfBoundsException for negative index")
    Stream<DynamicTest> getShouldThrowExceptionForNegativeIndex() {
      return createTestsForEachList(
          "get() should throw IndexOutOfBoundsException for negative index", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
          });
    }

    @TestFactory
    @DisplayName("get() should throw IndexOutOfBoundsException for index >= size")
    Stream<DynamicTest> getShouldThrowExceptionForIndexGreaterThanSize() {
      return createTestsForEachList(
          "get() should throw IndexOutOfBoundsException for index >= size", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(100));
          });
    }

    @TestFactory
    @DisplayName("get() should throw IndexOutOfBoundsException for empty list")
    Stream<DynamicTest> getShouldThrowExceptionForEmptyList() {
      return createTestsForEachList(
          "get() should throw IndexOutOfBoundsException for empty list", null,
          (List<String> list) -> assertThrows(IndexOutOfBoundsException.class,
              () -> list.get(0)));
    }

    @TestFactory
    @DisplayName("get() should return null if null was stored")
    Stream<DynamicTest> getShouldReturnNullIfNullWasStored() {
      return createTestsForEachList("get() should return null if null was stored", null,
          (List<String> list) -> {
            list.add(null);
            assertNull(list.get(0));
          });
    }
  }

  @Nested
  @DisplayName("Set operations")
  class SetTests {
    @TestFactory
    @DisplayName("set() should replace element and return old value")
    Stream<DynamicTest> setShouldReplaceElementAndReturnOldValue() {
      return createTestsForEachList("set() should replace element and return old value", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            String oldValue = list.set(1, "new_second");
            assertEquals("second", oldValue);
            assertEquals("new_second", list.get(1));
          });
    }

    @TestFactory
    @DisplayName("set() should not change list size")
    Stream<DynamicTest> setShouldNotChangeListSize() {
      return createTestsForEachList("set() should not change list size", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            int originalSize = list.size();
            list.set(0, "new_first");
            assertEquals(originalSize, list.size());
          });
    }

    @TestFactory
    @DisplayName("set() should throw IndexOutOfBoundsException for negative index")
    Stream<DynamicTest> setShouldThrowExceptionForNegativeIndex() {
      return createTestsForEachList(
          "set() should throw IndexOutOfBoundsException for negative index", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "element"));
          });
    }

    @TestFactory
    @DisplayName("set() should throw IndexOutOfBoundsException for index >= size")
    Stream<DynamicTest> setShouldThrowExceptionForIndexGreaterThanSize() {
      return createTestsForEachList(
          "set() should throw IndexOutOfBoundsException for index >= size", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, "element"));
          });
    }

    @TestFactory
    @DisplayName("set() should allow setting null values")
    Stream<DynamicTest> setShouldAllowSettingNullValues() {
      return createTestsForEachList("set() should allow setting null values", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            String oldValue = list.set(0, null);
            assertEquals("first", oldValue);
            assertNull(list.get(0));
          });
    }

    @TestFactory
    @DisplayName("set() should work with single element list")
    Stream<DynamicTest> setShouldWorkWithSingleElementList() {
      return createTestsForEachList("set() should work with single element list", null,
          (List<String> list) -> {
            list.add("only");
            String oldValue = list.set(0, "replaced");
            assertEquals("only", oldValue);
            assertEquals("replaced", list.get(0));
          });
    }
  }

  @Nested
  @DisplayName("Remove operations")
  class RemoveTests {
    @TestFactory
    @DisplayName("remove() should remove element and return it")
    Stream<DynamicTest> removeShouldRemoveElementAndReturnIt() {
      return createTestsForEachList("remove() should remove element and return it", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            String removed = list.remove(1);
            assertEquals("second", removed);
            assertEquals(2, list.size());
          });
    }

    @TestFactory
    @DisplayName("remove() should decrease size by 1")
    Stream<DynamicTest> removeShouldDecreaseSizeByOne() {
      return createTestsForEachList("remove() should decrease size by 1", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            int originalSize = list.size();
            list.remove(0);
            assertEquals(originalSize - 1, list.size());
          });
    }

    @TestFactory
    @DisplayName("remove() should shift subsequent elements left")
    Stream<DynamicTest> removeShouldShiftSubsequentElementsLeft() {
      return createTestsForEachList("remove() should shift subsequent elements left", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            list.remove(0);
            assertEquals("second", list.get(0));
            assertEquals("third", list.get(1));
          });
    }

    @TestFactory
    @DisplayName("remove() should throw IndexOutOfBoundsException for negative index")
    Stream<DynamicTest> removeShouldThrowExceptionForNegativeIndex() {
      return createTestsForEachList(
          "remove() should throw IndexOutOfBoundsException for negative index", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
          });
    }

    @TestFactory
    @DisplayName("remove() should throw IndexOutOfBoundsException for index >= size")
    Stream<DynamicTest> removeShouldThrowExceptionForIndexGreaterThanSize() {
      return createTestsForEachList(
          "remove() should throw IndexOutOfBoundsException for index >= size", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            assertThrows(IndexOutOfBoundsException.class, () -> list.remove(3));
          });
    }

    @TestFactory
    @DisplayName("remove() should work with last element")
    Stream<DynamicTest> removeShouldWorkWithLastElement() {
      return createTestsForEachList("remove() should work with last element", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            String removed = list.remove(2);
            assertEquals("third", removed);
            assertEquals(2, list.size());
            assertEquals("second", list.get(1));
          });
    }

    @TestFactory
    @DisplayName("remove() should work with single element list")
    Stream<DynamicTest> removeShouldWorkWithSingleElementList() {
      return createTestsForEachList("remove() should work with single element list", null,
          (List<String> list) -> {
            list.add("only");
            String removed = list.remove(0);
            assertEquals("only", removed);
            assertEquals(0, list.size());
            assertTrue(list.isEmpty());
          });
    }

    @TestFactory
    @DisplayName("remove() should handle null elements")
    Stream<DynamicTest> removeShouldHandleNullElements() {
      return createTestsForEachList("remove() should handle null elements", null,
          (List<String> list) -> {
            list.add("first");
            list.add(null);
            list.add("third");
            String removed = list.remove(1);
            assertNull(removed);
            assertEquals(2, list.size());
          });
    }
  }

  @Nested
  @DisplayName("Clear operations")
  class ClearTests {
    @TestFactory
    @DisplayName("clear() should make list empty")
    Stream<DynamicTest> clearShouldMakeListEmpty() {
      return createTestsForEachList("clear() should make list empty", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            list.clear();
            assertTrue(list.isEmpty());
          });
    }

    @TestFactory
    @DisplayName("clear() should set size to 0")
    Stream<DynamicTest> clearShouldSetSizeToZero() {
      return createTestsForEachList("clear() should set size to 0", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            list.clear();
            assertEquals(0, list.size());
          });
    }

    @TestFactory
    @DisplayName("clear() should work on already empty list")
    Stream<DynamicTest> clearShouldWorkOnAlreadyEmptyList() {
      return createTestsForEachList("clear() should work on already empty list", null,
          (List<String> list) -> {
            list.clear();
            assertEquals(0, list.size());
            assertTrue(list.isEmpty());
          });
    }

    @TestFactory
    @DisplayName("clear() should make all elements inaccessible")
    Stream<DynamicTest> clearShouldMakeAllElementsInaccessible() {
      return createTestsForEachList("clear() should make all elements inaccessible", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            list.clear();
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
          });
    }

    @TestFactory
    @DisplayName("clear() should allow adding elements after clearing")
    Stream<DynamicTest> clearShouldAllowAddingElementsAfterClearing() {
      return createTestsForEachList("clear() should allow adding elements after clearing", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            list.clear();
            assertTrue(list.add("new_element"));
            assertEquals(1, list.size());
            assertEquals("new_element", list.get(0));
          });
    }
  }

  @Nested
  @DisplayName("Generic Type Safety Tests")
  class GenericTypeTests {
    @TestFactory
    @DisplayName("Should work with Integer type")
    Stream<DynamicTest> shouldWorkWithIntegerType() {
      return createTestsForEachList("Should work with Integer type", null,
          (List<Integer> list) -> {
            list.add(1);
            list.add(2);
            list.add(3);
            assertEquals(1, list.get(0));
            assertEquals(2, list.get(1));
            assertEquals(3, list.get(2));
            list.set(1, 5);
            assertEquals(5, list.get(1));
            list.remove(0);
            assertEquals(2, list.size());
            assertEquals(5, list.get(0));
          });
    }

    @TestFactory
    @DisplayName("Should work with String type")
    Stream<DynamicTest> shouldWorkWithStringType() {
      return createTestsForEachList("Should work with String type", null,
          (List<String> list) -> {
            list.add("first");
            list.add("second");
            list.add("third");
            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));
            assertEquals("third", list.get(2));
            list.set(1, "fifth");
            assertEquals("fifth", list.get(1));
            list.remove(0);
            assertEquals(2, list.size());
            assertEquals("fifth", list.get(0));
          });
    }

    @TestFactory
    @DisplayName("Should work with custom object type")
    Stream<DynamicTest> shouldWorkWithCustomObjectType() {
      return createTestsForEachList("Should work with custom object type", null,
          (List<Person> list) -> {
            Person p1 = new Person("Alice");
            Person p2 = new Person("Bob");
            list.add(p1);
            list.add(p2);
            assertEquals(p1, list.get(0));
            assertEquals(p2, list.get(1));
            list.set(0, p2);
            assertEquals(p2, list.get(0));
            list.remove(1);
            assertEquals(1, list.size());
            assertEquals(p2, list.get(0));
          });
    }
  }

  static class Person {
    private final String name;

    public Person(String name) {
      this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (!(obj instanceof Person))
        return false;
      return name.equals(((Person) obj).name);
    }

    @Override
    public String toString() {
      return "Person{name='" + name + "'}";
    }
  }
}
