package org.example.week04.hashmapwithchainingstrategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HashMapWithChainingStrategy Specific Implementation Tests")
class HashMapWithChainingStrategyTest {

    @Test
    @DisplayName("equals() and hashCode() should work correctly")
    void equalsAndHashCodeShouldWorkCorrectly() {
        HashMapWithChainingStrategy<String, String> map1 = new HashMapWithChainingStrategy<>();
        HashMapWithChainingStrategy<String, String> map2 = new HashMapWithChainingStrategy<>();
        Map<String, String> hashMap = new HashMap<>();

        assertEquals(map1, map2);
        assertEquals(map1.hashCode(), map2.hashCode());

        map1.put("key1", "value1");
        map1.put("key2", "value2");
        map2.put("key1", "value1");
        map2.put("key2", "value2");
        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");

        assertEquals(map1, map2);
        assertEquals(map1.hashCode(), map2.hashCode());
        assertEquals(map1, hashMap);

        map1.put("key3", "value3");
        assertNotEquals(map1, map2);
    }

    @Test
    @DisplayName("toString() should return correct string representation")
    void toStringShouldReturnCorrectStringRepresentation() {
        HashMapWithChainingStrategy<String, String> map = new HashMapWithChainingStrategy<>();
        assertEquals("{}", map.toString());

        map.put("key1", "value1");
        String str = map.toString();
        assertTrue(str.contains("\"key1\":value1"));

        map.put("key2", "value2");
        str = map.toString();
        assertTrue(str.contains("\"key1\":value1"));
        assertTrue(str.contains("\"key2\":value2"));
    }

    @Test
    @DisplayName("should handle resizing correctly")
    void shouldHandleResizingCorrectly() {
        HashMapWithChainingStrategy<Integer, String> map = new HashMapWithChainingStrategy<>();

        for (int i = 0; i < 20; i++) {
            map.put(i, "value" + i);
        }

        assertEquals(20, map.size());

        for (int i = 0; i < 20; i++) {
            assertEquals("value" + i, map.get(i));
        }
    }

    @Test
    @DisplayName("remove() should work correctly with chained entries")
    void removeShouldWorkWithChainedEntries() {
        HashMapWithChainingStrategy<String, String> map = new HashMapWithChainingStrategy<>();

        map.put("Aa", "value1");
        map.put("BB", "value2");
        map.put("AaAa", "value3");

        assertEquals(3, map.size());
        String result = map.remove("BB");
        assertEquals("value2", result);
        assertEquals(2, map.size());
        assertEquals("value1", map.get("Aa"));
        assertEquals("value3", map.get("AaAa"));
        assertNull(map.get("BB"));
    }

    @Test
    @DisplayName("put() should handle collision by chaining")
    void putShouldHandleCollisionByChaining() {

        HashMapWithChainingStrategy<String, String> map = new HashMapWithChainingStrategy<>();

        map.put("Aa", "value1"); // hash = 2
        map.put("BB", "value2"); // hash = 2
        assertEquals(2, map.size());
        assertEquals("value1", map.get("Aa"));
        assertEquals("value2", map.get("BB"));

    }
}
