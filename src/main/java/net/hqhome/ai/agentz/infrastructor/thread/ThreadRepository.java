package net.hqhome.ai.agentz.infrastructor.thread;

import net.hqhome.ai.agentz.domain.thread.IThreadRepository;
import net.hqhome.ai.agentz.domain.thread.Message;
import net.hqhome.ai.agentz.domain.thread.Thread;
import net.hqhome.ai.agentz.infrastructor.thread.dataobject.MessageDOExample;
import net.hqhome.ai.agentz.infrastructor.thread.dataobject.ThreadDO;
import net.hqhome.ai.agentz.infrastructor.thread.dataobject.ThreadDOExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ThreadRepository implements IThreadRepository {

  @Autowired
  private ThreadMapper threadMapper;

  @Autowired
  private MessageMapper messageMapper;

  @Override
  public void save(Thread thread) {
    threadMapper.insertSelective(ThreadDataObjectConverter.INSTANCE.toDO(thread));
  }

  @Override
  public Thread getById(String threadId, String userId) {
    ThreadDOExample example = new ThreadDOExample();
    example.createCriteria()
            .andThreadIdEqualTo(threadId)
            .andUserIdEqualTo(userId);

    List<ThreadDO> list = threadMapper.selectByExample(example);
    if (list.isEmpty()) {
      return null;
    }

    return ThreadDataObjectConverter.INSTANCE.toDomain(list.getFirst());
  }

  @Override
  public List<Message> getMessagesByThreadId(String threadId) {
    MessageDOExample example = new MessageDOExample();
    example.createCriteria().andThreadIdEqualTo(threadId);
    var list = messageMapper.selectByExample(example);

    return ThreadDataObjectConverter.INSTANCE.toDomain(list);
  }

  @Override
  @Transactional
  public void update(Thread thread) {
    ThreadDOExample example = new ThreadDOExample();
    example.createCriteria()
            .andThreadIdEqualTo(thread.getId());
    threadMapper.updateByExampleSelective(ThreadDataObjectConverter.INSTANCE.toDO(thread), example);
  }

  @Override
  @Transactional
  public void update(Thread thread, Message message) {
    ThreadDOExample example = new ThreadDOExample();
    example.createCriteria()
            .andThreadIdEqualTo(thread.getId());
    threadMapper.updateByExampleSelective(ThreadDataObjectConverter.INSTANCE.toDO(thread), example);
    messageMapper.insertSelective(ThreadDataObjectConverter.INSTANCE.toDO(message, thread.getId()));
  }

}
