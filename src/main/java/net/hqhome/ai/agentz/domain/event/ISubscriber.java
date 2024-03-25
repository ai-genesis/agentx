package net.hqhome.ai.agentz.domain.event;

public interface ISubscriber {
  default void register(ISubscriber that, Class<? extends AbstractDomainEvent> clazz) {
    throw new UnsupportedOperationException("Unimplemented method 'listen'");
  }
  default void handle(AbstractDomainEvent event) {
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }
}
