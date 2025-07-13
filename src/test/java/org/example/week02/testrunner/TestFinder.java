package org.example.week02.testrunner;

import org.example.week02.testrunner.annotation.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestFinder {

    public static List<Class<?>> findClasses(String packageName) throws Exception {
        List<Class<?>> testClasses = new ArrayList<>();
        String path = packageName.replace('.', '/');
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL packageURL = loader.getResource(path);

        if (packageURL == null) return testClasses;

        File packageDir = new File(packageURL.getFile());
        scanDirectory(packageDir, packageName, testClasses);
        return testClasses;
    }

    private static void scanDirectory(File directory, String packageName, List<Class<?>> testClasses) throws Exception {
        if (!directory.exists() || !directory.isDirectory()) return;

        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                // Recursively scan subdirectories
                scanDirectory(file, packageName + "." + file.getName(), testClasses);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().replace(".class", "");
                Class<?> cls = Class.forName(className);

                // Check for @Test annotation
                for (var method : cls.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Test.class)) {
                        testClasses.add(cls);
                        break;
                    }
                }
            }
        }
    }

}
