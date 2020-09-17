package  br.com.pets.config;

import br.com.pets.service.impl.CurrentUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@ComponentScan("br.com.pets")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CurrentUserDetailsService currentUserDetailsService;

    @Autowired
    public SecurityConfig(CurrentUserDetailsService currentUserDetailsService) {
        this.currentUserDetailsService = currentUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/static/**").permitAll()
                // Permissão para console do banco de dados H2
                .antMatchers("/h2/**","/console/**").permitAll()
                .antMatchers("/", "/auth/**", "/login").permitAll()
                .antMatchers("/CadastroClientes/**").permitAll()
                .antMatchers("/Integracao/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .failureUrl("/auth/login?error=true")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .defaultSuccessUrl("/dashboard")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/auth/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling().accessDeniedPage("/auth/denied");

                // Remover em produção, apenas em fase de testes com banco de dados H2
                http.csrf().disable();
                http.headers().frameOptions().disable();
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(currentUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}