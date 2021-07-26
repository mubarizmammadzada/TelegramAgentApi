package com.example.telegramagentapi.config;

import com.example.telegramagentapi.security.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;

@Configuration
public class BaseConfig implements WebMvcConfigurer {
    final TokenInterceptor productServiceInterceptor;

    public BaseConfig(TokenInterceptor productServiceInterceptor){
        this.productServiceInterceptor = productServiceInterceptor;
    }
    @Override
    public  void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(productServiceInterceptor);
    }
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("telegrammailsender@gmail.com");
        mailSender.setPassword("12345678m@");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
