package ru.kadax.mytestapp_1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    SensorListAdapter SLA;
    SensorListViewAdapter SLVA;
    ListView list;

    ListView listvalue;
    AlertBox ab = new AlertBox(MainActivity.this);
    SensorsInformation SI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SI = new SensorsInformation(MainActivity.this);

    }

        public void CancelSettings_Click (View v){
            setContentView(R.layout.activity_main);
        }

        public void OkSettings_Click(View v)
        {
            if(SLA!=null) {
                if(list!=null){
                    ab.Show(String.valueOf(list.getSelectedItem()));
                    for(int i=0; SLA.getCount()<i;i++)
                    {

                    }
                }
            }

            setContentView(R.layout.activity_main);
        }

        public void Settings_Click (View v){
            setContentView(R.layout.settingslayout);

            final DataRequest dr =new DataRequest();

            dr.fetchData(new DataCallback() {
                @Override
                public void onError(String errorMessage) {
                    ab.Show(errorMessage);
                }

                @Override
                public void onSuccess(JSONArray result) {
                    list = (ListView) findViewById(R.id.Sensorslist);

                    SLA = new SensorListAdapter(getApplicationContext());
                    list.setAdapter(SLA);

                    SLA.clearData();
                    for(int i =0; i< result.length();i++){
                        try {
                            Object o = result.get(i);
                            if ( o instanceof JSONObject ) {
                                JSONObject jo = (JSONObject)o;
                                SensorItem si = new SensorItem(jo.getString("Name"),jo.getInt("id"));
                                SLA.addItem(si);
                            }
                        }
                        catch (Exception ex)
                        {

                        }
                    }
                    SLA.notifyDataSetChanged();
                }
            },"http://62.213.40.61:1180/sensors",this.getApplicationContext());
        }
        //http://62.213.40.61:1180/last?sensor=12

        public void updateBtnClick(View v)
        {
            if(SLVA!=null) {
                SLVA.clearData();
            }
            else
            {
                listvalue = (ListView) findViewById(R.id.listValues);
                SLVA = new SensorListViewAdapter(getApplicationContext());
                listvalue.setAdapter(SLVA);
            }


            if(SI.values)
            {
                for(int i =0; i< SI.SensorsValue.size();i++) {
                    SLVA.addItem(SI.SensorsValue.get(i));
                }
                SLVA.notifyDataSetChanged();
            }

            SI.GetSensorList();
        }


}



