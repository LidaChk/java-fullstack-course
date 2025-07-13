package org.example.utils;

public final class BenchmarkUtils {

  private BenchmarkUtils() {
  }

  public record BenchmarkResult(long timeMs, double memoryMB) {
  }

  public static long measureTaskExecutionTimeMS(Runnable task) {
    long startTime = System.currentTimeMillis();
    task.run();
    long endTime = System.currentTimeMillis();
    return endTime - startTime;
  }

  public static double measureTaskExecutionTimeMS(Runnable task, int iterations) {
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < iterations; i++) {
      task.run();
    }
    long endTime = System.currentTimeMillis();
    return (endTime - startTime) / (double) iterations;
  }

  public static double measureTaskMemoryUsageMB(Runnable task) {
    long memoryBefore = getUsedMemoryBefore();
    task.run();
    long memoryAfter = getUsedMemoryAfter();
    return bytesToMB(memoryAfter - memoryBefore);
  }

  public static BenchmarkResult measurePerformance(Runnable task) {
    long memoryBefore = getUsedMemoryBefore();
    long startTime = System.currentTimeMillis();
    task.run();
    long endTime = System.currentTimeMillis();
    long memoryAfter = getUsedMemoryAfter();

    double memoryMB = bytesToMB(memoryAfter - memoryBefore);
    return new BenchmarkResult(endTime - startTime, memoryMB);
  }

  private static double bytesToMB(long bytes) {
    return (double) bytes / (1024 * 1024);
  }

  private static long getUsedMemoryBefore() {
    Runtime.getRuntime().gc();
    return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
  }

  private static long getUsedMemoryAfter() {
    return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
  }
}
