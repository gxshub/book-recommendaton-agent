package csci318.demo.presentation.websocket;

import csci318.demo.service.RecommendationService;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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