package com.lucky.luckyproject.config;

import com.lucky.luckyproject.dto.UserDto;
import com.lucky.luckyproject.security.JwtAuthenticationFilter;
import com.lucky.luckyproject.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.saml.saml2.core.Assertion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.saml2.provider.service.authentication.OpenSaml5AuthenticationProvider;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

/**
 * 2025-2026??ìµœì‹  ?œì?: AD(SAML2) ìµœì´ˆ ë¡œê·¸??ë°?JWT ì§€???¸ì¦ ?µí•© ?¤ì •
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. ë³´ì•ˆ ê¸°ì´ˆ ?¤ì •
            .csrf(AbstractHttpConfigurer::disable) // REST API ê¸°ë°˜?´ë?ë¡?CSRF ë³´ì•ˆ??ë¹„í™œ?±í™” (? í° ë°©ì‹???¬ìš©?˜ê¸° ?Œë¬¸)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))                              // CORS ì»¤ìŠ¤?€ ?¤ì • ?ìš©
            // ?¸ì…˜???œë²„???€?¥í•˜ì§€ ?ŠëŠ” STATELESS ?•ì±… ?¤ì • (JWT ?¬ìš© ?„ìˆ˜ ì¡°ê±´)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 2. ?¸ê?(Authorization) ê·œì¹™
            .authorizeHttpRequests(auth -> auth
                // [ë¡œì»¬ ?„ìš©] ?”í˜¸??API ë³´í˜¸
                .requestMatchers("/api/crypto/**").access((authentication, context) -> {
                    String remoteAddress = context.getRequest().getRemoteAddr();
                    // [IP ê¸°ë°˜ ?œí•œ] ?”í˜¸??API??ë¡œì»¬(127.0.0.1) ?¸ì¶œë§??ˆìš©
                    boolean isLocal = new IpAddressMatcher("127.0.0.1").matches(remoteAddress) ||
                            new IpAddressMatcher("::1").matches(remoteAddress);
                    return new AuthorizationDecision(isLocal);
                })
                // ?¹ì • URL ?¨í„´???€???‘ê·¼ ?ˆìš© ê·œì¹™ ?•ì˜
                .requestMatchers("/user/**").hasRole("USER") // 'USER' ??• ??ê°€ì§??¬ìš©?ë§Œ ?‘ê·¼ ?ˆìš©
                .requestMatchers("/admin/**").hasRole("ADMIN") // 'ADMIN' ??• ??ê°€ì§??¬ìš©?ë§Œ ?‘ê·¼ ?ˆìš©
                .requestMatchers("/shared/**").hasAnyRole("USER", "ADMIN") // 'USER' ?ëŠ” 'ADMIN' ??•  ?‘ê·¼ ?ˆìš©
                // ?ˆìš© ê²½ë¡œ / ?¸ì¦ ?†ì´ ?‘ê·¼ ê°€?¥í•œ ê³µê°œ ê²½ë¡œ ?¤ì • (API, Swagger, SAML ê´€??
                .requestMatchers("/api/members/signup", "/api/members/login").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/v3/api-docs/**"
                    , "/swagger-ui/**"
                    , "/swagger-ui.html"
                    , "/login/**"
                    , "/saml2/**").permitAll()
                // ê·???API??JWT ?¸ì¦ ?„í„°ë¥??µí•´ ê±¸ëŸ¬ì§?
                .anyRequest().authenticated()
            )

//            // 3. AD(SAML2) ë¡œê·¸???„ë¡œ?¸ìŠ¤ ?¤ì •
              /* 3. AD(SAML2) ë¡œê·¸???„ë¡œ?¸ìŠ¤ ?¤ì • (?„ì¬ ì£¼ì„ ì²˜ë¦¬??
               - SAML ?¸ì¦ ?±ê³µ ??JWTë¥??ì„±?˜ì—¬ ?´ë¼?´ì–¸?¸ì—ê²?ë°œê¸‰?˜ëŠ” ë¡œì§ ?¬í•¨
               - successHandler: ?¸ì¦ ?±ê³µ ??AD ?•ë³´ë¥?ì¶”ì¶œ?˜ì—¬ JWT ? í°?¼ë¡œ ë³€?????‘ë‹µ
              */
//            .saml2Login(saml2 -> saml2
//                .authenticationManager(new ProviderManager(Collections.singletonList(saml2AuthenticationProvider())))
//                .successHandler((request, response, authentication) -> {
//                    Saml2AuthenticatedPrincipal principal = (Saml2AuthenticatedPrincipal) authentication.getPrincipal();
//                    String userId = principal.getName();
//
//                    // AD Claim URI ë§¤í•‘ ë³´ì™„ (?¤ì œ ?„ì²´ URI ?¬ìš© ê¶Œì¥)
//                    String email = principal.getFirstAttribute("schemas.xmlsoap.org");
//                    if (email == null) email = principal.getFirstAttribute("email");
//
//                    String name = principal.getFirstAttribute("schemas.xmlsoap.org");
//                    if (name == null) name = principal.getFirstAttribute("name");
//
//                    // JWT ë°œê¸‰??DTO ?ì„±
//                    UserDto userDto = UserDto.builder()
//                        .usrId(userId)
//                        .email(email != null ? email : "Unknown")
//                        .usrNm(name != null ? name : "Unknown")
//                        .roleGrpId("ROLE_USER")
//                        .build();
//
//                    String token = jwtTokenProvider.createToken(userDto);
//                    log.info("AD ?¸ì¦ ?±ê³µ [ID: {}] -> JWT ë°œê¸‰ ?„ë£Œ", userId);
//
//                    // ?‘ë‹µ ë³¸ë¬¸??? í° ë°˜í™˜
//                    response.setContentType("application/json;charset=UTF-8");
//                    response.setHeader("Authorization", "Bearer " + token);
//                    response.getWriter().write("{\"token\": \"" + token + "\", \"user\": \"" + userId + "\"}");
//                })
//            )

            // 4. [ì¤‘ìš”] JWT ?„í„° ë°°ì¹˜
            // UsernamePasswordAuthenticationFilter ?´ì „???ì–´ ? í° ê¸°ë°˜ ?¸ì¦??ë¨¼ì? ?˜í–‰?˜ê²Œ ??
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * AD(SAML2) ?‘ë‹µ ê²€ì¦?ë°??°ì´??ì¶”ì¶œ???„í•œ ?„ë¡œë°”ì´??
     * OpenSaml5ë¥??¬ìš©?˜ì—¬ SAML Assertion?ì„œ ?¬ìš©???•ë³´ë¥?ì¶”ì¶œ??
     */
    @Bean
    public AuthenticationProvider saml2AuthenticationProvider() {
        OpenSaml5AuthenticationProvider provider = new OpenSaml5AuthenticationProvider();

        // SAML ?‘ë‹µ(Response)??ë°›ì•˜?????¤í–‰??ì»¨ë²„???¤ì •
        provider.setResponseAuthenticationConverter(responseToken -> {
            // ê¸°ë³¸ SAML ?¸ì¦ ê°ì²´ ?ì„±
            Saml2Authentication authentication = OpenSaml5AuthenticationProvider
                .createDefaultResponseAuthenticationConverter()
                .convert(responseToken);

            if (authentication == null) return null;

            try {
                // SAML ?°ì´?°ì—???¤ì œ ?¬ìš©??ID(NameID)ë¥?ì¶”ì¶œ?˜ëŠ” ë¡œì§
                Assertion assertion = responseToken.getResponse().getAssertions().get(0);
                String userId = assertion.getSubject().getNameID().getValue();
                log.debug("SAML Assertion ?˜ì‹  (?¬ìš©??ID): {}", userId);
            } catch (Exception e) {
                log.error("SAML ?°ì´???Œì‹± ì¤??¤ë¥˜ ë°œìƒ: {}", e.getMessage());
            }

            return authentication;
        });
        return provider;
    }


    /**
     * CORS???€???¤ì •??ì»¤ìŠ¤?€?¼ë¡œ êµ¬ì„±?©ë‹ˆ??
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));    // ?ˆìš©???¤ë¦¬ì§?
        configuration.setAllowedMethods(List.of("*"));                          // ?ˆìš©??HTTP ë©”ì„œ??
        configuration.setAllowedHeaders(List.of("*"));                          // ëª¨ë“  ?¤ë” ?ˆìš©
        configuration.setAllowCredentials(true);                                    // ?¸ì¦ ?•ë³´ ?ˆìš©
        configuration.setMaxAge(3600L);                                             // ?„ë¦¬?Œë¼?´íŠ¸ ?”ì²­ ê²°ê³¼ë¥?3600ì´??™ì•ˆ ìºì‹œ
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);             // ëª¨ë“  ê²½ë¡œ???€?????¤ì • ?ìš©
        return source;
    }
}
