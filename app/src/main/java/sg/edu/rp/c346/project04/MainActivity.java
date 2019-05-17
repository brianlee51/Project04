package sg.edu.rp.c346.project04;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnViewInventory, btnAddItem;
    TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home Screen");
        btnAddItem = findViewById(R.id.buttonAddToInventory);
        btnViewInventory = findViewById(R.id.buttonViewInventory);
        // To populate from DB
        tvTotal = findViewById(R.id.textViewTotal);

        // To go to add parts page
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddPart.class);
                startActivity(intent);
            }
        });

        // To go to a custom List View page
        btnViewInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
