import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] planes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] runways = {1, 2, 3, 4, 5};

        Semaphore semaphore = new Semaphore(runways.length);
        ReentrantLock lock = new ReentrantLock();
        ExecutorService executorService = Executors.newFixedThreadPool(planes.length);

        for (int plane : planes) {
            executorService.submit(() -> {
                try {
                    semaphore.acquire();
                    lock.lock();
                    System.out.println("Самолет " + plane + " выруливает на полосу " + plane % runways.length);
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Полоса " + plane % runways.length + " приняла самолет " + plane);
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Самолет " + plane + " взлетел");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Полоса " + plane % runways.length + " освободилась");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    semaphore.release();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Все самолеты взлетели");
    }
}