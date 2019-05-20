package sg.edu.rp.c346.project04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class doAddPart extends AppCompatActivity {

    Button btnInsert, btnDelete;
    EditText etPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_add_part);
        btnInsert = findViewById(R.id.buttonInsert);
        btnDelete = findViewById(R.id.buttonDelete);
        etPart = findViewById(R.id.editTextPartName);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String part = etPart.getText().toString();
                part.toUpperCase();
                DBHelper db = new DBHelper(doAddPart.this);
                boolean status = db.dbContainPartName(part);
                if (part.length() == 0) {
                    Toast.makeText(doAddPart.this, "Cannot be empty", Toast.LENGTH_LONG).show();
                } else if (status == true) {
                    Toast.makeText(doAddPart.this, "No duplicates", Toast.LENGTH_LONG).show();
                } else {
                    db.insertPart(part);
                    db.close();
                    Toast.makeText(doAddPart.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), AddPart.class);
                    startActivity(intent);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String partName = etPart.getText().toString();
                partName = partName.toUpperCase();
                DBHelper db = new DBHelper(doAddPart.this);
                boolean partInExistingPartList = db.partInPartsList(partName);
                boolean dbPartContainsPart = db.dbContainPartName(partName);
                if (partInExistingPartList) {
                    Toast.makeText(getBaseContext(),"You cant delete " + partName + " because an existing entity is using it!",
                            Toast.LENGTH_LONG).show();
                } else if (partName.length() == 0){
                    Toast.makeText(getBaseContext(),"Please enter a part name!", Toast.LENGTH_LONG).show();
                } else if (dbPartContainsPart == false) {
                    Toast.makeText(getBaseContext(),"No such part type in current part type list!", Toast.LENGTH_LONG).show();
                } else {
                    boolean deleted = db.deletePart(partName);
                    if (deleted == true) {
                        Toast.makeText(getBaseContext(), "You have successfully deleted " + partName + " from the list",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getBaseContext(), AddPart.class);
                        startActivity(intent);
                    } else {
                        // By right, this statement will never appear for user's viewing pleasure
                        Toast.makeText(getBaseContext(), "Deletion failed", Toast.LENGTH_LONG);
                    }
                }
            }
        });
    }
}
