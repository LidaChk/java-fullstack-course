package org.example.week03;

import org.example.week03.customlinkedlist.CustomLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CustomLinkedListTest {

    private CustomLinkedList<String> customList;
    private LinkedList<String> expectedList;

    @BeforeEach
    void setUp() {
        customList = new CustomLinkedList<>();
        expectedList = new LinkedList<>();
    }

    // =============
    // Deque methods
    // =============

    @Test
    @DisplayName("Deque addFirst adds element to the front")
    void testAddFirst() {
        customList.addFirst("B");
        expectedList.addFirst("B");
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("Deque addLast adds element to the end")
    void testAddLast() {
        customList.addLast("A");
        expectedList.addLast("A");
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("Deque offerFirst adds element to the front")
    void testOfferFirst() {
        assertTrue(customList.offerFirst("X"));
        expectedList.offerFirst("X");
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("Deque offerLast adds element to the end")
    void testOfferLast() {
        assertTrue(customList.offerLast("Y"));
        expectedList.offerLast("Y");
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("Deque removeFirst removes and returns the first element")
    void testRemoveFirst() {
        customList.addFirst("A");
        expectedList.addFirst("A");
        assertEquals(expectedList.removeFirst(), customList.removeFirst());
    }

    @Test
    @DisplayName("Deque removeLast removes and returns the last element")
    void testRemoveLast() {
        customList.addLast("B");
        expectedList.addLast("B");
        assertEquals(expectedList.removeLast(), customList.removeLast());
    }

    @Test
    @DisplayName("Deque pollFirst removes and returns the first element or null")
    void testPollFirst() {
        assertNull(customList.pollFirst());
        customList.addFirst("A");
        expectedList.addFirst("A");
        assertEquals(expectedList.pollFirst(), customList.pollFirst());
    }

    @Test
    @DisplayName("Deque pollLast removes and returns the last element or null")
    void testPollLast() {
        assertNull(customList.pollLast());
        customList.addLast("B");
        expectedList.addLast("B");
        assertEquals(expectedList.pollLast(), customList.pollLast());
    }

    @Test
    @DisplayName("Deque getFirst returns the first element")
    void testGetFirst() {
        customList.addFirst("A");
        expectedList.addFirst("A");
        assertEquals(expectedList.getFirst(), customList.getFirst());
    }

    @Test
    @DisplayName("Deque getLast returns the last element")
    void testGetLast() {
        customList.addLast("B");
        expectedList.addLast("B");
        assertEquals(expectedList.getLast(), customList.getLast());
    }

    @Test
    @DisplayName("Deque peekFirst returns the first element or null")
    void testPeekFirst() {
        assertNull(customList.peekFirst());
        customList.addFirst("A");
        expectedList.addFirst("A");
        assertEquals(expectedList.peekFirst(), customList.peekFirst());
    }

    @Test
    @DisplayName("Deque peekLast returns the last element or null")
    void testPeekLast() {
        assertNull(customList.peekLast());
        customList.addLast("B");
        expectedList.addLast("B");
        assertEquals(expectedList.peekLast(), customList.peekLast());
    }

    @Test
    @DisplayName("Deque push adds element to the front")
    void testPush() {
        customList.push("X");
        expectedList.push("X");
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("Deque pop removes and returns the first element")
    void testPop() {
        customList.push("X");
        expectedList.push("X");
        assertEquals(expectedList.pop(), customList.pop());
    }

    // =============
    // List methods
    // =============

    @Test
    @DisplayName("List add appends element to the end")
    void testAdd() {
        customList.add("A");
        expectedList.add("A");
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("List remove removes the first element")
    void testRemove() {
        customList.add("A");
        expectedList.add("A");
        assertEquals(expectedList.remove(), customList.remove());
    }

    @Test
    @DisplayName("List get returns element at the specified index")
    void testGet() {
        customList.addAll(Arrays.asList("A", "B", "C"));
        expectedList.addAll(Arrays.asList("A", "B", "C"));
        assertEquals(expectedList.get(1), customList.get(1));
    }

    @Test
    @DisplayName("List set replaces element at the specified index")
    void testSet() {
        customList.addAll(Arrays.asList("A", "B", "C"));
        expectedList.addAll(Arrays.asList("A", "B", "C"));
        assertEquals(expectedList.set(1, "X"), customList.set(1, "X"));
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("List add with index inserts element at specified position")
    void testAddAtIndex() {
        customList.add("A");
        customList.add("C");
        expectedList.add("A");
        expectedList.add("C");
        customList.add(1, "B");
        expectedList.add(1, "B");
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("List remove by index removes element at specified position")
    void testRemoveByIndex() {
        customList.addAll(Arrays.asList("A", "B", "C"));
        expectedList.addAll(Arrays.asList("A", "B", "C"));
        assertEquals(expectedList.remove(1), customList.remove(1));
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("List contains returns true if element exists")
    void testContains() {
        customList.add("A");
        expectedList.add("A");
        assertTrue(customList.contains("A"));
        assertFalse(customList.contains("X"));
    }


    @Test
    @DisplayName("List containsAll returns true if all elements exist")
    void testContainsAll() {
        customList.addAll(Arrays.asList("A", "B", "C"));
        expectedList.addAll(Arrays.asList("A", "B", "C"));
        assertTrue(customList.containsAll(expectedList));
    }

    @Test
    @DisplayName("List addAll appends all elements to the end")
    void testAddAll() {
        Collection<String> coll = Arrays.asList("A", "B", "C");
        assertTrue(customList.addAll(coll));
        expectedList.addAll(coll);
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("List addAll with index inserts elements at specified position")
    void testAddAllWithIndex() {
        customList.addAll(Arrays.asList("A", "D"));
        expectedList.addAll(Arrays.asList("A", "D"));
        Collection<String> coll = Arrays.asList("B", "C");
        customList.addAll(1, coll);
        expectedList.addAll(1, coll);
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("List removeAll removes all matching elements")
    void testRemoveAll() {
        customList.addAll(Arrays.asList("A", "B", "C"));
        expectedList.addAll(Arrays.asList("A", "B", "C"));
        Collection<String> coll = Arrays.asList("A", "C");
        customList.removeAll(coll);
        expectedList.removeAll(coll);
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("List retainAll keeps only matching elements")
    void testRetainAll() {
        customList.addAll(Arrays.asList("A", "B", "C"));
        expectedList.addAll(Arrays.asList("A", "B", "C"));
        Collection<String> coll = Arrays.asList("B", "C");
        customList.retainAll(coll);
        expectedList.retainAll(coll);
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("List removeFirstOccurrence removes the first matching element")
    void testRemoveFirstOccurrence() {
        customList.addAll(Arrays.asList("A", "B", "A"));
        expectedList.addAll(Arrays.asList("A", "B", "A"));
        assertTrue(customList.removeFirstOccurrence("A"));
        expectedList.removeFirstOccurrence("A");
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("List removeLastOccurrence removes the last matching element")
    void testRemoveLastOccurrence() {
        customList.addAll(Arrays.asList("A", "B", "A"));
        expectedList.addAll(Arrays.asList("A", "B", "A"));
        assertTrue(customList.removeLastOccurrence("A"));
        expectedList.removeLastOccurrence("A");
        assertEquals(expectedList, customList);
    }

    @Test
    @DisplayName("List iterator traverses elements in order")
    void testIterator() {
        customList.addAll(Arrays.asList("A", "B", "C"));
        expectedList.addAll(Arrays.asList("A", "B", "C"));

        Iterator<String> customIter = customList.iterator();
        Iterator<String> expectedIter = expectedList.iterator();

        while (customIter.hasNext()) {
            assertTrue(expectedIter.hasNext());
            assertEquals(expectedIter.next(), customIter.next());
        }
        assertFalse(expectedIter.hasNext());
    }

    @Test
    @DisplayName("List descendingIterator traverses elements in reverse order")
    void testDescendingIterator() {
        customList.addAll(Arrays.asList("A", "B", "C"));
        expectedList.addAll(Arrays.asList("A", "B", "C"));

        Iterator<String> customIter = customList.descendingIterator();
        Iterator<String> expectedIter = expectedList.descendingIterator();

        while (customIter.hasNext()) {
            assertTrue(expectedIter.hasNext());
            assertEquals(expectedIter.next(), customIter.next());
        }
        assertFalse(expectedIter.hasNext());
    }

    @Test
    @DisplayName("List size returns correct number of elements")
    void testSize() {
        customList.add("A");
        expectedList.add("A");
        assertEquals(expectedList.size(), customList.size());
    }

    @DisplayName("List isEmpty returns true when list is empty")
    @Test
    void testIsEmpty() {
        assertTrue(customList.isEmpty());
        customList.add("A");
        assertFalse(customList.isEmpty());
    }

    @DisplayName("List clear removes all elements")
    @Test
    void testClear() {
        customList.add("A");
        customList.add("B");
        customList.clear();
        assertTrue(customList.isEmpty());
    }

    // =============
    // Null handling
    // =============

    @Test
    @DisplayName("List add allows null and handles it correctly")
    void testAddNull() {
        customList.add(null);
        expectedList.add(null);
        assertTrue(customList.contains(null));
    }

    @Test
    @DisplayName("List remove(null) removes null element")
    void testRemoveNull() {
        customList.add(null);
        expectedList.add(null);
        assertTrue(customList.remove(null));
        assertFalse(customList.contains(null));
    }

    @Test
    @DisplayName("List contains(null) returns true if null is present")
    void testContainsNull() {
        customList.add(null);
        assertTrue(customList.contains(null));
    }

    @Test
    @DisplayName("List removeFirstOccurrence(null) removes null element")
    void testRemoveFirstOccurrenceNull() {
        customList.add(null);
        customList.add("A");
        customList.add(null);
        assertTrue(customList.removeFirstOccurrence(null));
        assertEquals(1, customList.indexOf(null));
    }

    @Test
    @DisplayName("List removeLastOccurrence(null) removes last null element")
    void testRemoveLastOccurrenceNull() {
        customList.add(null);
        customList.add("A");
        customList.add(null);
        assertTrue(customList.removeLastOccurrence(null));
        assertEquals(0, customList.lastIndexOf(null));
    }

    @Test
    @DisplayName("List set(null) replaces element with null")
    void testSetNull() {
        customList.add("A");
        assertEquals("A", customList.set(0, null));
        assertNull(customList.get(0));
    }

    @Test
    @DisplayName("List add(null) is supported")
    void testAddNullElement() {
        customList.add(null);
        assertNull(customList.get(0));
    }

    @Test
    @DisplayName("List contains(null) returns true if null exists")
    void testContainsNullElement() {
        customList.add(null);
        assertTrue(customList.contains(null));
    }

    @Test
    @DisplayName("List remove(null) removes null element")
    void testRemoveNullElement() {
        customList.add(null);
        customList.add("A");
        assertTrue(customList.remove(null));
        assertFalse(customList.contains(null));
    }

    // =============
    // Index methods
    // =============

    @Test
    @DisplayName("List get retrieves element at index")
    void testGetByIndex() {
        customList.addAll(Arrays.asList("A", "B", "C"));
        expectedList.addAll(Arrays.asList("A", "B", "C"));
        assertEquals(expectedList.get(1), customList.get(1));
    }

    @Test
    @DisplayName("List indexOf returns index of element")
    void testIndexOf() {
        customList.addAll(Arrays.asList("A", "B", "C"));
        expectedList.addAll(Arrays.asList("A", "B", "C"));
        assertEquals(expectedList.indexOf("B"), customList.indexOf("B"));
    }

    @Test
    @DisplayName("List lastIndexOf returns last index of element")
    void testLastIndexOf() {
        customList.addAll(Arrays.asList("A", "B", "B", "C"));
        expectedList.addAll(Arrays.asList("A", "B", "B", "C"));
        assertEquals(expectedList.lastIndexOf("B"), customList.lastIndexOf("B"));
    }

    // =============
    // Edge cases
    // =============

    @Test
    @DisplayName("List addAll with empty collection returns false")
    void testAddAllWithEmptyCollection() {
        Collection<String> empty = new ArrayList<>();
        assertFalse(customList.addAll(empty));
    }

    @Test
    @DisplayName("List removeAll with empty collection returns false")
    void testRemoveAllWithEmptyCollection() {
        customList.add("A");
        Collection<String> empty = new ArrayList<>();
        assertFalse(customList.removeAll(empty));
    }

    @Test
    @DisplayName("List retainAll with empty collection clears list")
    void testRetainAllWithEmptyCollection() {
        customList.add("A");
        Collection<String> empty = new ArrayList<>();
        assertTrue(customList.retainAll(empty));
        assertTrue(customList.isEmpty());
    }

    @Test
    @DisplayName("List remove with index returns correct element")
    void testRemoveByIndexReturnsCorrectElement() {
        customList.add("A");
        customList.add("B");
        assertEquals("A", customList.remove(0));
        assertEquals("B", customList.get(0));
    }

    @Test
    @DisplayName("List set returns previous element")
    void testSetReturnsOldValue() {
        customList.add("A");
        assertEquals("A", customList.set(0, "B"));
        assertEquals("B", customList.get(0));
    }

    @Test
    @DisplayName("List add with index adds element at correct position")
    void testAddAtIndexAddsCorrectly() {
        customList.add("A");
        customList.add("C");
        customList.add(1, "B");
        assertEquals("B", customList.get(1));
    }

    @Test
    @DisplayName("List addAll with index adds elements in order")
    void testAddAllWithIndexAddsCorrectly() {
        customList.add("A");
        customList.add("D");
        Collection<String> coll = Arrays.asList("B", "C");
        customList.addAll(1, coll);
        assertEquals("B", customList.get(1));
        assertEquals("C", customList.get(2));
    }

    @Test
    @DisplayName("List remove with index removes correct element")
    void testRemoveAtIndex() {
        customList.addAll(Arrays.asList("A", "B", "C"));
        assertEquals("B", customList.remove(1));
        assertEquals("C", customList.get(1));
    }

    @Test
    @DisplayName("List addAll returns false for empty collection")
    void testAddAllReturnsFalseForEmpty() {
        Collection<String> empty = new ArrayList<>();
        assertFalse(customList.addAll(empty));
    }

    @Test
    @DisplayName("List removeAll returns false if no elements were removed")
    void testRemoveAllReturnsFalseIfNoRemoval() {
        customList.add("A");
        Collection<String> coll = new ArrayList<>();
        coll.add("B");
        assertFalse(customList.removeAll(coll));
    }

    @Test
    @DisplayName("List retainAll returns false if no elements were removed")
    void testRetainAllReturnsFalseIfNoRemoval() {
        customList.add("A");
        Collection<String> coll = new ArrayList<>();
        coll.add("A");
        coll.add("B");
        assertFalse(customList.retainAll(coll));
    }

    @Test
    @DisplayName("toArray() returns array with elements in correct order")
    void testToArray() {
        customList.addAll(Arrays.asList("A", "B", "C"));
        expectedList.addAll(Arrays.asList("A", "B", "C"));

        assertArrayEquals(expectedList.toArray(), customList.toArray());
    }
}