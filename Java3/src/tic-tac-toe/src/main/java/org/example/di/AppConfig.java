
package org.example.di;

import org.example.datasource.repository.GameRepository;
import org.example.datasource.repository.InMemoryGameRepository;
import org.example.domain.service.GameService;
import org.example.domain.service.impl.GameServiceImpl;
import org.example.web.mapper.GameWebMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    //@Bean
    //public GameRepository gameRepository() {
        //return new InMemoryGameRepository(); //
    //}

    @Bean
    public GameService gameService() {
        return new GameServiceImpl();
    }

    @Bean
    public GameWebMapper gameWebMapper(GameService gameService) {
        return new GameWebMapper(gameService);
    }
}