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
    }
}
