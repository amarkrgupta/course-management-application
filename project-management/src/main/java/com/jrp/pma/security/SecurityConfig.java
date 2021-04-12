/*
 * The table being used for the operation
 * 
 * CREATE SEQUENCE IF NOT EXISTS user_accounts_seq;

CREATE TABLE IF NOT EXISTS user_accounts (
	user_id BIGINT NOT NULL DEFAULT nextval('user_accounts_seq') PRIMARY KEY,
	username varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	role varchar(255),
	enabled boolean NOT NULL
);
 */


package com.jrp.pma.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration  //Adding it to Spring context
@EnableWebSecurity //Since we are dealing with web-security
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource datasource; //Spring is smart enough to automatically the wire the db in use, i.e. if properties file is using H2 then H2,etc.
	
	@Autowired
	BCryptPasswordEncoder bCryptEncoder;
	
	//To build our authentication rules
	//Override that configure method which has AuthenticationManagerBuilder class object as argument
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * Here, we can define our authentication mechanisms
		 * 1. In-memory: If we want to keep our username and pwd in the code
		 * 2. JDBC: If we want to extract our username and pwd from a database
		 * 3. LDAP:
		 * 4. Spring Data JPA also we can use
		 */
		
		//Now here we are using in-memory authentication provider to build our rules
		
//		auth.inMemoryAuthentication()
//		.withUser("myUser")
//			.password("pass")
//			.roles("USER")
//		.and()
//		.withUser("amar")
//			.password("pass2")
//			.roles("USER")
//		.and()
//		.withUser("managerUser")
//			.password("pass3")
//			.roles("ADMIN");
//		
		//jdbc backed authentication
		auth.jdbcAuthentication().dataSource(datasource)
		//.withDefaultSchema() - to generate default schema of two tables: users and authorities
		//Now for customized table and queries do the following
		.usersByUsernameQuery("select username,password,enabled "
				+ "from user_accounts where username = ?")
		.authoritiesByUsernameQuery("select username, role from user_accounts where username = ?")
		.passwordEncoder(bCryptEncoder); //This encoder is being used to decode to extract the password from the query above
		//we'll provide a form where users enter username and password and that would get encrypted and stored in the database
		//for that we'll make again use of that password encoder object
//		.withUser("myUser") //only needed for in-memory authentication
//			.password("pass")
//			.roles("USER")
//		.and()
//		.withUser("amar")
//			.password("pass2")
//			.roles("USER")
//		.and()
//		.withUser("managerUser")
//			.password("pass3")
//			.roles("ADMIN");
		
		
		//You can also chain the users using .and()
		
		/*
		 * Just writing the above code will give you an error:
		 * There is no PasswordEncoder mapped for the id "null"
		 * The reason by being by default spring expects the password to be encoded and not to expose it in the code.
		 * So, we have to define the bean, that encodes the password or atleast fools the spring-framework that it is doing the encoding   
		 */				
	}
	
/*
 * 	This was just dummy encryption
	Now the actual encoder implementation can be done here or in another class.
	We'll do it in another class and implement B-crypt encoder.
 */
	
//	@Bean
//	public PasswordEncoder getPasswordEncoder()
//	{
//		//The strike line is coming since the method is deprecated
//		//We are not encoding the password, but we are fooling the spring-framework that we are doing so.
//		return NoOpPasswordEncoder.getInstance();
//	}
	
	//You can configure authorization by overriding the configure method with HttpSecurity as args
	protected void configure(HttpSecurity http) throws Exception
	{
		//http.csrf().disable();  //Uncomment when using post api request
		//The auth rules are of decreasing order, i.e. the one at the top is of the highest preference and so on.
		http.authorizeRequests()
			.antMatchers("/projects/new").hasRole("ADMIN") //to match the end-points with the roles
			.antMatchers("/projects/save").hasRole("ADMIN")
			.antMatchers("/employees/new").hasRole("ADMIN")
			.antMatchers("/employees/save").hasRole("ADMIN")
			.antMatchers("/","/**").permitAll().and().formLogin();
		//	.antMatchers("/h2-console/**").permitAll()
		//	.antMatchers("/").authenticated().and().formLogin(); 
		//.authenticated() -regardless of their role, they will be authenticated to access the home-page of the web-site.
		//.formlogin() -gives the default form-login page. We can also customize it by specifying the file having the customized login form
		
//		//We are disabling these to access the h2-console
//		http.csrf().disable();
//		http.headers().frameOptions().disable();
	}
	
	
	

	
}
