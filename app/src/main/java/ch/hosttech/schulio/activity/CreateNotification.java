package ch.hosttech.schulio.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import ch.hosttech.schulio.R;

public class CreateNotification extends AppCompatActivity {
    private static final String CHANNEL_ID = "defaultChannel";
    private static final String CHANNEL_NAME = "Default Channel";

    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);

        EditText subjectName = findViewById(R.id.subjectName);
        EditText testName = findViewById(R.id.testName);

        Button button = findViewById(R.id.average);
        button.setOnClickListener(v -> sendNotification(subjectName.getText().toString(), testName.getText().toString()));

        this.notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotification(String subjectName, String testName) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle("Schul.io Testerinnerung!")
                .setContentText(subjectName + " Prüfung: " + testName)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(0, builder.build());
    }

}