package net.hqhome.ai.agentz.infrastructor.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OpenAIRequest {
    private String model;
    private List<?> messages;
    private List<?> stop;
    private Double temperature;
}
