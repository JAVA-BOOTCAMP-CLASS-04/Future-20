import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThenAcceptAndRun {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // thenAccept() example
        ExecutorService executor = Executors.newFixedThreadPool(2);

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            printCurrentThread("Genero el valor y lo devuelvo");
            return 10;
        }, executor);

        future1.thenAccept(value -> {
            printCurrentThread("Utilizo el valor " + value);
        }).thenAcceptAsync(value -> printCurrentThread("Vuelvo a utilizar el valor " + value)).get();

        // thenRun() example
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            printCurrentThread("Genero el valor y lo devuelvo");
            return 10;
        }, executor);

        future2.thenRun(() -> {
            printCurrentThread("Ejecuto una tarea");
        }).get();

        executor.shutdownNow();
    }

    private static void printCurrentThread(String text) {
        System.out.println(Thread.currentThread().getName() + " - " + text);
    }
}
