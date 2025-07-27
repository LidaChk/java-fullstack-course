package org.example.week03.customdeques;

import org.example.week03.customlinkedlist.CustomLinkedList;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class CustomDequeBasedOnCustomLinkedList<T> implements Deque<T> {
    private final CustomLinkedList<T> linkedList;

    public CustomDequeBasedOnCustomLinkedList() {
        this.linkedList = new CustomLinkedList<>();
    }

    public CustomDequeBasedOnCustomLinkedList(Collection<? extends T> c) {
        this.linkedList = new CustomLinkedList<>(c);
    }

    @Override
    public void addFirst(T t) {
        linkedList.addFirst(t);
    }

    @Override
    public void addLast(T t) {
        linkedList.addLast(t);
    }

    @Override
    public boolean offerFirst(T t) {
        return linkedList.offerFirst(t);
    }

    @Override
    public boolean offerLast(T t) {
        return linkedList.offerLast(t);
    }

    @Override
    public T removeFirst() {
        return linkedList.removeFirst();
    }

    @Override
    public T removeLast() {
        return linkedList.removeLast();
    }

    @Override
    public T pollFirst() {
        return linkedList.pollFirst();
    }

    @Override
    public T pollLast() {
        return linkedList.pollLast();
    }

    @Override
    public T getFirst() {
        return linkedList.getFirst();
    }

    @Override
    public T getLast() {
        return linkedList.getLast();
    }

    @Override
    public T peekFirst() {
        return linkedList.peekFirst();
    }

    @Override
    public T peekLast() {
        return linkedList.peekLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return linkedList.removeFirstOccurrence(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return linkedList.removeLastOccurrence(o);
    }

    @Override
    public boolean add(T t) {
        return linkedList.add(t);
    }

    @Override
    public boolean offer(T t) {
        return linkedList.offer(t);
    }

    @Override
    public T remove() {
        return linkedList.remove();
    }

    @Override
    public T poll() {
        return linkedList.poll();
    }

    @Override
    public T element() {
        return linkedList.element();
    }

    @Override
    public T peek() {
        return linkedList.peek();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return linkedList.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return linkedList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return linkedList.retainAll(c);
    }

    @Override
    public void clear() {
        linkedList.clear();
    }

    @Override
    public void push(T t) {
        linkedList.push(t);
    }

    @Override
    public T pop() {
        return linkedList.pop();
    }

    @Override
    public boolean remove(Object o) {
        return linkedList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return linkedList.containsAll(c);
    }

    @Override
    public boolean contains(Object o) {
        return linkedList.contains(o);
    }

    @Override
    public int size() {
        return linkedList.size();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return linkedList.iterator();
    }

    @Override
    public Object[] toArray() {
        return linkedList.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return linkedList.toArray(a);
    }

    @Override
    public Iterator<T> descendingIterator() {
        return linkedList.descendingIterator();
    }

    @Override
    public String toString() {
        return linkedList.toString();
    }
}
