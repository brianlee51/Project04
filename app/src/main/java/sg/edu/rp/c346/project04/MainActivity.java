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
    TextView tvTotal, tvUnique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("LEGO Parts Inventory");
        btnAddItem = findViewById(R.id.buttonAddToInventory);
        btnViewInventory = findViewById(R.id.buttonViewInventory);
        // To populate from DB
        tvTotal = findViewById(R.id.textViewTotal);
        tvUnique = findViewById(R.id.textViewUnique);

        DBHelper db = new DBHelper(MainActivity.this);
        int uniqueParts = db.getTotalUniqueParts();
        int totalParts = db.getTotalAmount();
        tvUnique.setText(uniqueParts + "");
        tvTotal.setText(totalParts + "");

        // To go to add parts page
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddPart.class);
                startActivityForResult(intent, 111);
            }
        });

        // To go to a custom List View page
        btnViewInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ViewInventory.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DBHelper db = new DBHelper(MainActivity.this);
        tvUnique.setText(db.getTotalUniqueParts() + "");
        tvTotal.setText(db.getTotalAmount() + "");
    }
}
