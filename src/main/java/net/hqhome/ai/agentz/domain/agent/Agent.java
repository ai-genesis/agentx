package net.hqhome.ai.agentz.domain.agent;

import lombok.Data;

import java.util.List;
// import net.hqhome.ai.agentz.domain.Thread;
// import net.hqhome.ai.agentz.domain.user.User;


@Data
public class Agent {
  protected String id;
  protected String name;
  protected String description;
  protected Model model;
  protected ModelParameter modelParameter;
  protected String creatorId;
  protected AgentType type;
  protected String systemMessage;
  // chat just response
  // rag 识别意图、向量召回，结果重排，模型回答（脚本形态，workflow？）（这几个是工具吗？应该是的，agent如何用脚本进行编排dag?）
  // thread add message --message added--> model-completion? no, script
  // react 系统prompt, 生成工具（fire an event），工具执行(执行完，再fire event)，agent根据结果执行下一步，执行完继续生成下一步任务

  protected List<Task> tasks;

  public String run(IAgentResource agentResource, List<ChatMessage> messages) {
//    var res =  model.chatCompletion();
//    Task task = parse(res);

    //rag filter message from messages, check status if a Task = new TaskB();

//    while (task != FinishTask) {
//      var r = task.run();
//      thread.add(r);
//    }


    // model.xxx();
    return null;
  }

  public void debug() {

  }

}
