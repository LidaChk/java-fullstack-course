package org.example.week02.testrunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.example.week02.testrunner.annotation.Test;

public class TestRunner {
    private int totalTests = 0;
    private int passedTests = 0;
    private int failedTests = 0;
    private long totalExecutionTime = 0;
    private final List<String> testResults = new ArrayList<>();

    public void runTests(String packageName) throws Exception {
        List<Class<?>> allClasses = ClassFinder.findClasses(packageName);
        List<Class<?>> testClasses = new ArrayList<>();

        for (Class<?> cls : allClasses) {
            if (!getAllTestMethods(cls).isEmpty()) {
                testClasses.add(cls);
            }
        }

        System.out.println("\n=== Custom Test Runner Results ===");
        System.out.println("Package: " + packageName);
        System.out.println("Classes scanned: " + allClasses.size());
        System.out.println("Test classes found: " + testClasses.size());

        for (Class<?> testClass : testClasses) {
            runTestClass(testClass);
        }

        printStatistics();
    }

    private void runTestClass(Class<?> testClass) throws Exception {
        Object testInstance = testClass.getDeclaredConstructor().newInstance();
        List<Method> testMethods = getAllTestMethods(testClass);

        int classTests = testMethods.size();

        for (Method method : testMethods) {
            runTestMethod(testInstance, method);
        }

        System.out.println("Tests discovered in " + testClass.getSimpleName() + ": " + classTests);
    }

    private void runTestMethod(Object instance, Method method) {
        String testName = instance.getClass().getSimpleName() + "." + method.getName();
        long startTime = System.currentTimeMillis();

        try {
            method.setAccessible(true);
            method.invoke(instance);
            long duration = System.currentTimeMillis() - startTime;
            testResults.add("✓ " + testName + " (" + duration + "ms)");
            passedTests++;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            Throwable cause = e.getCause();

            if (cause instanceof AssertionError) {
                testResults.add("✗ " + testName + " (" + duration + "ms) - AssertionFailed");
            } else {
                String failureMsg = cause != null ? cause.getClass().getSimpleName() : "Exception";
                testResults.add("✗ " + testName + " (" + duration + "ms) - " + failureMsg);
                e.printStackTrace();
            }
            failedTests++;
        }

        totalTests++;
        totalExecutionTime += System.currentTimeMillis() - startTime;
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
}
