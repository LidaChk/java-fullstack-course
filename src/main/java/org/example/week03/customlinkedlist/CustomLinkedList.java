package org.example.week03.customlinkedlist;

import java.util.*;

public class CustomLinkedList<T> implements List<T>, Deque<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public CustomLinkedList() {
        head = new Node<>(null, null, null);
        tail = new Node<>(null, null, null);

        clear();
    }

    public CustomLinkedList(Collection<? extends T> c) {
        this();
        addAll(c);
    }

    @Override
    public void clear() {
        head.setNext(tail);
        tail.setPrev(head);
        size = 0;
    }

    @Override
    public void addFirst(T t) {
        Node<T> newNode = new Node<>(head, t, head.getNext());
        head.setNext(newNode);
        size++;
    }

    @Override
    public void addLast(T t) {
        Node<T> newNode = new Node<>(tail.getPrev(), t, tail);
        head.setPrev(newNode);
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
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Node<T> node = head.getNext();
        head.setNext(node.getNext());
        size--;
        return node.getData();
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Node<T> node = tail.getPrev();
        tail.setPrev(node.getPrev());
        size--;
        return node.getData();
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
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return head.getNext().getData();
    }

    @Override
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return tail.getPrev().getData();
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
            if (current.getData().equals(o)) {
                removeCurrentAndDecreaseSize(current);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean removeLastOccurrence(Object o) {
        Node<T> current = tail.getPrev();
        while (current != head) {
            if (current.getData().equals(o)) {
                removeCurrentAndDecreaseSize(current);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean offer(T t) {
        addLast(t);
        return false;
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

    /**
     * Pushes an element onto the stack represented by this deque (in other
     * words, at the head of this deque)
     */
    @Override
    public void push(T t) {
        addFirst(t);
    }

    /**
     * Pops an element from the stack represented by this deque.  In other
     * words, removes and returns the first element of this deque.
     *
     * <p>This method is equivalent to {@link #removeFirst()}.
     */
    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head.getNext();

            @Override
            public boolean hasNext() {
                return current != tail;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements in the list");
                }
                T data = current.getData();
                current = current.getNext();
                return data;
            }
        };
    }

    @Override
    public Iterator<T> descendingIterator() {
        return new Iterator<T>() {
            private Node<T> current = tail.getPrev();

            @Override
            public boolean hasNext() {
                return current != head;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements in the list");
                }
                T data = current.getData();
                current = current.getPrev();
                return data;
            }
        };
    }


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
        Node<T> current = head.getNext();
        while (current != tail) {
            if (current.getData().equals(o)) {
                return true;
            }
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
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public boolean add(T t) {
        return offerLast(t);
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
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

    /**
     * Inserts all of the elements in the specified collection into this
     * list at the specified position (optional operation).
     *
     * @return {@code true} if this list changed as a result of the call
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c.isEmpty()) {
            return false;
        }

        Node<T> current = getNodeByIndex(index);

        for (T t : c) {
            Node<T> newNode = new Node<>(current.getPrev(), t, current);
            current.getPrev().setNext(newNode);
            current.setPrev(newNode);
            current = newNode;
            size++;
        }
        return true;
    }

    /**
     * Removes from this list all of its elements that are contained in the
     * specified collection (optional operation).
     *
     * @return {@code true} if this list changed as a result of the call
     */
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

    /**
     * Retains only the elements in this list that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this list all of its elements that are not contained in the
     * specified collection.
     *
     * @return {@code true} if this list changed as a result of the call
     */
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
            if (o.equals(current.getData())) {
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
            if (o.equals(current.getData())) {
                return i;
            }
            current = current.getPrev();
        }
        return -1;
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence).
     *
     * @return a list iterator over the elements in this list (in proper
     * sequence)
     */
    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns a view of the portion of this list between the specified
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.  (If
     * {@code fromIndex} and {@code toIndex} are equal, the returned list is
     * empty.)
     */
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", fromIndex: " + fromIndex + ", Size: " + size);
        }

        List<T> result = new ArrayList<>();

        Node<T> current = head;
        for (int i = 0; i <= fromIndex; i++) {
            current = current.getNext();
        }

        for (int i = fromIndex; i < toIndex; i++) {
            result.add(current.getData());
        }
        return result;
    }


    // HELPERS

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
}
