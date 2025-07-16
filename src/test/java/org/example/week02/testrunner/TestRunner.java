package org.example.week02.testrunner;

import org.example.week02.testrunner.annotation.AfterEach;
import org.example.week02.testrunner.annotation.BeforeEach;
import org.example.week02.testrunner.annotation.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TestRunner {
    private int totalTests = 0;
    private int passedTests = 0;
    private int failedTests = 0;
    private long totalExecutionTime = 0;
    private final List<String> testResults = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: TestRunner <packageName>");
            System.exit(1);
        }

        try {
            TestRunner runner = new TestRunner();
            runner.runTests(args[0]);
        } catch (Exception e) {
            System.err.println("Test execution failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void runTests(String packageName) throws Exception {
        List<Class<?>> allClasses = ClassFinder.findClasses(packageName);
        List<Class<?>> testClasses = new ArrayList<>();

        for (Class<?> testClass : allClasses) {
            List<Method> testMethods = getAllTestMethods(testClass);
            if (!testMethods.isEmpty()) {
                runTestClass(testClass, testMethods);
            }
        }

        System.out.println("\n=== Custom Test Runner Results ===");
        System.out.println("Package: " + packageName);
        System.out.println("Classes scanned: " + allClasses.size());
        System.out.println("Test classes found: " + testClasses.size());

        printStatistics();
    }

    private void runTestClass(Class<?> testClass, List<Method> testMethods) throws Exception {
        Object testInstance = testClass.getDeclaredConstructor().newInstance();
        List<Method> beforeMethods = getMethodsAnnotatedWith(testClass, BeforeEach.class);
        List<Method> afterMethods = getMethodsAnnotatedWith(testClass, AfterEach.class);

        int classTests = testMethods.size();

        for (Method method : testMethods) {

            invokeMethods(beforeMethods, testInstance);
            runTestMethod(testInstance, method);
            invokeMethods(afterMethods, testInstance);
        }

        System.out.println("Tests discovered in " + testClass.getSimpleName() + ": " + classTests);
    }

    private void runTestMethod(Object instance, Method testMethod) {
        String testName = instance.getClass().getSimpleName() + "." + testMethod.getName();
        Test testAnnotation = testMethod.getAnnotation(Test.class);
        String description = testAnnotation.description();
        long timeout = testAnnotation.timeout();
        long startTime = System.currentTimeMillis();

        try {
            List<Method> beforeMethods = getMethodsAnnotatedWith(instance.getClass(), BeforeEach.class);
            List<Method> afterMethods = getMethodsAnnotatedWith(instance.getClass(), AfterEach.class);

            invokeMethods(beforeMethods, instance);

            if (timeout > 0) {
                runTestMethodWithTimeout(instance, testMethod, timeout, testName, description, startTime);
            } else {
                testMethod.setAccessible(true);
                testMethod.invoke(instance);
                long duration = System.currentTimeMillis() - startTime;
                addResultLine(true, testName, duration, description);
                passedTests++;
            }

            invokeMethods(afterMethods, instance);

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            Throwable cause = e.getCause();

            if (cause instanceof AssertionError) {
                addResultLine(false, testName, duration, description);
            } else {
                String failureMsg = cause != null ? cause.getClass().getSimpleName() : "Exception";
                addResultLine(false, testName, duration, failureMsg + ": " + cause.getMessage());
                e.printStackTrace();
            }
            failedTests++;
        }

        totalTests++;
        totalExecutionTime += System.currentTimeMillis() - startTime;
    }

    private void addResultLine(Boolean passed, String testName, long duration, String description) {
        String result = passed ? "✓" : "✗";
        String resultLine = String.format("%s %s (%dms) - %s", result,
                testName, duration, description.isEmpty() ? "No description" : description);
        testResults.add(resultLine);
    }

    private List<Method> getAllTestMethods(Class<?> cls) {
        List<Method> methods = new ArrayList<>();
        Class<?> current = cls;

        while (current != null && current != Object.class) {
            for (Method method : current.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Test.class)) {
                    methods.add(method);
                }
            }
            current = current.getSuperclass();
        }

        return methods;
    }

    private void printStatistics() {
        System.out.println("\nTest Results:");
        testResults.forEach(System.out::println);

        System.out.println("\nSummary:");
        System.out.println("Total tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + failedTests);
        System.out.println("Total execution time: " + totalExecutionTime + "ms");

        if (totalTests > 0) {
            double successRate = (double) passedTests / totalTests * 100;
            System.out.printf("Success rate: %.1f%%\n", successRate);
        }
    }

    private List<Method> getMethodsAnnotatedWith(Class<?> cls,
                                                 Class<? extends java.lang.annotation.Annotation> annotation) {
        List<Method> methods = new ArrayList<>();
        Class<?> current = cls;

        while (current != null && current != Object.class) {
            for (Method method : current.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    methods.add(method);
                }
            }
            current = current.getSuperclass();
        }

        return methods;
    }

    private void invokeMethods(List<Method> methods, Object instance) throws Exception {
        for (Method method : methods) {
            method.setAccessible(true);
            method.invoke(instance);
        }
    }

    private void runTestMethodWithTimeout(Object instance, Method method, long timeout, String testName,
                                          String description, long startTime) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            try {
                method.invoke(instance);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        try {
            future.get(timeout, TimeUnit.MILLISECONDS);
            long duration = System.currentTimeMillis() - startTime;
            addResultLine(true, testName, duration, description);
            passedTests++;
        } catch (TimeoutException e) {
            future.cancel(true);
            long duration = System.currentTimeMillis() - startTime;
            addResultLine(false, testName, duration, "Test timed out after " + timeout + "ms");
            failedTests++;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            addResultLine(false, testName, duration, e.getCause().getMessage());
            e.printStackTrace();
            failedTests++;
        } finally {
            executor.shutdownNow();
        }
    }

}
