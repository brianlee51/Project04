package sg.edu.rp.c346.project04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class doAddColor extends AppCompatActivity {

    Button btnInsert, btnDelete;
    EditText etColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_add_color);
        btnInsert = findViewById(R.id.buttonInsert);
        etColor = findViewById(R.id.editTextColor);
        btnDelete = findViewById(R.id.buttonDelete);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = etColor.getText().toString();
                color.toUpperCase();
                DBHelper db = new DBHelper(doAddColor.this);
                boolean status = db.dbContainColor(color);
                if (color.length() == 0) {
                    Toast.makeText(doAddColor.this, "Cannot be empty", Toast.LENGTH_LONG).show();
                } else if (status == true) {
                    Toast.makeText(doAddColor.this, "No duplicates", Toast.LENGTH_LONG).show();
                } else {
                    db.insertColor(color);
                    db.close();
                    Toast.makeText(doAddColor.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), AddPart.class);
                    startActivity(intent);
                }
            }
        });
    }
}
