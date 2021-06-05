package com.codesoom.assignment;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 애플리케이션 메인 클래스
 */
@SpringBootApplication
public class App {
    /**
     * 서버를 시동합니다.
     *
     * @param args 주어진 인자
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * Mapper Bean을 정의하고 리턴합니다.
     *
     * @return 정의한 Mapper Bean
     */
    @Bean
    public Mapper dozerMapper() {
        return DozerBeanMapperBuilder.buildDefault();
    }
}
