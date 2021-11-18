package ch.hosttech.schulio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<SubjectModal> subjectModalArrayList;
    private DBHandler dbHandler;
    private SubjectRVAdapter subjectRVAdapter;
    private RecyclerView subjectRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createNotification = findViewById(R.id.createNotification);
        Button addSubject = findViewById(R.id.addSubject);

        createNotification.setOnClickListener(v -> {
            Intent resultIntent = new Intent(this, CreateNotification.class);
            startActivity(resultIntent);
        });

        addSubject.setOnClickListener(v -> {
            Intent resultIntent = new Intent(this, addSubject.class);
            startActivity(resultIntent);
        });

        loadAllSubjects();
    }

    @SuppressLint("ResourceAsColor")
    private void loadAllSubjects() {
        // initializing our all variables.
        subjectModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(MainActivity.this);

        // getting our course array
        // list from db handler class.
        subjectModalArrayList = dbHandler.readSubjects();

        int size = subjectModalArrayList.size();

        View linearLayout =  findViewById(R.id.ll_example);

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

            ShapeDrawable shape = new ShapeDrawable(new RectShape());

            shape.getPaint().setColor(Color.BLACK);
            shape.getPaint().setStyle(Paint.Style.STROKE);
            shape.getPaint().setStrokeWidth(3);

            subject.setBackground(shape);

            subject.setOnClickListener(v -> {
                Intent singleSubject = new Intent(this, singleSubject.class);
                singleSubject.putExtra("subjectName", modal.getSubjectName());
                startActivity(singleSubject);
            });

            ((LinearLayout) linearLayout).addView(subject);
        }


    }
}