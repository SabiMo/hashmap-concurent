package fr.codebusters;

/**
 * Point d'entrée de l'application.
 */
public class App {
    /**
     * Méthode qui lance l'application.
     */
    public static void main(String[] args) {
        HashMapConcurent<Integer, Integer> hashMapConcurent = new HashMapConcurent<>();
        hashMapConcurent.put(1, 1);
        hashMapConcurent.put(1, 2);
        hashMapConcurent.put(1, 2);
        hashMapConcurent.put(2, 2);
        hashMapConcurent.put(3, 3);
        hashMapConcurent.put(4, 4);
        hashMapConcurent.put(5, 5);

        hashMapConcurent.printMap();
        System.out.println("");

        hashMapConcurent.remove(1);
        hashMapConcurent.printMap();
        System.out.println("");

        System.out.println(hashMapConcurent.get(1));
        System.out.println(hashMapConcurent.get(2));

    }
}
