# DependencyLocator
[![](https://jitpack.io/v/nfdz/DependencyLocator.svg)](https://jitpack.io/#nfdz/DependencyLocator)

Library for dependency inversion in Java/Android using service locator pattern very easy to use and lightweight. It was designed to be very quick to learn. With this library you have no excuse to not implement dependency inversion in your project from the first moment. It is very easy to use for those who have been using this design pattern in other frameworks and languages. I have avoided using the word 'Service' since this concept is linked to other types of functionalities in Android.

To use it you just have to do the following:

 * Implement `Dependency` or  `SingletonDependency` interface.
 * Implement `DependencyProvider` that know how to create previous dependency.
 * Supply these provider to `DependencyLocator` in some point before use the dependencies (for instance at the entry point of your application).
```java
DependencyLocator.provide(MyDependency.class, new MyDependencyProvider());
```
 * Locate this dependency where and when you want to use:
```java
MyDependency dependency = (MyDependency)DependencyLocator.locate(MyDependency.class);
```
 * Optional: Release this dependency when you do not need use it anymore:
```java
DependencyLocator.release(dependency);
```

## Download

### Jitpack

It is very use integrate this library in your project as a dependency of your build system thanks to Jitpack. If you use gradle, you just have to add the following in your 'build.gradle' file:

   ```gradle
   allprojects {
	 repositories {
	 ...
         maven { url 'https://jitpack.io' }
      }
   }
   ...
   dependencies {
      compile 'com.github.nfdz:DependencyLocator:v1.0.0'
   }
   ```

Jitpack works with several build systems, please checkout the [documentation][https://jitpack.io/docs/BUILDING/] if you need help with yours.

### Manual JAR

You can download library JAR file from [releases][https://github.com/nfdz/DependencyLocator/releases] webpage of the github project.

## License

    Copyright 2017 nfdz

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
