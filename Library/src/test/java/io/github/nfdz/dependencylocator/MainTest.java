package io.github.nfdz.dependencylocator;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 * This set of tests checks that basic functionality and behaviour is correct.
 */
public class MainTest {

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

    /**
     * Check that DependencyLocator provides a valid dependency and destroys it after release.
     */
    @Test
    public void testProvideDependency() {

        MyDependency.MyDependencyProvider provider = new MyDependency.MyDependencyProvider();
        DependencyLocator.provide(MyDependency.class, provider);
        MyDependency dependency = (MyDependency)DependencyLocator.locate(MyDependency.class);

        assertNotNull(dependency);
        assertTrue(provider.created);
        assertFalse(provider.destroyed);

        DependencyLocator.release(dependency);
        assertTrue(provider.destroyed);

    }

    /**
     * Check that DependencyLocator provides same singleton dependency and destroys it when has been released by all.
     */
    @Test
    public void testProvideSingletonDependency() {

        MySingletonDependency.MySingletonDependencyProvider provider = new MySingletonDependency.MySingletonDependencyProvider();
        DependencyLocator.provide(MySingletonDependency.class, provider);
        MySingletonDependency dependency1 = (MySingletonDependency)DependencyLocator.locate(MySingletonDependency.class);
        MySingletonDependency dependency2 = (MySingletonDependency)DependencyLocator.locate(MySingletonDependency.class);
        MySingletonDependency dependency3 = (MySingletonDependency)DependencyLocator.locate(MySingletonDependency.class);

        assertNotNull(dependency1);
        assertNotNull(dependency2);
        assertNotNull(dependency3);

        assertEquals(provider.createCounter, 1);

        DependencyLocator.release(dependency1);

        assertEquals(provider.destroyCounter, 0);

        DependencyLocator.release(dependency2);
        DependencyLocator.release(dependency3);

        assertEquals(provider.destroyCounter, 1);

    }

    /**
     * Check that DependencyLocator overrides successfully a DependencyProvider when it has not been used.
     */
    @Test
    public void testOverrideProvider() {

        MyDependency.MyDependencyProvider provider1 = new MyDependency.MyDependencyProvider("1");
        MyDependency.MyDependencyProvider provider2 = new MyDependency.MyDependencyProvider("2");

        DependencyLocator.provide(MyDependency.class, provider1);
        DependencyLocator.provide(MyDependency.class, provider2);

        MyDependency dependency = (MyDependency)DependencyLocator.locate(MyDependency.class);
        assertEquals(dependency.tag, "2");

    }

    /**
     * Check that DependencyLocator launches an exception when it tries to override a DependencyProvider
     * that has already used.
     */
    @Test(expected = DependencyLocatorException.class)
    public void testOverrideUsedProvider() {

        MyDependency.MyDependencyProvider provider1 = new MyDependency.MyDependencyProvider();
        MyDependency.MyDependencyProvider provider2 = new MyDependency.MyDependencyProvider();

        DependencyLocator.provide(MyDependency.class, provider1);
        DependencyLocator.locate(MyDependency.class);
        DependencyLocator.provide(MyDependency.class, provider2);

    }

}