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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("List Implementation Tests - CustomList vs ArrayList")
class ListImplementationTest {


  private <T> void testAllLists(Integer capacity, Consumer<List<T>> test) {
    List<Supplier<List<T>>> factories = new ArrayList<>();
    if (capacity == null) {
      factories.add(ArrayList::new);
      factories.add(CustomList::new);
    } else {
      factories.add(() -> new ArrayList<>(capacity));
      factories.add(() -> new CustomList<>(capacity));
    }

    for (Supplier<List<T>> factory : factories) {
      List<T> list = factory.get();
      String listName = list.getClass().getSimpleName();
      try {
        test.accept(list);
        System.out.println("[PASSED] Test successful for " + listName);
      } catch (AssertionError e) {
        throw new AssertionError("Test failed for " + listName + ": " + e.getMessage(), e);
      } catch (Exception e) {
        throw new RuntimeException("Unexpected error for " + listName + ": " + e.getMessage(), e);
      }
    }
  }

  private <T> void testAllListsWithCapacity(int capacity, Consumer<List<T>> test) {
    testAllLists(capacity, test);
  }

  private <T> void testAllLists(Consumer<List<T>> test) {
    testAllLists(null, test);
  }

  @Nested
  @DisplayName("Constructor operations")
  class ConstructorTests {

    @Test
    @DisplayName("Default constructor should create empty list")
    void defaultConstructorShouldCreateEmptyList() {
      testAllLists((List<String> list) -> {
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
      });
    }

    @Test
    @DisplayName("Constructor with capacity should create empty list")
    void constructorWithCapacityShouldCreateEmptyList() {
      testAllListsWithCapacity(10, (List<String> list) -> {
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
      });
    }

    @Test
    @DisplayName("Constructor with zero capacity should work")
    void constructorWithZeroCapacityShouldWork() {
      testAllListsWithCapacity(0, (List<String> list) -> {
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());

        list.add("element");
        assertEquals(1, list.size());
      });
    }

    @Test
    @DisplayName("Constructor with negative capacity should throw IllegalArgumentException")
    void constructorWithNegativeCapacityShouldThrowException() {
      assertThrows(IllegalArgumentException.class, () -> testAllListsWithCapacity(-1, (List<String> list) -> {
      }));

      assertThrows(IllegalArgumentException.class, () -> testAllListsWithCapacity(-10, (List<String> list) -> {
      }));
    }
  }

  @Nested
  @DisplayName("Size and Empty operations")
  class SizeAndEmptyTests {

    @Test
    @DisplayName("size() should return 0 for empty list")
    void sizeShouldReturnZeroForEmptyList() {
      testAllLists((List<String> list) -> assertEquals(0, list.size()));
    }

    @Test
    @DisplayName("size() should return correct number after adding elements")
    void sizeShouldReturnCorrectNumberAfterAdding() {
      testAllLists((List<String> list) -> {
        list.add("first");
        assertEquals(1, list.size());

        list.add("second");
        assertEquals(2, list.size());

        list.add("third");
        assertEquals(3, list.size());
      });
    }

    @Test
    @DisplayName("isEmpty() should return true for new list")
    void isEmptyShouldReturnTrueForNewList() {
      testAllLists((List<String> list) -> assertTrue(list.isEmpty()));
    }

    @Test
    @DisplayName("isEmpty() should return false after adding element")
    void isEmptyShouldReturnFalseAfterAddingElement() {
      testAllLists((List<String> list) -> {
        list.add("element");
        assertFalse(list.isEmpty());
      });
    }

    @Test
    @DisplayName("isEmpty() should return true after clear()")
    void isEmptyShouldReturnTrueAfterClear() {
      testAllLists((List<String> list) -> {
        list.add("element");
        list.clear();
        assertTrue(list.isEmpty());
      });
    }
  }

  @Nested
  @DisplayName("Add operations")
  class AddTests {

    @Test
    @DisplayName("add() should return true when adding element")
    void addShouldReturnTrueWhenAddingElement() {
      testAllLists((List<String> list) -> assertTrue(list.add("element")));
    }

    @Test
    @DisplayName("add() should increase size by 1")
    void addShouldIncreaseSizeByOne() {
      testAllLists((List<String> list) -> {
        int initialSize = list.size();
        list.add("element");
        assertEquals(initialSize + 1, list.size());
      });
    }

    @Test
    @DisplayName("add() should allow adding null values")
    void addShouldAllowAddingNullValues() {
      testAllLists((List<String> list) -> {
        assertTrue(list.add(null));
        assertEquals(1, list.size());
      });
    }

    @Test
    @DisplayName("add() should allow adding duplicate elements")
    void addShouldAllowAddingDuplicateElements() {
      testAllLists((List<String> list) -> {
        list.add("duplicate");
        list.add("duplicate");
        assertEquals(2, list.size());
      });
    }

    @Test
    @DisplayName("add() should maintain insertion order")
    void addShouldMaintainInsertionOrder() {
      testAllLists((List<String> list) -> {
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

    @Test
    @DisplayName("add(index, element) should insert element at beginning")
    void addWithIndexShouldInsertElementAtBeginning() {
      testAllLists((List<String> list) -> {
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

    @Test
    @DisplayName("add(index, element) should insert element at middle")
    void addWithIndexShouldInsertElementAtMiddle() {
      testAllLists((List<String> list) -> {
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

    @Test
    @DisplayName("add(index, element) should insert element at end")
    void addWithIndexShouldInsertElementAtEnd() {
      testAllLists((List<String> list) -> {
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

    @Test
    @DisplayName("add(index, element) should insert element in empty list")
    void addWithIndexShouldInsertElementInEmptyList() {
      testAllLists((List<String> list) -> {
        list.add(0, "only_element");

        assertEquals(1, list.size());
        assertEquals("only_element", list.get(0));
      });
    }

    @Test
    @DisplayName("add(index, element) should increase size by 1")
    void addWithIndexShouldIncreaseSizeByOne() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");

        int originalSize = list.size();
        list.add(1, "inserted");
        assertEquals(originalSize + 1, list.size());
      });
    }

    @Test
    @DisplayName("add(index, element) should shift subsequent elements to the right")
    void addWithIndexShouldShiftSubsequentElementsToRight() {
      testAllLists((List<String> list) -> {
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

    @Test
    @DisplayName("add(index, element) should allow adding null values")
    void addWithIndexShouldAllowAddingNullValues() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");

        list.add(1, null);

        assertEquals(3, list.size());
        assertEquals("first", list.get(0));
        assertNull(list.get(1));
        assertEquals("second", list.get(2));
      });
    }

    @Test
    @DisplayName("add(index, element) should allow adding duplicate elements")
    void addWithIndexShouldAllowAddingDuplicateElements() {
      testAllLists((List<String> list) -> {
        list.add("duplicate");
        list.add("other");

        list.add(1, "duplicate");

        assertEquals(3, list.size());
        assertEquals("duplicate", list.get(0));
        assertEquals("duplicate", list.get(1));
        assertEquals("other", list.get(2));
      });
    }

    @Test
    @DisplayName("add(index, element) should throw IndexOutOfBoundsException for negative index")
    void addWithIndexShouldThrowExceptionForNegativeIndex() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");

        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "element"));
      });
    }

    @Test
    @DisplayName("add(index, element) should throw IndexOutOfBoundsException for index > size")
    void addWithIndexShouldThrowExceptionForIndexGreaterThanSize() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");

        assertThrows(IndexOutOfBoundsException.class, () -> list.add(3, "element"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(100, "element"));
      });
    }

    @Test
    @DisplayName("add(index, element) should work with index equal to size (append)")
    void addWithIndexShouldWorkWithIndexEqualToSize() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");

        int size = list.size();
        list.add(size, "appended");

        assertEquals(size + 1, list.size());
        assertEquals("appended", list.get(size));
      });
    }

    @Test
    @DisplayName("add(index, element) should handle multiple insertions correctly")
    void addWithIndexShouldHandleMultipleInsertionsCorrectly() {
      testAllLists((List<String> list) -> {
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

    @Test
    @DisplayName("add(index, element) should work after clear()")
    void addWithIndexShouldWorkAfterClear() {
      testAllLists((List<String> list) -> {
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

    @Test
    @DisplayName("get() should return correct element at valid index")
    void getShouldReturnCorrectElementAtValidIndex() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
        assertEquals("third", list.get(2));
      });
    }

    @Test
    @DisplayName("get() should throw IndexOutOfBoundsException for negative index")
    void getShouldThrowExceptionForNegativeIndex() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
      });
    }

    @Test
    @DisplayName("get() should throw IndexOutOfBoundsException for index >= size")
    void getShouldThrowExceptionForIndexGreaterThanSize() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(100));
      });
    }

    @Test
    @DisplayName("get() should throw IndexOutOfBoundsException for empty list")
    void getShouldThrowExceptionForEmptyList() {
      testAllLists((List<String> list) -> assertThrows(IndexOutOfBoundsException.class, () -> list.get(0)));
    }

    @Test
    @DisplayName("get() should return null if null was stored")
    void getShouldReturnNullIfNullWasStored() {
      testAllLists((List<String> list) -> {
        list.add(null);
        assertNull(list.get(0));
      });
    }
  }

  @Nested
  @DisplayName("Set operations")
  class SetTests {

    @Test
    @DisplayName("set() should replace element and return old value")
    void setShouldReplaceElementAndReturnOldValue() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        String oldValue = list.set(1, "new_second");
        assertEquals("second", oldValue);
        assertEquals("new_second", list.get(1));
      });
    }

    @Test
    @DisplayName("set() should not change list size")
    void setShouldNotChangeListSize() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        int originalSize = list.size();
        list.set(0, "new_first");
        assertEquals(originalSize, list.size());
      });
    }

    @Test
    @DisplayName("set() should throw IndexOutOfBoundsException for negative index")
    void setShouldThrowExceptionForNegativeIndex() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "element"));
      });
    }

    @Test
    @DisplayName("set() should throw IndexOutOfBoundsException for index >= size")
    void setShouldThrowExceptionForIndexGreaterThanSize() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, "element"));
      });
    }

    @Test
    @DisplayName("set() should allow setting null values")
    void setShouldAllowSettingNullValues() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        String oldValue = list.set(0, null);
        assertEquals("first", oldValue);
        assertNull(list.get(0));
      });
    }

    @Test
    @DisplayName("set() should work with single element list")
    void setShouldWorkWithSingleElementList() {
      testAllLists((List<String> list) -> {
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

    @Test
    @DisplayName("remove() should remove element and return it")
    void removeShouldRemoveElementAndReturnIt() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        String removed = list.remove(1);
        assertEquals("second", removed);
        assertEquals(2, list.size());
      });
    }

    @Test
    @DisplayName("remove() should decrease size by 1")
    void removeShouldDecreaseSizeByOne() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        int originalSize = list.size();
        list.remove(0);
        assertEquals(originalSize - 1, list.size());
      });
    }

    @Test
    @DisplayName("remove() should shift subsequent elements left")
    void removeShouldShiftSubsequentElementsLeft() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        list.remove(0);
        assertEquals("second", list.get(0));
        assertEquals("third", list.get(1));
      });
    }

    @Test
    @DisplayName("remove() should throw IndexOutOfBoundsException for negative index")
    void removeShouldThrowExceptionForNegativeIndex() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
      });
    }

    @Test
    @DisplayName("remove() should throw IndexOutOfBoundsException for index >= size")
    void removeShouldThrowExceptionForIndexGreaterThanSize() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(3));
      });
    }

    @Test
    @DisplayName("remove() should work with last element")
    void removeShouldWorkWithLastElement() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        String removed = list.remove(2);
        assertEquals("third", removed);
        assertEquals(2, list.size());
        assertEquals("second", list.get(1));
      });
    }

    @Test
    @DisplayName("remove() should work with single element list")
    void removeShouldWorkWithSingleElementList() {
      testAllLists((List<String> list) -> {
        list.add("only");

        String removed = list.remove(0);
        assertEquals("only", removed);
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
      });
    }

    @Test
    @DisplayName("remove() should handle null elements")
    void removeShouldHandleNullElements() {
      testAllLists((List<String> list) -> {
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

    @Test
    @DisplayName("clear() should make list empty")
    void clearShouldMakeListEmpty() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        list.clear();
        assertTrue(list.isEmpty());
      });
    }

    @Test
    @DisplayName("clear() should set size to 0")
    void clearShouldSetSizeToZero() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        list.clear();
        assertEquals(0, list.size());
      });
    }

    @Test
    @DisplayName("clear() should work on already empty list")
    void clearShouldWorkOnAlreadyEmptyList() {
      testAllLists((List<String> list) -> {
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
      });
    }

    @Test
    @DisplayName("clear() should make all elements inaccessible")
    void clearShouldMakeAllElementsInaccessible() {
      testAllLists((List<String> list) -> {
        list.add("first");
        list.add("second");
        list.add("third");

        list.clear();
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
      });
    }

    @Test
    @DisplayName("clear() should allow adding elements after clearing")
    void clearShouldAllowAddingElementsAfterClearing() {
      testAllLists((List<String> list) -> {
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

    @Test
    @DisplayName("Should work with Integer type")
    void shouldWorkWithIntegerType() {
      testAllLists((List<Integer> list) -> {
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

    @Test
    @DisplayName("Should work with String type")
    void shouldWorkWithStringType() {
      testAllLists((List<String> list) -> {
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

    @Test
    @DisplayName("Should work with custom object type")
    void shouldWorkWithCustomObjectType() {

      testAllLists((List<Person> list) -> {

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
    public int hashCode() {
      return name.hashCode();
    }

    @Override
    public String toString() {
      return "Person{name='" + name + "'}";
    }
  }
}
