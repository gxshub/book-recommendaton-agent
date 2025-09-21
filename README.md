# Multi-Agent Book Recommendation Application
This application is a multi-agent book recommendation system that uses AI agents to provide personalised book suggestions based on user preferences. 
It supports both REST API and WebSocket-based communication for interactive and session-based recommendations.
It is built using **Spring Boot** for backend development and **LangChain4j** for integrating AI capabilities.

## 1. Book Recommendation Workflow

- The [`ChatAgent`](./src/main/java/csci318/demo/service/ChatAgent.java) gathers user preferences and generates a concise book description.
- The [`RAGAgent`](./src/main/java/csci318/demo/service/RAGAgent.java) uses the description to provide specific book recommendations.
- The two agents share a session-based memory to remember recent dialogues and are orchestrated by the [`RecommendationService`](./src/main/java/csci318/demo/service/RecommendationService.java).

```java
public RecommendationService(ChatAgent chatAgent, RAGAgent ragAgent) {
    this.chatAgent = chatAgent;
    this.ragAgent = ragAgent;
}
```

## 2. Interfaces
### 2.1 REST API
```shell
# Linux/MacOS
curl -G "http://localhost:8080/chat-recommend" \
--data-urlencode "sessionId=1" \
--data-urlencode "userMessage=I would like to get a science fiction for my 10 year-old boy."
```

```shell
# Windows
curl -G "http://localhost:8080/chat-recommend" ^
--data-urlencode "sessionId=1" ^
--data-urlencode "userMessage=I would like to get a science fiction for my 10 year-old boy."
```

The user's input message is the content following `userMessage=`.
The agent will remember the recent dialogues per session defined by `sessionId`.
### 2.2 Interactive HTML Chat
Use the HTML frontend to have a conversation with the agents:

```shell
# Linux/MacOS
open ./src/main/resources/static/chat.html
```
```shell
# Windows
start ./src/main/resources/static/chat.html
```
<img src="./screenshot/chat.png" alt="chat" style="width:400px">

## 3 Spring Webflux and WebSocket
This project introduces **Spring WebFlux** and **WebSocket** to enable full-duplex, asynchronous communication between the client and server, 
making it ideal for interactive chat applications.

The [`ChatWebSocketHandler`](./src/main/java/csci318/demo/presentation/websocket/ChatWebSocketHandler.java) processes incoming WebSocket messages 
and delegates them to the [`RecommendationService`](./src/main/java/csci318/demo/service/RecommendationService.java).
<!--
```java 
@Component
public class ChatWebSocketHandler implements WebSocketHandler {
    private final RecommendationService recommendationService;
    public ChatWebSocketHandler(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        String sessionId = session.getId();
        return session.send(
                session.receive()
                        .map(WebSocketMessage::getPayloadAsText)
                        .map(text -> {
                            // delegate to service
                            String reply = recommendationService.recommend(sessionId, text);
                            return session.textMessage(reply);
                        })
        );
    }
}
```
-->

The [`WebSocketConfig`](./src/main/java/csci318/demo/infrastructure/ws/WebSocketConfig.java)
class defines the WebSocket endpoint and maps it to the ChatWebSocketHandler.
<!--
```java
@Configuration
public class WebSocketConfig {
    private final ChatWebSocketHandler handler;
    public WebSocketConfig(ChatWebSocketHandler handler) {
        this.handler = handler;
    }
    @Bean
    public SimpleUrlHandlerMapping handlerMapping() {
        return new SimpleUrlHandlerMapping(Map.of("/chat", handler), 10);
    }
    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
```
-->

To ensure asynchronous, non-blocking communication, the following property is set in
[`application.properties`](./src/main/resources/application.properties):
```properties
spring.main.web-application-type=reactive
```
