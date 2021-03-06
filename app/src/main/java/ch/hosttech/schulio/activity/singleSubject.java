package ch.hosttech.schulio.activity;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ch.hosttech.schulio.DBHandler;
import ch.hosttech.schulio.R;
import ch.hosttech.schulio.model.MarkModal;
import ch.hosttech.schulio.service.AverageService;


public class singleSubject extends AppCompatActivity {

    private ArrayList<MarkModal> markModalArrayList;
    private DBHandler dbHandler;
    private AverageService averageService;
    private boolean isAverageServiceBound = false;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View root;
    private float maxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_subject);

        Intent singleSubject = getIntent(); // gets the previously created intent
        String getSubjectName = singleSubject.getStringExtra("subjectName");
        TextView subjectName = findViewById(R.id.subjectName);
        subjectName.setText(getSubjectName);

        TextView addMark = findViewById(R.id.addMark);
        TextView goBack = findViewById(R.id.goBack);

        addMark.setOnClickListener(v -> {
            Intent addMarkA = new Intent(this, addMark.class);
            addMarkA.putExtra("subjectName", getSubjectName);
            startActivity(addMarkA);
        });

        goBack.setOnClickListener(v -> {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        });

        onStart();
        getNotesFromSubject(getSubjectName);

        // crate light sensor
        root = findViewById(R.id.all);
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

    @Override
    protected void onStart() {
        super.onStart();
        Intent bindBMIServiceIntent = new Intent(this, AverageService.class);
        bindService(bindBMIServiceIntent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        isAverageServiceBound = false;
    }

    private void populateView() {
        Intent singleSubject = getIntent(); // gets the previously created intent
        String getSubjectName = singleSubject.getStringExtra("subjectName");
        Button average = findViewById(R.id.average);

        if (isAverageServiceBound) {
            markModalArrayList = new ArrayList<>();
            ArrayList<Object> allMarks = new ArrayList<>();
            dbHandler = new DBHandler(singleSubject.this);

            markModalArrayList = dbHandler.readMarks(getSubjectName);

            for( int i = 0; i < markModalArrayList.size(); i++ )
            {
                MarkModal modal = markModalArrayList.get(i);
                allMarks.add(modal.getMark());
            }

            float averageMark = averageService.getAverage(allMarks);
            average.setText("Durchschnitt: " + averageMark);
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            AverageService.AvergaeBinder binder = (AverageService.AvergaeBinder)  iBinder;
            averageService = binder.getService();
            isAverageServiceBound = true;
            populateView();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isAverageServiceBound = false;
        }
    };

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void getNotesFromSubject(String subjectName) {
        markModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(singleSubject.this);

        // getting our course array
        // list from db handler class.
        markModalArrayList = dbHandler.readMarks(subjectName);

        View linearLayout =  findViewById(R.id.ll_example);

        for( int i = 0; i < markModalArrayList.size(); i++ )
        {
            MarkModal modal = markModalArrayList.get(i);

            TextView mark = new TextView(this);
            mark.setText(modal.getTestName() + "   " + modal.getMark());
            mark.setId(modal.getId());
            mark.setTextColor(R.color.black);
            mark.setPadding(25, 0 ,0 ,0);
            mark.setGravity(Gravity.CENTER_VERTICAL);
            mark.setBackgroundColor(getBackroundColor(modal.getMark()));
            mark.setHeight(130);

            mark.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            ((LinearLayout) linearLayout).addView(mark);
        }
    }

    private int getBackroundColor(float mark) {
        if (mark > 5){
            return Color.parseColor("#D5E8D4");
        } else if (mark > 4) {
            return Color.parseColor("#FFF2CC");
        } else {
            return Color.parseColor("#F8CECC");
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