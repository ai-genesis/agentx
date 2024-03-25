package net.hqhome.ai.agentz.infrastructor.agent.dto;

import lombok.Data;

import java.util.List;

@Data
public class OpenAIResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private Integer index;
        private Message message;
        private String finishReason;
    }

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}
