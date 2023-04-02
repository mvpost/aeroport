import java.util.concurrent.Semaphore;

public class Airport {
    private static final int NUM_RUNWAYS = 5;
    private static final int NUM_PLANES = 10;
    private static Semaphore[] runways = new Semaphore[NUM_RUNWAYS];
    private static int planesTakenOff = 0;

    public static void main(String[] args) {
        // Инициализация семафоров
        for (int i = 0; i < NUM_RUNWAYS; i++) {
            runways[i] = new Semaphore(1);
        }

        // Создание потоков-самолетов
        for (int i = 0; i < NUM_PLANES; i++) {
            new Thread(new Plane(i)).start();
        }
    }

    // Класс самолета
    private static class Plane implements Runnable {
        private int id;
        private Boolean takeOff = false;

        public Plane(int id) {
            this.id = id;
            this.takeOff = takeOff;
        }

        @Override
        public void run() {
            try {
                System.out.println("Самолет " + id + " запрашивает разрешение на взлет");
                while (!takeOff) {
                for (Semaphore runway : runways) {
                    if (runway.tryAcquire()) {
                        System.out.println("Самолет " + id + " выруливает на полосу " + getRunwayIndex(runway));
                        Thread.sleep(1000); // Имитация взлета
                        System.out.println("Самолет " + id + " взлетел");
                        takeOff = true;
                        runway.release(); // Освобождение семафора (полосы)
                        synchronized (this) {
                            planesTakenOff++;
                            if (planesTakenOff == NUM_PLANES) {
                                System.out.println("Все самолеты взлетели");
                            }
                        }
                        break;
                    }
                }}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Получение индекса семафора (полосы)
        private int getRunwayIndex(Semaphore runway) {
            for (int i = 0; i < runways.length; i++) {
                if (runways[i] == runway) {
                    return i;
                }
            }
            return -1;
        }
    }
}
