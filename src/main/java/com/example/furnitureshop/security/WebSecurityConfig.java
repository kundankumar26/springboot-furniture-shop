package com.example.furnitureshop.security;

import com.example.furnitureshop.security.jwt.AuthEntryPointJwt;
import com.example.furnitureshop.security.jwt.AuthTokenFilter;
import com.example.furnitureshop.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests().antMatchers("/**").permitAll()
//                .antMatchers("/api/test/**").permitAll()
//                .antMatchers("/employee/**").hasAnyRole("EMPLOYEE")
//                .antMatchers("/vendor/").hasAnyRole("ROLE_VENDOR")
//                .antMatchers("/admin/**").hasAnyRole("ADMIN")
//                .anyRequest().authenticated();
        http.authorizeRequests((authorizeRequests) -> {
                    authorizeRequests
                            .antMatchers("/employee/**").hasAnyRole("EMPLOYEE")
                            .antMatchers("/wishlist").hasAnyRole("EMPLOYEE")
                            .antMatchers("/cart").hasAnyRole("EMPLOYEE")
                            .antMatchers("/vendor/**").hasRole("VENDOR")
                            .antMatchers("/admin/**").hasRole("ADMIN");
                    })
                .authorizeRequests()
                .antMatchers("/products").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/signin").permitAll().and()
                .authorizeRequests().anyRequest().permitAll().and()
                .cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
