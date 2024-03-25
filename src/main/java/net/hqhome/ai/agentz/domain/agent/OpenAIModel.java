package net.hqhome.ai.agentz.domain.agent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.hqhome.ai.agentz.domain.agent.authorization.Authorization;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpenAIModel extends Model {
    private ModelType type = ModelType.OPENAI;

    private String encodedApiKey;

//    private Authorization authorization;

    private String decodeApiKey(String encodedApiKey) {
        return encodedApiKey;
    }
    public String chatCompletion(IAgentResource resource, List<ChatMessage> message) {
        return resource.chatCompletions(type, url, authorization, model, message, stop, temperature);
    }

    public String chatCompletion(IAgentResource resource, ModelParameter parameter, List<ChatMessage> message) {
        return resource.chatCompletions(type, url, authorization, model, message, parameter.getStop(), parameter.getTemperature());
    }


}
