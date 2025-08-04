package org.example.week04.hashmapwithchainingstrategy;

import java.util.*;

// ChainingStrategy: Resolves hash collisions by storing modes in linked lists (chains) at each bucket.
// When collision occurs, new node is added to the list (O(1)).
public class HashMapWithChainingStrategy<K, V> implements Map<K, V> {

    private Node<K, V>[] table;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 10;
    private static final float GROWTH_FACTOR = 1.5f;
    private static final float LOAD_FACTOR = 0.75f; // size/capacity

    @SuppressWarnings("unchecked")
    public HashMapWithChainingStrategy() {
        this.capacity = DEFAULT_CAPACITY;
        this.table = new Node[capacity];
        this.size = 0;
    }

    private int hash(K key) {
        return key == null ? 0 : Math.abs(((Object) key).hashCode() % capacity);
    }

    private void ensureCapacity() {
        if (size >= capacity * LOAD_FACTOR) {
            capacity = table.length == 0 ? DEFAULT_CAPACITY : Math.max((int) (table.length * GROWTH_FACTOR) + 1, table.length + 1);

            resize();
        }
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldTable = table;
        table = new Node[capacity];
        size = 0;

        for (Node<K, V> bucket : oldTable) {
            Node<K, V> current = bucket;
            while (current != null) {
                put(current.key, current.value);
                current = current.nextInChain;
            }
        }
    }

    @Override
    public V put(K key, V value) {
        ensureCapacity();

        int index = hash(key);
        Node<K, V> current = table[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            current = current.nextInChain;
        }

        table[index] = new Node<>(key, value, table[index]);
        size++;
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        K k = (K) key;
        int index = hash(k);
        Node<K, V> current = table[index];
        while (current != null) {
            if (Objects.equals(current.key, k)) {
                return current.value;
            }
            current = current.nextInChain;
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        K k = (K) key;
        int index = hash(k);
        Node<K, V> current = table[index];
        Node<K, V> prev = null;

        while (current != null) {
            if (Objects.equals(current.key, k)) {
                if (prev == null) {
                    table[index] = current.nextInChain;
                } else {
                    prev.nextInChain = current.nextInChain;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.nextInChain;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public boolean containsValue(Object value) {
        for (Node<K, V> bucket : table) {
            Node<K, V> current = bucket;
            while (current != null) {
                if (Objects.equals(value, current.value)) {
                    return true;
                }
                current = current.nextInChain;
            }
        }
        return false;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entries = new HashSet<>();
        for (Node<K, V> bucket : table) {
            Node<K, V> current = bucket;
            while (current != null) {
                entries.add(new AbstractMap.SimpleImmutableEntry<>(current.key, current.value));
                current = current.nextInChain;
            }
        }
        return entries;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Node<K, V> bucket : table) {
            Node<K, V> current = bucket;
            while (current != null) {
                keys.add(current.key);
                current = current.nextInChain;
            }
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (Node<K, V> bucket : table) {
            Node<K, V> current = bucket;
            while (current != null) {
                values.add(current.value);
                current = current.nextInChain;
            }
        }
        return values;
    }


    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Map<?, ?> m)) return false;
        return this.entrySet().equals(m.entrySet());
    }

    @Override
    public int hashCode() {
        return entrySet().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (Node<K, V> bucket : table) {
            Node<K, V> current = bucket;
            while (current != null) {
                if (!first) sb.append(", ");
                sb.append(current.key).append("=").append(current.value);
                first = false;
                current = current.nextInChain;
            }
        }
        sb.append("}");
        return sb.toString();
    }

}
