import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import java.util.*;

public class MultithreadingApiTesting {

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        log("Calling two APIs in parallel...\n");

        // Start both tasks asynchronously using CompletableFuture
        CompletableFuture<String> weatherFuture = getWeatherAsync();
        CompletableFuture<Double> stockPriceFuture = getStockPriceAsync();

        // Wait for both tasks to complete
        CompletableFuture.allOf(weatherFuture, stockPriceFuture).join();

        // Get the results
        String weatherResult = weatherFuture.get();
        double stockResult = stockPriceFuture.get();

        log("✅ Weather API Result: " + weatherResult);
        log("✅ Stock API Result: $" + stockResult);

        long endTime = System.currentTimeMillis();
        log("Total Time: " + (endTime - startTime) + " ms");
    }

    // Simulate async weather API
    public static CompletableFuture<String> getWeatherAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000); // Simulate 5 sec delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            log("✅ Delay and Returned Completed getWeatherAsync");
            return "Sunny, 75°F";
        });
    }

    // Simulate async stock price API
    public static CompletableFuture<Double> getStockPriceAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000); // Simulate 2 sec delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            log("✅ Delay and Returned Completed getStockPriceAsync");
            return Math.round((100 + Math.random() * 900) * 100.0) / 100.0;
        });
    }

    // Utility logger with timestamp
    public static void log(String message) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        System.out.println("[" + time + "] " + message);
    }
}
