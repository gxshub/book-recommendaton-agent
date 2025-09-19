package csci318.demo.service;

import csci318.demo.service.dto.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    private static final Logger log = LoggerFactory.getLogger(RecommendationService.class);

    private final ChatAgent chatAgent;
    private final RAGAgent ragAgent;

    public RecommendationService(ChatAgent chatAgent, RAGAgent ragAgent) {
        this.chatAgent = chatAgent;
        this.ragAgent = ragAgent;
    }

    public String recommend(String sessionId, String message) {
        try {
            log.info("Session [{}]: User message: {}", sessionId, message);

            ChatMessage chatMessage = this.chatAgent.chat(sessionId, message).content();
            log.info("Session [{}]: Chat agent response: {}", sessionId, chatMessage);

            switch (chatMessage.state()) {
                case CHAT -> {
                    return chatMessage.messageToCustomer();
                }
                case RAG -> {
                    String bookDescription = chatMessage.bookDescription();
                    log.info("Session [{}]: RAG agent book description: {}", sessionId, bookDescription);
                    String recommendation = this.ragAgent.answer(sessionId, bookDescription).content();
                    log.info("Session [{}]: RAG agent recommendation: {}", sessionId, recommendation);
                    return recommendation;
                }
                default -> {
                    log.error("Session [{}]: Unknown state from chat agent: {}", sessionId, chatMessage.state());
                    return "I'm sorry, but I'm having trouble processing your request right now. Please try again later.";
                }
            }
        } catch (Exception e) {
            log.error("Session [{}]: Error during recommendation process", sessionId, e);
            return "I'm sorry, but I'm having trouble processing your request right now. Please try again later.";
        }
    }
}

