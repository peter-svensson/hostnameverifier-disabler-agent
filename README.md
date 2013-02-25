Java HostnameVerificationDisabler Agent
=======================================

Overview:
---------

A [Javaagent](http://docs.oracle.com/javase/6/docs/api/java/lang/instrument/package-summary.html) that can be used to remove HostnameVerifications for HTTPS (SSL) connections with certificates, not for production use.... 

Usage:
------
Build and package the jar file using `mvn install`.
Add the java agent to your runtime by adding the  `-javaagent:<PATH_TO_JAR>` to your start command.

Example

    java -javaagent:/home/peterl/hostnamedisabler.jar Main

