package com.fit2cloud.selfService.sdk.common;

import com.fit2cloud.selfService.sdk.common.auth.AuthError;
import com.fit2cloud.selfService.sdk.common.auth.JwtAuthenticationProvider;
import com.fit2cloud.selfService.sdk.common.auth.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

import static org.springframework.http.HttpMethod.POST;


@EnableWebSecurity
@Configurable
public class AppConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtTokenFilter jwtTokenFilter;

    @Resource
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Resource
    private AuthError authError;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(POST ,"/user/register", "/token") // 不拦截
                .permitAll().antMatchers("/**/*") // 允许拦截
                .authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authError)
                .accessDeniedHandler(authError);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    /**
     * allow cross origin requests
     * 跨域相关
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}
