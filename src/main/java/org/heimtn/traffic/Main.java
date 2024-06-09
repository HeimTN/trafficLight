package org.heimtn.traffic;

import java.util.List;

public class Main {
    public static void main(String[] args){
        TrafficLightHandler handler = new TrafficLightHandler();
        List<Thread> threads = handler.createAllThreadTrafficLight();

        threads.forEach(Thread::start);

        Thread thread = new Thread(() -> {
            try {
                handler.randomUpdateDataTrafficLights();
                Thread.sleep(1500);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }

        });
        thread.start();
    }
}
