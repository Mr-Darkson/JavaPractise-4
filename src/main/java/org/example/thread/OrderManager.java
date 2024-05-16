package org.example.thread;

import org.example.App;
import org.example.model.Passenger;
import org.example.util.PassengerGenerator;
import org.example.util.Randomizer;

import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

public class OrderManager implements Runnable {
    private static final int PASSENGER_COUNT = App.parameters.get("passengers");
    private static final int generationLowerBoundDelay = App.parameters.get("generationLowerBoundDelay");
    private static final int generationUpperBoundDelay = App.parameters.get("generationUpperBoundDelay");
    private static final Queue<Passenger> orders = App.getOrders();

    @Override
    public void run() {
        int executed = 0;
        while (executed != PASSENGER_COUNT) {
            try {
                orders.add(PassengerGenerator.generate());
                Thread.sleep(Randomizer.inInterval(generationLowerBoundDelay,generationUpperBoundDelay) * 1000L);
                executed++;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
