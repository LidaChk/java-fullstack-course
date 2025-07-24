package org.example.week03;

import org.example.week03.customdeques.CustomArrayDeque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CustomArrayDeque Iterator Tests")
class CustomArrayDequeIteratorTest {

    private CustomArrayDeque<String> deque;

    @BeforeEach
    void setUp() {
        deque = new CustomArrayDeque<>();
    }

    /**
     * Creates a CustomArrayDeque with a wrapped buffer in the format
     * [0...afterLast...first...buffer.length-1]
     * The deque will contain elements [E, F, G, H, I, J, K, L] with the internal
     * buffer arranged as:
     * [K, L, null, null, E, F, G, H, I, J] where first=4 and afterLast=2
     *
     * @return CustomArrayDeque with the specific wrapped buffer arrangement
     */
    private CustomArrayDeque<String> createWrappedBufferDeque() {
        CustomArrayDeque<String> smallDeque = new CustomArrayDeque<>();

        // Add elements to fill positions 0, 1, 2, 3
        smallDeque.addLast("A"); // index 0
        smallDeque.addLast("B"); // index 1
        smallDeque.addLast("C"); // index 2
        smallDeque.addLast("D"); // index 3

        // Remove first three elements to move 'first' pointer to index 3
        assertEquals("A", smallDeque.pollFirst());
        assertEquals("B", smallDeque.pollFirst());
        assertEquals("C", smallDeque.pollFirst());

        // Add more elements to wrap around
        smallDeque.addLast("E"); // index 4
        smallDeque.addLast("F"); // index 5
        smallDeque.addLast("G"); // index 6
        smallDeque.addLast("H"); // index 7
        smallDeque.addLast("I"); // index 8
        smallDeque.addLast("J"); // index 9
        smallDeque.addLast("K"); // index 0 (wraps around)
        smallDeque.addLast("L"); // index 1 (wraps around)

        // Remove one more element to create the gap
        assertEquals("D", smallDeque.pollFirst());

        // Now we have: [K, L, null, null, E, F, G, H, I, J]
        // first = 4, afterLast = 2
        // Logical order: [E, F, G, H, I, J, K, L]
        return smallDeque;
    }

    // ========================
    // Tests for iterator()
    // ========================

    @Test
    @DisplayName("Iterator on empty deque should not have next")
    void iterator_EmptyDeque_HasNextReturnsFalse() {
        Iterator<String> iterator = deque.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("Iterator on empty deque should throw exception when calling next")
    void iterator_EmptyDeque_NextThrowsException() {
        Iterator<String> iterator = deque.iterator();
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @DisplayName("Iterator on single element deque should work correctly")
    void iterator_SingleElement_HasNextAndNextWorkCorrectly() {
        deque.add("A");
        Iterator<String> iterator = deque.iterator();

        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @DisplayName("Iterator should iterate elements in correct order")
    void iterator_MultipleElements_IteratesInOrder() {
        deque.add("A");
        deque.add("B");
        deque.add("C");

        Iterator<String> iterator = deque.iterator();

        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("B", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("C", iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @DisplayName("Iterator should remove element from middle correctly")
    void iterator_RemoveElementFromMiddle_RemovesCorrectly() {
        deque.add("A");
        deque.add("B");
        deque.add("C");

        Iterator<String> iterator = deque.iterator();

        assertEquals("A", iterator.next()); // Move to first element
        assertEquals("B", iterator.next()); // Move to second element

        iterator.remove(); // Remove "B"

        assertEquals(2, deque.size());
        assertEquals("A", deque.peekFirst());
        assertEquals("C", deque.peekLast());

        assertTrue(iterator.hasNext());
        assertEquals("C", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("Iterator should remove first element correctly")
    void iterator_RemoveFirstElement_RemovesCorrectly() {
        deque.add("A");
        deque.add("B");
        deque.add("C");

        Iterator<String> iterator = deque.iterator();

        assertEquals("A", iterator.next()); // Move to first element
        iterator.remove(); // Remove "A"

        assertEquals(2, deque.size());
        assertEquals("B", deque.peekFirst());
        assertEquals("C", deque.peekLast());

        assertTrue(iterator.hasNext());
        assertEquals("B", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("C", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("Iterator should remove last element correctly")
    void iterator_RemoveLastElement_RemovesCorrectly() {
        deque.add("A");
        deque.add("B");
        deque.add("C");

        Iterator<String> iterator = deque.iterator();

        assertEquals("A", iterator.next()); // Move to first element
        assertEquals("B", iterator.next()); // Move to second element
        assertEquals("C", iterator.next()); // Move to third element

        iterator.remove(); // Remove "C"

        assertEquals(2, deque.size());
        assertEquals("A", deque.peekFirst());
        assertEquals("B", deque.peekLast());

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @DisplayName("Iterator should throw exception when removing without calling next")
    void iterator_RemoveWithoutNext_ThrowsException() {
        deque.add("A");
        deque.add("B");

        Iterator<String> iterator = deque.iterator();

        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    @DisplayName("Iterator should throw exception when removing twice without calling next")
    void iterator_RemoveTwiceWithoutNext_ThrowsException() {
        deque.add("A");
        deque.add("B");

        Iterator<String> iterator = deque.iterator();

        iterator.next(); // Move to first element
        iterator.remove(); // First remove - should work

        assertThrows(IllegalStateException.class, iterator::remove); // Second remove without next - should fail
    }

    @Test
    @DisplayName("Iterator should work correctly with wrapped buffer")
    void iterator_WrappedBuffer_IteratesInOrder() {
        // Fill the buffer to cause wrapping
        for (int i = 0; i < 15; i++) {
            deque.addLast("X" + i);
        }

        // Remove some elements from the front to create space at the beginning
        for (int i = 0; i < 10; i++) {
            deque.pollFirst();
        }

        // Add more elements to wrap around
        deque.addLast("A");
        deque.addLast("B");
        deque.addLast("C");

        Iterator<String> iterator = deque.iterator();

        assertTrue(iterator.hasNext());
        assertEquals("X10", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("X11", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("X12", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("X13", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("X14", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("B", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("C", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("Iterator should work correctly with wrapped buffer [0...afterLast...first..buffer.length-1]")
    void iterator_WrappedBuffer_ZeroAfterLastFirst_LengthMinusOne() {
        CustomArrayDeque<String> smallDeque = createWrappedBufferDeque();

        // Now we have: [K, L, null, null, E, F, G, H, I, J]
        // first = 4, afterLast = 2
        // Logical order: [E, F, G, H, I, J, K, L]

        Iterator<String> iterator = smallDeque.iterator();

        assertTrue(iterator.hasNext());
        assertEquals("E", iterator.next()); // index 4
        assertTrue(iterator.hasNext());
        assertEquals("F", iterator.next()); // index 5
        assertTrue(iterator.hasNext());
        assertEquals("G", iterator.next()); // index 6
        assertTrue(iterator.hasNext());
        assertEquals("H", iterator.next()); // index 7
        assertTrue(iterator.hasNext());
        assertEquals("I", iterator.next()); // index 8
        assertTrue(iterator.hasNext());
        assertEquals("J", iterator.next()); // index 9
        assertTrue(iterator.hasNext());
        assertEquals("K", iterator.next()); // index 0
        assertTrue(iterator.hasNext());
        assertEquals("L", iterator.next()); // index 1
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("Iterator should remove elements correctly with wrapped buffer [0...afterLast...first..buffer.length-1]")
    void iterator_RemoveElements_WrappedBuffer_ZeroAfterLastFirst_LengthMinusOne() {
        CustomArrayDeque<String> smallDeque = createWrappedBufferDeque();

        // Now we have: [K, L, null, null, E, F, G, H, I, J]
        // first = 4, afterLast = 2
        // Logical order: [E, F, G, H, I, J, K, L]

        Iterator<String> iterator = smallDeque.iterator();

        // Remove first element "E"
        assertEquals("E", iterator.next());
        iterator.remove();
        assertEquals(7, smallDeque.size());

        // Remove middle element "H"
        assertEquals("F", iterator.next());
        assertEquals("G", iterator.next());
        assertEquals("H", iterator.next());
        iterator.remove();
        assertEquals(6, smallDeque.size());

        // Remove last element "L"
        assertEquals("I", iterator.next());
        assertEquals("J", iterator.next());
        assertEquals("K", iterator.next());
        assertEquals("L", iterator.next());
        iterator.remove();
        assertEquals(5, smallDeque.size());

        // Verify final state
        assertFalse(iterator.hasNext());
        assertEquals(5, smallDeque.size());
        assertEquals("F", smallDeque.peekFirst());
        assertEquals("K", smallDeque.peekLast());
    }

    // ========================
    // Tests for descendingIterator()
    // ========================

    @Test
    @DisplayName("Descending iterator on empty deque should not have next")
    void descendingIterator_EmptyDeque_HasNextReturnsFalse() {
        Iterator<String> iterator = deque.descendingIterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("Descending iterator on empty deque should throw exception when calling next")
    void descendingIterator_EmptyDeque_NextThrowsException() {
        Iterator<String> iterator = deque.descendingIterator();
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @DisplayName("Descending iterator on single element deque should work correctly")
    void descendingIterator_SingleElement_HasNextAndNextWorkCorrectly() {
        deque.add("A");
        Iterator<String> iterator = deque.descendingIterator();

        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @DisplayName("Descending iterator should iterate elements in reverse order")
    void descendingIterator_MultipleElements_IteratesInReverseOrder() {
        deque.add("A");
        deque.add("B");
        deque.add("C");

        Iterator<String> iterator = deque.descendingIterator();

        assertTrue(iterator.hasNext());
        assertEquals("C", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("B", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @DisplayName("Descending iterator should remove element from middle correctly")
    void descendingIterator_RemoveElementFromMiddle_RemovesCorrectly() {
        deque.add("A");
        deque.add("B");
        deque.add("C");

        Iterator<String> iterator = deque.descendingIterator();

        assertEquals("C", iterator.next()); // Move to last element
        assertEquals("B", iterator.next()); // Move to middle element

        iterator.remove(); // Remove "B"

        assertEquals(2, deque.size());
        assertEquals("A", deque.peekFirst());
        assertEquals("C", deque.peekLast());

        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("Descending iterator should remove last element correctly")
    void descendingIterator_RemoveLastElement_RemovesCorrectly() {
        deque.add("A");
        deque.add("B");
        deque.add("C");

        Iterator<String> iterator = deque.descendingIterator();

        assertEquals("C", iterator.next()); // Move to last element
        iterator.remove(); // Remove "C"

        assertEquals(2, deque.size());
        assertEquals("A", deque.peekFirst());
        assertEquals("B", deque.peekLast());

        assertTrue(iterator.hasNext());
        assertEquals("B", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("Descending iterator should remove first element correctly")
    void descendingIterator_RemoveFirstElement_RemovesCorrectly() {
        deque.add("A");
        deque.add("B");
        deque.add("C");

        Iterator<String> iterator = deque.descendingIterator();

        assertEquals("C", iterator.next()); // Move to last element
        assertEquals("B", iterator.next()); // Move to middle element
        assertEquals("A", iterator.next()); // Move to first element

        iterator.remove(); // Remove "A"

        assertEquals(2, deque.size());
        assertEquals("B", deque.peekFirst());
        assertEquals("C", deque.peekLast());

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @DisplayName("Descending iterator should throw exception when removing without calling next")
    void descendingIterator_RemoveWithoutNext_ThrowsException() {
        deque.add("A");
        deque.add("B");

        Iterator<String> iterator = deque.descendingIterator();

        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    @DisplayName("Descending iterator should throw exception when removing twice without calling next")
    void descendingIterator_RemoveTwiceWithoutNext_ThrowsException() {
        deque.add("A");
        deque.add("B");

        Iterator<String> iterator = deque.descendingIterator();

        iterator.next(); // Move to first element (in reverse order)
        iterator.remove(); // First remove - should work

        assertThrows(IllegalStateException.class, iterator::remove); // Second remove without next - should fail
    }

    @Test
    @DisplayName("Descending iterator should work correctly with wrapped buffer")
    void descendingIterator_WrappedBuffer_IteratesInReverseOrder() {
        // Fill the buffer to cause wrapping
        for (int i = 0; i < 15; i++) {
            deque.addLast("X" + i);
        }

        // Remove some elements from the front to create space at the beginning
        for (int i = 0; i < 10; i++) {
            deque.pollFirst();
        }

        // Add more elements to wrap around
        deque.addLast("A");
        deque.addLast("B");
        deque.addLast("C");

        Iterator<String> iterator = deque.descendingIterator();

        assertTrue(iterator.hasNext());
        assertEquals("C", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("B", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("X14", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("X13", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("X12", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("X11", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("X10", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("Descending iterator should work correctly with wrapped buffer [0...afterLast...first..buffer.length-1]")
    void descendingIterator_WrappedBuffer_ZeroAfterLastFirst_LengthMinusOne() {
        CustomArrayDeque<String> smallDeque = createWrappedBufferDeque();

        // Now we have: [K, L, null, null, E, F, G, H, I, J]
        // first = 4, afterLast = 2
        // Logical order: [E, F, G, H, I, J, K, L]
        // Reverse order: [L, K, J, I, H, G, F, E]

        Iterator<String> iterator = smallDeque.descendingIterator();

        assertTrue(iterator.hasNext());
        assertEquals("L", iterator.next()); // index 1
        assertTrue(iterator.hasNext());
        assertEquals("K", iterator.next()); // index 0
        assertTrue(iterator.hasNext());
        assertEquals("J", iterator.next()); // index 9
        assertTrue(iterator.hasNext());
        assertEquals("I", iterator.next()); // index 8
        assertTrue(iterator.hasNext());
        assertEquals("H", iterator.next()); // index 7
        assertTrue(iterator.hasNext());
        assertEquals("G", iterator.next()); // index 6
        assertTrue(iterator.hasNext());
        assertEquals("F", iterator.next()); // index 5
        assertTrue(iterator.hasNext());
        assertEquals("E", iterator.next()); // index 4
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("Descending iterator should remove elements correctly with wrapped buffer [0...afterLast...first..buffer.length-1]")
    void descendingIterator_RemoveElements_WrappedBuffer_ZeroAfterLastFirst_LengthMinusOne() {
        CustomArrayDeque<String> smallDeque = createWrappedBufferDeque();

        // Now we have: [K, L, null, null, E, F, G, H, I, J]
        // first = 4, afterLast = 2
        // Logical order: [E, F, G, H, I, J, K, L]
        // Reverse order: [L, K, J, I, H, G, F, E]

        Iterator<String> iterator = smallDeque.descendingIterator();

        // Remove first element "L" (in reverse order)
        assertEquals("L", iterator.next());
        iterator.remove();
        assertEquals(7, smallDeque.size());

        // Remove middle element "H" (in reverse order)
        assertEquals("K", iterator.next());
        assertEquals("J", iterator.next());
        assertEquals("I", iterator.next());
        assertEquals("H", iterator.next());
        iterator.remove();
        assertEquals(6, smallDeque.size());

        // Remove last element "E" (in reverse order)
        assertEquals("G", iterator.next());
        assertEquals("F", iterator.next());
        assertEquals("E", iterator.next());
        iterator.remove();
        assertEquals(5, smallDeque.size());

        // Verify final state
        assertFalse(iterator.hasNext());
        assertEquals(5, smallDeque.size());
        assertEquals("F", smallDeque.peekFirst());
        assertEquals("K", smallDeque.peekLast());
    }
}
