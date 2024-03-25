package net.hqhome.ai.agentz.domain.tool;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.PostConstruct;
import net.hqhome.ai.agentz.domain.AbstractDomainService;
import net.hqhome.ai.agentz.domain.event.AbstractDomainEvent;
import net.hqhome.ai.agentz.domain.event.events.MessageAddedDomainEvent;
import net.hqhome.ai.agentz.domain.thread.Message;
import net.hqhome.ai.agentz.domain.thread.Thread;
import org.springframework.stereotype.Service;

@Service
public class ToolDomainService extends AbstractDomainService {

  @PostConstruct
  public void init() {
    super.register(this, MessageAddedDomainEvent.class);
  }

  @Override
  public void handle(AbstractDomainEvent event) {
//    ToolEvent ;
//    String res = event.getToolId.run();
//    MessageAddedDomainEvent messageAddedDomainEvent;
//
//    publishEvent(messageAddedDomainEvent);
  }

  public void addMessage(Thread thread, Message message) {
    thread.addMessage(message);

    MessageAddedDomainEvent messageAddedDomainEvent = new MessageAddedDomainEvent();
    messageAddedDomainEvent.setThreadId(thread.getId());
    messageAddedDomainEvent.setAgentId(thread.getAgentId());
    messageAddedDomainEvent.setMessages(JSON.toJSONString(thread.getMessages()));

    publishEvent(messageAddedDomainEvent);
  }

}
