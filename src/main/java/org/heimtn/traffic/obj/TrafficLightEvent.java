package org.heimtn.traffic.obj;

public class TrafficLightEvent {
    private String type;
    private int numCars;
    private int numPedestrian;
    private String senderId;
    private TrafficLightState state;
    private long timestamp;

    public TrafficLightEvent(String type, int numCars, int numPedestrian, String senderId, TrafficLightState state, long timestamp) {
        this.type = type;
        this.numCars = numCars;
        this.numPedestrian = numPedestrian;
        this.senderId = senderId;
        this.state = state;
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public int getNumCars() {
        return numCars;
    }

    public int getNumPedestrian() {
        return numPedestrian;
    }

    public String getSenderId() {
        return senderId;
    }

    public TrafficLightState getState() {
        return state;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
