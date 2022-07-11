# Using Micrometer

This demo app shows how a Spring Boot application can expose a Prometheus metrics endpoint for scraping.

To run the application :
> first method :
1. clone the repo
2. Open it in any IDE (STS , Intellij) as maven project.
3. build it or maven clean and then maven install.
4. run the application

> Second Method :

download the jar file from src/target and run it:-
command : java -jar <jar_file_name>.jar

### Fetch application metrics :
run the script file: This script curl the application endpoints in an infinite loop.
In directory of run.sh file ----
command : ./run.sh

To see the application metrics : `http://localhost:8080/actuator/prometheus`


 
