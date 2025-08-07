package org.example.week04.hashmapwithchainingstrategy;

import org.example.week04.linkedhashmapwithchainingstrategy.LinkedHashMapWithChainingStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LinkedHashMapWithChainingStrategy Tests")
class LinkedHashMapWithChainingStrategyTest {

    @Test
    @DisplayName("should maintain insertion order in entrySet")
    void shouldMaintainInsertionOrderInEntrySet() {
        LinkedHashMapWithChainingStrategy<String, String> map = new LinkedHashMapWithChainingStrategy<>();

        map.put("first", "1");
        map.put("second", "2");
        map.put("third", "3");

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();

        assertTrue(iterator.hasNext());
        Map.Entry<String, String> first = iterator.next();
        assertEquals("first", first.getKey());
        assertEquals("1", first.getValue());

        assertTrue(iterator.hasNext());
        Map.Entry<String, String> second = iterator.next();
        assertEquals("second", second.getKey());
        assertEquals("2", second.getValue());

        assertTrue(iterator.hasNext());
        Map.Entry<String, String> third = iterator.next();
        assertEquals("third", third.getKey());
        assertEquals("3", third.getValue());

        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("should maintain insertion order in keySet")
    void shouldMaintainInsertionOrderInKeySet() {
        LinkedHashMapWithChainingStrategy<String, String> map = new LinkedHashMapWithChainingStrategy<>();

        map.put("first", "1");
        map.put("second", "2");
        map.put("third", "3");

        Iterator<String> iterator = map.keySet().iterator();

        assertTrue(iterator.hasNext());
        assertEquals("first", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("second", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("third", iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("should maintain insertion order in values")
    void shouldMaintainInsertionOrderInValues() {
        LinkedHashMapWithChainingStrategy<String, String> map = new LinkedHashMapWithChainingStrategy<>();

        map.put("first", "1");
        map.put("second", "2");
        map.put("third", "3");

        Iterator<String> iterator = map.values().iterator();

        assertTrue(iterator.hasNext());
        assertEquals("1", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("2", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("3", iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("should maintain insertion order in toString")
    void shouldMaintainInsertionOrderInToString() {
        LinkedHashMapWithChainingStrategy<String, String> map = new LinkedHashMapWithChainingStrategy<>();

        map.put("first", "1");
        map.put("second", "2");
        map.put("third", "3");

        String str = map.toString();
        System.out.println("toString output: " + str);
        // Check that the order is maintained in the string representation
        int firstIndex = str.indexOf("\"first\":1");
        int secondIndex = str.indexOf("\"second\":2");
        int thirdIndex = str.indexOf("\"third\":3");

        assertTrue(firstIndex < secondIndex, "First should come before second. firstIndex: " + firstIndex
                + ", secondIndex: " + secondIndex + ", str: " + str);
        assertTrue(secondIndex < thirdIndex, "Second should come before third. secondIndex: " + secondIndex
                + ", thirdIndex: " + thirdIndex + ", str: " + str);
    }

    @Test
    @DisplayName("should maintain insertion order after updates")
    void shouldMaintainInsertionOrderAfterUpdates() {
        LinkedHashMapWithChainingStrategy<String, String> map = new LinkedHashMapWithChainingStrategy<>();

        map.put("first", "1");
        map.put("second", "2");
        map.put("third", "3");

        // Update existing key
        map.put("second", "updated");

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();

        assertTrue(iterator.hasNext());
        Map.Entry<String, String> first = iterator.next();
        assertEquals("first", first.getKey());
        assertEquals("1", first.getValue());

        assertTrue(iterator.hasNext());
        Map.Entry<String, String> second = iterator.next();
        assertEquals("second", second.getKey());
        assertEquals("updated", second.getValue());

        assertTrue(iterator.hasNext());
        Map.Entry<String, String> third = iterator.next();
        assertEquals("third", third.getKey());
        assertEquals("3", third.getValue());

        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("should maintain insertion order after removals")
    void shouldMaintainInsertionOrderAfterRemovals() {
        LinkedHashMapWithChainingStrategy<String, String> map = new LinkedHashMapWithChainingStrategy<>();

        map.put("first", "1");
        map.put("second", "2");
        map.put("third", "3");
        map.put("fourth", "4");

        // Remove middle element
        map.remove("second");

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();

        assertTrue(iterator.hasNext());
        Map.Entry<String, String> first = iterator.next();
        assertEquals("first", first.getKey());
        assertEquals("1", first.getValue());

        assertTrue(iterator.hasNext());
        Map.Entry<String, String> third = iterator.next();
        assertEquals("third", third.getKey());
        assertEquals("3", third.getValue());

        assertTrue(iterator.hasNext());
        Map.Entry<String, String> fourth = iterator.next();
        assertEquals("fourth", fourth.getKey());
        assertEquals("4", fourth.getValue());

        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("should maintain insertion order even with hash collisions")
    void shouldMaintainInsertionOrderEvenWithHashCollisions() {
        LinkedHashMapWithChainingStrategy<CollisionKey, String> map = new LinkedHashMapWithChainingStrategy<>();

        CollisionKey key1 = new CollisionKey("first");
        CollisionKey key2 = new CollisionKey("second");
        CollisionKey key3 = new CollisionKey("third");

        // All these keys will have the same hash code, causing collisions
        map.put(key1, "value1");
        map.put(key2, "value2");
        map.put(key3, "value3");

        // Verify the entries are in insertion order despite collisions
        Iterator<Map.Entry<CollisionKey, String>> iterator = map.entrySet().iterator();

        assertTrue(iterator.hasNext());
        Map.Entry<CollisionKey, String> first = iterator.next();
        assertEquals(key1, first.getKey());
        assertEquals("value1", first.getValue());

        assertTrue(iterator.hasNext());
        Map.Entry<CollisionKey, String> second = iterator.next();
        assertEquals(key2, second.getKey());
        assertEquals("value2", second.getValue());

        assertTrue(iterator.hasNext());
        Map.Entry<CollisionKey, String> third = iterator.next();
        assertEquals(key3, third.getKey());
        assertEquals("value3", third.getValue());

        assertFalse(iterator.hasNext());
    }
}
