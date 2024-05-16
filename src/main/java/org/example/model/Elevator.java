package org.example.model;

import lombok.Data;
import org.example.App;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;


@Data
public class Elevator {

    private int id;

    private HashMap<Integer, Integer> buttonPanel  = new HashMap<>();
    private static volatile Queue<Passenger> orders = App.getOrders();

    private boolean free = true;
    private int curFloor = 1;
    private int targetFloor = curFloor;
    private Passenger targetPassenger = null;

    public Elevator(int id) {
        this.id = id;
    }

    public static ArrayList<Elevator> create(int x) {
        ArrayList<Elevator> list = new ArrayList<>();
        for(int i = 0; i < x; i++) {
            list.add(new Elevator(i+1));
        }
        return list;
    }

    public boolean assignIfFree() {
        Passenger pas = orders.peek();
        if(this.isFree()) {
            if(!(pas == null)) {
                pas = orders.poll();
                this.setTargetFloor(pas.getFrom_floor());
                this.setTargetPassenger(pas);
                this.free = false;
                System.out.printf("Лифт №%d выезжает на этаж %d\n", id, targetFloor);
                return true;
            }
        }
        return false;
    }

    public boolean moveIfNotFree() {
        if(!isFree()) {
            if(curFloor == targetFloor) return true;
            if(curFloor < targetFloor) curFloor++;
            else curFloor--;
            System.out.printf("Лифт №%d проезжает %d этаж. Его цель - %d\n", id, curFloor, targetFloor);
            return true;
        }
        return false;
    }

    public void takeFellPass() {
        int pass = 0;
        for(Passenger x : orders) {
            if(x.getFrom_floor() == this.curFloor) { // 15 - 0
                if(this.curFloor <= x.getTo_floor() && x.getTo_floor() <= this.targetFloor) {
                    this.buttonPanel.put(x.getTo_floor(), buttonPanel.getOrDefault(x.getTo_floor(), 0) + 1);
                    pass++;
                    orders.remove(x);
                }
                else if(this.curFloor >= x.getTo_floor() && x.getTo_floor() >= this.targetFloor) {
                    this.buttonPanel.put(x.getTo_floor(), buttonPanel.getOrDefault(x.getTo_floor(), 0) + 1);
                    pass++;
                    orders.remove(x);
                }
            }
        }
        if(pass != 0) {
            System.out.printf("Лифт №%d проезжая %d этаж взял %d пассажиров\n", id, curFloor, pass);
        }
    }

    public void letOut() {
        Integer ordersToCurFloor = buttonPanel.get(curFloor);
        if(ordersToCurFloor != null) {
            System.out.printf("Лифт №%d высадил на %d этаже %d пассажиров\n", id, curFloor, ordersToCurFloor);
            App.access(ordersToCurFloor);
            buttonPanel.remove(curFloor);
        }
    }

    public boolean onTargetFloor() {
        return (curFloor == targetFloor);
    }

    public void changeTarget() {
        if(targetPassenger == null) {
            this.free = true;
            System.out.printf("Лифт №%d теперь свободен\n", id);
        }
        else {
            this.targetFloor = getTargetPassenger().getTo_floor();
            this.buttonPanel.put(targetFloor, 1);
            this.targetPassenger = null;
            int pass = 1;
            for(Passenger x : orders) {
                if(x.getFrom_floor() == this.curFloor) { // 15 - 0
                    if(this.curFloor <= x.getTo_floor() && x.getTo_floor() <= this.targetFloor) {
                        this.buttonPanel.put(x.getTo_floor(), buttonPanel.getOrDefault(x.getTo_floor(), 0) + 1);
                        pass++;
                        orders.remove(x);
                    }
                    else if(this.curFloor >= x.getTo_floor() && x.getTo_floor() >= this.targetFloor) {
                        this.buttonPanel.put(x.getTo_floor(), buttonPanel.getOrDefault(x.getTo_floor(), 0) + 1);
                        pass++;
                        orders.remove(x);
                    }
                }
            }
            if(pass != 0) {
                System.out.printf("Лифт №%d взял %d пассажиров на этаже %d. Целевой этаж поменялся на %d\n", id, pass, curFloor, targetFloor);
            }
        }





    }

}
