package com.barry.bigdata.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@PropertySources({@PropertySource("classpath:application.properties")})
public class SpringConfig {

  private static final Logger logger = LoggerFactory.getLogger(SpringConfig.class);
  private static ApplicationContext staticApplicationContext;

  @Autowired
  private ApplicationContext applicationContext;

  public static ApplicationContext getApplicationContext() {
    return staticApplicationContext;
  }

  @PostConstruct
  public void initialize() {
    logger.info("SpringConfig initialize, applicationContext == null is {}",
            applicationContext == null);
    staticApplicationContext = applicationContext;
  }

  @Bean(name = "jsonTemplate")
  public MappingJackson2JsonView getJsonTemplate() {
    MappingJackson2JsonView jsonTemplate = new MappingJackson2JsonView();
    jsonTemplate.setObjectMapper(getObjectMapper());
    return jsonTemplate;
  }

  @Bean(name = "objectMapper")
  ObjectMapper getObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    //mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    // mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
    return mapper;
  }

  @PreDestroy
  public void destroy() {
    logger.info("SpringConfig destroy");
  }

}
