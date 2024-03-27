package net.hqhome.ai.agentz.application;

import jakarta.annotation.PostConstruct;
import net.hqhome.ai.agentz.domain.event.AbstractDomainEvent;
import net.hqhome.ai.agentz.domain.event.ISubscriber;
import net.hqhome.ai.agentz.domain.event.events.AgentFinishedDomainEvent;
import net.hqhome.ai.agentz.domain.thread.*;
import net.hqhome.ai.agentz.domain.thread.Thread;
import net.hqhome.ai.agentz.userinterface.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.hqhome.ai.agentz.domain.agent.AgentFactory;
import net.hqhome.ai.agentz.domain.user.User;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ThreadService implements ISubscriber {
    @Autowired
    private ThreadFactory threadFactory;
    @Autowired
    private AgentFactory agentFactory;

    @Autowired
    private IThreadRepository threadRepository;
    @Autowired
    private ThreadDomainService threadDomainService;

    @Autowired
    private ISubscriber subscriberAdapter;

    private final Map<String, Map.Entry<Thread, DeferredResult<ResponseWrapper<String>>>> threadDeferredResult = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        subscriberAdapter.register(this, AgentFinishedDomainEvent.class);
    }

    @Override
    public void handle(AbstractDomainEvent event) {
        AgentFinishedDomainEvent ev = (AgentFinishedDomainEvent) event;
        String threadId = ev.getThreadId();
        Map.Entry<Thread, DeferredResult<ResponseWrapper<String>>> entry = threadDeferredResult.get(threadId);
        Thread thread = entry.getKey();
        var deferredResult = entry.getValue();

        if (ev.getIsError()) {
            threadDomainService.updateStatus(thread, ThreadStatus.IDLE);
            deferredResult.setErrorResult(ev.getError());
        } else {
            String content = ev.getResult();
            thread.setStatus(ThreadStatus.IDLE);
            threadDomainService.updateStatusAndAddMessage(thread, threadFactory.createAssistantMessage(content));
            deferredResult.setResult(ResponseWrapper.success(ev.getResult()));
        }

        threadDeferredResult.remove(threadId);
    }

    public String createThread(String agentId, User user) {
        var agent = agentFactory.get(agentId);
        if (agent == null) {
            throw new RuntimeException("agent not found");
        }
        var thread = threadFactory.create(agentId, user.getId());
        threadRepository.save(thread);

        return thread.getId();
    }

    @Nullable
    public Thread getThreadById(String threadId, User user) {
        var thread = threadFactory.get(threadId, user.getId());

        return thread;
    }

    public void addMessage(String threadId, String message, User user, DeferredResult<ResponseWrapper<String>> deferredResult) {
        var thread = getThreadById(threadId, user);

        if (thread == null) {
            deferredResult.setErrorResult(new RuntimeException("thread not found"));
            return;
        }
//        if (thread.getStatus().equals(ThreadStatus.RUNNING)) {
//            deferredResult.setErrorResult(new RuntimeException("thread is running"));
//            return;
//        }

        var previous = threadDeferredResult.putIfAbsent(threadId, Map.entry(thread, deferredResult));

        if (previous != null) {
            deferredResult.setErrorResult(new RuntimeException("thread is running"));
            return;
        }

        var msg = threadFactory.createUserMessage(message);
        threadDomainService.updateStatusAndAddMessage(thread, msg);
    }

}
