package org.heimtn.traffic.obj;


import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Абстрактный класс представляющий собой описание светофоров и его работу.
 * Все события хранятся внутри светофора и передаются другим светофорам данного "перекрестка".
 * Имеет константные поля с длительностью работы светофора.
 */
public abstract class TrafficLight {
    private String id;
    protected TrafficLightState state;
    protected long lastStateChangeTime;
    protected Queue<TrafficLightEvent> eventQueue;
    private Map<String, TrafficLight> otherTrafficLight;
    private Timer timer;

    protected static final int BASE_GREEN_DURATION = 30;
    protected static final int MIN_GREEN_DURATION = 15;
    protected static final int MAX_GREEN_DURATION = 60;

    public TrafficLight(String id){
        this.id = id;
        this.state = TrafficLightState.RED;
        this.lastStateChangeTime = System.currentTimeMillis();
        this.eventQueue = new ConcurrentLinkedQueue<>();
        this.otherTrafficLight = new HashMap<>();
        this.timer = new Timer(true);
    }

    public void addOtherTrafficLight(TrafficLight tl){
        otherTrafficLight.put(tl.getId(), tl);
    }

    public String getId() {
        return id;
    }

    public void addEvent(TrafficLightEvent event){
        eventQueue.add(event);
    }

    public abstract void updateData(int number);

    public void run(){
        while (true){
            handleEvents();
            updateCurrentState();
            adjustGreenLightDuration();
            sleep(1000);
        }
    }

    abstract void handleEvents();

    abstract void updateCurrentState();

    protected void changeState(TrafficLightState newState, int numCars, int numPedestrian){
        state = newState;
        lastStateChangeTime = System.currentTimeMillis();
        System.out.println("Traffic Light "+ id+" changed to "+ newState);

        TrafficLightEvent event = new TrafficLightEvent("state_change", numCars, numPedestrian, id, state, System.currentTimeMillis());
        otherTrafficLight.values().forEach(tl -> tl.addEvent(event));
    }

    protected int getGreenLightDuration(int numFactor){
        int factor = numFactor/10;
        int greenLightDuration = BASE_GREEN_DURATION+factor;

        return Math.max(MIN_GREEN_DURATION, Math.min(greenLightDuration, MAX_GREEN_DURATION));
    }

    protected int getRedLightDuration(){
        return BASE_GREEN_DURATION;
    }

    abstract int getNumFactor();

    private void adjustGreenLightDuration(){
        int newDuration = getGreenLightDuration(getNumFactor());
        System.out.println("Traffic Light " + id + " adjusted green light duration to " + newDuration + " seconds.");
    }

    private void sleep(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
