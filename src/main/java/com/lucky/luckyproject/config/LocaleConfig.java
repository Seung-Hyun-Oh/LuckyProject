package com.lucky.luckyproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * 다국어 설정을 위한 config
 */
@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        // 브라우저의 Accept-Language 헤더를 기준으로 언어 결정
        AcceptHeaderLocaleResolver slr = new AcceptHeaderLocaleResolver();
        slr.setDefaultLocale(Locale.KOREA); // 기본값 한국어
        return slr;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("messages/messages"); // 파일명 prefix
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true); // 메시지 키가 없을 때 에러 대신 키를 그대로 출력
        // 3. 로케일 정보를 찾지 못했을 때 시스템 기본 로케일 사용 여부
        source.setFallbackToSystemLocale(true);
        source.setCacheSeconds(3600); // 2025년 성능 최적화를 위한 캐시 설정
        return source;
    }
}
