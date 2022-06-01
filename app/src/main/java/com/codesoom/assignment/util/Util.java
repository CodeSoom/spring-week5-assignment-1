package com.codesoom.assignment.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

@Component
public class Util {
	@Bean
	public Mapper dozerMapper() {
		return DozerBeanMapperBuilder.buildDefault();
	}
}
