package net.hqhome.ai.agentz.domain.agent;

import java.util.UUID;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import net.hqhome.ai.agentz.domain.agent.authorization.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class AgentFactory {
  @Autowired
  private IAgentRepository agentRepository;

  public Agent create(String name, String description, String creatorId) {
    var agent = new Agent();
    agent.setId(UUID.randomUUID().toString());
    agent.setName(name);
    agent.setDescription(description);
    agent.setCreatorId(creatorId);

    return agent;
  }

  public Agent create(String name, String description, AgentType type, Model model, String systemMessage, String creatorId, ModelParameter modelParameter) {
    switch (type) {
      case CHAT -> {
        var agent = new ChatAgent();
        agent.setId(UUID.randomUUID().toString());
        agent.setName(name);
        agent.setDescription(description);
        agent.setSystemMessage(systemMessage);
        agent.setCreatorId(creatorId);
        agent.setModel(model);
        agent.setType(type);
        agent.setModelParameter(modelParameter);

        return agent;
      }
    }
    return null;
  }

  @Nullable
  public Agent get(String id) {
    return agentRepository.getById(id);
  }

  @Nullable
  public Model getModel(String modelId) {
    return agentRepository.getModelById(modelId);
  }

  public Model createModel(String name, String description, String creatorId, String modelType, String url, String model, Authorization authorization) {
    if (modelType.equals(ModelType.OPENAI.toString())) {
      var openAIModel = new OpenAIModel();
      openAIModel.setId(UUID.randomUUID().toString());
      openAIModel.setName(name);
      openAIModel.setDescription(description);
      openAIModel.setCreatorId(creatorId);
      openAIModel.setUrl(url);
      openAIModel.setModel(model);
      openAIModel.setAuthorization(authorization);

      return openAIModel;
    }
    return null;
  }

  public static void main(String[] args) {
    var json = """
            {"type":"bearer", "token": "token"}
            """;

    Authorization a = JSON.parseObject(json, Authorization.class);
    System.out.println(a);
  }
}
