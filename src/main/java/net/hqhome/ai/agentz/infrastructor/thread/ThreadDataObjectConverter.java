package net.hqhome.ai.agentz.infrastructor.thread;

import net.hqhome.ai.agentz.domain.thread.Message;
import net.hqhome.ai.agentz.domain.thread.Thread;
import net.hqhome.ai.agentz.domain.thread.ThreadStatus;
import net.hqhome.ai.agentz.infrastructor.thread.dataobject.MessageDO;
import net.hqhome.ai.agentz.infrastructor.thread.dataobject.ThreadDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ThreadDataObjectConverter {
    ThreadDataObjectConverter INSTANCE = Mappers.getMapper( ThreadDataObjectConverter.class );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "threadId", source = "id")
    @Mapping(target = "status", expression = "java( thread.getStatus().getCode() )")
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "modifiedTime", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    ThreadDO toDO(Thread thread);

    @InheritInverseConfiguration
    Thread toDomain(ThreadDO threadDo);

    Message toDomain(MessageDO messageDO);

    List<Message> toDomain(List<MessageDO> messageDOList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "threadId", source = "threadId")
    MessageDO toDO(Message message, String threadId);

    default ThreadStatus map(Integer value) {
        return ThreadStatus.of(value);
    }
}
