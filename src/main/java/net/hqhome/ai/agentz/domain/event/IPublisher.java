package net.hqhome.ai.agentz.domain.event;

public interface IPublisher {
  void publishEvent(AbstractDomainEvent event);
}
