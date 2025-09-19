package csci318.demo.service.dto;

public record ChatMessage(State state,
                          String messageToCustomer,
                          String bookDescription) {}
