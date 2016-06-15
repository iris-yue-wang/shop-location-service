package org.shop.location.application;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"org.shop.location.services"})
@EnableAutoConfiguration
@EnableCaching
public class UnitTestConfiguration {

}
