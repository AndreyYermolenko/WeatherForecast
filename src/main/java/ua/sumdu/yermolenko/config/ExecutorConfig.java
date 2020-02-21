package ua.sumdu.yermolenko.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class ExecutorConfig allows to combine threads into one group.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@Configuration
public class ExecutorConfig {

    @Bean
    public ExecutorService getExecutor(@Value("${executor.countofthread}") int threads) {
        return Executors.newFixedThreadPool(threads);
    }
}
