import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ExceptionHandling {

    public String catalogarPorEdad_Exceptionally(int age) throws ExecutionException, InterruptedException {
        CompletableFuture<String> catalogFuture = CompletableFuture.supplyAsync(() -> {
            if(age < 0) {
                throw new IllegalArgumentException("Age can not be negative");
            }
            if(age > 18) {
                return "Adult";
            } else {
                return "Child";
            }
        }).exceptionally(ex -> {
            System.out.println("Oops! We have an exception - " + ex.getMessage());
            return "Unknown!";
        });

        return catalogFuture.get();
    }

    public String catalogarPorEdad_Handle(int age) throws ExecutionException, InterruptedException {
        CompletableFuture<String> catalogFuture = CompletableFuture.supplyAsync(() -> {
            if(age < 0) {
                throw new IllegalArgumentException("Age can not be negative");
            }
            if(age > 18) {
                return "Adult";
            } else {
                return "Child";
            }
        }).handle((res, ex) -> {
            if (ex != null) {
                System.out.println("Oops! We have an exception - " + ex.getMessage());
                return "Unknown!";
            }
            return res;
        });

        return catalogFuture.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExceptionHandling exceptionHandling = new ExceptionHandling();

        System.out.println(exceptionHandling.catalogarPorEdad_Exceptionally(10));
        System.out.println(exceptionHandling.catalogarPorEdad_Handle(10));
        System.out.println(exceptionHandling.catalogarPorEdad_Exceptionally(20));
        System.out.println(exceptionHandling.catalogarPorEdad_Handle(20));
        System.out.println(exceptionHandling.catalogarPorEdad_Exceptionally(-1));
        System.out.println(exceptionHandling.catalogarPorEdad_Handle(-1));


    }
}
