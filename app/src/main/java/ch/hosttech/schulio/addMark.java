package ch.hosttech.schulio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class addMark extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mark);

        EditText getTestName = findViewById(R.id.testName);
        EditText getTestMark = findViewById(R.id.testMark);
        Button photoButton = (Button) this.findViewById(R.id.doPhoto);
        ImageView imageView = findViewById(R.id.imageView);

        DBHandler dbHandler = new DBHandler(addMark.this);

        Intent addMarkA = getIntent(); // gets the previously created intent
        String subjectName = addMarkA.getStringExtra("subjectName");

        Button addMark = findViewById(R.id.addMark);

        addMark.setOnClickListener(v -> {
            String testName = getTestName.getText().toString();
            String testMarkString = getTestMark.getText().toString();
            float testMark = Float.parseFloat(testMarkString);

            // validating if the text fields are empty or not.
            if (testName.isEmpty()) {
                Toast.makeText(addMark.this, "Bitte Geben Sie den Prüfungsnamen ein..", Toast.LENGTH_SHORT).show();
                return;
            }

            // on below line we are calling a method to add new
            // course to sqlite data and pass all our values to it.
            dbHandler.addNewMark(subjectName, testName, testMark);

            Intent singSubject = new Intent(this, singleSubject.class);
            singSubject.putExtra("subjectName", subjectName);
            startActivity(singSubject);
        });

        if (ContextCompat.checkSelfPermission(addMark.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(addMark.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }

        photoButton.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        }
    }
}