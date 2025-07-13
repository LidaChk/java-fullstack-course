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
        List<Class<?>> testClasses = TestFinder.findClasses(packageName);

        System.out.println("\n=== Custom Test Runner Results ===");
        System.out.println("Package: " + packageName);
        System.out.println("Classes scanned: " + testClasses.size());

        for (Class<?> testClass : testClasses) {
            runTestClass(testClass);
        }

        printStatistics();
    }

    private void runTestClass(Class<?> testClass) throws Exception {
        Object testInstance = testClass.getDeclaredConstructor().newInstance();
        Method[] methods = testClass.getDeclaredMethods();

        int classTests = 0;
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                classTests++;
                runTestMethod(testInstance, method);
            }
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
                e.printStackTrace();
                testResults.add("✗ " + testName + " (" + duration + "ms) - " + failureMsg);
            }
            failedTests++;
        }

        totalTests++;
        totalExecutionTime += System.currentTimeMillis() - startTime;
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
