package sg.edu.rp.c346.project04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddPart extends AppCompatActivity {

    Spinner spnColor, spnPart, spnArea;
    Button btnEditColor, btnBack, btnInsert, btnEditPart, btnEditArea;
    EditText etQuantity, etLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_part);
        setTitle("Add New Part");

        spnColor = findViewById(R.id.spinnerColor);
        btnEditColor = findViewById(R.id.buttonColor);
        btnBack = findViewById(R.id.buttonBack);
        btnInsert = findViewById(R.id.buttonInsert);
        btnEditPart = findViewById(R.id.buttonEditPart);
        spnPart = findViewById(R.id.spinnerPart);
        spnArea = findViewById(R.id.spinnerArea);
        btnEditArea = findViewById(R.id.buttonEditArea);
        etLocation = findViewById(R.id.editTextLocation);
        etQuantity = findViewById(R.id.editTextQuantity);

        DBHelper db = new DBHelper(AddPart.this);

        //For Populating color spinner
        ArrayList<String> data = db.getColorName();
        db.close();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, data);
        dataAdapter.notifyDataSetChanged();
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnColor.setAdapter(dataAdapter);

        //For populating PART spinner
        ArrayList<String> dataPart = db.getPartName();
        db.close();
        ArrayAdapter<String> dataAdapterForPart = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dataPart);
        dataAdapterForPart.notifyDataSetChanged();
        dataAdapterForPart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPart.setAdapter(dataAdapterForPart);

        //For populating Area spinner
        ArrayList<String> area = db.getArea();
        db.close();
        ArrayAdapter<String> dataAdapterForArea = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, area);
        dataAdapterForArea.notifyDataSetChanged();
        dataAdapterForArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnArea.setAdapter(dataAdapterForArea);

        btnEditColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), doAddColor.class);
                startActivity(intent);
            }
        });

        btnEditPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), doAddPart.class);
                startActivity(intent);
            }
        });

        btnEditArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), doAddArea.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemLocation = etLocation.getText().toString();
                String quantity = etQuantity.getText().toString();
                DBHelper db = new DBHelper(AddPart.this);
                if (quantity.length() == 0) {
                    Toast.makeText(getBaseContext(), "Item quantity cannot be empty!", Toast.LENGTH_LONG).show();
                } else if (itemLocation.length() == 0) {
                    Toast.makeText(getBaseContext(), "Put N.A if you do not have a specified location to store item!", Toast.LENGTH_LONG).show();
                } else if (spnColor.getSelectedItem() == null || spnPart.getSelectedItem() == null || spnArea.getSelectedItem() == null){
                    Toast.makeText(getBaseContext(), "Please add in attributes!", Toast.LENGTH_LONG).show();
                } else {
                    int quantityInInt = Integer.parseInt(quantity);
                    String colorName = spnColor.getSelectedItem().toString();
                    String partName = spnPart.getSelectedItem().toString();
                    String studName = spnArea.getSelectedItem().toString();
                    int actualToBeInsertedColorPos = db.getColorID(colorName);
                    int actualToBeInsertedStudPos = db.getStudID(studName);
                    int actualToBeInsertedPartPos = db.getPartID(partName);
                    db.insertItem(quantityInInt, itemLocation, actualToBeInsertedPartPos, actualToBeInsertedColorPos, actualToBeInsertedStudPos);
                    db.close();
                    Toast.makeText(getBaseContext(), "Item successfully inserted!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
