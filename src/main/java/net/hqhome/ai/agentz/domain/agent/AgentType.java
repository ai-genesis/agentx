package net.hqhome.ai.agentz.domain.agent;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum AgentType {
    CHAT,
    RAG,
    REACT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static AgentType of(String str) {
        return Arrays.stream(AgentType.values()).filter(agentType -> agentType.toString().equals(str)).findFirst().get();
    }
}
