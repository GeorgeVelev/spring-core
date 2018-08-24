package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity security) throws Exception {
    security.authorizeRequests()
      .mvcMatchers("/hello").authenticated();
  }
  
//    
//    security.formLogin().loginPage("login").permitAll()
//      .and()
//      .logout().logoutSuccessUrl("/").permitAll();
//  }
//  
//  public void configure(WebSecurity webSecurity) throws Exception { 
//    //webSecurity.
//  }
//  
//  @Autowired
//  public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception { 
//    authBuilder.inMemoryAuthentication()
//      .withUser("admin").password("admin").roles("ADMIN");
//  }
}
