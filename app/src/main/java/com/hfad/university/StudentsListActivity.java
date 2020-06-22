package com.hfad.university;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static com.hfad.university.University_Database.STUDENTS;
import static com.hfad.university.University_Database.St_Name;
import static com.hfad.university.University_Database.St_id;

public class StudentsListActivity extends Activity {
    SQLiteDatabase database;
    University_Database universityDatabase;
    Cursor cursor;
    ListView studentsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        studentsListView = findViewById(R.id.stList);
        try{
            universityDatabase = new University_Database(this);
            database = universityDatabase.getReadableDatabase();

            cursor = database.query(STUDENTS, new String[]{St_id, St_Name},
                    null, null, null, null, null);

            SimpleCursorAdapter studentsListAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{St_Name},
                    new int[] {android.R.id.text1}, 0);
            studentsListView.setAdapter(studentsListAdapter);

            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> listView, View view, int position, long id) {

                    Intent intent = new Intent(StudentsListActivity.this,
                                                                StudentsActivity.class);
                    intent.putExtra(StudentsActivity.Students_Number, (int) id);
                    startActivity(intent);
                }
            };
            studentsListView.setOnItemClickListener(itemClickListener);
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_students_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.change_stTable:
                intent = new Intent(StudentsListActivity.this,
                                                StudentsChangeActivity.class);
                startActivity(intent);
                break;

            case R.id.query_stTable:
                intent = new Intent(StudentsListActivity.this,
                                             UniversityQueryActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        studentsListView = findViewById(R.id.stList);
        universityDatabase = new University_Database(this);
        database = universityDatabase.getReadableDatabase();

        try {
            Cursor newStCursor = database.query(STUDENTS,
                            new String[]{St_id, St_Name},
                   null, null, null, null, null);
            CursorAdapter adapter = (CursorAdapter) studentsListView.getAdapter();
            adapter.changeCursor(newStCursor);
            cursor = newStCursor;
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        universityDatabase.close();
    }
}