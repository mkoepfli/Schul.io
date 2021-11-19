package ch.hosttech.schulio.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.hosttech.schulio.DBHandler;
import ch.hosttech.schulio.R;

public class addSubject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        EditText subjectName = findViewById(R.id.subjectName);
        Button addSubject = findViewById(R.id.average);

        DBHandler dbHandler = new DBHandler(addSubject.this);

        addSubject.setOnClickListener(v -> {
            // below line is to get data from all edit text fields.
            String subName = subjectName.getText().toString();

            // validating if the text fields are empty or not.
            if (subName.isEmpty()) {
                Toast.makeText(addSubject.this, "Bitte Geben Sie einen Fachnamen ein..", Toast.LENGTH_SHORT).show();
                return;
            }

            // on below line we are calling a method to add new
            // course to sqlite data and pass all our values to it.
            dbHandler.addNewSubject(subName);

            Intent singleSubject = new Intent(this, MainActivity.class);
            startActivity(singleSubject);
        });
    }
}