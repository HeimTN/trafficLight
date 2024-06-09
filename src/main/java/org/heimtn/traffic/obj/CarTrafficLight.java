package org.heimtn.traffic.obj;

public class CarTrafficLight extends TrafficLight {
    private int numCar;

    private static final int YELLOW_DURATION = 5;

    public CarTrafficLight(String id){
        super(id);
        numCar = 0;
    }


    @Override
    public void updateData(int number) {
        this.numCar = number;
    }

    @Override
    void handleEvents() {
        while (!eventQueue.isEmpty()){
            TrafficLightEvent event = eventQueue.poll();
            if(event.getType().equals("queue_update")){
                updateData(event.getNumCars());
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
                    changeState(TrafficLightState.GREEN, numCar, 0);
                }
            }
            case YELLOW -> {
                if(timePassed >= YELLOW_DURATION){
                    changeState(TrafficLightState.RED, numCar, 0);
                }
            }
            case GREEN -> {
                if(timePassed >= getGreenLightDuration(getNumFactor())){
                    changeState(TrafficLightState.YELLOW, numCar, 0);
                }
            }
        }
    }

    @Override
    int getNumFactor() {
        return numCar;
    }

}
