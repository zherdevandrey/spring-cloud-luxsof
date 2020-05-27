package com.example.cardservice;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.stream.StreamSupport;

@RestController
@Slf4j
public class CardRest {

    @Autowired
    Environment environment;

    @Autowired
    private CardNumberGenerator generator;

    @RequestMapping("/create")
    public String createNewCard() {
        return generator.generate();
    }

    @PostConstruct
    public void test(){
        log.info("====== Environment and configuration ======");
        log.info("Active profiles: {}", Arrays.toString(environment.getActiveProfiles()));
        final MutablePropertySources sources = ((AbstractEnvironment) environment).getPropertySources();
        StreamSupport.stream(sources.spliterator(), false).filter(ps -> ps instanceof EnumerablePropertySource)
                .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames()).flatMap(Arrays::stream).distinct()
                .forEach(prop -> {
                    Object resolved = environment.getProperty(prop, Object.class);
                    if (resolved instanceof String) {
                        log.info("{} - {}", prop, environment.getProperty(prop));
                    } else {
                        log.info("{} - {}", prop, "NON-STRING-VALUE");
                    }
                });
        log.debug("===========================================");
    }
}
