package com.example.dguamazon;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
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
    int fromCode;
    int toCode;

    private ArrayList<SubwayItem> data = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subwaylist);



//        from = (TextView) findViewById(R.id.from);
//        to = (TextView) findViewById(R.id.to);

//        ListView listView = (ListView) findViewById(R.id.subwayListView);

//        data = new ArrayList<>();
        final Resources res = new Resources();
        String [] name = res.name;
//        String [] subName = res.subName;
        final Integer [] code = res.stationCode;
//
//        for(int i = 0; i <name.length; i++){
//            SubwayItem item = new SubwayItem(name[i],subName[i],code[i]);
//            data.add(item);
//        }

        final Spinner fromSpinner = (Spinner) findViewById(R.id.fromSpinner);
        final Spinner toSpinner = (Spinner) findViewById(R.id.toSpinner);

        ArrayAdapter<String> fromSpinnerAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, name);
        fromSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromSpinnerAdapter);

        ArrayAdapter<String> toSpinnerAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, name);
        toSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(toSpinnerAdapter);

        fromName = fromSpinner.getSelectedItem().toString();
        toName = toSpinner.getSelectedItem().toString();

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromName = fromSpinner.getItemAtPosition(position).toString();
                fromCode = code[position];
                Toast.makeText(SubwayList.this, fromName+"선택 "+fromCode+"선택", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toName = toSpinner.getItemAtPosition(position).toString();
                toCode = code[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        onResume();



//        SubwayAdapter adapter = new SubwayAdapter(this, R.layout.subwayitem, data);
//        listView.setAdapter(adapter);
//
//        onResume();
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View v, int position, long id) {
//                getMyDialog(position);
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!fromName.equals("SELECT") && !toName.equals("SELECT")){
            fromName = "";
            toName = "";
            Intent pass = new Intent(getApplicationContext(), SubwayClicked.class);
            pass.putExtra("fromName",fromName);
            pass.putExtra("toName",toName);
            pass.putExtra("fromCode", fromCode);
            pass.putExtra("toCode",toCode);
            startActivity(pass);
        }
    }

    public void getMyDialog(final int position){
        final String stationName = data.get(position).getName();
        final String subName = data.get(position).getSubName();
        final int stCode = data.get(position).getCode();

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
                        fromCode = stCode;
                        from.setText(stationName);
                        onResume();
                        break;

                    case 1:
                        Toast.makeText(SubwayList.this,"ARRIVE : "+stationName,Toast.LENGTH_SHORT).show();
                        toFlag = true;
                        toName = stationName;
                        toCode = stCode;
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