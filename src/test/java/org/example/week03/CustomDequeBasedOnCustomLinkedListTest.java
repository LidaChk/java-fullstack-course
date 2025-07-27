package org.example.week03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.example.week03.customdeques.CustomDequeBasedOnCustomLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CustomDequeBasedOnCustomLinkedList Tests")
class CustomDequeBasedOnCustomLinkedListTest {

  private CustomDequeBasedOnCustomLinkedList<String> deque;

  @BeforeEach
  void setUp() {
    deque = new CustomDequeBasedOnCustomLinkedList<>();
  }

  // ========================
  // Basic Functionality Tests
  // ========================

  @Test
  @DisplayName("New deque should be empty")
  void newDequeShouldBeEmpty() {
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
  }

  @Test
  @DisplayName("AddFirst should add element to the front")
  void addFirstShouldAddElementToFront() {
    deque.addFirst("A");
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    assertEquals("A", deque.peekFirst());
  }

  @Test
  @DisplayName("AddLast should add element to the end")
  void addLastShouldAddElementToEnd() {
    deque.addLast("A");
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    assertEquals("A", deque.peekLast());
  }

  @Test
  @DisplayName("Add should add element to the end (Queue interface)")
  void addShouldAddElementToEnd() {
    assertTrue(deque.add("A"));
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    assertEquals("A", deque.peekLast());
  }

  @Test
  @DisplayName("OfferFirst should add element to the front")
  void offerFirstShouldAddElementToFront() {
    assertTrue(deque.offerFirst("A"));
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    assertEquals("A", deque.peekFirst());
  }

  @Test
  @DisplayName("OfferLast should add element to the end")
  void offerLastShouldAddElementToEnd() {
    assertTrue(deque.offerLast("A"));
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    assertEquals("A", deque.peekLast());
  }

  @Test
  @DisplayName("Offer should add element to the end (Queue interface)")
  void offerShouldAddElementToEnd() {
    assertTrue(deque.offer("A"));
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    assertEquals("A", deque.peekLast());
  }

  @Test
  @DisplayName("Push should add element to the front (Stack interface)")
  void pushShouldAddElementToFront() {
    deque.push("A");
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    assertEquals("A", deque.peekFirst());
  }

  @Test
  @DisplayName("RemoveFirst should remove and return first element")
  void removeFirstShouldRemoveAndReturnFirstElement() {
    deque.addLast("A");
    assertEquals("A", deque.removeFirst());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
  }

  @Test
  @DisplayName("RemoveLast should remove and return last element")
  void removeLastShouldRemoveAndReturnLastElement() {
    deque.addLast("A");
    assertEquals("A", deque.removeLast());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
  }

  @Test
  @DisplayName("Remove should remove and return first element (Queue interface)")
  void removeShouldRemoveAndReturnFirstElement() {
    deque.addLast("A");
    assertEquals("A", deque.remove());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
  }

  @Test
  @DisplayName("Pop should remove and return first element (Stack interface)")
  void popShouldRemoveAndReturnFirstElement() {
    deque.push("A");
    assertEquals("A", deque.pop());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
  }

  @Test
  @DisplayName("GetFirst should return first element without removing it")
  void getFirstShouldReturnFirstElementWithoutRemoving() {
    deque.addLast("A");
    assertEquals("A", deque.getFirst());
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
  }

  @Test
  @DisplayName("GetLast should return last element without removing it")
  void getLastShouldReturnLastElementWithoutRemoving() {
    deque.addLast("A");
    assertEquals("A", deque.getLast());
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
  }

  @Test
  @DisplayName("Element should return first element without removing it (Queue interface)")
  void elementShouldReturnFirstElementWithoutRemoving() {
    deque.addLast("A");
    assertEquals("A", deque.element());
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
  }

  @Test
  @DisplayName("PollFirst should remove and return first element or null")
  void pollFirstShouldRemoveAndReturnFirstElementOrNull() {
    assertNull(deque.pollFirst());
    deque.addLast("A");
    assertEquals("A", deque.pollFirst());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
  }

  @Test
  @DisplayName("PollLast should remove and return last element or null")
  void pollLastShouldRemoveAndReturnLastElementOrNull() {
    assertNull(deque.pollLast());
    deque.addLast("A");
    assertEquals("A", deque.pollLast());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
  }

  @Test
  @DisplayName("Poll should remove and return first element or null (Queue interface)")
  void pollShouldRemoveAndReturnFirstElementOrNull() {
    assertNull(deque.poll());
    deque.addLast("A");
    assertEquals("A", deque.poll());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
  }

  @Test
  @DisplayName("PeekFirst should return first element or null without removing it")
  void peekFirstShouldReturnFirstElementOrNullWithoutRemoving() {
    assertNull(deque.peekFirst());
    deque.addLast("A");
    assertEquals("A", deque.peekFirst());
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
  }

  @Test
  @DisplayName("PeekLast should return last element or null without removing it")
  void peekLastShouldReturnLastElementOrNullWithoutRemoving() {
    assertNull(deque.peekLast());
    deque.addLast("A");
    assertEquals("A", deque.peekLast());
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
  }

  @Test
  @DisplayName("Peek should return first element or null without removing it (Queue interface)")
  void peekShouldReturnFirstElementOrNullWithoutRemoving() {
    assertNull(deque.peek());
    deque.addLast("A");
    assertEquals("A", deque.peek());
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
  }

  // ========================
  // Boundary Condition Tests
  // ========================

  @Test
  @DisplayName("AddFirst and removeFirst should work correctly with single element")
  void addFirstAndRemoveFirstWithSingleElement() {
    deque.addFirst("A");
    assertEquals("A", deque.removeFirst());
    assertTrue(deque.isEmpty());
  }

  @Test
  @DisplayName("AddLast and removeLast should work correctly with single element")
  void addLastAndRemoveLastWithSingleElement() {
    deque.addLast("A");
    assertEquals("A", deque.removeLast());
    assertTrue(deque.isEmpty());
  }

  @Test
  @DisplayName("AddFirst and removeLast should work correctly with single element")
  void addFirstAndRemoveLastWithSingleElement() {
    deque.addFirst("A");
    assertEquals("A", deque.removeLast());
    assertTrue(deque.isEmpty());
  }

  @Test
  @DisplayName("AddLast and removeFirst should work correctly with single element")
  void addLastAndRemoveFirstWithSingleElement() {
    deque.addLast("A");
    assertEquals("A", deque.removeFirst());
    assertTrue(deque.isEmpty());
  }

  @Test
  @DisplayName("AddFirst multiple times should maintain order")
  void addFirstMultipleTimesShouldMaintainOrder() {
    deque.addFirst("A");
    deque.addFirst("B");
    deque.addFirst("C");
    assertEquals("C", deque.removeFirst());
    assertEquals("B", deque.removeFirst());
    assertEquals("A", deque.removeFirst());
    assertTrue(deque.isEmpty());
  }

  @Test
  @DisplayName("AddLast multiple times should maintain order")
  void addLastMultipleTimesShouldMaintainOrder() {
    deque.addLast("A");
    deque.addLast("B");
    deque.addLast("C");
    assertEquals("A", deque.removeFirst());
    assertEquals("B", deque.removeFirst());
    assertEquals("C", deque.removeFirst());
    assertTrue(deque.isEmpty());
  }

  @Test
  @DisplayName("Mixed addFirst and addLast operations should maintain correct order")
  void mixedAddFirstAndAddLastOperationsShouldMaintainOrder() {
    deque.addFirst("B");
    deque.addLast("C");
    deque.addFirst("A");
    assertEquals("A", deque.removeFirst());
    assertEquals("B", deque.removeFirst());
    assertEquals("C", deque.removeFirst());
    assertTrue(deque.isEmpty());
  }

  @Test
  @DisplayName("RemoveFirst on empty deque should throw NoSuchElementException")
  void removeFirstOnEmptyDequeShouldThrowException() {
    assertThrows(NoSuchElementException.class, () -> deque.removeFirst());
  }

  @Test
  @DisplayName("RemoveLast on empty deque should throw NoSuchElementException")
  void removeLastOnEmptyDequeShouldThrowException() {
    assertThrows(NoSuchElementException.class, () -> deque.removeLast());
  }

  @Test
  @DisplayName("GetFirst on empty deque should throw NoSuchElementException")
  void getFirstOnEmptyDequeShouldThrowException() {
    assertThrows(NoSuchElementException.class, () -> deque.getFirst());
  }

  @Test
  @DisplayName("GetLast on empty deque should throw NoSuchElementException")
  void getLastOnEmptyDequeShouldThrowException() {
    assertThrows(NoSuchElementException.class, () -> deque.getLast());
  }

  @Test
  @DisplayName("Remove on empty deque should throw NoSuchElementException")
  void removeOnEmptyDequeShouldThrowException() {
    assertThrows(NoSuchElementException.class, () -> deque.remove());
  }

  @Test
  @DisplayName("Element on empty deque should throw NoSuchElementException")
  void elementOnEmptyDequeShouldThrowException() {
    assertThrows(NoSuchElementException.class, () -> deque.element());
  }

  @Test
  @DisplayName("Pop on empty deque should throw NoSuchElementException")
  void popOnEmptyDequeShouldThrowException() {
    assertThrows(NoSuchElementException.class, () -> deque.pop());
  }

  // ========================
  // Null Value Tests
  // ========================

  @Test
  @DisplayName("AddFirst should accept null values")
  void addFirstShouldAcceptNullValues() {
    deque.addFirst(null);
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    assertTrue(deque.contains(null));
  }

  @Test
  @DisplayName("AddLast should accept null values")
  void addLastShouldAcceptNullValues() {
    deque.addLast(null);
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    assertTrue(deque.contains(null));
  }

  @Test
  @DisplayName("Add should accept null values")
  void addShouldAcceptNullValues() {
    deque.add(null);
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    assertTrue(deque.contains(null));
  }

  @Test
  @DisplayName("OfferFirst should accept null values")
  void offerFirstShouldAcceptNullValues() {
    assertTrue(deque.offerFirst(null));
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    assertTrue(deque.contains(null));
  }

  @Test
  @DisplayName("OfferLast should accept null values")
  void offerLastShouldAcceptNullValues() {
    assertTrue(deque.offerLast(null));
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    assertTrue(deque.contains(null));
  }

  @Test
  @DisplayName("Offer should accept null values")
  void offerShouldAcceptNullValues() {
    assertTrue(deque.offer(null));
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    assertTrue(deque.contains(null));
  }

  @Test
  @DisplayName("Push should accept null values")
  void pushShouldAcceptNullValues() {
    deque.push(null);
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    assertTrue(deque.contains(null));
  }

  @Test
  @DisplayName("Contains should work with null values")
  void containsShouldWorkWithNullValues() {
    deque.addLast("A");
    assertFalse(deque.contains(null));
    deque.addLast(null);
    assertTrue(deque.contains(null));
    deque.removeLast(); // Remove null
    assertFalse(deque.contains(null));
  }

  @Test
  @DisplayName("RemoveFirst should work with null values")
  void removeFirstShouldWorkWithNullValues() {
    deque.addFirst(null);
    deque.addFirst("A");
    assertEquals("A", deque.removeFirst());
    assertNull(deque.removeFirst());
    assertTrue(deque.isEmpty());
  }

  @Test
  @DisplayName("RemoveLast should work with null values")
  void removeLastShouldWorkWithNullValues() {
    deque.addLast(null);
    deque.addLast("A");
    assertEquals("A", deque.removeLast());
    assertNull(deque.removeLast());
    assertTrue(deque.isEmpty());
  }

  @Test
  @DisplayName("GetFirst should work with null values")
  void getFirstShouldWorkWithNullValues() {
    deque.addFirst(null);
    assertNull(deque.getFirst());
    deque.addFirst("A");
    assertEquals("A", deque.getFirst());
  }

  @Test
  @DisplayName("GetLast should work with null values")
  void getLastShouldWorkWithNullValues() {
    deque.addLast(null);
    assertNull(deque.getLast());
    deque.addLast("A");
    assertEquals("A", deque.getLast());
  }

  @Test
  @DisplayName("PollFirst should work with null values")
  void pollFirstShouldWorkWithNullValues() {
    assertNull(deque.pollFirst());
    deque.addFirst(null);
    assertNull(deque.pollFirst());
    assertTrue(deque.isEmpty());
  }

  @Test
  @DisplayName("PollLast should work with null values")
  void pollLastShouldWorkWithNullValues() {
    assertNull(deque.pollLast());
    deque.addLast(null);
    assertNull(deque.pollLast());
    assertTrue(deque.isEmpty());
  }

  @Test
  @DisplayName("PeekFirst should work with null values")
  void peekFirstShouldWorkWithNullValues() {
    assertNull(deque.peekFirst());
    deque.addFirst(null);
    assertNull(deque.peekFirst());
    assertEquals(1, deque.size());
  }

  @Test
  @DisplayName("PeekLast should work with null values")
  void peekLastShouldWorkWithNullValues() {
    assertNull(deque.peekLast());
    deque.addLast(null);
    assertNull(deque.peekLast());
    assertEquals(1, deque.size());
  }

  @Test
  @DisplayName("Pop should work with null values")
  void popShouldWorkWithNullValues() {
    deque.push(null);
    assertNull(deque.pop());
    assertTrue(deque.isEmpty());
  }

  // ========================
  // Collection Interface Tests
  // ========================

  @Test
  @DisplayName("AddAll should add all elements from collection")
  void addAllShouldAddAllElementsFromCollection() {
    Collection<String> coll = Arrays.asList("A", "B", "C");
    assertTrue(deque.addAll(coll));
    assertEquals(3, deque.size());
    assertEquals("A", deque.peekFirst());
    assertEquals("C", deque.peekLast());
  }

  @Test
  @DisplayName("AddAll with empty collection should return false")
  void addAllWithEmptyCollectionShouldReturnFalse() {
    Collection<String> empty = new ArrayList<>();
    assertFalse(deque.addAll(empty));
    assertTrue(deque.isEmpty());
  }

  @Test
  @DisplayName("ContainsAll should return true if all elements exist")
  void containsAllShouldReturnTrueIfAllElementsExist() {
    deque.addAll(Arrays.asList("A", "B", "C"));
    Collection<String> coll = Arrays.asList("A", "C");
    assertTrue(deque.containsAll(coll));
  }

  @Test
  @DisplayName("ContainsAll should return false if any element is missing")
  void containsAllShouldReturnFalseIfAnyElementIsMissing() {
    deque.addAll(Arrays.asList("A", "B"));
    Collection<String> coll = Arrays.asList("A", "C");
    assertFalse(deque.containsAll(coll));
  }

  @Test
  @DisplayName("RemoveAll should remove all matching elements")
  void removeAllShouldRemoveAllMatchingElements() {
    deque.addAll(Arrays.asList("A", "B", "C", "B", "D"));
    Collection<String> coll = Arrays.asList("B", "D");
    assertTrue(deque.removeAll(coll));
    assertEquals(2, deque.size());
    assertTrue(deque.contains("A"));
    assertTrue(deque.contains("C"));
    assertFalse(deque.contains("B"));
    assertFalse(deque.contains("D"));
  }

  @Test
  @DisplayName("RemoveAll with empty collection should return false")
  void removeAllWithEmptyCollectionShouldReturnFalse() {
    deque.add("A");
    Collection<String> empty = new ArrayList<>();
    assertFalse(deque.removeAll(empty));
    assertEquals(1, deque.size());
  }

  @Test
  @DisplayName("RetainAll should keep only matching elements")
  void retainAllShouldKeepOnlyMatchingElements() {
    deque.addAll(Arrays.asList("A", "B", "C", "D"));
    Collection<String> coll = Arrays.asList("B", "C", "E");
    assertTrue(deque.retainAll(coll));
    assertEquals(2, deque.size());
    assertFalse(deque.contains("A"));
    assertTrue(deque.contains("B"));
    assertTrue(deque.contains("C"));
    assertFalse(deque.contains("D"));
  }

  @Test
  @DisplayName("RetainAll with empty collection should clear deque")
  void retainAllWithEmptyCollectionShouldClearDeque() {
    deque.add("A");
    Collection<String> empty = new ArrayList<>();
    assertTrue(deque.retainAll(empty));
    assertTrue(deque.isEmpty());
  }

  @Test
  @DisplayName("Clear should remove all elements")
  void clearShouldRemoveAllElements() {
    deque.addAll(Arrays.asList("A", "B", "C"));
    assertEquals(3, deque.size());
    deque.clear();
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
  }

  @Test
  @DisplayName("ToArray should return elements in correct order")
  void toArrayShouldReturnElementsInCorrectOrder() {
    deque.addAll(Arrays.asList("A", "B", "C"));
    Object[] array = deque.toArray();
    assertArrayEquals(new Object[] { "A", "B", "C" }, array);
  }

  // ========================
  // Deque Specific Tests
  // ========================

  @Test
  @DisplayName("RemoveFirstOccurrence should remove first matching element")
  void removeFirstOccurrenceShouldRemoveFirstMatchingElement() {
    deque.addAll(Arrays.asList("A", "B", "A", "C"));
    assertTrue(deque.removeFirstOccurrence("A"));
    assertEquals(3, deque.size());
    assertEquals("B", deque.removeFirst());
    assertEquals("A", deque.removeFirst());
    assertEquals("C", deque.removeFirst());
  }

  @Test
  @DisplayName("RemoveFirstOccurrence should return false if element not found")
  void removeFirstOccurrenceShouldReturnFalseIfElementNotFound() {
    deque.addAll(Arrays.asList("A", "B", "C"));
    assertFalse(deque.removeFirstOccurrence("D"));
    assertEquals(3, deque.size());
  }

  @Test
  @DisplayName("RemoveLastOccurrence should remove last matching element")
  void removeLastOccurrenceShouldRemoveLastMatchingElement() {
    deque.addAll(Arrays.asList("A", "B", "A", "C"));
    assertTrue(deque.removeLastOccurrence("A"));
    assertEquals(3, deque.size());
    assertEquals("A", deque.removeFirst());
    assertEquals("B", deque.removeFirst());
    assertEquals("C", deque.removeFirst());
  }

  @Test
  @DisplayName("RemoveLastOccurrence should return false if element not found")
  void removeLastOccurrenceShouldReturnFalseIfElementNotFound() {
    deque.addAll(Arrays.asList("A", "B", "C"));
    assertFalse(deque.removeLastOccurrence("D"));
    assertEquals(3, deque.size());
  }

  @Test
  @DisplayName("RemoveFirstOccurrence should work with null values")
  void removeFirstOccurrenceShouldWorkWithNullValues() {
    deque.addAll(Arrays.asList("A", null, "B", null, "C"));
    assertTrue(deque.removeFirstOccurrence(null));
    assertEquals(4, deque.size());
    assertEquals("A", deque.removeFirst());
    assertEquals("B", deque.removeFirst());
    assertNull(deque.removeFirst());
    assertEquals("C", deque.removeFirst());
  }

  @Test
  @DisplayName("RemoveLastOccurrence should work with null values")
  void removeLastOccurrenceShouldWorkWithNullValues() {
    deque.addAll(Arrays.asList("A", null, "B", null, "C"));
    assertTrue(deque.removeLastOccurrence(null));
    assertEquals(4, deque.size());
    assertEquals("A", deque.removeFirst());
    assertNull(deque.removeFirst());
    assertEquals("B", deque.removeFirst());
    assertEquals("C", deque.removeFirst());
  }

  // ========================
  // Constructor Tests
  // ========================

  @Test
  @DisplayName("Constructor with collection should initialize deque with elements")
  void constructorWithCollectionShouldInitializeDequeWithElements() {
    Collection<String> coll = Arrays.asList("A", "B", "C");
    CustomDequeBasedOnCustomLinkedList<String> newDeque = new CustomDequeBasedOnCustomLinkedList<>(coll);
    assertEquals(3, newDeque.size());
    assertEquals("A", newDeque.removeFirst());
    assertEquals("B", newDeque.removeFirst());
    assertEquals("C", newDeque.removeFirst());
  }

  @Test
  @DisplayName("Constructor with empty collection should create empty deque")
  void constructorWithEmptyCollectionShouldCreateEmptyDeque() {
    Collection<String> empty = new ArrayList<>();
    CustomDequeBasedOnCustomLinkedList<String> newDeque = new CustomDequeBasedOnCustomLinkedList<>(empty);
    assertTrue(newDeque.isEmpty());
  }
}
