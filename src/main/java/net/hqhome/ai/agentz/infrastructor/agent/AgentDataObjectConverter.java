package net.hqhome.ai.agentz.infrastructor.agent;

import com.alibaba.fastjson2.JSON;
import net.hqhome.ai.agentz.domain.agent.*;
import net.hqhome.ai.agentz.domain.agent.authorization.Authorization;
import net.hqhome.ai.agentz.domain.thread.Message;
import net.hqhome.ai.agentz.domain.thread.Thread;
import net.hqhome.ai.agentz.domain.thread.ThreadStatus;
import net.hqhome.ai.agentz.infrastructor.agent.dataobject.AgentDO;
import net.hqhome.ai.agentz.infrastructor.agent.dataobject.ModelDO;
import net.hqhome.ai.agentz.infrastructor.thread.dataobject.MessageDO;
import net.hqhome.ai.agentz.infrastructor.thread.dataobject.ThreadDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AgentDataObjectConverter {
    AgentDataObjectConverter INSTANCE = Mappers.getMapper( AgentDataObjectConverter.class );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "agentId", source = "id")
    @Mapping(target = "modelId", expression = "java(agent.getModel().getId())")
    @Mapping(target = "type", expression = "java(agent.getType().toString())")
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "modifiedTime", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    AgentDO toDO(Agent agent);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modelId", source = "id")
    @Mapping(target = "type", expression = "java(model.getType().toString())")
//    @Mapping(target = "userId", source = "creatorId")
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "modifiedTime", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    ModelDO toDO(Model model);

//    @InheritInverseConfiguration

    default Agent toAgent(AgentDO agentDO) {
        if (agentDO.getType().equals(AgentType.CHAT.toString())) {
            return toChatAgent(agentDO);
        }
        return null;
    }

    default Model toDomain(ModelDO modelDO) {
        if (modelDO.getType().equals(ModelType.OPENAI.toString())) {
            return toOpenAIModel(modelDO);
        }
        return null;
    }

    @InheritInverseConfiguration(name = "toDO")
    ChatAgent toChatAgent(AgentDO agentDO);

    @InheritInverseConfiguration(name = "toDO")
    OpenAIModel toOpenAIModel(ModelDO modelDO);


    default String map(Authorization authorization) {
        return JSON.toJSONString(authorization);
    }

    default String map(ModelParameter modelParameter) {
        return JSON.toJSONString(modelParameter);
    }

    default Authorization map(String value) {
        return JSON.parseObject(value, Authorization.class);
    }

    default ModelParameter mapToModelParameter(String value) {
        return JSON.parseObject(value, ModelParameter.class);
    }

    default AgentType mapAgentType(String str) {
        return AgentType.of(str);
    }

    default ModelType mapModelType(String str) {
        return ModelType.of(str);
    }
}
