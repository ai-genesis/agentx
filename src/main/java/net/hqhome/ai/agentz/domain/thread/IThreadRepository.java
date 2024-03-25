package net.hqhome.ai.agentz.domain.thread;

import java.util.List;

public interface IThreadRepository {

  void save(Thread thread);
  Thread getById(String threadId, String userId);

  List<Message> getMessagesByThreadId(String threadId);

  void update(Thread thread, Message message);
  void update(Thread thread);
}
