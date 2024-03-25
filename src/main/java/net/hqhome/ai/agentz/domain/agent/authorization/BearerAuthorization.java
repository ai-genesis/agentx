package net.hqhome.ai.agentz.domain.agent.authorization;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JSONType(typeName = "bearer")
public class BearerAuthorization extends Authorization {
    private String token;
}
