package net.hqhome.ai.agentz.infrastructor.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import net.hqhome.ai.agentz.domain.event.AbstractDomainEvent;
import net.hqhome.ai.agentz.domain.event.IPublisher;

@Primary
@Component
public class PublisherAdapter implements IPublisher {

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void publishEvent(AbstractDomainEvent event) {
    applicationEventPublisher.publishEvent(event);
  }

}
