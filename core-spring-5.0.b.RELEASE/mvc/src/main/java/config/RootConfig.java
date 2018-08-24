package config;

import javax.annotation.PostConstruct;

import com.samskivert.mustache.Mustache.Compiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import accounts.AccountManager;
import accounts.internal.JpaAccountManager;

/**
 * Imports Rewards application from rewards-db project.
 */
@Configuration
@Import({AppConfig.class,DbConfig.class,SecurityConfig.class})
@EnableTransactionManagement
public class RootConfig {

  @Autowired
  private Environment environment;
  
  public RootConfig() {
    System.out.println("hello from RootConfig constructor");
  }
  
  @PostConstruct
  public void postConstruct() { 
    System.out.println("hello from RootConfig postConstruct");
    System.out.println("Environment is " + environment);
    System.out.println("prefix is " + environment.getProperty("spring.mustache.prefix"));
  }
  
	/**
	 * A new service has been created for accessing Account information.
	 * 
	 * @return The new account-manager instance.
	 */
	@Bean
	public AccountManager accountManager() {
	  System.out.println("hello from accountManager");
		return new JpaAccountManager();
	}
	
	/**
	 * Default Mustache view-resolver. Has no prefix or suffix, so you have to
	 * provide full path to HTML templates.
	 * 
	 * @param mustacheCompiler
	 * @return
	 */

//	// TODO-07b: Entering the full path of templates is tedious. This view-resolver
//	//           needs configuring. But we will let Spring Boot do it all, so comment
//	//           out this bean, then look for TODO-07c in application.properties.
//	//
	@Bean
	public MustacheViewResolver mustacheViewResolver(Compiler mustacheCompiler) {
		MustacheViewResolver resolver = new MustacheViewResolver(mustacheCompiler);
		
		//Experiment to see if I can hook up the existing resolver using app.props without Spring boot.  Success!! 
		resolver.setPrefix(environment.getProperty("spring.mustache.prefix"));
		resolver.setSuffix(environment.getProperty("spring.mustache.suffix"));
		resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
		return resolver;
	}
}
