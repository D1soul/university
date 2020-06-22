package com.hfad.university;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.database.sqlite.SQLiteException;

import static com.hfad.university.University_Database.DEPARTMENT;
import static com.hfad.university.University_Database.Dep_Name;
import static com.hfad.university.University_Database.Dep_id;

public class DepartmentsListActivity extends Activity {
    SQLiteDatabase database;
    University_Database universityDatabase;
    Cursor cursor;
    ListView departmentsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments_list);

        departmentsListView = findViewById(R.id.dep_list);
        try {
            universityDatabase = new University_Database(this);
            database = universityDatabase.getReadableDatabase();

            cursor = database.query(DEPARTMENT, new String[]{Dep_id, Dep_Name},
                    null, null, null, null, null);

            SimpleCursorAdapter depListAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{Dep_Name},
                    new int[]{android.R.id.text1}, 0);
            departmentsListView.setAdapter(depListAdapter);

            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> listView, View view, int position, long id) {

                    Intent intent = new Intent(DepartmentsListActivity.this, DepartmentsActivity.class);
                    intent.putExtra(DepartmentsActivity.Departments_Number, (int) id);
                    startActivity(intent);
                }
            };
            departmentsListView.setOnItemClickListener(itemClickListener);
        }
        catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_departments_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.change_depTable:
                intent = new Intent(DepartmentsListActivity.this, DepartmentsChangeActivity.class);
                startActivity(intent);
                break;

            case R.id.query_depTable:
                intent = new Intent(DepartmentsListActivity.this, UniversityQueryActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        departmentsListView = findViewById(R.id.dep_list);

        try {
             universityDatabase = new University_Database(this);
             database = universityDatabase.getReadableDatabase();

             Cursor newDepCursor = database.query(DEPARTMENT,
                     new String[]{Dep_id, Dep_Name},
                    null, null, null, null, null);
             CursorAdapter adapter = (CursorAdapter)departmentsListView.getAdapter();
             adapter.changeCursor(newDepCursor);
             cursor = newDepCursor;
        }
        catch (SQLiteException e) {
             Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
             toast.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        universityDatabase.close();
    }
}