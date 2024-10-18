package fr.codebusters;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 * La classe {@code HashMapConcurent} représente une HashMap implémenté de
 * façon concurente
 */
public class HashMapConcurrent<K, V> {
    private final Node<K, V>[] buckets;
    private final ReentrantLock[] locks;
    private final int CAPACITY;

    /**
     * Constructeur de la classe {@code HashMapConcurent}.
     */
    @SuppressWarnings("unchecked")
    public HashMapConcurrent() {
        this(2);
    }

    public HashMapConcurrent(int capacity) {
        this.CAPACITY = capacity;
        this.buckets = new Node[capacity];
        locks = new ReentrantLock[capacity];
        Arrays.setAll(locks, i -> new ReentrantLock());
    }

    /**
     * @param key la clé.
     * @return l'indice du bucket dans lequel se trouve (potentielement) l'élément.
     */
    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode()) % CAPACITY;
    }

    /**
     * @param key la clé.
     * @return l'élément si ce dernier existe dans la map, sinon null.
     */
    public synchronized V get(K key) {
        int index = getBucketIndex(key);

        locks[index].lock();
        try {
            Node<K, V> node = buckets[index];

            while (node != null) {
                if (node.key.equals(key)) {
                    return node.value;
                }
                node = node.next;
            }
            return null;
        } finally {
            locks[index].unlock();
        }
    }

    /**
     * Place le couple (key, value) dans la map
     * 
     * @param key   la clé.
     * @param value la valeur.
     */
    public synchronized void put(K key, V value) {
        int index = getBucketIndex(key);

        locks[index].lock();
        try {
            if (buckets[index] == null) {
                buckets[index] = new Node<>(key, value);
            }

            Node<K, V> node = buckets[index];
            while (node != null) {
                if (node.key.equals(key)) {
                    node.value = value;
                    return;
                }

                if (node.next == null) {
                    node.next = new Node<>(key, value);
                }

                node = node.next;
            }
        } finally {
            locks[index].unlock();
        }
    }

    /**
     * Supprime le couple (key, value) dans la map s'il existe
     * 
     * @param key la clé.
     */
    public synchronized void remove(K key) {
        int index = getBucketIndex(key);

        locks[index].lock();
        try {
            Node<K, V> prev = null;
            Node<K, V> node = buckets[index];

            while (node != null) {
                if (node.key.equals(key)) {
                    if (prev == null) {
                        buckets[index] = node.next;
                    } else {
                        prev.next = node.next;
                    }
                    return;
                }
                prev = node;
                node = node.next;
            }
        } finally {
            locks[index].unlock();
        }
    }

    /**
     * Affiche la map
     */
    public synchronized void printMap() {
        for (int i = 0; i < CAPACITY; i++) {
            System.out.print("Bucket " + (i + 1) + "/" + CAPACITY + ": ");
            Node<K, V> node = buckets[i];
            if (node == null) {
                System.out.println(node);
                continue;
            } else {
                System.out.print(node);
            }
            for (node = node.next; node != null; node = node.next) {
                System.out.print(", " + node);
            }
            System.out.println();
        }
    }
}
