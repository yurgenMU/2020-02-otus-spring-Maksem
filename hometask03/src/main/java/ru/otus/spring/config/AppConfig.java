package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.io.IOService;
import ru.otus.spring.io.IOServiceImpl;
import ru.otus.spring.loader.ResourceFileDataLoader;
import ru.otus.spring.service.SettingsService;


@Configuration
@EnableConfigurationProperties(Props.class)
public class AppConfig {

    @Bean
    MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/i18n/bundle");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    IOService ioService() {
        return new IOServiceImpl(System.in, System.out);
    }

    @Bean
    ResourceFileDataLoader languagesDataLoader(Props props) {
        return new ResourceFileDataLoader(props.getLanguagesFile());
    }

    @Bean
    ResourceFileDataLoader questionsDataLoader(SettingsService settingsService) {
        return new ResourceFileDataLoader(settingsService);
    }

}
