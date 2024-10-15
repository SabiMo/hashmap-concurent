package fr.codebusters;

public class Node<K, V> {
    final K key;
    V value;
    Node<K, V> next;

    Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
