package br.com.ph.phorum.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("br.com.ph.phorum.domain.repository")
@EnableTransactionManagement
public class Database {

}
