package net.hqhome.ai.agentz.userinterface;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import net.hqhome.ai.agentz.infrastructor.common.SuperHttpClient;
import net.hqhome.ai.agentz.userinterface.dto.MessageRequest;
import net.hqhome.ai.agentz.userinterface.dto.ThreadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import net.hqhome.ai.agentz.application.ThreadService;
// import net.hqhome.ai.agentz.domain.event.EventType;
import net.hqhome.ai.agentz.domain.event.IPublisher;
import net.hqhome.ai.agentz.domain.user.User;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.*;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/thread")
public class ThreadController {
    @Autowired
    private ThreadService threadService;

    @Autowired
    private IPublisher publisher;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseWrapper<String> create(@RequestBody ThreadRequest body) {
        User user = new User();
        user.setId("test1");
        var threadId = threadService.createThread(body.getAgentId(), user);
        return ResponseWrapper.success(threadId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{threadId}/message")
    public DeferredResult<ResponseWrapper<String>> createMessage(@PathVariable String threadId, @RequestBody MessageRequest body) {
        User user = new User();
        user.setId("test1");

        DeferredResult<ResponseWrapper<String>> deferredResult = new DeferredResult<>();
        threadService.addMessage(threadId, body.getMessage(), user, deferredResult);
        return deferredResult;
    }

}
