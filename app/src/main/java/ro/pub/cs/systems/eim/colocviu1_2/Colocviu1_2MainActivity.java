package ro.pub.cs.systems.eim.colocviu1_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Colocviu1_2MainActivity extends AppCompatActivity {

    Button addTermButton;
    Button computeButton;
    TextView sumView;
    EditText addTermText;
    int sum = 0;
    int lastSum = -1;

    public static boolean isNumeric(String string) {
        int intValue;

        if(string == null || string.equals("")) {
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("ro.pub.cs.systems.eim.practicaltest01.broadcastreceiverextra", intent.getStringExtra("ro.pub.cs.systems.eim.practicaltest01.broadcastreceiverextra"));
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_2_main);

        addTermButton = (Button) findViewById(R.id.addTermButton);
        computeButton = (Button) findViewById(R.id.computeButton);
        sumView = (TextView) findViewById(R.id.sumView);
        addTermText = (EditText) findViewById(R.id.addTermText);

        addTermButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = addTermText.getText().toString();
                if (sumView.getText().toString().length() < 1) {
                    if (input.length() > 0 && isNumeric(input)) {
                        sumView.setText(input);
                        sum += Integer.parseInt(input);
                        startPracticalService();
                    }
                }
                else {
                    if (input.length() > 0 && isNumeric(input)) {
                        String output = sumView.getText().toString() + " + " + input;
                        sumView.setText(output);
                        sum += Integer.parseInt(input);
                        startPracticalService();
                    }
                }
            }
        }));

        computeButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastSum == sum) {
                    System.out.println("Intent not invoked!");
                    Toast.makeText(Colocviu1_2MainActivity.this, Integer.toString(sum), Toast.LENGTH_LONG).show();
                    return;
                }
                lastSum = sum;
                Intent intent = new Intent(getApplicationContext(), Colocviu1_2SecondaryActivity.class);
                intent.putExtra("sum", sum);
                startActivityForResult(intent, 1);
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("sum");

                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("sum", sum);
        outState.putString("sumString", sumView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        sum = savedInstanceState.getInt("sum");
        sumView.setText(savedInstanceState.getString("sumString"));
    }

    private void startPracticalService() {
        if (sum > 10) {
            Intent intent = new Intent(getApplicationContext(), Colocviu1_2Service.class);
            intent.putExtra("sum", sum);
            getApplicationContext().startService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), Colocviu1_2Service.class);
        getApplicationContext().stopService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
        Toast.makeText(Colocviu1_2MainActivity.this, Integer.toString(sum), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
        Toast.makeText(Colocviu1_2MainActivity.this, Integer.toString(sum), Toast.LENGTH_LONG).show();
    }
}