package ro.pub.cs.systems.eim.colocviu1_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Colocviu1_2SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            Intent resultIntent = new Intent();
            if (extras != null) {
                resultIntent.putExtra("sum", Integer.toString(extras.getInt("sum")));
                System.out.println(extras.getInt("sum"));
            }
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }
}