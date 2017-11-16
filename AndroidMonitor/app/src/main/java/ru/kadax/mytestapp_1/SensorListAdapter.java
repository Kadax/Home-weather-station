package ru.kadax.mytestapp_1;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kadax on 04.11.2017.
 */

public class SensorListAdapter extends BaseAdapter {
    ArrayList<SensorItem> data;
    Context ctx;
    LayoutInflater lInflater;

    //Инициализация
    public SensorListAdapter(Context context)
    {
        ctx = context;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = new ArrayList<SensorItem>();
    }
    // кол-во элементов
    @Override
    public int getCount() {
        return data.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    //сенсор по позиции
    SensorItem getSensor(int position)
    {
        return ((SensorItem) getItem(position));
    }

    //пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.sensoritemlist, parent, false);
        }

        SensorItem p = getSensor(position);

        ((TextView)view.findViewById(R.id.SensId)).setText(String.valueOf(p.sensorid));
        ((TextView)view.findViewById(R.id.SensDescr)).setText(p.name);

       // CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);

        return view;
    }

    public void clearData() {
        // clear the data
        data.clear();
    }
    public void addItem(SensorItem SI)
    {
        data.add(SI);
    }


}


