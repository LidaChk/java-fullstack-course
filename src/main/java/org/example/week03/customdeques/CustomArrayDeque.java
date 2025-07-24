package org.example.week03.customdeques;

import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CustomArrayDeque<T> implements Deque<T> {
    private Object[] buffer;
    private int first;
    private int afterLast;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
    private static final double GROWTH_FACTOR = 1.5;

    public CustomArrayDeque() {
        buffer = new Object[DEFAULT_CAPACITY];
        first = 0;
        afterLast = 0;
        size = 0;
    }

    public CustomArrayDeque(Collection<? extends T> c) {
        this();
        addAll(c);
    }

    // ========================
    // Helper Methods
    // ========================

    private int normalizeIdx(int idx) {
        return (idx + buffer.length) % buffer.length;
    }

    private void ensureCapacity() {
        if (size == buffer.length) {
            int newCapacity = buffer.length == 0 ? DEFAULT_CAPACITY : Math.max((int) (buffer.length * GROWTH_FACTOR) + 1, buffer.length + 1);

            resize(newCapacity);
        }
    }

    private void resize(int newCapacity) {
        Object[] newBuffer = new Object[newCapacity];

        int bufferLength = buffer.length;

        int startIdx = normalizeIdx(first);

        // [first...buffer.length-1]
        int part1Len = bufferLength - startIdx;
        if (part1Len > size) {
            part1Len = size;
        }
        System.arraycopy(buffer, startIdx, newBuffer, 0, part1Len);

        // [0...afterLast)
        int part2Len = size - part1Len;
        if (part2Len > 0) {
            System.arraycopy(buffer, 0, newBuffer, part1Len, part2Len);
        }

        first = 0;
        afterLast = size;
        buffer = newBuffer;
    }

    private void checkEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
    }

    @SuppressWarnings("unchecked")
    private T removeByNormalizedIndex(int index) {

        int nFirst = normalizeIdx(first);
        int nAfterLast = normalizeIdx(afterLast);

        if (index == nFirst) return pollFirst();

        if (index == nAfterLast - 1) return pollLast();

        T removedElement = (T) buffer[index];

        // [first...index...afterLast) || [0...index...afterLast) +
        // [first...buffer.length-1
        // [first...buffer.length-1]
        if (index < nAfterLast) {

            System.arraycopy(buffer, index + 1, buffer, index, nAfterLast - index - 1);
            buffer[nAfterLast - 1] = null;
            afterLast--;

        } else {
            // (index >= nFirst && nAfterLast <= nFirst)
            // [0...afterLast) + [first...index...buffer.length-1]
            buffer[nFirst] = null;
            first++;
        }
        size--;
        return removedElement;
    }

    // ========================
    // Deque Interface Methods
    // ========================

    @Override
    public void addFirst(T t) {
        ensureCapacity();
        first = normalizeIdx(first - 1);
        buffer[first] = t;
        size++;
    }

    @Override
    public void addLast(T t) {
        ensureCapacity();
        buffer[afterLast] = t;
        afterLast = normalizeIdx(afterLast + 1);
        size++;
    }

    @Override
    public boolean offerFirst(T t) {
        addFirst(t);
        return true;
    }

    @Override
    public boolean offerLast(T t) {
        addLast(t);
        return true;
    }

    @Override
    public T pollFirst() {
        if (isEmpty()) return null;
        @SuppressWarnings("unchecked") T value = (T) buffer[first];
        buffer[first] = null;
        first = normalizeIdx(first + 1);
        size--;
        return value;
    }

    @Override
    public T pollLast() {
        if (isEmpty()) return null;
        afterLast = normalizeIdx(afterLast - 1);
        @SuppressWarnings("unchecked") T value = (T) buffer[afterLast];
        buffer[afterLast] = null;
        size--;
        return value;
    }

    @Override
    public T removeFirst() {
        checkEmpty();
        return pollFirst();
    }

    @Override
    public T removeLast() {
        checkEmpty();
        return pollLast();
    }


    @Override
    public T getFirst() {
        checkEmpty();
        @SuppressWarnings("unchecked")
        T value = (T) buffer[first];
        return value;
    }

    @Override
    public T getLast() {
        checkEmpty();
        int lastIdx = normalizeIdx(afterLast - 1);
        @SuppressWarnings("unchecked")
        T value = (T) buffer[lastIdx];
        return value;
    }

    @Override
    public T peekFirst() {
        if (isEmpty()) return null;
        @SuppressWarnings("unchecked")
        T value = (T) buffer[first];
        return value;
    }

    @Override
    public T peekLast() {
        if (isEmpty()) return null;
        int lastIdx = normalizeIdx(afterLast - 1);
        @SuppressWarnings("unchecked")
        T value = (T) buffer[lastIdx];
        return value;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        Iterator<T> iter = iterator();
        while (iter.hasNext()) {
            if (Objects.equals(iter.next(), o)) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        Iterator<T> iter = descendingIterator();
        while (iter.hasNext()) {
            if (Objects.equals(iter.next(), o)) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(T t) {
        addLast(t);
        return true;
    }

    @Override
    public boolean offer(T t) {
        addLast(t);
        return true;
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        return getFirst();
    }

    @Override
    public T peek() {
        return peekFirst();
    }

    @Override
    public void push(T t) {
        addFirst(t);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    // ========================
    // Collection Interface
    // ========================

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            Object e = buffer[(first + i) % buffer.length];
            if (Objects.equals(e, o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int cursor = 0;
            private int lastRet = -1;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int idx = normalizeIdx(first + cursor);
                T element = (T) buffer[idx];
                lastRet = cursor;
                cursor++;
                return element;
            }

            @Override
            public void remove() {
                if (lastRet < 0) {
                    throw new IllegalStateException();
                }
                removeByNormalizedIndex(normalizeIdx(first + lastRet));
                cursor = lastRet;
                lastRet = -1;
            }
        };
    }

    @Override
    public Iterator<T> descendingIterator() {
        return new Iterator<>() {
            private int cursor = size - 1;
            private int lastRet = -1;

            @Override
            public boolean hasNext() {
                return cursor >= 0;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int idx = normalizeIdx(first + cursor);
                T element = (T) buffer[idx];
                lastRet = cursor;
                cursor--;
                return element;
            }

            @Override
            public void remove() {
                if (lastRet < 0) {
                    throw new IllegalStateException();
                }

                removeByNormalizedIndex(normalizeIdx(first + lastRet));

                cursor = lastRet - 1;
                lastRet = -1;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        for (int i = 0; i < size; i++) {
            arr[i] = buffer[(first + i) % buffer.length];
        }
        return arr;
    }


    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c.isEmpty()) return false;
        for (T item : c) {
            addLast(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            while (removeFirstOccurrence(o)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<T> iter = iterator();
        while (iter.hasNext()) {
            if (!c.contains(iter.next())) {
                iter.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        Arrays.fill(buffer, null);
        first = 0;
        afterLast = 0;
        size = 0;
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
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
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(", ");
            sb.append(buffer[(first + i) % buffer.length]);
        }
        sb.append("]");
        return sb.toString();
    }
}
