# DependencyLocator

Library for dependency inversion in Java/Android using service locator pattern very easy to use and lightweight. It was designed to be very quick to learn to use and ease to work with dependency inversion for those who have been using this type of design pattern in other types of frameworks and languages. I have avoided using the word Service since this concept is linked to other types of functionalities in Android.

To use it you just have to do the following:

 * Implement `Dependency` or  `SingletonDependency` interface.
 * Implement `DependencyProvider` that know how to create previous dependency.
 * Supply these provider to `DependencyLocator` in some point before use the dependencies (for instance at the entry point of your application).
   ```DependencyLocator.provide(MyDependency.class, new MyDependencyProvider());```
 * Locate this dependency where and when you want to use:
   ```MyDependency dependency = (MyDependency)DependencyLocator.locate(MyDependency.class);```
 * Optional: Release this dependency when you do not need use it anymore:
   ```DependencyLocator.release(dependency);```



