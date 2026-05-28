package com.votacion.sistema_votacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(
    exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
    }
)
public class SistemaVotacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaVotacionApplication.class, args);
	}

}
