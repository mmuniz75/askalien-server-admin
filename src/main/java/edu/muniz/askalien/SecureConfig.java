package edu.muniz.askalien;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Configuration
@EnableWebSecurity
public class SecureConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		final String USER_GUEST = System.getenv("USER_GUEST");
	    final String USER_GUEST_PASSWORD = System.getenv("USER_GUEST_PASSWORD");
	    final String USER_ADMIN = System.getenv("USER_ADMIN");
	    final String USER_ADMIN_PASSWORD = System.getenv("USER_ADMIN_PASSWORD");

		auth.inMemoryAuthentication()
		.withUser(USER_GUEST).password(USER_GUEST_PASSWORD).roles("USER").and()
		.withUser(USER_ADMIN).password(USER_ADMIN_PASSWORD).roles("ADMIN","USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		//.anyRequest().permitAll()
		.antMatchers("/**/**").hasRole("USER")
		.antMatchers("/answer/*").hasRole("ADMIN")
		.antMatchers("/video/*").hasRole("ADMIN")
		.and().httpBasic()
		;
		
		http.csrf().disable();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
		super.configure(web);
	}

	@Bean
	protected Module module() {
	    return new Hibernate5Module();
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins("http://admin.askalien.men",
                				"http://localhost:4200",
                				"http://localhost",
                				"http://aws-website-askalien-admin-8enqo.s3-website-us-east-1.amazonaws.com",
                				"https://dtlfems0yypcj.cloudfront.net")
                .allowedMethods("GET", "POST", "OPTIONS", "PUT")
                ;
            }
        };
    }
}
