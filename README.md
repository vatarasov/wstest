# wstest
Leaking of file descriptors used by Tomcat WebSocket Client

# How to reproduce bug
1. Clone the project
2. Go into it
3. ./wsserver/gradlew build
4. ./wsclient/gradlew build
5. java -jar ./wsserver/build/libs/wsserver-0.0.1-SNAPSHOT.jar
6. java -jar ./wsclient/build/libs/wsclient-0.0.1-SNAPSHOT.jar
7. lsof -p <pid_client_proc> | wc -l (keep in mind the number of opened file descriptor, for example 81)
8. kill -p <pid_server_proc>
9. java -jar ./wsserver/build/libs/wsserver-0.0.1-SNAPSHOT.jar
10. lsof -p <pid_client_proc> | wc -l (see that number of opened file descriptors incremented on 1, for example 82)

The number of opened file descriptors will grow up while we will repeat restarting of the server
