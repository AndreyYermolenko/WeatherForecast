package ua.sumdu.yermolenko.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.sumdu.yermolenko.services.impl.WeatherStackServiceImpl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class ExecutorSingleton implements the singleton pattern
 * and allows you to combine threads into one group.
 *
 * @author AndreyYermolenko
 * Created on 31.01.2020
 */
public class ExecutorSingleton {
    private final static Logger logger = LogManager.getLogger(WeatherStackServiceImpl.class);

    private static int countOfThread;
    static {
        File file = new File("src" + File.separator
                + "main" + File.separator
                + "resources" + File.separator
                + "application.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            logger.error("ExecutorSingleton problem.", e);
        }
        countOfThread = Integer.parseInt(properties.getProperty("executor.countofthread"));
    }
    private static volatile ExecutorService executor;

    private ExecutorSingleton() {}

    public static ExecutorService getExecutor() {
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
