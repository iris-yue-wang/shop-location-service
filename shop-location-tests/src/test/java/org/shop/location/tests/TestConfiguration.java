package org.shop.location.tests;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@EnableAutoConfiguration
@ComponentScan("org.shop.location.services")
@EnableCaching
@Profile("tests")
public class TestConfiguration {

}
