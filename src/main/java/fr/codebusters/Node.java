package fr.codebusters;

public class Node<K, V> {
    final K key;
    volatile V value;
    Node<K, V> next;

    Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + " -> " + value;
    }
}
