package net.hqhome.ai.agentz.domain.event.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.hqhome.ai.agentz.domain.event.AbstractDomainEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class AgentFinishedDomainEvent extends AbstractDomainEvent {
    private Boolean isError;
    private Exception error;
    private String threadId;
    private String userId;
    private String result;
}
