package org.example.Configuration;

import org.example.Services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.RequestMatcher;


@Configuration
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService userService;

    /**
     * use basicAuth for REST
     * should remove for GraphQL
     */


    //RestConfigure
    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http//.csrf().disable();
                .authorizeRequests()
               // .antMatchers("/graphql","/api/plants").permitAll()
                //.authorizeRequests()
                .antMatchers("/registration", "/login", "/api/plants")
                .permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and()
                //.csrf().disable().antMatcher("/api/plants")
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and().logout().permitAll();
    }*/
    /**
     * GraphQL Config
     */



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/graphql").permitAll()
                .and()
                .requestCache()
                .requestCache(new NullRequestCache())
                .and()
                .headers()
                .frameOptions().sameOrigin() // needed for H2 web console
                .and()
                .sessionManagement()
                .maximumSessions(1);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

}
