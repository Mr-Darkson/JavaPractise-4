package org.example;

import lombok.Getter;
import lombok.SneakyThrows;
import org.example.model.Elevator;
import org.example.model.Passenger;
import org.example.thread.OrderManager;
import org.example.util.JsonFileParser;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class App
{
    public static HashMap<String,Integer> parameters = JsonFileParser.parse();
    @Getter
    private static  volatile  Queue<Passenger> orders = new ConcurrentLinkedQueue<>();

    private static final List<Elevator> elevators = Elevator.create(parameters.get("elevators"));
    private static final int outputDelayInMs = parameters.get("outputDelayInMs");


    private static int completedOrders = 0;


    @SneakyThrows
    public static void main(String[] args )
    {
        simulateMakingOrders();
        activateElevators();
    }
    public static void access(int n) {
        completedOrders+=n;
    }

    private static void simulateMakingOrders() {
        OrderManager ordersShell = new OrderManager();
        Thread orderManager = new Thread(ordersShell);
        orderManager.start();
    }

    private static void activateElevators() throws InterruptedException {
        int i = 1;
        while (i <= parameters.get("numberOfStages")) {
            System.out.printf("---Этап %d--- CO: %d\n", i++, completedOrders);
            if(i % 10 == 0) {
                System.out.print(orders + "\n");
            }
            for(int j = 0; j < elevators.size(); j++) {

                Elevator el = elevators.get(j);
                el.assignIfFree();
                if(el.moveIfNotFree()) {
                    el.letOut();
                    if(el.onTargetFloor()) {
                        el.changeTarget();
                    }
                    else el.takeFellPass();
                    if(j != elevators.size()-1)System.out.print(">\n");

                }
                Thread.sleep(outputDelayInMs);

            }
            Thread.sleep(outputDelayInMs);
        }
    }


}
