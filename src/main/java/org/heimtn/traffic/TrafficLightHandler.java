package org.heimtn.traffic;

import org.heimtn.traffic.obj.CarTrafficLight;
import org.heimtn.traffic.obj.PedestrianTrafficLight;
import org.heimtn.traffic.obj.TrafficLight;
import org.heimtn.traffic.obj.TrafficLightEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TrafficLightHandler {

    private List<CarTrafficLight> carTrafficLightList;
    private List<PedestrianTrafficLight> pedestrianTrafficLightList;

    public TrafficLightHandler(){
        this.carTrafficLightList = new ArrayList<>();
        this.pedestrianTrafficLightList = new ArrayList<>();
    }

    public List<Thread> createAllThreadTrafficLight(){
        if(carTrafficLightList.isEmpty() || pedestrianTrafficLightList.isEmpty()){
            createAllTrafficLight();
        }
        List<Thread> result = new ArrayList<>();
        carTrafficLightList.forEach(tl -> result.add(new Thread(tl::run)));
        pedestrianTrafficLightList.forEach(tl -> result.add(new Thread(tl::run)));
        return result;
    }

    public List<? super TrafficLight> getCarTrafficLight(){
        return new ArrayList<>(carTrafficLightList);
    }
    public List<? super TrafficLight> getPedestrianTrafficLight(){
        return new ArrayList<>(pedestrianTrafficLightList);
    }

    public void randomUpdateDataTrafficLights(){
        carTrafficLightList.forEach(tl ->{
            tl.addEvent(new TrafficLightEvent("queue_update", (int)(Math.random()*20), 0, null, null, System.currentTimeMillis()));
        });
        pedestrianTrafficLightList.forEach(tl ->{
            tl.addEvent(new TrafficLightEvent("queue_update", 0, (int)(Math.random()*30), null, null, System.currentTimeMillis()));
        });
    }

    private void createAllTrafficLight(){

        for(int i = 0; i < 5; i++){
            carTrafficLightList.add(new CarTrafficLight(generateRandomId("car")));
        }
        carTrafficLightList.forEach(tl ->{
            for (CarTrafficLight carTrafficLight : carTrafficLightList) {
                if (!Objects.equals(tl.getId(), carTrafficLight.getId())) {
                    tl.addOtherTrafficLight(carTrafficLight);
                }
            }
        });

        for(int i = 0; i < 9; i++){
            pedestrianTrafficLightList.add(new PedestrianTrafficLight(generateRandomId("ped")));
        }
        pedestrianTrafficLightList.forEach(tl ->{
            for (PedestrianTrafficLight pedestrianTrafficLight : pedestrianTrafficLightList) {
                if (!Objects.equals(tl.getId(), pedestrianTrafficLight.getId())) {
                    tl.addOtherTrafficLight(pedestrianTrafficLight);
                }
            }
        });

        carTrafficLightList.forEach(tl ->{
            for(PedestrianTrafficLight pedestrianTrafficLight : pedestrianTrafficLightList){
                tl.addOtherTrafficLight(pedestrianTrafficLight);
            }
        });
        pedestrianTrafficLightList.forEach(tl ->{
            for(CarTrafficLight carTrafficLight : carTrafficLightList){
                tl.addOtherTrafficLight(carTrafficLight);
            }
        });


    }

    private static String generateRandomId(String type){
        StringBuilder result = new StringBuilder().append("tl");
        for(int i = 0; i < 5; i++){
            int random = (int)(Math.random() * 9);
            result.append(random);
        }
        switch (type) {
            case "car" -> result.append("c");
            case "ped" -> result.append("p");
        }

        return result.toString();

    }
}
