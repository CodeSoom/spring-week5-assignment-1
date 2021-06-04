package com.codesoom.assignment;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 스프링 부트 앱
 */
@SpringBootApplication
public class App {
    /**
     * 스프링 부트 애플리케이션을 시동합니다.
     *
     * @param args 수프링 부트 애플리케이션에 주어진 인자
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * DozerMapper 객체를 Java Bean으로 만듭니다.
     *
     * @return Java Bean으로 쓸 DozerMapper 객체
     */
    @Bean
    public Mapper dozerMapper() {
        return DozerBeanMapperBuilder.buildDefault();
    }
}
