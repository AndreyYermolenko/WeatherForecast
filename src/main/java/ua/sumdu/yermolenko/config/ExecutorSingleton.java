package ua.sumdu.yermolenko.config;

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
            throw new RuntimeException(e);
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
