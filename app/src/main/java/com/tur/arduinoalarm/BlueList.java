package com.tur.arduinoalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BlueList extends BaseAdapter {

    //int count;
    String[] bluetoothName;
    String[] address;
    int count;





    Context context;
    // int [] imageId;
    private static LayoutInflater inflater=null;
    public BlueList(PairedList mainActivity, String[] bluetoothName1,String[] address1, int count1) {
        // TODO Auto-generated constructor stub


        count = count1;
        bluetoothName = bluetoothName1;
        address = address1;


        context=mainActivity;
        // imageId=prgmImages;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return count;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        LinearLayout lv;
        TextView btName;
        TextView btAddress;




    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final BlueList.Holder holder=new BlueList.Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_item, null);

        holder.lv = (LinearLayout) rowView.findViewById(R.id.ll);
        holder.btName = (TextView) rowView.findViewById(R.id.bluetoothname);
        holder.btAddress=(TextView) rowView.findViewById(R.id.address);


        holder.btName.setText(bluetoothName[position]);
        holder.btAddress.setText(address[position]);





        holder.lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ((PairedList)context).connectWithBluetooth(bluetoothName[position],address[position]);
            }
        });






        return rowView;
    }




}
