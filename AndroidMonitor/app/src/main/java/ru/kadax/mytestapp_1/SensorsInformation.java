package ru.kadax.mytestapp_1;

import android.content.Context;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Created by Kadax on 04.12.2017.
 */

public class SensorsInformation {
    public List<SensorItem> Sensors;
    public List<SensorValue> SensorsValue;
    public Date lastupdate;
    boolean sensors;
    boolean values;
    AlertBox alertBox;
    Context context;
    String UrlSensors = "http://62.213.40.61:1180/sensors";
    String UrlValues = "http://62.213.40.61:1180/last?sensor=";

    SensorsInformation(Context _context)
    {
        context = _context;
        alertBox = new AlertBox(context);
        if(Sensors==null)
        {
            sensors = false;
        }
        if(SensorsValue==null)
        {
            values = false;
        }
        GetSensorList();
    }

    public void GetSensorList()
    {
        final DataRequest dr =new DataRequest();

        dr.fetchData(new DataCallback() {
            @Override
            public void onError(String errorMessage) {
                alertBox.Show(errorMessage);
            }

            @Override
            public void onSuccess(JSONArray result) {
                Sensors.clear();
                for(int i =0; i< result.length();i++){
                    try {

                        Object o = result.get(i);
                        if ( o instanceof JSONObject) {
                            JSONObject jo = (JSONObject)o;
                            SensorItem si = new SensorItem(jo.getString("Name"),jo.getInt("id"));
                            Sensors.add(si);
                        }
                    }
                    catch (Exception ex)
                    {
                        alertBox.Show(ex.getMessage());
                    }
                }
                sensors = true;
                GetSensorValues();
            }
        },UrlSensors,context.getApplicationContext());
    }
    public void GetSensorValues(){
        SensorsValue.clear();
        for(int i=0;i<Sensors.size();i++)
        {
            SensorItem si = Sensors.get(i);
            final DataRequest dr =new DataRequest();
            dr.fetchData(new DataCallback() {
                @Override
                public void onError(String errorMessage) {
                    alertBox.Show(errorMessage);
                }

                @Override
                public void onSuccess(JSONArray result) {
                    for(int i =0; i< result.length();i++){
                        try {
                            Object o = result.get(i);
                            if ( o instanceof JSONObject) {
                                JSONObject jo = (JSONObject)o;
                                SensorValue sv = new SensorValue(jo.getString("Name"),jo.getInt("id"),jo.getDouble("Value"));
                                SensorsValue.add(sv);
                            }
                        }
                        catch (Exception ex)
                        {
                            alertBox.Show(ex.getMessage());
                        }
                    }
                    values = true;
                }
            },UrlValues+si.sensorid,context.getApplicationContext());
        }
    }
}
