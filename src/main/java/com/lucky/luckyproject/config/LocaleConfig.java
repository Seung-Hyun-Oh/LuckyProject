package com.lucky.luckyproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * ?¤êµ­???¤ì •???„í•œ config
 */
@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        // ë¸Œë¼?°ì???Accept-Language ?¤ë”ë¥?ê¸°ì??¼ë¡œ ?¸ì–´ ê²°ì •
        AcceptHeaderLocaleResolver slr = new AcceptHeaderLocaleResolver();
        slr.setDefaultLocale(Locale.KOREA); // ê¸°ë³¸ê°??œêµ­??
        return slr;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("messages/messages"); // ?Œì¼ëª?prefix
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true); // ë©”ì‹œì§€ ?¤ê? ?†ì„ ???ëŸ¬ ?€???¤ë? ê·¸ë?ë¡?ì¶œë ¥
        // 3. ë¡œì????•ë³´ë¥?ì°¾ì? ëª»í–ˆ?????œìŠ¤??ê¸°ë³¸ ë¡œì????¬ìš© ?¬ë?
        source.setFallbackToSystemLocale(true);
        source.setCacheSeconds(3600); // 2025???±ëŠ¥ ìµœì ?”ë? ?„í•œ ìºì‹œ ?¤ì •
        return source;
    }
}
