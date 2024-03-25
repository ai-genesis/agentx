package net.hqhome.ai.agentz.infrastructor.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import net.hqhome.ai.agentz.domain.event.AbstractDomainEvent;
import net.hqhome.ai.agentz.domain.event.ISubscriber;

@Primary
@Component
public class SubscriberAdapter implements ISubscriber {

  private Map<Class<? extends AbstractDomainEvent>, List<ISubscriber>> subscribers = new ConcurrentHashMap<>();

  @EventListener
  public void onApplicationEvent(AbstractDomainEvent event) {
    subscribers.getOrDefault(event.getClass(), new ArrayList<>()).forEach(sub -> sub.handle(event));
  }

  @Override
  public void register(ISubscriber that, Class<? extends AbstractDomainEvent> clazz) {
    subscribers.compute(clazz, (k, v) -> {
      if (v == null) {
        return Lists.newArrayList(that);
      }

      v.add(that);
      return v;
    });
  }
}
