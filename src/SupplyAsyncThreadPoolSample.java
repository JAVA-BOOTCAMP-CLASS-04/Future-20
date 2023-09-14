import java.util.concurrent.*;

public class SupplyAsyncThreadPoolSample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(10);
        // Run a task specified by a Supplier object asynchronously
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            printCurrentThread("Termine de ejecutar");
            return "Result of the asynchronous computation";
        }, executor);


// Block and get the result of the Future
        String result = future.get();
        printCurrentThread(result);

        executor.shutdownNow();
    }

    private static void printCurrentThread(String text) {
        System.out.println(Thread.currentThread().getName() + " - " + text);
    }
}
