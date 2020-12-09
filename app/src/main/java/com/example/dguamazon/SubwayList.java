package com.example.dguamazon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
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

        final Resources res = new Resources();
        String [] name = res.name;
        final Integer [] code = res.stationCode;


        final Spinner fromSpinner = (Spinner) findViewById(R.id.fromSpinner);
        final Spinner toSpinner = (Spinner) findViewById(R.id.toSpinner);

        ArrayAdapter<String> fromSpinnerAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, name);
        fromSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromSpinnerAdapter);
        fromSpinner.setPrompt("FROM");

        ArrayAdapter<String> toSpinnerAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, name);
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
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
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
        if(!fromName.equals("") && !toName.equals("")){
            Intent pass = new Intent(getApplicationContext(), SubwayClicked.class);
            pass.putExtra("fromName",fromName);
            pass.putExtra("toName",toName);
            pass.putExtra("fromCode", fromCode);
            pass.putExtra("toCode",toCode);
            fromName = "";
            toName = "";
            startActivity(pass);
        }
    }
}