package org.example.week03.customlinkedlist;

import java.util.*;

/**
 * A custom doubly linked list implementation that implements both List<T> and
 * Deque<T> interfaces.
 */
public class CustomLinkedList<T> implements List<T>, Deque<T> {
    private final Node<T> head;
    private final Node<T> tail;
    private int size;

    // ========================
    // Constructors
    // ========================
    public CustomLinkedList() {
        head = new Node<>(null, null, null);
        tail = new Node<>(null, null, null);
        clear();
    }

    public CustomLinkedList(Collection<? extends T> c) {
        this();
        addAll(c);
    }

    // ========================
    // Core Doubly Linked List Methods
    // ========================

    @Override
    public void clear() {
        head.setNext(tail);
        tail.setPrev(head);
        size = 0;
    }

    @Override
    public void addFirst(T t) {
        Node<T> nextNode = head.getNext();
        Node<T> newNode = new Node<>(head, t, nextNode);
        nextNode.setPrev(newNode);
        head.setNext(newNode);
        size++;
    }

    @Override
    public void addLast(T t) {
        Node<T> prevNode = tail.getPrev();
        Node<T> newNode = new Node<>(prevNode, t, tail);
        prevNode.setNext(newNode);
        tail.setPrev(newNode);
        size++;
    }

    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Node<T> node = head.getNext();
        head.setNext(node.getNext());
        size--;
        return node.getData();
    }

    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Node<T> node = tail.getPrev();
        Node<T> prevNode = node.getPrev();
        prevNode.setNext(tail);
        tail.setPrev(prevNode);
        size--;
        return node.getData();
    }

    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return head.getNext().getData();
    }

    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return tail.getPrev().getData();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // ========================
    // Helper Methods
    // ========================

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> current = head.getNext();
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current;
    }

    private void removeCurrentAndDecreaseSize(Node<T> current) {
        current.getPrev().setNext(current.getNext());
        current.getNext().setPrev(current.getPrev());
        size--;
    }

    // ========================
    // List<T> Methods
    // ========================

    @Override
    public boolean add(T t) {
        return offerLast(t);
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean contains(Object o) {
        Node<T> current = head.getNext();
        while (current != tail) {
            if (Objects.equals(current.getData(), o)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int index = 0;
        Node<T> current = head.getNext();
        while (current != tail) {
            result[index++] = current.getData();
            current = current.getNext();
        }
        return result;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.isEmpty()) {
            return true;
        }
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c.isEmpty()) {
            return false;
        }
        for (T t : c) {
            add(t);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c.isEmpty()) {
            return false;
        }
        Node<T> current = getNodeByIndex(index);
        Node<T> prev = current.getPrev();
        for (T t : c) {
            Node<T> newNode = new Node<>(prev, t, current);
            prev.setNext(newNode);
            prev = newNode;
            size++;
        }
        current.setPrev(prev);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        Node<T> current = head.getNext();
        while (current != tail) {
            T data = current.getData();
            if (c.contains(data)) {
                Node<T> next = current.getNext();
                removeCurrentAndDecreaseSize(current);
                modified = true;
                current = next;
            } else {
                current = current.getNext();
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Node<T> current = head.getNext();
        while (current != tail) {
            T data = current.getData();
            if (!c.contains(data)) {
                Node<T> next = current.getNext();
                removeCurrentAndDecreaseSize(current);
                modified = true;
                current = next;
            } else {
                current = current.getNext();
            }
        }
        return modified;
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).getData();
    }

    @Override
    public T set(int index, T element) {
        Node<T> current = getNodeByIndex(index);
        T data = current.getData();
        current.setData(element);
        return data;
    }

    @Override
    public void add(int index, T element) {
        Node<T> current = getNodeByIndex(index);
        Node<T> newNode = new Node<>(current.getPrev(), element, current);
        current.getPrev().setNext(newNode);
        current.setPrev(newNode);
        size++;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNodeByIndex(index);
        T data = current.getData();
        removeCurrentAndDecreaseSize(current);
        return data;
    }

    @Override
    public int indexOf(Object o) {
        Node<T> current = head.getNext();
        for (int i = 0; i < size; i++) {
            if (Objects.equals(current.getData(), o)) {
                return i;
            }
            current = current.getNext();
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node<T> current = tail.getPrev();
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(current.getData(), o)) {
                return i;
            }
            current = current.getPrev();
        }
        return -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> current = head.getNext();
            private Node<T> lastReturned = null;

            @Override
            public boolean hasNext() {
                return current != tail;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements in the list");
                }
                lastReturned = current;
                T data = current.getData();
                current = current.getNext();
                return data;
            }

            @Override
            public void remove() {
                if (lastReturned == null) {
                    throw new IllegalStateException("No element to remove");
                }
                removeCurrentAndDecreaseSize(lastReturned);
                lastReturned = null;
            }
        };
    }

    // ========================
    // Deque<T> Methods
    // ========================

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
        return size == 0 ? null : removeFirst();
    }

    @Override
    public T pollLast() {
        return size == 0 ? null : removeLast();
    }

    @Override
    public T peekFirst() {
        return head.getNext().getData();
    }

    @Override
    public T peekLast() {
        return tail.getPrev().getData();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        Node<T> current = head.getNext();
        while (current != tail) {
            if (Objects.equals(current.getData(), o)) {
                removeCurrentAndDecreaseSize(current);
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        Node<T> current = tail.getPrev();
        while (current != head) {
            if (Objects.equals(current.getData(), o)) {
                removeCurrentAndDecreaseSize(current);
                return true;
            }
            current = current.getPrev();
        }
        return false;
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

    @Override
    public Iterator<T> descendingIterator() {
        return new Iterator<>() {
            private Node<T> current = tail.getPrev();
            private Node<T> lastReturned = null;

            @Override
            public boolean hasNext() {
                return current != head;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements in the list");
                }
                lastReturned = current;
                T data = current.getData();
                current = current.getPrev();
                return data;
            }

            @Override
            public void remove() {
                if (lastReturned == null) {
                    throw new IllegalStateException("No element to remove");
                }
                removeCurrentAndDecreaseSize(lastReturned);
                lastReturned = null;
            }
        };
    }

    // ========================
    // Unsupported Methods
    // ========================

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("toArray(T1[] a) Not supported yet.");
    }

    @Override
    public ListIterator<T> listIterator() {
        List<T> listCopy = new ArrayList<>(this);
        return (ListIterator<T>) listCopy.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("listIterator(int index) Not supported yet.");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("subList Not supported yet.");
        // What is view??

        /*
         * if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
         * throw new IndexOutOfBoundsException("fromIndex: " + fromIndex +
         * ", fromIndex: " + fromIndex + ", Size: " + size);
         * }
         *
         * List<T> result = new ArrayList<>();
         *
         * Node<T> current = head;
         * for (int i = 0; i <= fromIndex; i++) {
         * current = current.getNext();
         * }
         *
         * for (int i = fromIndex; i < toIndex; i++) {
         * result.add(current.getData());
         * }
         * return result;
         */

    }

    // ========================
    // toString
    // ========================

    public String toString() {
        Node<T> current = head.getNext();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (current != tail) {
            sb.append(current.getData());
            current = current.getNext();
            if (current != tail) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
