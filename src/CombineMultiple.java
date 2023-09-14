import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class CombineMultiple {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return 1;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return 2;
        });

        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return 3;
        });

        System.out.println("El primero en terminar es " + CompletableFuture.anyOf(future1, future2, future3).get());

        CompletableFuture<Void> combineAll = CompletableFuture.allOf(future1, future2, future3);

        combineAll.thenAccept(__ -> System.out.println("La suma de los 3 es " + (future1.join() + future2.join() + future3.join()))).get();

        int resultado = Stream.of(future1, future2, future3)
                                .map(f -> f.join())
                                .reduce(Integer::sum)
                                .orElseThrow(() -> new RuntimeException("No pude sumar"));

        System.out.println("Suma resultante -> " + resultado);
    }
}
