package ch.hosttech.schulio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class singleSubject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_subject);

        Intent singleSubject = getIntent(); // gets the previously created intent
        String getSubjectName = singleSubject.getStringExtra("subjectName");

        TextView subjectName = findViewById(R.id.subjectName);
        subjectName.setText(getSubjectName);

        getNotesFromSubject();

    }

    private void getNotesFromSubject() {
    }
}