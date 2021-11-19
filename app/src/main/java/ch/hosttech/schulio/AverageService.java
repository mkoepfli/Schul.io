package ch.hosttech.schulio;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;

public class AverageService extends Service {

    private final IBinder binder = new AvergaeBinder();

    public class AvergaeBinder extends Binder {
        public AverageService getService() {
            return AverageService.this;
        }
    }

    public AverageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public float getAverage(ArrayList<Object> allMarks){

        int size = allMarks.size();
        float king = 0;
        for( int i = 0; i < allMarks.size(); i++ )
        {
            Object tester = allMarks.get(i);
            float x = ((Number)tester).floatValue();
            king = king + x;

        }

        return king / size;
    }
}