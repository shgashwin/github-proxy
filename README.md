# github-proxy

## Instruction to execute the github-proxy project.

1. Clone the github-proxy project to local machine
2. Build the maven project using command > mvn clean install
3. Go to /target folder and execute the command > java -jar github-proxy-0.0.1-SNAPSHOT.jar
4. Access the swagger file using link: http://localhost:8080/githubproxy/swagger-ui.html#/github-proxy-controller or 
    using the postman access the API http://localhost:8080/githubproxy/rest/search/repositories?q=topic:java
