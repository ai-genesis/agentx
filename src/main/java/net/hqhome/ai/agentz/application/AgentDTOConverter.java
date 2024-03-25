package net.hqhome.ai.agentz.application;

import com.alibaba.fastjson2.JSON;
import net.hqhome.ai.agentz.domain.agent.Model;
import net.hqhome.ai.agentz.domain.agent.ModelParameter;
import net.hqhome.ai.agentz.domain.agent.ModelType;
import net.hqhome.ai.agentz.domain.agent.OpenAIModel;
import net.hqhome.ai.agentz.domain.agent.authorization.Authorization;
import net.hqhome.ai.agentz.infrastructor.agent.dataobject.ModelDO;
import net.hqhome.ai.agentz.userinterface.dto.AgentRequest;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AgentDTOConverter {
    AgentDTOConverter INSTANCE = Mappers.getMapper( AgentDTOConverter.class );


    ModelParameter toDomain(AgentRequest.ModelParameter modelParameter);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modelId", source = "id")
    @Mapping(target = "type", expression = "java(model.getType().toString())")
//    @Mapping(target = "userId", source = "creatorId")
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "modifiedTime", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    ModelDO toDO(Model model);

    default Model toDomain(ModelDO modelDO) {
        if (modelDO.getType().equals(ModelType.OPENAI.toString())) {
            return toOpenAIModel(modelDO);
        }
        return null;
    }

    @InheritInverseConfiguration(name = "toDO")
    OpenAIModel toOpenAIModel(ModelDO modelDO);


    default String map(Authorization authorization) {
        return JSON.toJSONString(authorization);
    }

    default Authorization map(String value) {
        return JSON.parseObject(value, Authorization.class);
    }

    default ModelType mapModelType(String str) {
        return ModelType.of(str);
    }
}
