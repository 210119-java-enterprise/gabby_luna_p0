package com.revature.utilities;

import java.util.Arrays;

public class Map<K, V> {

    private int size;
    private final int CAPACITY = 10;

    @SuppressWarnings("unchecked")
    private Entry<K, V>[] entries = new Entry[CAPACITY];

    public boolean containsKey(K key) {
        for (Entry i : entries) {
             if (i.key.equals(key))
                return true;
        }
        return false;
    }

    public LinkedList<K> keyList() {
        LinkedList<K> keyList = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            keyList.insert(entries[i].key);
        }
        return keyList;
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        for (Entry i : entries) {
             if (i.key.equals(key))
                return (V) i.value;
        }
        return null;
    }

    public V getOrDefault(K key, V defaultValue) {
        if (!containsKey(key)) {
            return defaultValue;
        }
        return get(key);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Adds a new entry to the map using the provided key and value. Returns the
     * value previously associated with the key. If the key was not in the map prior,
     * returns null.
     *
     * @param key
     * @param value
     * @return the previous value associated with the key, could return null
     */
    public V put(K key, V value) {
        V previousValue = null;

        boolean wasInserted = true;
        for (int i = 0; i < size; i++) {
            if (entries[i].key.equals(key)) {
                previousValue = entries[i].value;
                entries[i].value = value;
                wasInserted = false;
                break;
            }
        }
        if (wasInserted) {
            ensureCapacity();
            entries[size++] = new Entry<>(key, value);
        }

        return previousValue;

    }

    public void remove(K key) {
        //Todo: rewrite
        boolean wasRemoved = false;
        for (int i = 0; i < size; i++) {
            if (entries[i].key == null) {
                if (entries[i].key == key) {
                    entries[i] = entries[size - 1];
                    size--;
                    wasRemoved = true;
                }
            } else if (entries[i].key.equals(key)) {
                entries[i] = entries[size - 1];
                size--;
                wasRemoved = true;
            }
        }
        if (wasRemoved) {
            condenseArray();
        }
    }

    public int getSize() {
        return size;
    }

    // this method will be helpful after putting new entries into the map
    private void ensureCapacity() {
        if (size == entries.length) {
            entries = Arrays.copyOf(entries, entries.length * 2);
        }
    }

    // this method will be helpful after removing a key from the map
    private void condenseArray() {
        if (size * 2 < entries.length) {
            entries = Arrays.copyOf(entries, entries.length / 2);
        }
    }

    private static class Entry<K, V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

}