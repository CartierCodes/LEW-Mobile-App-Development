package edu.lewisu.cs.example.calendarprovider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Integer> mCalIds;

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 123;
    private Boolean mCalendarPermission = false;
    private ArrayList<String> mCalDisplayNames;
    private EditText mEventTextView;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEventTextView = findViewById(R.id.eventTextView);
        mSpinner = findViewById(R.id.calendarSpinner);
        mCalDisplayNames = new ArrayList<>();
        mCalIds = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestCalendarPermission();
        if(mCalendarPermission){
            queryCalendar();
        }
    }


    private void queryCalendar(){
        Uri uri = CalendarContract.Calendars.CONTENT_URI;

        String selection = CalendarContract.Calendars.VISIBLE + "=1";

        String[] calendarsProjection = new String[]{
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.VISIBLE
        };

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, calendarsProjection, selection, null, null);

        if(cursor != null && cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                String calName = cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME));
                int calId = (int) cursor.getLong(cursor.getColumnIndex(CalendarContract.Calendars._ID));
                mCalIds.add(calId);
                mCalDisplayNames.add(calName);
            }
        }
        if (cursor != null) {
            cursor.close();
        }

        if(mCalDisplayNames.size() == 0){
            mCalDisplayNames.add("no calendars found");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Uri eventsUri = CalendarContract.Events.CONTENT_URI;

                Calendar now = Calendar.getInstance();
                long nowMillis = now.getTimeInMillis();

                String selection = "((" + CalendarContract.Events.CALENDAR_ID + " = ? ) AND (" +
                        CalendarContract.Events.DTSTART + " > ?))";

                String [] selectionArgs = new String[]{
                        Integer.toString(mCalIds.get(position)),
                        Long.toString(nowMillis)
                };

                String [] projection = new String[]{
                        CalendarContract.Events._ID,
                        CalendarContract.Events.TITLE,
                        CalendarContract.Events.DTSTART,
                        CalendarContract.Events.CALENDAR_ID
                };

                Cursor eventCursor = resolver.query(eventsUri, projection, selection, selectionArgs, null);

                StringBuilder eventText = new StringBuilder("");
                if(eventCursor != null && eventCursor.getCount() > 0) {
                    while(eventCursor.moveToNext()){
                        String eventTitle = eventCursor.getString(eventCursor.getColumnIndex(CalendarContract.Events.TITLE));
                        eventText.append(eventTitle).append("\n");
                    }
                }
                if(eventCursor != null) {
                    eventCursor.close();
                }
                mEventTextView.setText(eventText.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**********************PERMISSION HANDLING******************************************************/



    //if permission has been granted run query otherwise request permission to read calendar
    private void requestCalendarPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_CALENDAR)
                == PackageManager.PERMISSION_GRANTED) {
            mCalendarPermission = true;

        }else{
            //request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALENDAR},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_PERMISSIONS_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCalendarPermission = true;

                }
            }
        }

    }
}