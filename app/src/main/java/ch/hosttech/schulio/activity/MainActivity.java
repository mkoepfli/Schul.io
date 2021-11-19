package ch.hosttech.schulio.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import ch.hosttech.schulio.DBHandler;
import ch.hosttech.schulio.R;
import ch.hosttech.schulio.model.SubjectModal;

public class MainActivity extends AppCompatActivity {

    private ArrayList<SubjectModal> subjectModalArrayList;
    private DBHandler dbHandler;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View root;
    private float maxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createNotification = findViewById(R.id.createNotification);
        Button addSubject = findViewById(R.id.average);

        createNotification.setOnClickListener(v -> {
            Intent resultIntent = new Intent(this, CreateNotification.class);
            startActivity(resultIntent);
        });

        addSubject.setOnClickListener(v -> {
            Intent resultIntent = new Intent(this, addSubject.class);
            startActivity(resultIntent);
        });

        loadAllSubjects();

        root = findViewById(R.id.ll_example);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            finish();
        }

        // max value for light sensor
        maxValue = lightSensor.getMaximumRange();

        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];

                // between 0 and 255
                int newValue = (int) (255f * value / maxValue);
                root.setBackgroundColor(Color.rgb(newValue, newValue, newValue));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    @SuppressLint("ResourceAsColor")
    private void loadAllSubjects() {
        // initializing our all variables.
        subjectModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(MainActivity.this);

        // getting our course array
        // list from db handler class.
        subjectModalArrayList = dbHandler.readSubjects();

        View linearLayout =  findViewById(R.id.ll_example);

        // foreach subjectModal it should create a new Textview
        // under linearLayout
        for( int i = 0; i < subjectModalArrayList.size(); i++ )
        {
            SubjectModal modal = subjectModalArrayList.get(i);
            TextView subject = new TextView(this);
            subject.setText(modal.getSubjectName());
            subject.setId(modal.getId());
            subject.setTextColor(R.color.black);
            subject.setPadding(25, 0 ,0 ,0);
            subject.setGravity(Gravity.CENTER_VERTICAL);
            subject.setBackgroundColor(Color.parseColor("#FFFFC107"));
            subject.setHeight(130);

            subject.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            subject.setOnClickListener(v -> {
                Intent singleSubject = new Intent(this, singleSubject.class);
                singleSubject.putExtra("subjectName", modal.getSubjectName());
                startActivity(singleSubject);
            });

            ((LinearLayout) linearLayout).addView(subject);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }
}