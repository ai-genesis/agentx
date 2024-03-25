package net.hqhome.ai.agentz.domain.thread;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ThreadStatus {
    INVALID(-1),
    CREATED(0),
    IDLE(1),
    RUNNING(2);


    private final int code;

    ThreadStatus(int code) {
        this.code = code;
    }

    public static ThreadStatus of(int code) {
        return Arrays.stream(ThreadStatus.values())
                .filter(v -> v.code == code)
                .findFirst()
                .orElse(ThreadStatus.INVALID);
    }
}
