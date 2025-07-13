package org.example.week02.testrunner;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassFinder {

    public static List<Class<?>> findClasses(String packageName) throws Exception {
        List<Class<?>> allClasses = new ArrayList<>();
        String path = packageName.replace('.', '/');
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL packageURL = loader.getResource(path);

        if (packageURL == null)
            return allClasses;

        File packageDir = new File(packageURL.getFile());
        scanDirectory(packageDir, packageName, allClasses);
        return allClasses;
    }

    private static void scanDirectory(File directory, String packageName, List<Class<?>> allClasses) throws Exception {
        if (!directory.exists() || !directory.isDirectory())
            return;

        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                scanDirectory(file, packageName + "." + file.getName(), allClasses);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().replace(".class", "");
                Class<?> cls = Class.forName(className);
                allClasses.add(cls);
            }
        }
    }
}
