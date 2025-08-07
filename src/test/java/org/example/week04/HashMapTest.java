package org.example.week04;

import org.example.week04.linkedhashmapwithchainingstrategy.LinkedHashMapWithChainingStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestFactory;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HashMap Tests")
class HashMapTest {

    private <K, V> Stream<DynamicTest> createTestsForEachMap(String testName,
                                                             Consumer<Map<K, V>> test) {
        List<Supplier<Map<K, V>>> mapFactories = new ArrayList<>();
        mapFactories.add(HashMapWithChainingStrategy::new);
        mapFactories.add(LinkedHashMapWithChainingStrategy::new);
        mapFactories.add(HashMap::new);

        Stream.Builder<DynamicTest> testBuilder = Stream.builder();

        for (Supplier<Map<K, V>> factory : mapFactories) {
            final String factoryName = factory.get().getClass().getSimpleName();
            testBuilder.add(DynamicTest.dynamicTest(testName + " [" + factoryName + "]", () -> {
                Map<K, V> map = factory.get();
                test.accept(map);
            }));
        }
        return testBuilder.build();
    }

    @Nested
    @DisplayName("Constructor operations")
    class ConstructorTests {
        @TestFactory
        @DisplayName("Default constructor should create empty map")
        Stream<DynamicTest> defaultConstructorShouldCreateEmptyMap() {
            return createTestsForEachMap("Default constructor should create empty map",
                    (Map<String, String> map) -> {
                        assertEquals(0, map.size());
                        assertTrue(map.isEmpty());
                    });
        }
    }

    @Nested
    @DisplayName("Put operations")
    class PutTests {
        @TestFactory
        @DisplayName("put() should add new key-value pair")
        Stream<DynamicTest> putShouldAddNewKeyValuePair() {
            return createTestsForEachMap("put() should add new key-value pair",
                    (Map<String, String> map) -> {
                        String result = map.put("key1", "value1");
                        assertNull(result);
                        assertEquals(1, map.size());
                        assertEquals("value1", map.get("key1"));
                    });
        }

        @TestFactory
        @DisplayName("put() should replace value for existing key")
        Stream<DynamicTest> putShouldReplaceValueForExistingKey() {
            return createTestsForEachMap("put() should replace value for existing key",
                    (Map<String, String> map) -> {
                        map.put("key1", "value1");
                        String result = map.put("key1", "value2");
                        assertEquals("value1", result);
                        assertEquals(1, map.size());
                        assertEquals("value2", map.get("key1"));
                    });
        }

        @TestFactory
        @DisplayName("put() should handle null key")
        Stream<DynamicTest> putShouldHandleNullKey() {
            return createTestsForEachMap("put() should handle null key",
                    (Map<String, String> map) -> {
                        String result = map.put(null, "nullValue");
                        assertNull(result);
                        assertEquals(1, map.size());
                        assertEquals("nullValue", map.get(null));
                    });
        }

        @TestFactory
        @DisplayName("put() should handle null value")
        Stream<DynamicTest> putShouldHandleNullValue() {
            return createTestsForEachMap("put() should handle null value",
                    (Map<String, String> map) -> {
                        String result = map.put("nullKey", null);
                        assertNull(result);
                        assertEquals(1, map.size());
                        assertTrue(map.containsKey("nullKey"));
                        assertNull(map.get("nullKey"));
                    });
        }

        @TestFactory
        @DisplayName("put() should handle both null key and null value")
        Stream<DynamicTest> putShouldHandleBothNullKeyAndNullValue() {
            return createTestsForEachMap("put() should handle both null key and null value",
                    (Map<String, String> map) -> {
                        String result = map.put(null, null);
                        assertNull(result);
                        assertEquals(1, map.size());
                        assertTrue(map.containsKey(null));
                        assertNull(map.get(null));
                    });
        }

        @TestFactory
        @DisplayName("put() should work with different key types")
        Stream<DynamicTest> putShouldWorkWithDifferentKeyTypes() {
            return createTestsForEachMap("put() should work with different key types",
                    (Map<Integer, String> map) -> {
                        map.put(1, "one");
                        map.put(2, "two");
                        assertEquals(2, map.size());
                        assertEquals("one", map.get(1));
                        assertEquals("two", map.get(2));
                    });
        }

    }

    @Nested
    @DisplayName("Get operations")
    class GetTests {
        @TestFactory
        @DisplayName("get() should return value for existing key")
        Stream<DynamicTest> getShouldReturnValueForExistingKey() {
            return createTestsForEachMap("get() should return value for existing key",
                    (Map<String, String> map) -> {
                        map.put("key1", "value1");
                        assertEquals("value1", map.get("key1"));
                    });
        }

        @TestFactory
        @DisplayName("get() should return null for non-existing key")
        Stream<DynamicTest> getShouldReturnNullForNonExistingKey() {
            return createTestsForEachMap("get() should return null for non-existing key",
                    (Map<String, String> map) -> {
                        assertNull(map.get("nonExistingKey"));
                    });
        }

        @TestFactory
        @DisplayName("get() should handle null key")
        Stream<DynamicTest> getShouldHandleNullKey() {
            return createTestsForEachMap("get() should handle null key",
                    (Map<String, String> map) -> {
                        map.put(null, "nullValue");
                        assertEquals("nullValue", map.get(null));
                    });
        }

        @TestFactory
        @DisplayName("get() should return null for null key when not present")
        Stream<DynamicTest> getShouldReturnNullForNullKeyWhenNotPresent() {
            return createTestsForEachMap("get() should return null for null key when not present",
                    (Map<String, String> map) -> {
                        assertNull(map.get(null));
                    });
        }

        @TestFactory
        @DisplayName("get() should return null for existing key with null value")
        Stream<DynamicTest> getShouldReturnNullForExistingKeyWithNullValue() {
            return createTestsForEachMap("get() should return null for existing key with null value",
                    (Map<String, String> map) -> {
                        map.put("nullValueKey", null);
                        assertNull(map.get("nullValueKey"));
                    });
        }
    }

    @Nested
    @DisplayName("Remove operations")
    class RemoveTests {
        @TestFactory
        @DisplayName("remove() should remove existing key and return its value")
        Stream<DynamicTest> removeShouldRemoveExistingKeyAndReturnValue() {
            return createTestsForEachMap("remove() should remove existing key and return its value",
                    (Map<String, String> map) -> {
                        map.put("key1", "value1");
                        String result = map.remove("key1");
                        assertEquals("value1", result);
                        assertEquals(0, map.size());
                        assertFalse(map.containsKey("key1"));
                    });
        }

        @TestFactory
        @DisplayName("remove() should return null for non-existing key")
        Stream<DynamicTest> removeShouldReturnNullForNonExistingKey() {
            return createTestsForEachMap("remove() should return null for non-existing key",
                    (Map<String, String> map) -> {
                        assertNull(map.remove("nonExistingKey"));
                        assertEquals(0, map.size());
                    });
        }

        @TestFactory
        @DisplayName("remove() should handle null key")
        Stream<DynamicTest> removeShouldHandleNullKey() {
            return createTestsForEachMap("remove() should handle null key",
                    (Map<String, String> map) -> {
                        map.put(null, "nullValue");
                        String result = map.remove(null);
                        assertEquals("nullValue", result);
                        assertEquals(0, map.size());
                        assertFalse(map.containsKey(null));
                    });
        }

        @TestFactory
        @DisplayName("remove() should handle null key when not present")
        Stream<DynamicTest> removeShouldHandleNullKeyWhenNotPresent() {
            return createTestsForEachMap("remove() should handle null key when not present",
                    (Map<String, String> map) -> {
                        assertNull(map.remove(null));
                        assertEquals(0, map.size());
                    });
        }

        @TestFactory
        @DisplayName("remove() should handle existing key with null value")
        Stream<DynamicTest> removeShouldHandleExistingKeyWithNullValue() {
            return createTestsForEachMap("remove() should handle existing key with null value",
                    (Map<String, String> map) -> {
                        map.put("nullValueKey", null);
                        String result = map.remove("nullValueKey");
                        assertNull(result);
                        assertEquals(0, map.size());
                        assertFalse(map.containsKey("nullValueKey"));
                    });
        }

    }

    @Nested
    @DisplayName("Size operations")
    class SizeTests {
        @TestFactory
        @DisplayName("size() should return 0 for empty map")
        Stream<DynamicTest> sizeShouldReturnZeroForEmptyMap() {
            return createTestsForEachMap("size() should return 0 for empty map",
                    (Map<String, String> map) -> {
                        assertEquals(0, map.size());
                    });
        }

        @TestFactory
        @DisplayName("size() should return correct count after adding elements")
        Stream<DynamicTest> sizeShouldReturnCorrectCountAfterAddingElements() {
            return createTestsForEachMap("size() should return correct count after adding elements",
                    (Map<String, String> map) -> {
                        assertEquals(0, map.size());
                        map.put("key1", "value1");
                        assertEquals(1, map.size());
                        map.put("key2", "value2");
                        assertEquals(2, map.size());
                        map.put("key3", "value3");
                        assertEquals(3, map.size());
                    });
        }

        @TestFactory
        @DisplayName("size() should decrease after removing elements")
        Stream<DynamicTest> sizeShouldDecreaseAfterRemovingElements() {
            return createTestsForEachMap("size() should decrease after removing elements",
                    (Map<String, String> map) -> {
                        map.put("key1", "value1");
                        map.put("key2", "value2");
                        map.put("key3", "value3");
                        assertEquals(3, map.size());
                        map.remove("key2");
                        assertEquals(2, map.size());
                        map.remove("key1");
                        assertEquals(1, map.size());
                    });
        }

        @TestFactory
        @DisplayName("size() should remain same when replacing existing key")
        Stream<DynamicTest> sizeShouldRemainSameWhenReplacingExistingKey() {
            return createTestsForEachMap("size() should remain same when replacing existing key",
                    (Map<String, String> map) -> {
                        map.put("key1", "value1");
                        assertEquals(1, map.size());
                        map.put("key1", "value2");
                        assertEquals(1, map.size());
                    });
        }

        @TestFactory
        @DisplayName("size() should return 0 after clear()")
        Stream<DynamicTest> sizeShouldReturnZeroAfterClear() {
            return createTestsForEachMap("size() should return 0 after clear()",
                    (Map<String, String> map) -> {
                        map.put("key1", "value1");
                        map.put("key2", "value2");
                        assertEquals(2, map.size());
                        map.clear();
                        assertEquals(0, map.size());
                    });
        }
    }

    @Nested
    @DisplayName("ContainsKey operations")
    class ContainsKeyTests {
        @TestFactory
        @DisplayName("containsKey() should return true for existing key")
        Stream<DynamicTest> containsKeyShouldReturnTrueForExistingKey() {
            return createTestsForEachMap("containsKey() should return true for existing key",
                    (Map<String, String> map) -> {
                        map.put("key1", "value1");
                        assertTrue(map.containsKey("key1"));
                    });
        }

        @TestFactory
        @DisplayName("containsKey() should return false for non-existing key")
        Stream<DynamicTest> containsKeyShouldReturnFalseForNonExistingKey() {
            return createTestsForEachMap("containsKey() should return false for non-existing key",
                    (Map<String, String> map) -> {
                        assertFalse(map.containsKey("nonExistingKey"));
                    });
        }

        @TestFactory
        @DisplayName("containsKey() should handle null key when present")
        Stream<DynamicTest> containsKeyShouldHandleNullKeyWhenPresent() {
            return createTestsForEachMap("containsKey() should handle null key when present",
                    (Map<String, String> map) -> {
                        map.put(null, "nullValue");
                        assertTrue(map.containsKey(null));
                    });
        }

        @TestFactory
        @DisplayName("containsKey() should handle null key when not present")
        Stream<DynamicTest> containsKeyShouldHandleNullKeyWhenNotPresent() {
            return createTestsForEachMap("containsKey() should handle null key when not present",
                    (Map<String, String> map) -> {
                        assertFalse(map.containsKey(null));
                    });
        }

        @TestFactory
        @DisplayName("containsKey() should return true for key with null value")
        Stream<DynamicTest> containsKeyShouldReturnTrueForKeyWithNullValue() {
            return createTestsForEachMap("containsKey() should return true for key with null value",
                    (Map<String, String> map) -> {
                        map.put("nullValueKey", null);
                        assertTrue(map.containsKey("nullValueKey"));
                    });
        }
    }

    @Nested
    @DisplayName("EntrySet operations")
    class EntrySetTests {
        @TestFactory
        @DisplayName("entrySet() should return empty set for empty map")
        Stream<DynamicTest> entrySetShouldReturnEmptySetForEmptyMap() {
            return createTestsForEachMap("entrySet() should return empty set for empty map",
                    (Map<String, String> map) -> {
                        Set<Map.Entry<String, String>> entrySet = map.entrySet();
                        assertNotNull(entrySet);
                        assertEquals(0, entrySet.size());
                        assertTrue(entrySet.isEmpty());
                    });
        }

        @TestFactory
        @DisplayName("entrySet() should contain all entries")
        Stream<DynamicTest> entrySetShouldContainAllEntries() {
            return createTestsForEachMap("entrySet() should contain all entries",
                    (Map<String, String> map) -> {
                        map.put("key1", "value1");
                        map.put("key2", "value2");
                        map.put("key3", "value3");

                        Set<Map.Entry<String, String>> entrySet = map.entrySet();
                        assertEquals(3, entrySet.size());

                        Set<Map.Entry<String, String>> expectedEntries = new HashSet<>();
                        expectedEntries.add(new AbstractMap.SimpleEntry<>("key1", "value1"));
                        expectedEntries.add(new AbstractMap.SimpleEntry<>("key2", "value2"));
                        expectedEntries.add(new AbstractMap.SimpleEntry<>("key3", "value3"));

                        assertEquals(expectedEntries, entrySet);
                    });
        }

        @TestFactory
        @DisplayName("entrySet() should handle null keys and values")
        Stream<DynamicTest> entrySetShouldHandleNullKeysAndValues() {
            return createTestsForEachMap("entrySet() should handle null keys and values",
                    (Map<String, String> map) -> {
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
                    });
        }

    }

    @Nested
    @DisplayName("KeySet operations")
    class KeySetTests {
        @TestFactory
        @DisplayName("keySet() should return empty set for empty map")
        Stream<DynamicTest> keySetShouldReturnEmptySetForEmptyMap() {
            return createTestsForEachMap("keySet() should return empty set for empty map",
                    (Map<String, String> map) -> {
                        Set<String> keySet = map.keySet();
                        assertNotNull(keySet);
                        assertEquals(0, keySet.size());
                        assertTrue(keySet.isEmpty());
                    });
        }

        @TestFactory
        @DisplayName("keySet() should contain all keys")
        Stream<DynamicTest> keySetShouldContainAllKeys() {
            return createTestsForEachMap("keySet() should contain all keys",
                    (Map<String, String> map) -> {
                        map.put("key1", "value1");
                        map.put("key2", "value2");
                        map.put("key3", "value3");

                        Set<String> keySet = map.keySet();
                        assertEquals(3, keySet.size());
                        assertTrue(keySet.contains("key1"));
                        assertTrue(keySet.contains("key2"));
                        assertTrue(keySet.contains("key3"));
                    });
        }

        @TestFactory
        @DisplayName("keySet() should handle null key")
        Stream<DynamicTest> keySetShouldHandleNullKey() {
            return createTestsForEachMap("keySet() should handle null key",
                    (Map<String, String> map) -> {
                        map.put(null, "nullValue");
                        map.put("normal", "normal");

                        Set<String> keySet = map.keySet();
                        assertEquals(2, keySet.size());
                        assertTrue(keySet.contains(null));
                        assertTrue(keySet.contains("normal"));
                    });
        }

    }

    @Nested
    @DisplayName("Values operations")
    class ValuesTests {
        @TestFactory
        @DisplayName("values() should return empty collection for empty map")
        Stream<DynamicTest> valuesShouldReturnEmptyCollectionForEmptyMap() {
            return createTestsForEachMap("values() should return empty collection for empty map",
                    (Map<String, String> map) -> {
                        Collection<String> values = map.values();
                        assertNotNull(values);
                        assertEquals(0, values.size());
                        assertTrue(values.isEmpty());
                    });
        }

        @TestFactory
        @DisplayName("values() should contain all values")
        Stream<DynamicTest> valuesShouldContainAllValues() {
            return createTestsForEachMap("values() should contain all values",
                    (Map<String, String> map) -> {
                        map.put("key1", "value1");
                        map.put("key2", "value2");
                        map.put("key3", "value3");

                        Collection<String> values = map.values();
                        assertEquals(3, values.size());
                        assertTrue(values.contains("value1"));
                        assertTrue(values.contains("value2"));
                        assertTrue(values.contains("value3"));
                    });
        }

        @TestFactory
        @DisplayName("values() should handle null value")
        Stream<DynamicTest> valuesShouldHandleNullValue() {
            return createTestsForEachMap("values() should handle null value",
                    (Map<String, String> map) -> {
                        map.put("nullKey", null);
                        map.put("normal", "normal");

                        Collection<String> values = map.values();
                        assertEquals(2, values.size());
                        assertTrue(values.contains(null));
                        assertTrue(values.contains("normal"));
                    });
        }

        @TestFactory
        @DisplayName("containsValue() should return true for existing value")
        Stream<DynamicTest> containsValueShouldReturnTrueForExistingValue() {
            return createTestsForEachMap("containsValue() should return true for existing value",
                    (Map<String, String> map) -> {
                        map.put("key1", "value1");
                        map.put("key2", "value2");
                        map.put("key3", null);

                        assertTrue(map.containsValue("value1"));
                        assertTrue(map.containsValue("value2"));
                        assertTrue(map.containsValue(null));
                        assertFalse(map.containsValue("nonExistingValue"));
                    });
        }
    }

    @Nested
    @DisplayName("PutAll operations")
    class PutAllTests {
        @TestFactory
        @DisplayName("putAll() should add all entries from another map")
        Stream<DynamicTest> putAllShouldAddAllEntriesFromAnotherMap() {
            return createTestsForEachMap("putAll() should add all entries from another map",
                    (Map<String, String> map) -> {
                        Map<String, String> sourceMap = new HashMap<>();
                        sourceMap.put("key1", "value1");
                        sourceMap.put("key2", "value2");
                        sourceMap.put("key3", "value3");

                        map.putAll(sourceMap);

                        assertEquals(3, map.size());
                        assertEquals("value1", map.get("key1"));
                        assertEquals("value2", map.get("key2"));
                        assertEquals("value3", map.get("key3"));
                    });
        }
    }

    @TestFactory
    @DisplayName("clear() should remove all entries")
    Stream<DynamicTest> clearShouldRemoveAllEntries() {
        return createTestsForEachMap("clear() should remove all entries",
                (Map<String, String> map) -> {
                    map.put("key1", "value1");
                    map.put("key2", "value2");
                    map.put("key3", "value3");

                    map.clear();

                    assertEquals(0, map.size());
                    assertTrue(map.isEmpty());
                    assertFalse(map.containsKey("key1"));
                    assertNull(map.get("key1"));
                });
    }

}
