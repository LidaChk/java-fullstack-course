package org.example.week03.customlinkedlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node<T> {
    private Node<T> prev;
    private T data;
    private Node<T> next;
}
