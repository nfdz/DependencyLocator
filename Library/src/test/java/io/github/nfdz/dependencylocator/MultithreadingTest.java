package io.github.nfdz.dependencylocator;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This set of tests checks that multi-threading behaviour is correct.
 */
public class MultithreadingTest {


    public static final int NUM_THREADS = 50;
    public static final int NUM_RUNNABLES = 200;

    /**
     * Reset instance of DependencyLocator each time before run a test in order to avoid problems running several
     * test sharing same instance.
     */
    @Before
    public void resetDependencyLocator() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = DependencyLocator.class.getDeclaredField("INSTANCE");
        instance.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(instance, instance.getModifiers() & ~Modifier.FINAL);
        instance.set(null, new DependencyLocatorImpl());
    }

    @Test
    public void testProvideDependency() throws InterruptedException {
        MyDependency.MyDependencyProvider provider = new MyDependency.MyDependencyProvider();
        DependencyLocator.provide(MyDependency.class, provider);

        ExecutorService threadPool = Executors.newFixedThreadPool(NUM_THREADS);
        try {
            for (int i = 0; i < NUM_RUNNABLES; i++) {
                int finalI = i;
                threadPool.submit(new Runnable() {
                    public void run() {
                        System.out.println("Runnable > Start > " + finalI);
                        MyDependency dependency = (MyDependency)DependencyLocator.locate(MyDependency.class);
                        try {
                            Thread.sleep(new Random().nextInt(1000));
                        } catch (InterruptedException e) {
                            // swallow
                        }
                        DependencyLocator.release(dependency);
                        System.out.println("Runnable > Finish > " + finalI);
                    }
                });
            }
            boolean timeout = threadPool.awaitTermination(10, TimeUnit.SECONDS);
            assertFalse(timeout);
            assertEquals(NUM_RUNNABLES, provider.createCounter);
            assertEquals(NUM_RUNNABLES, provider.destroyCounter);
        } finally {
            threadPool.shutdownNow();
        }
    }

    @Test
    public void testProvideSingletonDependency() throws InterruptedException {
        MySingletonDependency.MySingletonDependencyProvider provider = new MySingletonDependency.MySingletonDependencyProvider();
        DependencyLocator.provide(MySingletonDependency.class, provider);

        ExecutorService threadPool = Executors.newFixedThreadPool(NUM_THREADS);
        try {
            for (int i = 0; i < NUM_RUNNABLES; i++) {
                int finalI = i;
                threadPool.submit(new Runnable() {
                    public void run() {
                        System.out.println("Runnable > Start > " + finalI);
                        MySingletonDependency dependency = (MySingletonDependency)DependencyLocator.locate(MySingletonDependency.class);
                        try {
                            Thread.sleep(new Random().nextInt(1000));
                        } catch (InterruptedException e) {
                            // swallow
                        }
                        DependencyLocator.release(dependency);
                        System.out.println("Runnable > Finish > " + finalI);
                    }
                });
            }
            boolean timeout = threadPool.awaitTermination(10, TimeUnit.SECONDS);
            assertFalse(timeout);
            assertEquals(1, provider.createCounter);
            assertEquals(1, provider.destroyCounter);
        } finally {
            threadPool.shutdownNow();
        }
    }
}
