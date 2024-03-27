package net.hqhome.ai.agentz.domain.thread;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.hqhome.ai.agentz.domain.AbstractDomainService;
import net.hqhome.ai.agentz.domain.event.events.MessageAddedDomainEvent;

import java.util.function.Consumer;

@Slf4j
@Service
public class ThreadDomainService extends AbstractDomainService {

    @Autowired
    private IThreadRepository threadRepository;

    public void updateStatusAndAddMessage(Thread thread, Message message, Consumer<String> callback) {
        if (message.getRole().equals(Message.ROLE_USER)) {
//            if (thread.isRunning()) {
//                log.warn("add message failed, because thread {} is running", thread.getId());
//                return;
//            }

            synchronized (thread) {
                if (thread.isRunning()) {
                    log.warn("add message failed, because thread {} is running", thread.getId());
                    return;
                }

                thread.setStatus(ThreadStatus.RUNNING);
                thread.addMessage(message);
                threadRepository.update(thread, message);

                MessageAddedDomainEvent messageAddedDomainEvent = new MessageAddedDomainEvent();
                messageAddedDomainEvent.setThreadId(thread.getId());
                messageAddedDomainEvent.setAgentId(thread.getAgentId());
                messageAddedDomainEvent.setMessages(JSON.toJSONString(thread.getMessages()));

                publishEvent(messageAddedDomainEvent);
            }

        } else if (message.getRole().equals(Message.ROLE_ASSISTANT)) {
            thread.addMessage(message);
            threadRepository.addMessage(message, thread.getId());
        }
    }

    public void updateStatusAndAddMessage(Thread thread, Message message) {
        if (message.getRole().equals(Message.ROLE_USER)) {
//            if (thread.isRunning()) {
//                log.warn("add message failed, because thread {} is running", thread.getId());
//                return;
//            }

            synchronized (thread) {
                if (thread.isRunning()) {
                    log.warn("add message failed, because thread {} is running", thread.getId());
                    return;
                }

                thread.setStatus(ThreadStatus.RUNNING);
                thread.addMessage(message);
                threadRepository.update(thread, message);

                MessageAddedDomainEvent messageAddedDomainEvent = new MessageAddedDomainEvent();
                messageAddedDomainEvent.setThreadId(thread.getId());
                messageAddedDomainEvent.setAgentId(thread.getAgentId());
                messageAddedDomainEvent.setMessages(JSON.toJSONString(thread.getMessages()));

                publishEvent(messageAddedDomainEvent);
            }

        } else if (message.getRole().equals(Message.ROLE_ASSISTANT)) {
            thread.addMessage(message);
            threadRepository.update(thread, message);
        }
    }

    public void updateStatus(Thread thread, ThreadStatus status) {
        thread.setStatus(status);
        threadRepository.update(thread);
    }
}
