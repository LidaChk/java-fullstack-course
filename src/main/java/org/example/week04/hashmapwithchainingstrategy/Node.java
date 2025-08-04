package org.example.week04.hashmapwithchainingstrategy;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Node<K, V> {
    final K key;
    V value;
    Node<K, V> nextInChain;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node<?, ?> node)) return false;
        return Objects.equals(key, node.key) &&
                Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
        // faster than Object.hash(key, value)
        // nullsafe
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }
}
