package Appello11_1_23.secondaParte;

import java.io.Serializable;

public class Misura implements Serializable {
    private double value;
    private final int sensorId;

    public Misura(int sensorId,double value){
        this.sensorId = sensorId;
        this.value = value;
    }
    public double getValue(){
        return value;
    }

    public void setValue(double value){
        this.value = value;
    }

    public int getSensorId(){
        return sensorId;
    }
}//Misura
