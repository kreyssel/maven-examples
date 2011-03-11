Example of how to create a generic Logging Agent with Java Intrumentation API and Javassist
===========================================================================================

In many situations is it helpful to add logging functions to running code.

In this example we use the Java Intrumentation API and Javassist to add the
logging functionality at runtime. For this feature we manipulate the bytecode
for any class at runtime and add java.util.logging code for any class method.

This maven project packages all classes (incl. javassist) in a "fat jar".
This jar you can use directly to add it to the java command line: 

	java -javaagent:loggingagent-0.0.1-SNAPSHOT-jar-with-dependencies.jar

This was inspired by blog post:

http://today.java.net/article/2008/04/22/add-logging-class-load-time-java-instrumentation  