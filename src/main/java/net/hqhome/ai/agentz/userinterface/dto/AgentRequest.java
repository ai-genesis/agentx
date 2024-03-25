package net.hqhome.ai.agentz.userinterface.dto;

import lombok.Data;
import net.hqhome.ai.agentz.domain.agent.AgentType;

import java.util.List;

@Data
public class AgentRequest {
    private String name;
    private String description;
    private String modelId;
    private AgentType type;
    private ModelParameter modelParameter;
    private String systemMessage;

    @Data
    public static class ModelParameter {
        private Double temperature;
        private List<String> stop;
    }
}
