package net.hqhome.ai.agentz.userinterface.dto;

import lombok.Data;
import net.hqhome.ai.agentz.domain.agent.authorization.Authorization;

@Data
public class ModelRequest {
    private String modelType;
    private String name;
    private String description;
    private String url;
    private String model;
    private Authorization authorization;
}
