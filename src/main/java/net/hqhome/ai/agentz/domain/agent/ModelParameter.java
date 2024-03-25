package net.hqhome.ai.agentz.domain.agent;

import lombok.Data;

import java.util.List;

@Data
public class ModelParameter {
//    private String model;
    private Double temperature;
    private List<String> stop;
}
