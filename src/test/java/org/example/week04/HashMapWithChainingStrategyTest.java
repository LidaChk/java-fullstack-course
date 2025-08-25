package org.example.week04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HashMapWithChainingStrategy Specific Implementation Tests")
class HashMapWithChainingStrategyTest {

    private static Stream<Arguments> mapContentsProvider() {
        return Stream.of(
                Arguments.of(Collections.emptyMap()),
                Arguments.of(Map.of("key1", "value1")),
                Arguments.of(Map.of("key1", "value1", "key2", "value2")),
                Arguments.of(Map.of("key1", "value1", "key2", "value2", "key3", "value3"))
        );
    }

    @ParameterizedTest
    @MethodSource("mapContentsProvider")
    @DisplayName("equals() and hashCode() between two custom maps")
    void equalsAndHashCodeBetweenCustomMaps(Map<String, String> contents) {
        HashMapWithChainingStrategy<String, String> map1 = new HashMapWithChainingStrategy<>();
        HashMapWithChainingStrategy<String, String> map2 = new HashMapWithChainingStrategy<>();

        map1.putAll(contents);
        map2.putAll(contents);

        assertEquals(map1, map2);
        assertEquals(map1.hashCode(), map2.hashCode());
    }

    @ParameterizedTest
    @MethodSource("mapContentsProvider")
    @DisplayName("equals() and hashCode() between custom and standard HashMap")
    void equalsAndHashCodeWithStandardMap(Map<String, String> contents) {
        HashMapWithChainingStrategy<String, String> custom = new HashMapWithChainingStrategy<>();

        custom.putAll(contents);
        Map<String, String> standard = new HashMap<>(contents);

        assertEquals(custom, standard);
        assertEquals(custom.hashCode(), standard.hashCode());
    }

    @ParameterizedTest
    @MethodSource("mapContentsProvider")
    @DisplayName("custom maps with different contents are not equal")
    void differentCustomMapsNotEqual(Map<String, String> contents) {
        HashMapWithChainingStrategy<String, String> map1 = new HashMapWithChainingStrategy<>();
        HashMapWithChainingStrategy<String, String> map2 = new HashMapWithChainingStrategy<>();

        map1.putAll(contents);
        map2.putAll(contents);

        map1.put("key4", "value4");
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
        HashMapWithChainingStrategy<CollisionKey, String> map = new HashMapWithChainingStrategy<>();

        CollisionKey key1 = new CollisionKey("first");
        CollisionKey key2 = new CollisionKey("second");
        CollisionKey key3 = new CollisionKey("third");

        map.put(key1, "value1");
        map.put(key2, "value2");
        map.put(key3, "value3");

        assertEquals(3, map.size());
        assertEquals("value1", map.get(key1));
        assertEquals("value2", map.get(key2));
        assertEquals("value3", map.get(key3));
    }
}