package com.example;

import java.time.LocalDate;
import java.util.Locale;

import javax.naming.SizeLimitExceededException;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.UrlPathHelper;

import com.example.date.USLocalDateFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class MeineConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(FormatterRegistry registry) {
	registry.addFormatterForFieldType(LocalDate.class, new USLocalDateFormatter());
    }

    @Bean
    public LocaleResolver localeResolver() {
	SessionLocaleResolver localeResolver = new SessionLocaleResolver();
	localeResolver.setDefaultLocale(Locale.US);
	return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {

	LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
	localeChangeInterceptor.setParamName("lang");
	return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

	registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
	EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer = new EmbeddedServletContainerCustomizer() {

	    @Override
	    public void customize(ConfigurableEmbeddedServletContainer container) {
		container.addErrorPages(new ErrorPage(MultipartException.class, "/uploadError"));
		container.addErrorPages(new ErrorPage(SizeLimitExceededException.class, "/uploadError"));
	    }
	};
	return embeddedServletContainerCustomizer;
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
	UrlPathHelper helper = new UrlPathHelper();
	// semicolon darf in der URI genutzt werden
	helper.setRemoveSemicolonContent(false);
	configurer.setUrlPathHelper(helper);
	// punkt darf in URI genutzt werden
	configurer.setUseRegisteredSuffixPatternMatch(true);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
	ObjectMapper objectMapper = builder.createXmlMapper(false).build();
	objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	return objectMapper;
    }

    // @Bean
    // public ViewResolver internalViewResolver() {
    // ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    // viewResolver.
    // viewResolver.setSuffix(".html");
    // return viewResolver;
    // }

}
