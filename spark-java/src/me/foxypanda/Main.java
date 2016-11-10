package me.foxypanda;

import java.util.Timer;

public class Main {

    public static void main(String[] args) throws Exception {
        Serial serial = new Serial();
        serial.initialize();
        AmbientLighting lighting = new AmbientLighting(serial.getSerial());
        Timer timer = new Timer();
        timer.schedule(lighting, 0, 50);
    }

}
