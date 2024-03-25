package net.hqhome.ai.agentz.domain.agent;

import java.util.Arrays;

public enum ModelType {
    UNKNOWN,
    OPENAI;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static ModelType of(String str) {
        return Arrays.stream(ModelType.values()).filter(modelType -> modelType.toString().equals(str)).findFirst().get();
    }
}
