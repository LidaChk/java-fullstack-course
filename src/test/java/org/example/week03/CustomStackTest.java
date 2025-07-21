package org.example.week03;

import org.example.week03.customlinkedlist.CustomStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

class CustomStackTest {
    private CustomStack<String> stack;

    @BeforeEach
    void setUp() {
        stack = new CustomStack<>();
    }

    @Test
    @DisplayName("Push adds element to the top of the stack")
    void testPush() {
        stack.push("A");
        assertEquals(1, stack.size());
        assertEquals("A", stack.peek());
    }

    @Test
    @DisplayName("Pop removes and returns the top element")
    void testPop() {
        stack.push("A");
        stack.push("B");
        assertEquals("B", stack.pop());
        assertEquals("A", stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    @DisplayName("Pop throws EmptyStackException when stack is empty")
    void testPopEmptyStack() {
        assertThrows(EmptyStackException.class, stack::pop);
    }

    @Test
    @DisplayName("Peek returns the top element without removing it")
    void testPeek() {
        stack.push("A");
        stack.push("B");
        assertEquals("B", stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    @DisplayName("Peek throws EmptyStackException when stack is empty")
    void testPeekEmptyStack() {
        assertThrows(EmptyStackException.class, stack::peek);
    }

    @Test
    @DisplayName("IsEmpty returns true when stack is empty")
    void testIsEmpty() {
        assertTrue(stack.isEmpty());
        stack.push("A");
        assertFalse(stack.isEmpty());
    }

    @Test
    @DisplayName("Size returns correct number of elements")
    void testSize() {
        assertEquals(0, stack.size());
        stack.push("A");
        stack.push("B");
        assertEquals(2, stack.size());
        stack.pop();
        assertEquals(1, stack.size());
    }

    @Test
    @DisplayName("Clear removes all elements from the stack")
    void testClear() {
        stack.push("A");
        stack.push("B");
        stack.clear();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    @DisplayName("Contains returns true if element is in the stack")
    void testContains() {
        stack.push("A");
        stack.push("B");
        assertTrue(stack.contains("A"));
        assertFalse(stack.contains("C"));
    }

    @Test
    @DisplayName("Pushing and popping multiple elements works correctly")
    void testMultiplePushAndPop() {
        stack.push("A");
        stack.push("B");
        stack.push("C");

        assertEquals("C", stack.pop());
        assertEquals("B", stack.pop());
        assertEquals("A", stack.pop());

        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("Pushing null is allowed")
    void testPushNull() {
        stack.push(null);
        assertNotNull(stack);
        assertEquals(1, stack.size());
        assertNull(stack.peek());
    }

    @Test
    @DisplayName("Peek returns null if pushed null")
    void testPeekNull() {
        stack.push(null);
        assertNull(stack.peek());
    }

    @Test
    @DisplayName("Pop returns null if pushed null")
    void testPopNull() {
        stack.push(null);
        assertNull(stack.pop());
        assertTrue(stack.isEmpty());
    }

}
