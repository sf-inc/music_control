package com.github.charlyb01.music_control.config;

public enum ScrollSpeed {
    DISABLED(0),
    SLOW(4),
    NORMAL(3),
    FAST(2);

    public final int tick;

    ScrollSpeed(int tick) {
        this.tick = tick;
    }
}
