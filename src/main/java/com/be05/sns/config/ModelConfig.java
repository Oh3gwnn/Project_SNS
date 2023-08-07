package com.be05.sns.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {
    // Model Mapper : Change Type [DTO <-> Entity]
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}