package io.github.nfdz.dependencylocator;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void testProvideDependency() {

        MyDependency.MyDependencyProvider provider = new MyDependency.MyDependencyProvider();
        DependencyLocator.provide(MyDependency.class, provider);
        MyDependency dependency = (MyDependency)DependencyLocator.locate(MyDependency.class);

        assertNotNull(dependency);
        assertTrue(provider.created);
        assertTrue(provider.created);
        assertFalse(provider.destroyed);

        DependencyLocator.release(dependency);
        assertTrue(provider.destroyed);

    }

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
        assertEquals(provider.initializeCounter, 1);

        DependencyLocator.release(dependency1);

        assertEquals(provider.destroyCounter, 0);

        DependencyLocator.release(dependency2);
        DependencyLocator.release(dependency3);

        assertEquals(provider.destroyCounter, 1);
    }

    @Test
    public void testOverrideProvider() {

        MyDependency.MyDependencyProvider provider1 = new MyDependency.MyDependencyProvider();
        MyDependency.MyDependencyProvider provider2 = new MyDependency.MyDependencyProvider();

        DependencyLocator.provide(MyDependency.class, provider1);
        DependencyLocator.provide(MyDependency.class, provider2);

    }

    @Test(expected = DependencyLocatorException.class)
    public void testOverrideUsedProvider() {

        MyDependency.MyDependencyProvider provider1 = new MyDependency.MyDependencyProvider();
        MyDependency.MyDependencyProvider provider2 = new MyDependency.MyDependencyProvider();

        DependencyLocator.provide(MyDependency.class, provider1);
        DependencyLocator.locate(MyDependency.class);
        DependencyLocator.provide(MyDependency.class, provider2);

    }

}