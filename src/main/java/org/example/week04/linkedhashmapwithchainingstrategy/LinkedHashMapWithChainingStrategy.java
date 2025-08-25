package org.example.week04.linkedhashmapwithchainingstrategy;

import org.example.week04.HashMapWithChainingStrategy;

import java.util.*;

public class LinkedHashMapWithChainingStrategy<K, V> extends HashMapWithChainingStrategy<K, V> {

    private LinkedNode<K, V> head;
    private LinkedNode<K, V> tail;

    @SuppressWarnings("unchecked")
    public LinkedHashMapWithChainingStrategy() {
        super();

        this.head = new LinkedNode<>(null, null);
        this.tail = new LinkedNode<>(null, null);

        this.head.nextInInsertionOrder = this.tail;
        this.tail.prevInInsertionOrder = this.head;

        this.table = new LinkedNode[capacity];
    }

    @Override
    public V put(K key, V value) {

        int index = hash(key);
        LinkedNode<K, V> current = (LinkedNode<K, V>) table[index];


        LinkedNode<K, V> existingNode = null;
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                existingNode = current;
                break;
            }
            current = (LinkedNode<K, V>) current.nextInChain;
        }

        if (existingNode != null) {

            V oldValue = existingNode.value;
            existingNode.value = value;
            return oldValue;
        } else {

            ensureCapacity();


            LinkedNode<K, V> newNode = new LinkedNode<>(key, value);


            int newIndex = hash(key);
            newNode.nextInChain = table[newIndex];
            table[newIndex] = newNode;


            addToInsertionOrderList(newNode);

            size++;
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        K k = (K) key;
        int index = hash(k);
        LinkedNode<K, V> current = (LinkedNode<K, V>) table[index];
        LinkedNode<K, V> prev = null;

        while (current != null) {
            if (Objects.equals(current.key, k)) {


                if (prev == null) {
                    table[index] = current.nextInChain;
                } else {
                    prev.nextInChain = current.nextInChain;
                }


                removeFromInsertionOrderList(current);

                size--;
                return current.value;
            }
            prev = current;
            current = (LinkedNode<K, V>) current.nextInChain;
        }
        return null;
    }

    @Override
    public void clear() {
        super.clear();

        head = new LinkedNode<>(null, null);
        tail = new LinkedNode<>(null, null);
        head.nextInInsertionOrder = tail;
        tail.prevInInsertionOrder = head;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entries = new LinkedHashSet<>();
        LinkedNode<K, V> current = head.nextInInsertionOrder;
        while (current != tail) {
            entries.add(new AbstractMap.SimpleImmutableEntry<>(current.key, current.value));
            current = current.nextInInsertionOrder;
        }
        return entries;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new LinkedHashSet<>();
        LinkedNode<K, V> current = head.nextInInsertionOrder;
        while (current != tail) {
            keys.add(current.key);
            current = current.nextInInsertionOrder;
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        LinkedNode<K, V> current = head.nextInInsertionOrder;
        while (current != tail) {
            values.add(current.value);
            current = current.nextInInsertionOrder;
        }
        return values;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        LinkedNode<K, V> current = head.nextInInsertionOrder;
        while (current != tail) {
            if (!first) sb.append(", ");
            sb.append("\"").append(current.key).append("\"").append(":").append(current.value);
            first = false;
            current = current.nextInInsertionOrder;
        }
        sb.append("}");
        return sb.toString();
    }

    private void addToInsertionOrderList(LinkedNode<K, V> node) {

        LinkedNode<K, V> lastNode = tail.prevInInsertionOrder;
        lastNode.nextInInsertionOrder = node;
        node.prevInInsertionOrder = lastNode;
        node.nextInInsertionOrder = tail;
        tail.prevInInsertionOrder = node;
    }


    private void removeFromInsertionOrderList(LinkedNode<K, V> node) {
        node.prevInInsertionOrder.nextInInsertionOrder = node.nextInInsertionOrder;
        node.nextInInsertionOrder.prevInInsertionOrder = node.prevInInsertionOrder;

        node.prevInInsertionOrder = null;
        node.nextInInsertionOrder = null;
    }

}
