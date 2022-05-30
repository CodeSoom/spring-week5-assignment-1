package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * '미디어 기반 시연' Root Value Object
 * <p>
 * All Known Extending Classes:
 * ImageDemo
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class MediaDemo {
    /**
     * '미디어 기반 시연'이 업로드된 URL
     */
    private String url;
}
