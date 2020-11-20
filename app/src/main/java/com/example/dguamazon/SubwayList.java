package com.example.dguamazon;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class SubwayList extends AppCompatActivity{
    String [] text = {"DEPART","ARRIVE"};
    TextView from;
    TextView to;
    boolean fromFlag = false;
    boolean toFlag = false;
    String fromName;
    String toName;

    private ArrayList<SubwayItem> data = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subwaylist);

        from = (TextView) findViewById(R.id.from);
        to = (TextView) findViewById(R.id.to);

        ListView listView = (ListView) findViewById(R.id.subwayListView);

        data = new ArrayList<>();
        Resources res = new Resources();
        String [] name = res.name;
        String [] subName = res.subName;

        for(int i = 0; i <name.length; i++){
            SubwayItem item = new SubwayItem(name[i],subName[i]);
            data.add(item);
        }

        

        SubwayAdapter adapter = new SubwayAdapter(this, R.layout.subwayitem, data);
        listView.setAdapter(adapter);

        onResume();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                getMyDialog(position);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(fromFlag==true && toFlag==true){
            fromFlag = false;
            toFlag = false;
            to.setText("Destination");
            from.setText("Depature");
            Intent pass = new Intent(getApplicationContext(),SubwayClicked.class);
            pass.putExtra("fromName",fromName);
            pass.putExtra("toName",toName);
            startActivity(pass);
        }
    }

    public void getMyDialog(final int position){
        final String stationName = data.get(position).getName();
        final String subName = data.get(position).getSubName();
        AlertDialog.Builder builder = new AlertDialog.Builder(SubwayList.this);
        builder.setTitle("Select Option");
        builder.setItems(text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Toast.makeText(SubwayList.this,"DEPART : "+stationName,Toast.LENGTH_SHORT).show();
                        fromFlag = true;
                        fromName = stationName;
                        from.setText(stationName);
                        onResume();
                        break;

                    case 1:
                        Toast.makeText(SubwayList.this,"ARRIVE : "+stationName,Toast.LENGTH_SHORT).show();
                        toFlag = true;
                        toName = stationName;
                        to.setText(stationName);
                        onResume();
                        break;

                }
                dialog.dismiss();
            }
        });
        builder.show();
    }
}