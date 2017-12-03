package ru.kadax.mytestapp_1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kadax on 04.11.2017.
 */

public class SensorListViewAdapter extends BaseAdapter {
    ArrayList<SensorValue> data;
    Context ctx;
    LayoutInflater lInflater;

    //Инициализация
    public SensorListViewAdapter(Context context)
    {
        ctx = context;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = new ArrayList<SensorValue>();
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
    SensorValue getSensor(int position)
    {
        return ((SensorValue) getItem(position));
    }

    //пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.sensoritemlist, parent, false);
        }

        SensorValue p = getSensor(position);

        ((TextView)view.findViewById(R.id.SensValue)).setText(String.valueOf(p.value));
        ((TextView)view.findViewById(R.id.SensDescr)).setText(p.name);

       // CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);

        return view;
    }

    public void clearData() {
        // clear the data
        data.clear();
    }

    public void addItem(SensorValue SI)
    {
        data.add(SI);
    }

}


