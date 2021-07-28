package com.security.security.security;
import com.security.security.auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.security.security.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder ;
    private final ApplicationUserService applicationUserService;
    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }
    protected void configure( HttpSecurity http) throws Exception {
    http
//            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/","index","/css/*","/js/*").permitAll()
            .antMatchers("/api/**").hasRole(STUDENT.name())
//            .antMatchers(HttpMethod.DELETE ,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//            .antMatchers(HttpMethod.POST ,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//            .antMatchers(HttpMethod.PUT ,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//            .antMatchers(HttpMethod.GET , "/management/api/**").hasAnyRole(ADMIN.name() , ADMINTRAINE.name())
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/courses",true)
                    .passwordParameter("password")
                    .usernameParameter("username")
            .and()
            .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("ahmed123")
                    .rememberMeParameter("remember-me")
            .and()
            .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout" , "GET"))//if Csrf is note disable that mean you should delete this line
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID","Idea-25fb9f2e")
                    .logoutSuccessUrl("/login");
    //            .httpBasic();
//            .anyRequest()
    }
//    @Bean
//    protected UserDetailsService userDetailsService() {
//     UserDetails AhmedUser =  User.builder()
//                .username("ahmed")
//                .password(passwordEncoder.encode("ahmed123"))
//                .authorities(STUDENT.getGrantedAutorities())
//                .build();
//     UserDetails LindaUser = User.builder()
//                 .username("linda")
//                 .password(passwordEncoder.encode("linda123"))
//                 .authorities(ADMIN.getGrantedAutorities())
//                 .build();
//     UserDetails tomUser = User.builder()
//                 .username("tom")
//                 .password(passwordEncoder.encode("tom123"))
//                 .authorities(ADMINTRAINE.getGrantedAutorities())
//                 .build();
//      return new InMemoryUserDetailsManager(
//                  AhmedUser,
//                  LindaUser,
//                  tomUser
//
//                     );
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    auth.authenticationProvider(daoAuthenticationProvider());
}
      @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider(){
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(passwordEncoder);
            provider.setUserDetailsService(applicationUserService);
            return provider ;

        }


}

