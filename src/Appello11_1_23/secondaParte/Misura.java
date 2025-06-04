package Appello11_1_23.secondaParte;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Misura implements Serializable {
    private double value;
    private int sensorId;
    private long dataTime;

    public Misura(int sensorId) {
        this.sensorId = sensorId;
        this.value = 0;
        this.dataTime = new Date().getTime();
    }

    public void setSensorId(int sensorId) {
        this.sensorId=sensorId;
    }

    public void setValue(double value){
        this.value = value;
    }

    public void setDataTime(long dataTime){
        this.dataTime = dataTime;
    }

    public int getSensorId(){
        return sensorId;
    }

    public double getValue(){
        return value;
    }

    public long getDataTime(){
        return dataTime;
    }

    public int hashCode(){
        return sensorId;
    }

    public boolean equals(Object o){
        if(o==null)
            return false;
        if(!(o instanceof Misura))
            return false;
        Misura m = (Misura) o;
        return m.getSensorId() == sensorId;
    }

    public void misura(){
        this.value = new Random().nextDouble();
        this.dataTime = new Date().getTime();
    }

    public String toString(){
        return +sensorId+":"+value+":"+dataTime+":";
    }
}//Misura
