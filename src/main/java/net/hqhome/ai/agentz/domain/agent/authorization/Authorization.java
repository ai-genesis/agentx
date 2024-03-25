package net.hqhome.ai.agentz.domain.agent.authorization;

import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = BearerAuthorization.class, name = "bearer")})
@JSONType(typeKey = "type", seeAlso = {BearerAuthorization.class})
@Data
public abstract class Authorization {
    private String type;
}
