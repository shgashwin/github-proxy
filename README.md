# github-proxy

## Instruction to execute the github-proxy project.

1. Clone the github-proxy project to local machine
2. Build the maven project using command > mvn clean install
3. Go to /target folder of the project and execute the command > java -jar github-proxy-0.0.1-SNAPSHOT.jar or run the GithubProxyApplication.java file from IDE
4. Application can be accessed from the below path using swagger or postman call. 
    Swagger Link: http://localhost:8080/githubproxy/swagger-ui.html#/github-proxy-controller or 
    using the postman: http://localhost:8080/githubproxy/rest/search/repositories?q=topic:java
