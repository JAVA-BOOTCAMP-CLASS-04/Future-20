import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class IsPromise {

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture.runAsync(() -> {
            try {
                System.out.println("Inicia ejecucion de promesa");
                TimeUnit.SECONDS.sleep(3);
                System.out.println("Finaliza ejecucion de promesa");
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }).thenAccept(__ -> System.out.println("Finalizo con exito ... ejecuto siguiente tarea."))
          .exceptionally(exc -> {
              System.out.println("Hubo un error");
              return null;
          });

        for (int i = 0; i < 10; i++) {
            System.out.println("La ejecución principal continua " + (i + 1));
            TimeUnit.SECONDS.sleep(1);
        }

    }
}
