package net.hqhome.ai.agentz.domain.agent;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import net.hqhome.ai.agentz.domain.event.events.AgentFinishedDomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import net.hqhome.ai.agentz.domain.AbstractDomainService;
import net.hqhome.ai.agentz.domain.event.AbstractDomainEvent;
// import net.hqhome.ai.agentz.domain.event.EventType;
import net.hqhome.ai.agentz.domain.event.events.MessageAddedDomainEvent;

import java.util.List;

@Slf4j
@Service
public class AgentDomainService extends AbstractDomainService {

  @Autowired
  private AgentFactory agentFactory;

  @Autowired
  private IAgentResource agentResource;
  @PostConstruct
  public void init() {
    super.register(this, MessageAddedDomainEvent.class);
  }

  @Override
  public void handle(AbstractDomainEvent event) {
    if (event instanceof MessageAddedDomainEvent msgAddedEvent) {
      Agent agent = agentFactory.get(msgAddedEvent.getAgentId());
      String res = agent.run(agentResource, JSON.parseArray(msgAddedEvent.getMessages(), ChatMessage.class));

      if (agent instanceof ChatAgent) {
        AgentFinishedDomainEvent agentFinishedDomainEvent = new AgentFinishedDomainEvent();
        agentFinishedDomainEvent.setThreadId(msgAddedEvent.getThreadId());
        agentFinishedDomainEvent.setUserId(msgAddedEvent.getUserId());
        agentFinishedDomainEvent.setIsError(false);
        agentFinishedDomainEvent.setResult(res);
        publishEvent(agentFinishedDomainEvent);
      }
    }


//    MessageAddedDomainEvent e = (MessageAddedDomainEvent) event;
//
//    Agent agent = agentFactory.get(e.getAgentId());
//
//    List<ChatMessage> messages = JSON.parseArray(e.getMessages(), ChatMessage.class);
//    AgentFinishedDomainEvent agentFinishedDomainEvent = new AgentFinishedDomainEvent();
//    agentFinishedDomainEvent.setThreadId(e.getThreadId());
//
//    try {
////      Task result = agent.run();
////      if (result is FinishedTask) {
////        AgentFinishedDomainEvent agentFinishedDomainEvent
////      } else {
////        ToolEvent tool;
////        publishEvent(tool);
////      }
//      String res = agent.getModel().chatCompletion(agentResource, messages);
//      // TODO agent parse res base on agent type
//      // rag agent.run();
//
//      agentFinishedDomainEvent.setIsError(false);
//      agentFinishedDomainEvent.setResult(res);
//    } catch (Exception ex) {
//      log.error("seeeeee", ex);
//      agentFinishedDomainEvent.setIsError(true);
//      agentFinishedDomainEvent.setError(ex);
//    }
//
//    // agent parse res
////    AgentFinishedDomainEvent agentFinishedDomainEvent = new AgentFinishedDomainEvent();
////    agentFinishedDomainEvent.setThreadId(e.getThreadId());
////    agentFinishedDomainEvent.setResult(res);
//    publishEvent(agentFinishedDomainEvent);


//    event = (MessageAddedDomainEvent) event;
//    List<ChatMessage> messages = new ArrayList<>();
//    ChatMessage message = new ChatMessage();
//    message.setRole("user");
//    message.setContent("hello");
//    messages.add(message);
//    OpenAIModel model = new OpenAIModel();
//    model.setUrl("https://api.openai.com/v1/chat/completions");
//    model.setModel("gpt-3.5-turbo");
//    model.setEncodedApiKey("");
//    String a = model.chatCompletion(agentResource, messages);
//    System.out.println(a);
//    AgentFinishedDomainEvent agentFinishedDomainEvent = new AgentFinishedDomainEvent();
//    agentFinishedDomainEvent.setThreadId(((MessageAddedDomainEvent) event).getThreadId());
//    agentFinishedDomainEvent.setResult(a);
//    publish(agentFinishedDomainEvent);
//    model.set("https://api.openai.com/v1/chat/completions");
//    model.setUrl("https://api.openai.com/v1/chat/completions");
//    String a = agentResource.chatCompletions("https://api.openai.com/v1/chat/completions",
//            "",
//            "gpt-3.5-turbo",
//            messages,
//            null,
//            1.);
    // TODO call agent run or debug based on event
//    throw new UnsupportedOperationException("12323232Unimplemented method 'handle'");
  }
}
