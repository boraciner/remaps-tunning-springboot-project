package mg.remaps.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import mg.remaps.service.Udservice.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    String[] resources = new String[]{
    		"/assets/**","/include/**","/css/**","/icons/**","/img/**","/js/**","/layer/**"
    };
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
    	
    	 http
    	 .csrf().disable()
         .authorizeRequests()
	        .antMatchers(resources).permitAll()  
	        .antMatchers("/","/index").permitAll()
	        .antMatchers("/login").permitAll()
	        .antMatchers("/page-register").permitAll()
	        .antMatchers("/page_register_select_brand").permitAll()
	        .antMatchers("/page_register_select_model").permitAll()
	        .antMatchers("/page_register_select_year").permitAll()
	        .antMatchers("/page_register_select_engine").permitAll()
	        .antMatchers("/page-login").permitAll()
	        .antMatchers("/page-pricing-standard").access("hasRole('USER') or hasRole('ADMIN')")
	        .antMatchers("/contact-1").access("hasRole('ADMIN')")
	        .antMatchers("/contact-2").access("hasRole('USER') or hasRole('ADMIN')")
	        .antMatchers("/user*").access("hasRole('USER') or hasRole('ADMIN')")
             .anyRequest().authenticated()
             .and()
         .formLogin()
             .loginPage("/page-login")
             .permitAll()
             .defaultSuccessUrl("/")
             .failureUrl("/login?error=true")
             .usernameParameter("username")
             .passwordParameter("password")
             .and()
         .logout();
             
    }
    
    
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        return bCryptPasswordEncoder;
    }
	
    @Autowired
    UserDetailsServiceImpl userDetailsService;
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
 
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());     
    }
}