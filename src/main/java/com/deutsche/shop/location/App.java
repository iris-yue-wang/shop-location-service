package com.deutsche.shop.location;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

/**
 * Shop Location Service Application
 * 
 * @author Yue Wang
 *
 */
@SpringBootApplication
@EnableCaching
public class App 
{
    public static void main( String[] args )
    {
    	ApplicationContext ctx = SpringApplication.run(App.class, args);
    }
}
