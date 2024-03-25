package net.hqhome.ai.agentz.domain.thread;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.hqhome.ai.agentz.domain.agent.Agent;

import javax.annotation.Nullable;

@Component
public class ThreadFactory {
    @Autowired
    private IThreadRepository threadRepository;

    public Thread create(String agentId, String userId) {
        var thread = new Thread();
        thread.setId(UUID.randomUUID().toString());
        thread.setAgentId(agentId);
        thread.setUserId(userId);
        thread.setMessages(new ArrayList<>());
        thread.setStatus(ThreadStatus.CREATED);

        return thread;
    }

    @Nullable
    public Thread get(String id, String userId) {
        var thread = threadRepository.getById(id, userId);
        if (thread == null) {
            return null;
        }
        var messages = threadRepository.getMessagesByThreadId(thread.getId());
        thread.setMessages(messages);
        return thread;
    }

    public Message createUserMessage(String content) {
        return new Message(Message.ROLE_USER, content);
    }

    public Message createAssistantMessage(String content) {
        return new Message(Message.ROLE_ASSISTANT, content);
    }
}
