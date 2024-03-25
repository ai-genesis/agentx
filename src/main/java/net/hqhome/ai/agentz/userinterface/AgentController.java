package net.hqhome.ai.agentz.userinterface;

import jakarta.servlet.http.HttpServletResponse;
import net.hqhome.ai.agentz.domain.agent.AgentDomainService;
import net.hqhome.ai.agentz.domain.event.AbstractDomainEvent;
import net.hqhome.ai.agentz.userinterface.dto.AgentRequest;
import net.hqhome.ai.agentz.userinterface.dto.ModelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import net.hqhome.ai.agentz.application.AgentService;
import net.hqhome.ai.agentz.domain.user.User;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentDomainService agentDomainService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseWrapper<String> create(@RequestBody AgentRequest request) {
        var user = new User();
        user.setId("admin");
        return ResponseWrapper.success(agentService.createAgent(request, user));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/model")
    public ResponseWrapper<String> createModel(@RequestBody ModelRequest body) {
        var user = new User();
        user.setId("admin");
        return ResponseWrapper.success(agentService.createModel(body, user));
    }

}
