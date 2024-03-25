package net.hqhome.ai.agentz.domain.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
  public static String ROLE_SYSTEM = "system";
  public static String ROLE_USER = "user";
  public static String ROLE_TOOL = "tool";
  public static String ROLE_ASSISTANT = "assistant";

  private String role;
  private String content;
}
