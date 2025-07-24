package org.example.week03;

import org.example.week03.customqueue.CustomQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CustomQueueTest {

    private CustomQueue<String> queue;

    @BeforeEach
    void setUp() {
        queue = new CustomQueue<>();
    }


    @Test
    @DisplayName("add(T) adds element to the tail and returns true")
    void testAdd() {
        assertTrue(queue.add("A"));
        assertEquals(1, queue.size());
        assertEquals("A", queue.peek());
    }

    @Test
    @DisplayName("add(null) should be allowed if underlying list permits it")
    void testAddNull() {
        assertTrue(queue.add(null));
        assertNull(queue.peek());
        assertEquals(1, queue.size());
    }


    @Test
    @DisplayName("offer(T) adds element and returns true")
    void testOffer() {
        assertTrue(queue.offer("B"));
        assertEquals("B", queue.peek());
    }

    @Test
    @DisplayName("offer(null) returns true if nulls are allowed")
    void testOfferNull() {
        assertTrue(queue.offer(null));
        assertNull(queue.peek());
    }


    @Test
    @DisplayName("remove() throws NoSuchElementException when queue is empty")
    void testRemoveEmpty() {
        assertThrows(NoSuchElementException.class, () -> queue.remove());
    }

    @Test
    @DisplayName("remove() removes and returns the head element (FIFO order)")
    void testRemove() {
        queue.add("First");
        queue.add("Second");

        assertEquals("First", queue.remove());
        assertEquals("Second", queue.peek());
        assertEquals(1, queue.size());
    }


    @Test
    @DisplayName("poll() returns null when queue is empty")
    void testPollEmpty() {
        assertNull(queue.poll());
    }

    @Test
    @DisplayName("poll() removes and returns head element")
    void testPoll() {
        queue.offer("A");
        queue.offer("B");

        assertEquals("A", queue.poll());
        assertEquals("B", queue.peek());
        assertEquals(1, queue.size());

        assertEquals("B", queue.poll());
        assertNull(queue.poll());
    }


    @Test
    @DisplayName("element() throws NoSuchElementException when queue is empty")
    void testElementEmpty() {
        assertThrows(NoSuchElementException.class, () -> queue.element());
    }

    @Test
    @DisplayName("element() returns head without removing it")
    void testElement() {
        queue.add("X");
        queue.add("Y");

        assertEquals("X", queue.element());
        assertEquals(2, queue.size());
        assertEquals("X", queue.peek());
    }


    @Test
    @DisplayName("peek() returns null when queue is empty")
    void testPeekEmpty() {
        assertNull(queue.peek());
    }

    @Test
    @DisplayName("peek() returns head without removing it")
    void testPeek() {
        queue.offer("Item1");
        queue.offer("Item2");

        assertEquals("Item1", queue.peek());
        assertEquals(2, queue.size());
        assertEquals("Item1", queue.peek());
    }


    @Test
    @DisplayName("Queue follows FIFO: first in, first out")
    void testFifoOrder() {
        queue.add("1st");
        queue.add("2nd");
        queue.add("3rd");

        assertEquals("1st", queue.remove());
        assertEquals("2nd", queue.poll());
        assertEquals("3rd", queue.peek());
        assertEquals("3rd", queue.element());

        queue.poll();
        assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("Multiple nulls can be added and retrieved in FIFO order")
    void testMultipleNulls() {
        queue.add(null);
        queue.offer(null);
        queue.add("not null");

        assertNull(queue.remove());
        assertNull(queue.poll());
        assertEquals("not null", queue.remove());
        assertTrue(queue.isEmpty());
    }
}