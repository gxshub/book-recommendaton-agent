package csci318.demo.presentation.controller;

import csci318.demo.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService){
        this.recommendationService = recommendationService;
    }

    @GetMapping("/chat-recommend")
    public String getRecommendation(@RequestParam String sessionId, @RequestParam String userMessage) {
        return this.recommendationService.recommend(sessionId, userMessage);
    }
}
