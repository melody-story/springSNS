package com.example.sns.util;

public record CursorRequest(Long key, int  size) {
    public static final Long NONE_KEY = -1L;

    public Boolean hasKey() {
        // 카를 줄수도 있어 안줄수도 있다. 정책이 변경될수있기 떄문에 서비스단에서는 검증하지 않는다.
        return key != null;
    }
    public CursorRequest next(Long key) {
        return new CursorRequest(key,size);
    }
}
