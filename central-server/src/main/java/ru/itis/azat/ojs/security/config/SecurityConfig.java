package ru.itis.azat.ojs.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.itis.azat.ojs.security.filter.TokenAuthFilter;

@ComponentScan("ru.itis.azat.ojs")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Qualifier("authProvider")
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Qualifier("tokenAuthenticationProvider")
    @Autowired
    private AuthenticationProvider tokenAuthProvider;

    @Autowired
    private TokenAuthFilter tokenAuthFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter  filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http.csrf().disable();

        http
                .addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/**").hasAuthority("USER")
                .antMatchers("/profile/**").hasAuthority("USER")
                .antMatchers("/tasks/create").hasAuthority("CREATOR")
                .antMatchers("/tasks/*").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/sign-up").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .permitAll()
                    .usernameParameter("login")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .permitAll();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(tokenAuthProvider);
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}