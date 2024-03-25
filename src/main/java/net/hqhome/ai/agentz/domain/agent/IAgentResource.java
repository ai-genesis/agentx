package net.hqhome.ai.agentz.domain.agent;

import net.hqhome.ai.agentz.domain.agent.authorization.Authorization;

import java.util.List;

public interface IAgentResource {
    String chatCompletions(ModelType type, String url, String token, String model, List<ChatMessage> messageList, List<String> stop, Double temperature);
    String chatCompletions(ModelType type, String url, Authorization authorization, String model, List<ChatMessage> messageList, List<String> stop, Double temperature);
}
