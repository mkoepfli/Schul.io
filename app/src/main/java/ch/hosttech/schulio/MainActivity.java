package ch.hosttech.schulio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createNotification = findViewById(R.id.createNotification);

        createNotification.setOnClickListener(v -> {
            Intent resultIntent = new Intent(this, CreateNotification.class);
            startActivity(resultIntent);
        });
    }
}