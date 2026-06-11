package com.votacion.sistema_votacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de seguridad de Spring Security.
 * Define las reglas de acceso y protección de la aplicación.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad.
     * En esta configuración se permite el acceso a todas las rutas,
     * se deshabilita la protección CSRF y se desactiva el formulario
     * de inicio de sesión predeterminado de Spring Security.
     *
     * @param http Objeto HttpSecurity utilizado para configurar la seguridad.
     * @return Cadena de filtros de seguridad configurada.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Deshabilita la protección CSRF
            .csrf(csrf -> csrf.disable())

            // Permite el acceso a todas las solicitudes HTTP
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/**").permitAll()
                    .anyRequest().authenticated()
            )

            // Deshabilita el formulario de login predeterminado
            .formLogin(form -> form.disable());

        // Construye y devuelve la configuración de seguridad
        return http.build();
    }
}