package org.example.week01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CustomListTest {

    @Nested
    @DisplayName("Size and Empty operations")
    class SizeAndEmptyTests {

        private CustomList<String> list;

        @BeforeEach
        void setUp() {
            list = new CustomList<>();
        }

        @Test
        @DisplayName("size() should return 0 for empty list")
        void sizeShouldReturnZeroForEmptyList() {
            assertEquals(0, list.size());
        }

        @Test
        @DisplayName("size() should return correct number after adding elements")
        void sizeShouldReturnCorrectNumberAfterAdding() {
            list.add("first");
            assertEquals(1, list.size());

            list.add("second");
            assertEquals(2, list.size());

            list.add("third");
            assertEquals(3, list.size());
        }

        @Test
        @DisplayName("isEmpty() should return true for new list")
        void isEmptyShouldReturnTrueForNewList() {
            assertTrue(list.isEmpty());
        }

        @Test
        @DisplayName("isEmpty() should return false after adding element")
        void isEmptyShouldReturnFalseAfterAddingElement() {
            list.add("element");
            assertFalse(list.isEmpty());
        }

        @Test
        @DisplayName("isEmpty() should return true after clear()")
        void isEmptyShouldReturnTrueAfterClear() {
            list.add("element");
            list.clear();
            assertTrue(list.isEmpty());
        }
    }

    @Nested
    @DisplayName("Add operations")
    class AddTests {

        private CustomList<String> list;

        @BeforeEach
        void setUp() {
            list = new CustomList<>();
        }

        @Test
        @DisplayName("add() should return true when adding element")
        void addShouldReturnTrueWhenAddingElement() {
            assertTrue(list.add("element"));
        }

        @Test
        @DisplayName("add() should increase size by 1")
        void addShouldIncreaseSizeByOne() {
            int initialSize = list.size();
            list.add("element");
            assertEquals(initialSize + 1, list.size());
        }

        @Test
        @DisplayName("add() should allow adding null values")
        void addShouldAllowAddingNullValues() {
            assertTrue(list.add(null));
            assertEquals(1, list.size());
        }

        @Test
        @DisplayName("add() should allow adding duplicate elements")
        void addShouldAllowAddingDuplicateElements() {
            list.add("duplicate");
            list.add("duplicate");
            assertEquals(2, list.size());
        }

        @Test
        @DisplayName("add() should maintain insertion order")
        void addShouldMaintainInsertionOrder() {
            list.add("first");
            list.add("second");
            list.add("third");

            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));
            assertEquals("third", list.get(2));
        }
    }

    @Nested
    @DisplayName("Get operations")
    class GetTests {

        private CustomList<String> list;

        @BeforeEach
        void setUp() {
            list = new CustomList<>();
            list.add("first");
            list.add("second");
            list.add("third");
        }

        @Test
        @DisplayName("get() should return correct element at valid index")
        void getShouldReturnCorrectElementAtValidIndex() {
            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));
            assertEquals("third", list.get(2));
        }

        @Test
        @DisplayName("get() should throw IndexOutOfBoundsException for negative index")
        void getShouldThrowExceptionForNegativeIndex() {
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        }

        @Test
        @DisplayName("get() should throw IndexOutOfBoundsException for index >= size")
        void getShouldThrowExceptionForIndexGreaterThanSize() {
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(100));
        }

        @Test
        @DisplayName("get() should throw IndexOutOfBoundsException for empty list")
        void getShouldThrowExceptionForEmptyList() {
            CustomList<String> emptyList = new CustomList<>();
            assertThrows(IndexOutOfBoundsException.class, () -> emptyList.get(0));
        }

        @Test
        @DisplayName("get() should return null if null was stored")
        void getShouldReturnNullIfNullWasStored() {
            CustomList<String> nullList = new CustomList<>();
            nullList.add(null);
            assertNull(nullList.get(0));
        }
    }

    @Nested
    @DisplayName("Set operations")
    class SetTests {

        private CustomList<String> list;

        @BeforeEach
        void setUp() {
            list = new CustomList<>();
            list.add("first");
            list.add("second");
            list.add("third");
        }

        @Test
        @DisplayName("set() should replace element and return old value")
        void setShouldReplaceElementAndReturnOldValue() {
            String oldValue = list.set(1, "new_second");
            assertEquals("second", oldValue);
            assertEquals("new_second", list.get(1));
        }

        @Test
        @DisplayName("set() should not change list size")
        void setShouldNotChangeListSize() {
            int originalSize = list.size();
            list.set(0, "new_first");
            assertEquals(originalSize, list.size());
        }

        @Test
        @DisplayName("set() should throw IndexOutOfBoundsException for negative index")
        void setShouldThrowExceptionForNegativeIndex() {
            assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "element"));
        }

        @Test
        @DisplayName("set() should throw IndexOutOfBoundsException for index >= size")
        void setShouldThrowExceptionForIndexGreaterThanSize() {
            assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, "element"));
        }

        @Test
        @DisplayName("set() should allow setting null values")
        void setShouldAllowSettingNullValues() {
            String oldValue = list.set(0, null);
            assertEquals("first", oldValue);
            assertNull(list.get(0));
        }

        @Test
        @DisplayName("set() should work with single element list")
        void setShouldWorkWithSingleElementList() {
            CustomList<String> singleList = new CustomList<>();
            singleList.add("only");

            String oldValue = singleList.set(0, "replaced");
            assertEquals("only", oldValue);
            assertEquals("replaced", singleList.get(0));
        }
    }

    @Nested
    @DisplayName("Remove operations")
    class RemoveTests {

        private CustomList<String> list;

        @BeforeEach
        void setUp() {
            list = new CustomList<>();
            list.add("first");
            list.add("second");
            list.add("third");
        }

        @Test
        @DisplayName("remove() should remove element and return it")
        void removeShouldRemoveElementAndReturnIt() {
            String removed = list.remove(1);
            assertEquals("second", removed);
            assertEquals(2, list.size());
        }

        @Test
        @DisplayName("remove() should decrease size by 1")
        void removeShouldDecreaseSizeByOne() {
            int originalSize = list.size();
            list.remove(0);
            assertEquals(originalSize - 1, list.size());
        }

        @Test
        @DisplayName("remove() should shift subsequent elements left")
        void removeShouldShiftSubsequentElementsLeft() {
            list.remove(0);
            assertEquals("second", list.get(0));
            assertEquals("third", list.get(1));
        }

        @Test
        @DisplayName("remove() should throw IndexOutOfBoundsException for negative index")
        void removeShouldThrowExceptionForNegativeIndex() {
            assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        }

        @Test
        @DisplayName("remove() should throw IndexOutOfBoundsException for index >= size")
        void removeShouldThrowExceptionForIndexGreaterThanSize() {
            assertThrows(IndexOutOfBoundsException.class, () -> list.remove(3));
        }

        @Test
        @DisplayName("remove() should work with last element")
        void removeShouldWorkWithLastElement() {
            String removed = list.remove(2);
            assertEquals("third", removed);
            assertEquals(2, list.size());
            assertEquals("second", list.get(1));
        }

        @Test
        @DisplayName("remove() should work with single element list")
        void removeShouldWorkWithSingleElementList() {
            CustomList<String> singleList = new CustomList<>();
            singleList.add("only");

            String removed = singleList.remove(0);
            assertEquals("only", removed);
            assertEquals(0, singleList.size());
            assertTrue(singleList.isEmpty());
        }

        @Test
        @DisplayName("remove() should handle null elements")
        void removeShouldHandleNullElements() {
            CustomList<String> nullList = new CustomList<>();
            nullList.add("first");
            nullList.add(null);
            nullList.add("third");

            String removed = nullList.remove(1);
            assertNull(removed);
            assertEquals(2, nullList.size());
        }
    }

    @Nested
    @DisplayName("Clear operations")
    class ClearTests {

        private CustomList<String> list;

        @BeforeEach
        void setUp() {
            list = new CustomList<>();
            list.add("first");
            list.add("second");
            list.add("third");
        }

        @Test
        @DisplayName("clear() should make list empty")
        void clearShouldMakeListEmpty() {
            list.clear();
            assertTrue(list.isEmpty());
        }

        @Test
        @DisplayName("clear() should set size to 0")
        void clearShouldSetSizeToZero() {
            list.clear();
            assertEquals(0, list.size());
        }

        @Test
        @DisplayName("clear() should work on already empty list")
        void clearShouldWorkOnAlreadyEmptyList() {
            CustomList<String> emptyList = new CustomList<>();
            emptyList.clear();
            assertEquals(0, emptyList.size());
            assertTrue(emptyList.isEmpty());
        }

        @Test
        @DisplayName("clear() should make all elements inaccessible")
        void clearShouldMakeAllElementsInaccessible() {
            list.clear();
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        }

        @Test
        @DisplayName("clear() should allow adding elements after clearing")
        void clearShouldAllowAddingElementsAfterClearing() {
            list.clear();
            assertTrue(list.add("new_element"));
            assertEquals(1, list.size());
            assertEquals("new_element", list.get(0));
        }
    }

}
