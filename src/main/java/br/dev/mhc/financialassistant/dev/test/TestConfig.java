package br.dev.mhc.financialassistant.dev.test;

import br.dev.mhc.financialassistant.dev.test.services.TestDBSeedService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    private final TestDBSeedService dbSeedService;

    public TestConfig(TestDBSeedService dbSeedService) {
        this.dbSeedService = dbSeedService;
    }

    @Bean
    public boolean instantiateDataBase() {
        dbSeedService.databaseSeeding();
        return true;
    }
}
