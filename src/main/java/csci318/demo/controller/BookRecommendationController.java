package csci318.demo.controller;

import csci318.demo.service.BookRecommendationAgent;
import dev.langchain4j.service.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookRecommendationController {

    private final BookRecommendationAgent bookRecommendationAgent;

    public BookRecommendationController(BookRecommendationAgent bookRecommendationAgent) {
        this.bookRecommendationAgent = bookRecommendationAgent;
    }

    @GetMapping("/recommendation")
    public String customerSupportAgent(@RequestParam String sessionId, @RequestParam String userMessage) {
        Result<String> result = bookRecommendationAgent.answer(sessionId, userMessage);
        return result.content();
    }
}
