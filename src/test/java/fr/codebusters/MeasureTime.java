package fr.codebusters;

public class MeasureTime {
    private long start;
    private long end;

    MeasureTime () {

    }


    public void measureStart() {
        start = System.nanoTime();
    }

    public void measureEnd() {
        end = System.nanoTime();
    }

    public void measurePrint() {
        long diff = end - start;
        System.out.println("Elapsed time: " + (diff / 1000000000 % 1000) + "s " + (diff / 1000000 % 1000) + "ms " + (diff / 1000 % 1000) + "µs " + (diff % 1000) + "ns");
    }

}
