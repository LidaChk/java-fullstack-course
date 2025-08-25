package org.example.week04;

import org.example.week04.linkedhashmapwithchainingstrategy.LinkedHashMapWithChainingStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HashMap Tests")
class HashMapTest {

    private static Stream<Arguments> mapSuppliers() {
        return Stream.of(
                Arguments.of("HashMapWithChainingStrategy",
                        (Supplier<Map<String, String>>) HashMapWithChainingStrategy::new),
                Arguments.of("LinkedHashMapWithChainingStrategy",
                        (Supplier<Map<String, String>>) LinkedHashMapWithChainingStrategy::new),
                Arguments.of("HashMap", (Supplier<Map<String, String>>) HashMap::new));
    }

    private static Stream<Arguments> mapSuppliersInt() {
        return Stream.of(
                Arguments.of("HashMapWithChainingStrategy",
                        (Supplier<Map<Integer, String>>) HashMapWithChainingStrategy::new),
                Arguments.of("LinkedHashMapWithChainingStrategy",
                        (Supplier<Map<Integer, String>>) LinkedHashMapWithChainingStrategy::new),
                Arguments.of("HashMap", (Supplier<Map<Integer, String>>) HashMap::new));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Constructor: should create empty map")
    void constructorShouldCreateEmptyMap(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Put: should add new key-value pair")
    void putShouldAddNewKeyValuePair(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        String result = map.put("key1", "value1");
        assertNull(result);
        assertEquals(1, map.size());
        assertEquals("value1", map.get("key1"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Put: should replace value for existing key")
    void putShouldReplaceValueForExistingKey(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("key1", "value1");
        String result = map.put("key1", "value2");
        assertEquals("value1", result);
        assertEquals(1, map.size());
        assertEquals("value2", map.get("key1"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Put: should handle null key")
    void putShouldHandleNullKey(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        String result = map.put(null, "nullValue");
        assertNull(result);
        assertEquals(1, map.size());
        assertEquals("nullValue", map.get(null));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Put: should handle null value")
    void putShouldHandleNullValue(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        String result = map.put("nullKey", null);
        assertNull(result);
        assertEquals(1, map.size());
        assertTrue(map.containsKey("nullKey"));
        assertNull(map.get("nullKey"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Put: should handle both null key and null value")
    void putShouldHandleBothNullKeyAndNullValue(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        String result = map.put(null, null);
        assertNull(result);
        assertEquals(1, map.size());
        assertTrue(map.containsKey(null));
        assertNull(map.get(null));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliersInt")
    @DisplayName("Put: should work with different key types")
    void putShouldWorkWithDifferentKeyTypes(String name, Supplier<Map<Integer, String>> mapSupplier) {
        Map<Integer, String> map = mapSupplier.get();
        map.put(1, "one");
        map.put(2, "two");
        assertEquals(2, map.size());
        assertEquals("one", map.get(1));
        assertEquals("two", map.get(2));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Get: should return value for existing key")
    void getShouldReturnValueForExistingKey(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("key1", "value1");
        assertEquals("value1", map.get("key1"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Get: should return null for non-existing key")
    void getShouldReturnNullForNonExistingKey(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        assertNull(map.get("nonExistingKey"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Get: should handle null key")
    void getShouldHandleNullKey(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put(null, "nullValue");
        assertEquals("nullValue", map.get(null));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Get: should return null for null key when not present")
    void getShouldReturnNullForNullKeyWhenNotPresent(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        assertNull(map.get(null));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Get: should return null for existing key with null value")
    void getShouldReturnNullForExistingKeyWithNullValue(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("nullValueKey", null);
        assertNull(map.get("nullValueKey"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Remove: should remove existing key and return its value")
    void removeShouldRemoveExistingKeyAndReturnValue(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("key1", "value1");
        String result = map.remove("key1");
        assertEquals("value1", result);
        assertEquals(0, map.size());
        assertFalse(map.containsKey("key1"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Remove: should return null for non-existing key")
    void removeShouldReturnNullForNonExistingKey(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        assertNull(map.remove("nonExistingKey"));
        assertEquals(0, map.size());
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Remove: should handle null key")
    void removeShouldHandleNullKey(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put(null, "nullValue");
        String result = map.remove(null);
        assertEquals("nullValue", result);
        assertEquals(0, map.size());
        assertFalse(map.containsKey(null));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Remove: should handle null key when not present")
    void removeShouldHandleNullKeyWhenNotPresent(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        assertNull(map.remove(null));
        assertEquals(0, map.size());
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Remove: should handle existing key with null value")
    void removeShouldHandleExistingKeyWithNullValue(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("nullValueKey", null);
        String result = map.remove("nullValueKey");
        assertNull(result);
        assertEquals(0, map.size());
        assertFalse(map.containsKey("nullValueKey"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Size: should return 0 for empty map")
    void sizeShouldReturnZeroForEmptyMap(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        assertEquals(0, map.size());
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Size: should return correct count after adding elements")
    void sizeShouldReturnCorrectCountAfterAddingElements(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        assertEquals(0, map.size());
        map.put("key1", "value1");
        assertEquals(1, map.size());
        map.put("key2", "value2");
        assertEquals(2, map.size());
        map.put("key3", "value3");
        assertEquals(3, map.size());
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Size: should decrease after removing elements")
    void sizeShouldDecreaseAfterRemovingElements(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        assertEquals(3, map.size());
        map.remove("key2");
        assertEquals(2, map.size());
        map.remove("key1");
        assertEquals(1, map.size());
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Size: should remain same when replacing existing key")
    void sizeShouldRemainSameWhenReplacingExistingKey(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("key1", "value1");
        assertEquals(1, map.size());
        map.put("key1", "value2");
        assertEquals(1, map.size());
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Size: should return 0 after clear()")
    void sizeShouldReturnZeroAfterClear(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertEquals(2, map.size());
        map.clear();
        assertEquals(0, map.size());
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("ContainsKey: should return true for existing key")
    void containsKeyShouldReturnTrueForExistingKey(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("key1", "value1");
        assertTrue(map.containsKey("key1"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("ContainsKey: should return false for non-existing key")
    void containsKeyShouldReturnFalseForNonExistingKey(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        assertFalse(map.containsKey("nonExistingKey"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("ContainsKey: should handle null key when present")
    void containsKeyShouldHandleNullKeyWhenPresent(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put(null, "nullValue");
        assertTrue(map.containsKey(null));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("ContainsKey: should handle null key when not present")
    void containsKeyShouldHandleNullKeyWhenNotPresent(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        assertFalse(map.containsKey(null));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("ContainsKey: should return true for key with null value")
    void containsKeyShouldReturnTrueForKeyWithNullValue(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("nullValueKey", null);
        assertTrue(map.containsKey("nullValueKey"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("EntrySet: should return empty set for empty map")
    void entrySetShouldReturnEmptySetForEmptyMap(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        assertNotNull(entrySet);
        assertEquals(0, entrySet.size());
        assertTrue(entrySet.isEmpty());
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("EntrySet: should contain all entries")
    void entrySetShouldContainAllEntries(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        assertEquals(3, entrySet.size());

        Set<Map.Entry<String, String>> expectedEntries = new HashSet<>();
        expectedEntries.add(new AbstractMap.SimpleImmutableEntry<>("key1", "value1"));
        expectedEntries.add(new AbstractMap.SimpleImmutableEntry<>("key2", "value2"));
        expectedEntries.add(new AbstractMap.SimpleImmutableEntry<>("key3", "value3"));

        assertEquals(expectedEntries, entrySet);
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("EntrySet: should handle null keys and values")
    void entrySetShouldHandleNullKeysAndValues(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put(null, "nullKey");
        map.put("nullValue", null);
        map.put("normal", "normal");

        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        assertEquals(3, entrySet.size());

        boolean foundNullKey = false;
        boolean foundNullValue = false;
        boolean foundNormal = false;

        for (Map.Entry<String, String> entry : entrySet) {
            if (entry.getKey() == null && "nullKey".equals(entry.getValue())) {
                foundNullKey = true;
            } else if ("nullValue".equals(entry.getKey()) && entry.getValue() == null) {
                foundNullValue = true;
            } else if ("normal".equals(entry.getKey()) && "normal".equals(entry.getValue())) {
                foundNormal = true;
            }
        }

        assertTrue(foundNullKey);
        assertTrue(foundNullValue);
        assertTrue(foundNormal);
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("KeySet: should return empty set for empty map")
    void keySetShouldReturnEmptySetForEmptyMap(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        Set<String> keySet = map.keySet();
        assertNotNull(keySet);
        assertEquals(0, keySet.size());
        assertTrue(keySet.isEmpty());
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("KeySet: should contain all keys")
    void keySetShouldContainAllKeys(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        Set<String> keySet = map.keySet();
        assertEquals(3, keySet.size());
        assertTrue(keySet.contains("key1"));
        assertTrue(keySet.contains("key2"));
        assertTrue(keySet.contains("key3"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("KeySet: should handle null key")
    void keySetShouldHandleNullKey(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put(null, "nullValue");
        map.put("normal", "normal");

        Set<String> keySet = map.keySet();
        assertEquals(2, keySet.size());
        assertTrue(keySet.contains(null));
        assertTrue(keySet.contains("normal"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Values: should return empty collection for empty map")
    void valuesShouldReturnEmptyCollectionForEmptyMap(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        Collection<String> values = map.values();
        assertNotNull(values);
        assertEquals(0, values.size());
        assertTrue(values.isEmpty());
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Values: should contain all values")
    void valuesShouldContainAllValues(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        Collection<String> values = map.values();
        assertEquals(3, values.size());
        assertTrue(values.contains("value1"));
        assertTrue(values.contains("value2"));
        assertTrue(values.contains("value3"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Values: should handle null value")
    void valuesShouldHandleNullValue(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("nullKey", null);
        map.put("normal", "normal");

        Collection<String> values = map.values();
        assertEquals(2, values.size());
        assertTrue(values.contains(null));
        assertTrue(values.contains("normal"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Values: containsValue() should return true for existing value")
    void containsValueShouldReturnTrueForExistingValue(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", null);

        assertTrue(map.containsValue("value1"));
        assertTrue(map.containsValue("value2"));
        assertTrue(map.containsValue(null));
        assertFalse(map.containsValue("nonExistingValue"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("PutAll: should add all entries from another map")
    void putAllShouldAddAllEntriesFromAnotherMap(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        Map<String, String> sourceMap = new HashMap<>();
        sourceMap.put("key1", "value1");
        sourceMap.put("key2", "value2");
        sourceMap.put("key3", "value3");

        map.putAll(sourceMap);

        assertEquals(3, map.size());
        assertEquals("value1", map.get("key1"));
        assertEquals("value2", map.get("key2"));
        assertEquals("value3", map.get("key3"));
    }

    @ParameterizedTest(name = "{0}: {displayName}")
    @MethodSource("mapSuppliers")
    @DisplayName("Clear: should remove all entries")
    void clearShouldRemoveAllEntries(String name, Supplier<Map<String, String>> mapSupplier) {
        Map<String, String> map = mapSupplier.get();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        map.clear();

        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
        assertFalse(map.containsKey("key1"));
        assertNull(map.get("key1"));
    }
}
