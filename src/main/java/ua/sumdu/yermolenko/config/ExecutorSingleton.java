package ua.sumdu.yermolenko.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class ExecutorSingleton allows to combine threads into one group.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
@Component
public class ExecutorSingleton {
    @Value("${executor.countofthread}")
    private int countOfThread;
    private volatile ExecutorService executor;

    public ExecutorService getExecutor() {
        if (executor == null) {
            synchronized (ExecutorSingleton.class) {
                if (executor == null) {
                    executor = Executors.newFixedThreadPool(countOfThread);
                }
            }
        }
        return executor;
    }
}
