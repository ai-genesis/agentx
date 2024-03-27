package net.hqhome.ai.agentz.application;

import net.hqhome.ai.agentz.domain.agent.authorization.Authorization;
import net.hqhome.ai.agentz.domain.event.AbstractDomainEvent;
import net.hqhome.ai.agentz.infrastructor.agent.AgentRepository;
import net.hqhome.ai.agentz.userinterface.dto.AgentRequest;
import net.hqhome.ai.agentz.userinterface.dto.ModelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.hqhome.ai.agentz.domain.agent.AgentFactory;
import net.hqhome.ai.agentz.domain.user.User;
import org.springframework.web.context.request.async.DeferredResult;

@Service
public class AgentService {
  @Autowired
  private AgentFactory agentFactory;

  @Autowired
  private AgentRepository agentRepository;

  public String createAgent(AgentRequest request, User creator) {
    var model = agentFactory.getModel(request.getModelId());  // TODO check user
    if (model == null) {
      throw new RuntimeException("model not found");
    }
    var agent = agentFactory.create(request.getName(), request.getDescription(), request.getType(), model, request.getSystemMessage(), creator.getId(), AgentDTOConverter.INSTANCE.toDomain(request.getModelParameter()));
    agentRepository.save(agent);
    return agent.getId();
  }

  public String createModel(ModelRequest request, User creator) {
    var m = agentFactory.createModel(request.getName(), request.getDescription(), creator.getId(), request.getModelType(), request.getUrl(), request.getModel(), request.getAuthorization());
    agentRepository.saveModel(m);
    return m.getId();
  }

  public String createModel(User creator, String name, String description, String modelType, String url, String model, Authorization authorization) {
    var m = agentFactory.createModel(name, description, creator.getId(), modelType, url, model, authorization);
    return m.getId();
  }

}
