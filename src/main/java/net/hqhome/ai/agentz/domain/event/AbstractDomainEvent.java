package net.hqhome.ai.agentz.domain.event;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class AbstractDomainEvent {
  private String id;

  public AbstractDomainEvent() {
    id = UUID.randomUUID().toString();
  }
}
