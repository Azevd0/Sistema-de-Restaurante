package com.br.davyson.GerenciamentoPedidos.profile;

import com.br.davyson.GerenciamentoPedidos.database.DbApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestProfile {

    public DbApi dbApi;

    public TestProfile(DbApi dbApi) {
        this.dbApi = dbApi;
    }

    @Bean
    public CommandLineRunner instanciarMenu() {
        return args -> {
            dbApi.instanciarMenu();
        };
    }
    @Bean
    public CommandLineRunner instanciarCartoes(){
        return args -> dbApi.instanciarCartoes();
    }
}
