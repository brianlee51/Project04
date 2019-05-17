package sg.edu.rp.c346.project04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddPart extends AppCompatActivity {

    Spinner spnColor, spnPart;
    Button btnEditColor, btnBack, btnInsert, btnEditPart;

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

        DBHelper db = new DBHelper(AddPart.this);
        ArrayList<String> data = db.getColor();
        db.close();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, data);
        dataAdapter.notifyDataSetChanged();
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnColor.setAdapter(dataAdapter);

        btnEditColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), doAddColor.class);
                startActivity(intent);
            }
        });

    }
}
