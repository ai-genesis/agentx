package net.hqhome.ai.agentz.infrastructor.agent;

import net.hqhome.ai.agentz.domain.agent.*;
import net.hqhome.ai.agentz.infrastructor.agent.dataobject.AgentDOExample;
import net.hqhome.ai.agentz.infrastructor.agent.dataobject.ModelDOExample;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AgentRepository implements IAgentRepository {

  @Autowired
  private AgentMapper agentMapper;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public Agent getById(String id) {
    AgentDOExample example = new AgentDOExample();
    example.createCriteria().andAgentIdEqualTo(id);
    var agentList = agentMapper.selectByExample(example);

    if (agentList.isEmpty()) {
      return null;
    }

    var agent = AgentDataObjectConverter.INSTANCE.toAgent(agentList.getFirst());
    agent.setModel(getModelById(agentList.getFirst().getModelId()));

    return agent;
  }

  @Override
  public void save(Agent agent) {
    agentMapper.insertSelective(AgentDataObjectConverter.INSTANCE.toDO(agent));
  }

  @Override
  public void saveModel(Model model) {
    modelMapper.insertSelective(AgentDataObjectConverter.INSTANCE.toDO(model));
  }

  @Override
  public Model getModelById(String id) {
    ModelDOExample example = new ModelDOExample();
    example.createCriteria()
                    .andModelIdEqualTo(id);
    var modelDOs = modelMapper.selectByExample(example);

    if (modelDOs.isEmpty()) {
      return null;
    }

    return AgentDataObjectConverter.INSTANCE.toDomain(modelDOs.getFirst());
  }


}
