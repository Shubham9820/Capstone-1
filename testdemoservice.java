TestDemoService.java
package com.howtodoinjava;
 
import java.util.ArrayList;
import java.util.List;
 
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.memory.InMemoryDaoImpl;
 
public class TestDemoService {
     
    static ApplicationContext applicationContext = null;
    static InMemoryDaoImpl userDetailsService = null;
     
    /**
     * Initialize the application context to re-use in all test cases
     * */
    @BeforeClass
    public static void setup()
    {
        //Create application context instance
        applicationContext = new ClassPathXmlApplicationContext("application-security.xml");
        //Get user details service configured in configuration
        userDetailsService = applicationContext.getBean(InMemoryDaoImpl.class);
    }
     
    /**
     * Test the valid user with valid role
     * */
    @Test
    public void testValidRole()
    {
        //Get the user by username from configured user details service
        UserDetails userDetails = userDetailsService.loadUserByUsername ("lokesh");
        Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        DemoService service = (DemoService) applicationContext.getBean("demoService");
        service.method();
    }
     
    /**
     * Test the valid user with INVALID role
     * */
    @Test (expected = AccessDeniedException.class)
    public void testInvalidRole()
    {
        UserDetails userDetails = userDetailsService.loadUserByUsername ("lokesh");
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthorityImpl("ROLE_INVALID"));
        Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails.getUsername(), userDetails.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        DemoService service = (DemoService) applicationContext.getBean("demoService");
        service.method();
    }
     
    /**
     * Test the INVALID user
     * */
    @Test (expected = AccessDeniedException.class)
    public void testInvalidUser()
    {
        UserDetails userDetails = userDetailsService.loadUserByUsername ("admin");
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthorityImpl("ROLE_INVALID"));
        Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails.getUsername(), userDetails.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        DemoService service = (DemoService) applicationContext.getBean("demoService");
        service.method();
    }
     
}