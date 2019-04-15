package cug.wfh.wenda.configuration;

import cug.wfh.wenda.intercepter.PassportIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WendaWebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    PassportIntercepter passportIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportIntercepter);
        super.addInterceptors(registry);
    }
}
