package com.livk.autoconfigure.useragent;

import com.livk.autoconfigure.useragent.reactive.ReactiveUserAgentFilter;
import com.livk.autoconfigure.useragent.reactive.ReactiveUserAgentResolver;
import com.livk.autoconfigure.useragent.servlet.UserAgentFilter;
import com.livk.autoconfigure.useragent.servlet.UserAgentResolver;
import com.livk.autoconfigure.useragent.support.HttpUserAgentParser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * The type User agent auto configuration.
 */
@AutoConfiguration
public class UserAgentAutoConfiguration {

    /**
     * The type User agent mvc auto configuration.
     */
    @AutoConfiguration
    @RequiredArgsConstructor
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public static class UserAgentMvcAutoConfiguration implements WebMvcConfigurer {

        private final HttpUserAgentParser<?> userAgentParse;

        /**
         * Filter registration bean filter registration bean.
         *
         * @return the filter registration bean
         */
        @Bean
        public FilterRegistrationBean<UserAgentFilter<?>> filterRegistrationBean() {
            FilterRegistrationBean<UserAgentFilter<?>> registrationBean = new FilterRegistrationBean<>();
            registrationBean.setFilter(new UserAgentFilter<>(userAgentParse));
            registrationBean.addUrlPatterns("/*");
            registrationBean.setName("userAgentFilter");
            registrationBean.setOrder(1);
            return registrationBean;
        }

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
            resolvers.add(new UserAgentResolver<>(userAgentParse));
        }
    }

    /**
     * The type User agent reactive auto configuration.
     */
    @AutoConfiguration
    @RequiredArgsConstructor
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    public static class UserAgentReactiveAutoConfiguration implements WebFluxConfigurer {

        private final HttpUserAgentParser<?> userAgentParse;

        /**
         * Reactive user agent filter reactive user agent filter.
         *
         * @return the reactive user agent filter
         */
        @Bean
        public ReactiveUserAgentFilter<?> reactiveUserAgentFilter() {
            return new ReactiveUserAgentFilter<>(userAgentParse);
        }

        @Override
        public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
            configurer.addCustomResolver(new ReactiveUserAgentResolver<>(userAgentParse));
        }
    }
}
