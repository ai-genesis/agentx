package net.hqhome.ai.agentz.domain.event.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.hqhome.ai.agentz.domain.event.AbstractDomainEvent;
import net.hqhome.ai.agentz.domain.thread.Message;

import java.util.function.Consumer;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageAddedDomainEvent extends AbstractDomainEvent {
    private String threadId;
    private String agentId;
    private String messages;
}
