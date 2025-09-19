package csci318.demo;

import csci318.demo.infrastructure.agentic.ModelLogger;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class BookRecommendationApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(BookRecommendationApplication.class, args);
    }

    @Bean
    ChatModelListener chatModelLogger() {return new ModelLogger();}

}
