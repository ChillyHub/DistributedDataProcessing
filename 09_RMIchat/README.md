### Run

1. First, run class HelloServer in: practice10/src/main/java/rmi/server/HelloServer.java
2. Second, run class HelloClient in: practice10/src/main/java/rmi/client/HelloClient.java

### Requst

1. Return to the server in the message() function a response like: "Hello, <name>!" to the received message. Print this response on the client.
2. Add a new Message class to the server, containing the strings name and message. Add objMessage() function to the HelloServer class, which accepts a Message class object. Send an object of this class by the client and display a response from the server.
3. Add to the client a new class named Message2, inherited from Message, which, in addition to the name and message strings, contains the getName() method, which returns name. Achieve, by assigning the same serialVersionUID, the client transmits an object of the Message class and receives the Message2 object on the server using the unchanged objMessage() function.