package org.example.week04.linkedhashmapwithchainingstrategy;

import lombok.Getter;
import org.example.week04.Node;

import java.util.Objects;

@Getter
public class LinkedNode<K, V> extends Node<K, V> {
    LinkedNode<K, V> nextInInsertionOrder;
    LinkedNode<K, V> prevInInsertionOrder;

    public LinkedNode(K key, V value) {
        super(key, value, null);
        this.nextInInsertionOrder = null;
        this.prevInInsertionOrder = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof LinkedNode<?, ?> node))
            return false;
        if (!super.equals(o))
            return false;
        return Objects.equals(nextInInsertionOrder, node.nextInInsertionOrder) &&
                Objects.equals(prevInInsertionOrder, node.prevInInsertionOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nextInInsertionOrder, prevInInsertionOrder);
    }
}
