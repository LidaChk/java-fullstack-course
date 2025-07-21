package org.example.week03.customlinkedlist;

import java.util.*;

public class CustomQueue<T> implements Queue<T> {
    private final CustomLinkedList<T> list = new CustomLinkedList<>();

    // ========================
    // Constructors
    // ========================

    public CustomQueue() {
    }

    public CustomQueue(Collection<? extends T> c) {
        this();
        addAll(c);
    }

    // ========================
    // Queue Interface Methods
    // ========================

    @Override
    public boolean add(T t) {
        return list.offerLast(t);
    }

    @Override
    public boolean offer(T t) {
        return list.offerLast(t);
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list.removeFirst();
    }

    @Override
    public T poll() {
        return isEmpty() ? null : list.removeFirst();
    }

    @Override
    public T element() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list.getFirst();
    }

    @Override
    public T peek() {
        return isEmpty() ? null : list.getFirst();
    }

    // ========================
    // Collection Interface Methods
    // ========================

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }


    @Override
    public boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    @Override
    public boolean remove(Object o) {
        return list.removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return new HashSet<>(list).containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    // ========================
    // Unsupported Methods
    // ========================
    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("toArray(T1[] a) Not supported yet.");
    }

    // ========================
    // toString
    // ========================

    @Override
    public String toString() {
        return list.toString();
    }
}