package fr.codebusters;

/**
 * Point d'entrée de l'application.
 */
public class App {
    /**
     * Méthode qui lance l'application.
     */
    public static void main(String[] args) {
        HashMapConcurrent<Integer, Integer> hashMapConcurrent = new HashMapConcurrent<>(10);
        hashMapConcurrent.put(1, 1);
        hashMapConcurrent.put(1, 2);
        hashMapConcurrent.put(1, 2);
        hashMapConcurrent.put(2, 2);
        hashMapConcurrent.put(3, 3);
        hashMapConcurrent.put(4, 4);
        hashMapConcurrent.put(5, 5);
        hashMapConcurrent.put(6, 6);
        hashMapConcurrent.put(7, 7);
        hashMapConcurrent.put(8, 8);
        hashMapConcurrent.put(9, 9);
        hashMapConcurrent.put(10, 10);
        hashMapConcurrent.put(11, 12);
        hashMapConcurrent.put(12, 12);

        hashMapConcurrent.printMap();
        System.out.println();

        hashMapConcurrent.remove(1);
        hashMapConcurrent.printMap();
        System.out.println();
    }
}
