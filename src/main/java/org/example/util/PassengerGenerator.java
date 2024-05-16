package org.example.util;

import org.example.App;
import org.example.model.Passenger;

public class PassengerGenerator {
    private static final int MAX_FLOOR = App.parameters.get("floors");
    public static Passenger generate() {
        Passenger newPassenger = new Passenger();
        int rnd1 = Randomizer.inInterval(1, MAX_FLOOR);
        int rnd2;
        do { rnd2 = Randomizer.inInterval(1, MAX_FLOOR);} while (rnd2 == rnd1);

        newPassenger.setFrom_floor(rnd1);
        newPassenger.setTo_floor(rnd2);
        return newPassenger;
    }
}
