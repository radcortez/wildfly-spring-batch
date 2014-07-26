# Wildfly - Spring Batch - Sample Application #

## How to run ? ##

* You need JDK 7 or higher, Maven 3 and Wildfly 8 to run the application.

* All the following commands need to be executed in the project root.

* Generate the Wildfly CLI scripts to install Spring Batch: `mvn process-resources`.

* To install Spring Batch in your Wildfly instance, start Wildfly and run: `mvn wildfly:execute-commands -P install-spring-batch`.

* Now you can check the application execution by running `mvn test`.

* To remove Spring Batch from your Wildfly instance, have Wildfly running and execute `mvn wildfly:execute-commands -P remove-spring-batch`

## Resources ##

* [Spring Batch as Wildfly Module](http://www.radcortez.com/spring-batch-as-wildfly-module/)
