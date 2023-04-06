package com.example.sns.util;

public record CursorRequest(Long key, Long size) {
    public static final Long NONE_KEY = -1L;

    public CursorRequest next(Long key) {
        return new CursorRequest(key,size);
    }
}
