package ru.kadax.mytestapp_1;

/**
 * Created by Kadax on 02.12.2017.
 */

public class SensorValue {
    String name;
    int sensorid;
    double value;

    SensorValue(String _name, int _sensorid, double _value){
        name=_name;
        sensorid=_sensorid;
        value = _value;
    }
}
