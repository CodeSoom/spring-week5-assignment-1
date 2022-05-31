package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ImageDemo extends MediaDemo{
    private String url;
}
