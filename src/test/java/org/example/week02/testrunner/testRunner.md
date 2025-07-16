to run custom test runner:

run `src/test/java/org/example/week02/testrunner/SampleRun.java` from IDE

or

```
mvn dependency:build-classpath -Dmdep.outputFile=cp.txt compile test-compile && java -cp "target/test-classes;target/classes;$(cat cp.txt)" org.example.week02.testrunner.TestRunner org.example.week02.testrunner
```
