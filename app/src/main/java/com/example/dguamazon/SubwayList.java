package com.example.dguamazon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SubwayList extends AppCompatActivity{
    String fromName;
    String toName;
    int fromCode;
    int toCode;

    private ArrayList<SubwayItem> data = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subwaylist);

        System.out.println("Start");

        final Resources res = new Resources();
        String [] name = res.name;
        final List<String> fromList = new ArrayList<>(Arrays.asList(name));
        String [] name2 = res.name2;
        final List<String> toList = new ArrayList<>(Arrays.asList(name2));
        final Integer [] code = res.stationCode;


        final Spinner fromSpinner = (Spinner) findViewById(R.id.fromSpinner);
        final Spinner toSpinner = (Spinner) findViewById(R.id.toSpinner);

        ArrayAdapter<String> fromSpinnerAdapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_item, fromList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                    return false;
                else
                    return true;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        fromSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromSpinnerAdapter);
        fromSpinner.setPrompt("FROM");


        ArrayAdapter<String> toSpinnerAdapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_item, toList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                    return false;
                else
                    return true;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        toSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(toSpinnerAdapter);
        toSpinner.setPrompt("TO");

        fromName = fromSpinner.getSelectedItem().toString();
        toName = toSpinner.getSelectedItem().toString();

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromName = fromSpinner.getItemAtPosition(position).toString();
                fromCode = code[position];
                onResume();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { return; }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toName = toSpinner.getItemAtPosition(position).toString();
                toCode = code[position];
                onResume();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!fromName.equals("Select From Station") && !toName.equals("Select To Station")){
            Intent pass = new Intent(getApplicationContext(), SubwayClicked.class);
            pass.putExtra("fromName",fromName);
            pass.putExtra("toName",toName);
            pass.putExtra("fromCode", fromCode);
            pass.putExtra("toCode",toCode);
            fromName = "Select From Station";
            toName = "Select To Station";
            startActivity(pass);
        }
    }
}