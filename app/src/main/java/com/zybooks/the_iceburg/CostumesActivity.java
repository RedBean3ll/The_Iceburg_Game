package com.zybooks.the_iceburg;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.ImageButton;
import android.os.Bundle;

public class CostumesActivity extends AppCompatActivity{
    public static String EXTRA_COSTUME = "costume id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.costumes_menu);

        ImageButton defaulto = findViewById(R.id.costume_defaulto);
        ImageButton moyai = findViewById(R.id.costume_moyai);
        ImageButton animal = findViewById(R.id.costume_animal);
        ImageButton mug = findViewById(R.id.costume_mug);
        ImageButton harambe = findViewById(R.id.costume_harambe);
        ImageButton crab = findViewById(R.id.costume_crab);
        ImageButton pill = findViewById(R.id.costume_pill);

        defaulto.setOnClickListener(viewC -> otnCostumeSelected(0));
        animal.setOnClickListener(viewC -> otnCostumeSelected(1));
        moyai.setOnClickListener(viewC -> otnCostumeSelected(2));
        mug.setOnClickListener(viewC -> otnCostumeSelected(4));
        harambe.setOnClickListener(viewC -> otnCostumeSelected(3));
        crab.setOnClickListener(viewC -> otnCostumeSelected(5));
        pill.setOnClickListener(viewC -> otnCostumeSelected(6));
    }


    public void otnCostumeSelected(int costume) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_COSTUME, costume);
        setResult(RESULT_OK, intent);
        finish();
    }
}