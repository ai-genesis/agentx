package net.hqhome.ai.agentz.domain.agent;

import java.util.List;

public class ChatAgent extends Agent {
    @Override
    public String run(IAgentResource agentResource, List<ChatMessage> messages) {
        return model.chatCompletion(agentResource, modelParameter, messages);
    }
}
