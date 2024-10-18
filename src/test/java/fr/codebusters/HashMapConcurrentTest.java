package fr.codebusters;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.*;
import org.junit.jupiter.api.Test;

class HashMapConcurrentTest {

    static final int SIZE = 32 * 1024;

    private Long[] initRandom(MeasureTime measureTime) {
        measureTime.measureStart();
        Random r = new Random();
        Long[] randoms = new Long[SIZE * 2];
        for (int i = 0; i < SIZE * 2; i++)
            randoms[i] = r.nextLong(SIZE);
        measureTime.measureEnd();
        measureTime.measurePrint();
        return randoms;
    }

    private void submitMethodCached(Consumer<Integer> op, MeasureTime measureTime)
            throws InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        measureTime.measureStart();
        for (int i = 0; i < SIZE; i++) {
            int finalI = i * 2;
            threadPool.submit(() -> op.accept(finalI));
        }
        threadPool.shutdown();
        while (!threadPool.awaitTermination(1, TimeUnit.SECONDS))
            continue;
        measureTime.measureEnd();
        measureTime.measurePrint();
    }

    private void submitMethodSimultaneous(Consumer<Integer> op, MeasureTime measureTime)
            throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch wait = new CountDownLatch(SIZE);
        for (int i = 0; i < SIZE; i++) {
            int finalI = i * 2;
            new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                op.accept(finalI);
                wait.countDown();
            }).start();
        }
        measureTime.measureStart();
        latch.countDown();
        wait.await();
        measureTime.measureEnd();
        measureTime.measurePrint();
    }

    @Test
    void test_add_cached() throws InterruptedException {
        MeasureTime measureTime = new MeasureTime();
        Long[] randoms = initRandom(measureTime);
        HashMapConcurrent<Long, Long> hm = new HashMapConcurrent<>();
        submitMethodCached(i -> hm.put(randoms[i], randoms[i + 1]), measureTime);
        System.out.println(Arrays.toString(randoms));
        hm.printMap();
    }

    @Test
    void test_add_simultaneous() throws InterruptedException {
        MeasureTime measureTime = new MeasureTime();
        Long[] randoms = initRandom(measureTime);
        HashMapConcurrent<Long, Long> hm = new HashMapConcurrent<>();
        submitMethodSimultaneous(i -> hm.put(randoms[i], randoms[i + 1]), measureTime);
        System.out.println(Arrays.toString(randoms));
        hm.printMap();
    }
}
