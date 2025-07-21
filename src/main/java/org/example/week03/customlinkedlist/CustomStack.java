package org.example.week03.customlinkedlist;

import java.util.EmptyStackException;

public class CustomStack<T> {
    private final CustomLinkedList<T> list = new CustomLinkedList<>();

    public void push(T item) {
        list.addFirst(item);
    }

    public T pop() {
        if (list.isEmpty()) {
            throw new EmptyStackException();
        }
        return list.removeFirst();
    }

    public T peek() {
        if (list.isEmpty()) {
            throw new EmptyStackException();
        }
        return list.peekFirst();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public boolean contains(T item) {
        return list.contains(item);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}