package com.github.charlyb01.music_control.config;

public enum ScrollSpeed {
    DISABLED(-1, -1),
    SLOW(4, 40),
    NORMAL(3, 25),
    FAST(2, 15);

    public final int tick;
    public final int delay;

    ScrollSpeed(int tick, int delay) {
        this.tick = tick;
        this.delay = delay;
    }
}
