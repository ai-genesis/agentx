package net.hqhome.ai.agentz.domain.agent;

import lombok.Data;
import lombok.Getter;
import net.hqhome.ai.agentz.domain.agent.authorization.Authorization;

import java.util.List;

@Data
public abstract class Model {
  private ModelType type = ModelType.UNKNOWN;

  protected String id;
  protected String name;
  protected String description;
  protected String creatorId;
  protected List<String> stop;
  protected Double temperature;
  protected String url;
  protected String model;
  protected Authorization authorization;

  public abstract String chatCompletion(IAgentResource resource, List<ChatMessage> message);

  public abstract String chatCompletion(IAgentResource resource, ModelParameter parameter, List<ChatMessage> message);
}
