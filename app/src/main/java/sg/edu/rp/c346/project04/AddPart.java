package sg.edu.rp.c346.project04;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    Button btnEditColor, btnInsert, btnEditPart, btnEditArea;
    EditText etQuantity, etLocation;
    ArrayList<String> alColor, alPart, alArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_part);
        setTitle("Add New Part");

        spnColor = findViewById(R.id.spinnerColor);
        btnEditColor = findViewById(R.id.buttonColor);
        btnInsert = findViewById(R.id.buttonInsert);
        btnEditPart = findViewById(R.id.buttonEditPart);
        spnPart = findViewById(R.id.spinnerPart);
        spnArea = findViewById(R.id.spinnerArea);
        btnEditArea = findViewById(R.id.buttonEditArea);
        etLocation = findViewById(R.id.editTextLocation);
        etQuantity = findViewById(R.id.editTextQuantity);

        DBHelper db = new DBHelper(AddPart.this);

        //For Populating color spinner
        alColor = new ArrayList<>();
        alColor.clear();
        alColor.addAll(db.getColorName());
        db.close();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, alColor);
        dataAdapter.notifyDataSetChanged();
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnColor.setAdapter(dataAdapter);

        //For populating PART spinner
        alPart = new ArrayList<>();
        alPart.clear();
        alPart.addAll(db.getPartName());
        db.close();
        ArrayAdapter<String> dataAdapterForPart = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, alPart);
        dataAdapterForPart.notifyDataSetChanged();
        dataAdapterForPart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPart.setAdapter(dataAdapterForPart);

        //For populating Area spinner
        alArea = new ArrayList<>();
        alArea.clear();
        alArea.addAll(db.getArea());
        db.close();
        ArrayAdapter<String> dataAdapterForArea = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, alArea);
        dataAdapterForArea.notifyDataSetChanged();
        dataAdapterForArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnArea.setAdapter(dataAdapterForArea);

        btnEditColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), doAddColor.class);
                startActivityForResult(intent, 9);
            }
        });

        btnEditPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), doAddPart.class);
                startActivityForResult(intent, 9);
            }
        });

        btnEditArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), doAddArea.class);
                startActivityForResult(intent, 9);
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
                    boolean status = db.dbContainsDuplicate(colorName, partName, studName);
                    if (status == true) {
                        Toast.makeText(getBaseContext(), "This part already exist in the current database!", Toast.LENGTH_LONG).show();
                    } else {
                        int actualToBeInsertedColorPos = db.getColorID(colorName);
                        int actualToBeInsertedStudPos = db.getStudID(studName);
                        int actualToBeInsertedPartPos = db.getPartID(partName);
                        db.insertItem(quantityInInt, itemLocation, actualToBeInsertedPartPos, actualToBeInsertedColorPos, actualToBeInsertedStudPos);
                        db.close();
                        Toast.makeText(getBaseContext(), "Item successfully inserted!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent();
                        setResult(RESULT_OK,i);
                        finish();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9) {
            DBHelper db = new DBHelper(AddPart.this);
            alColor.clear();
            alPart.clear();
            alArea.clear();
            alColor.addAll(db.getColorName());
            alPart.addAll(db.getPartName());
            alArea.addAll(db.getArea());
        }
    }
}
