package net.hqhome.ai.agentz.domain.agent;

public interface IAgentRepository {
  Agent getById(String id);

  void save(Agent agent);

  void saveModel(Model model);

  Model getModelById(String id);
}
