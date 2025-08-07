package org.example.week04.hashmapwithchainingstrategy;

public record CollisionKey(String value) {
    @Override
    public int hashCode() {
        return 42;
    }
}
