package net.hqhome.ai.agentz.domain;

import org.springframework.beans.factory.annotation.Autowired;

import net.hqhome.ai.agentz.domain.event.AbstractDomainEvent;
import net.hqhome.ai.agentz.domain.event.IPublisher;
import net.hqhome.ai.agentz.domain.event.ISubscriber;

public abstract class AbstractDomainService implements IPublisher, ISubscriber {
  @Autowired
  private IPublisher publisherAdapter;
  @Autowired
  private ISubscriber subscriberAdapter;

  @Override
  public void register(ISubscriber that, Class<? extends AbstractDomainEvent> clazz) {
    subscriberAdapter.register(that, clazz);
  }

  @Override
  public void publishEvent(AbstractDomainEvent event) {
    publisherAdapter.publishEvent(event);
  }
}
