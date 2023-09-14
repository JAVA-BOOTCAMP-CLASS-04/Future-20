import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CombineFutures {

    class User {
        private int id;
        private double cardId;

        public User(int id) {
            this.setId(id);
            Random r = new Random();
            this.setCardId(r.nextDouble());
        }

        public int getId() { return id;}

        public void setId(int id) {
            this.id = id;
        }

        public double getCardId() { return cardId;}

        public void setCardId(double cardId) {
            this.cardId = cardId;
        }
    }

    CompletableFuture<User> getUsersDetail(int userId) {
        return CompletableFuture.supplyAsync(() -> {
            return new User(userId);
        });
    }

    CompletableFuture<Double> getCreditCardId(User user) {
        return CompletableFuture.supplyAsync(() -> {
            return user.getCardId();
        });
    }

    public void composeSample() throws ExecutionException, InterruptedException {
        CompletableFuture<CompletableFuture<Double>> res = this.getUsersDetail(10).thenApply(u -> this.getCreditCardId(u));
        CompletableFuture<Double> res2 = this.getUsersDetail(10)
                .thenApply(u -> {
                    try {
                        return this.getCreditCardId(u).get();
                    } catch (InterruptedException iExc) {
                    } catch (ExecutionException exExc) {
                    }
                    return null;
                });


        CompletableFuture<Double> result = this.getUsersDetail(10)
                .thenCompose(user -> this.getCreditCardId(user));

        res.thenAccept(r -> System.out.println("El id card es " + r)).get();
        res2.thenAccept(r -> System.out.println("El id card es " + r)).get();
        result.thenAccept(r -> System.out.println("El id card es " + r)).get();

    }

    public void combineSample() throws ExecutionException, InterruptedException {
        System.out.println("Retrieving weight.");
        CompletableFuture<Double> weightInKgFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return 65.0;
        });

        System.out.println("Retrieving height.");
        CompletableFuture<Double> heightInCmFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return 177.8;
        });

        System.out.println("Calculating BMI.");
        CompletableFuture<Double> combinedFuture = weightInKgFuture
                .thenCombine(heightInCmFuture, (weightInKg, heightInCm) -> {
                    Double heightInMeter = heightInCm/100;
                    return weightInKg/(heightInMeter*heightInMeter);
                });

        System.out.println("Your BMI is - " + combinedFuture.get());

    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CombineFutures combineFutures = new CombineFutures();
        // THEN COMPOSE SAMPLE (LOS FUTUROS A COMBINAR SON DEPENDIENTES)
        combineFutures.composeSample();

        //THEN COMBINE SAMPLE (LOS FUTUROS A COMBINAR SON INDEPENDIENTES)
        combineFutures.combineSample();
    }
}
