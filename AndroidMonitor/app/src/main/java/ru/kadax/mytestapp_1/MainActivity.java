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


public class MainActivity extends AppCompatActivity {

    SensorListAdapter SLA;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

        public void CancelSettings_Click (View v){
            setContentView(R.layout.activity_main);
        }

        public void Settings_Click (View v){
            setContentView(R.layout.settingslayout);



            final DataRequest dr =new DataRequest();

            dr.fetchData(new DataCallback() {
                @Override
                public void onError(String errorMessage) {
                    alertBox(errorMessage);
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
            DataRequest dr =new DataRequest();
            dr.fetchData(new DataCallback() {
                @Override
                public void onError(String errorMessage) {
                    alertBox(errorMessage);
                }

                @Override
                public void onSuccess(JSONArray result) {
                    alertBox(String.valueOf(result));
                }
            },"http://62.213.40.61:1180/last?sensor=12",this.getApplicationContext());
        }

        public void alertBox(String message)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Сообщение")
                    .setMessage(message)
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
}



