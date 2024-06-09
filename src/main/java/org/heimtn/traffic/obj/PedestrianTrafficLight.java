package org.heimtn.traffic.obj;

/**
 * Класс представляющий пешеходный светофор
 */
public class PedestrianTrafficLight extends TrafficLight {
    private int numPedestrian;

    public PedestrianTrafficLight(String id){
        super(id);
        this.numPedestrian = 0;
    }
    @Override
    public void updateData(int number) {
        this.numPedestrian = number;
    }

    @Override
    void handleEvents() {
        while (!eventQueue.isEmpty()){
            TrafficLightEvent event = eventQueue.poll();
            if(event.getType().equals("queue_update")){
                updateData(event.getNumPedestrian());
            }
        }
    }

    @Override
    void updateCurrentState() {
        long currentTime = System.currentTimeMillis();
        long timePassed = (currentTime - lastStateChangeTime) / 1000;

        switch (state){
            case RED -> {
                if(timePassed >= getRedLightDuration()){
                    changeState(TrafficLightState.GREEN, 0, numPedestrian);
                }
            }
            case GREEN -> {
                if(timePassed >= getGreenLightDuration(getNumFactor())){
                    changeState(TrafficLightState.RED, 0, numPedestrian);
                }
            }
        }
    }

    @Override
    int getNumFactor() {
        return numPedestrian;
    }
}
