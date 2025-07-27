package org.example.week03;

import org.example.week03.customdeques.CustomDequeBasedOnCustomLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("CustomDequeBasedOnCustomLinkedList Tests")
public class CustomDequeBasedOnCustomLinkedListTest extends CustomArrayDequeTest {

    @BeforeEach
    @Override
    void setUp() {
        deque = new CustomDequeBasedOnCustomLinkedList<>();
    }
}
