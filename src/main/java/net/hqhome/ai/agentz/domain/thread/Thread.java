package net.hqhome.ai.agentz.domain.thread;

import java.util.List;

import com.google.common.collect.Lists;
import lombok.Data;

@Data
public class Thread {
  private String id;
  private String agentId;
  private String userId;
  private List<Message> messages;
  private ThreadStatus status;

  public void addMessage(Message message) {
//    status = ThreadStatus.RUNNING;
    if (messages == null) {
      messages = Lists.newArrayList();
    }
    messages.add(message);
  }

  public Boolean isRunning() {
    return status == ThreadStatus.RUNNING;
  }
}
