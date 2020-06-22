package com.hfad.university;

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

import static com.hfad.university.University_Database.PROFESSORS;
import static com.hfad.university.University_Database.Pr_id;
import static com.hfad.university.University_Database.Pr_Name;

public class ProfessorsListActivity extends Activity {
    SQLiteDatabase database;
    University_Database universityDatabase;
    Cursor cursor;
    ListView professorsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professors_list);

        universityDatabase = new University_Database(this);
        try{
            professorsListView = findViewById(R.id.prList);
            database = universityDatabase.getWritableDatabase();
            cursor = database.rawQuery("SELECT " + Pr_id + ","
                               + Pr_Name  + " FROM " + PROFESSORS,
                                                null);

            SimpleCursorAdapter professorsListAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{Pr_Name},
                    new int[] {android.R.id.text1}, 0);
            professorsListView.setAdapter(professorsListAdapter);

            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> listView, View view, int position, long id) {

                    Intent intent = new Intent(ProfessorsListActivity.this,
                                                                ProfessorsActivity.class);
                    intent.putExtra(ProfessorsActivity.Professors_Number, (int) id);
                    startActivity(intent);
                }
            };
            professorsListView.setOnItemClickListener(itemClickListener);
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_professors_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.change_prTable:
                intent = new Intent(ProfessorsListActivity.this,
                                                ProfessorsChangeActivity.class);
                startActivity(intent);
                break;

            case R.id.query_prTable:
                intent = new Intent(ProfessorsListActivity.this,
                                                UniversityQueryActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        professorsListView = findViewById(R.id.prList);

        try {
            universityDatabase = new University_Database(this);
            database = universityDatabase.getReadableDatabase();

            Cursor newPrCursor = database.rawQuery("SELECT " + Pr_id + "," + Pr_Name
                                                + " FROM " + PROFESSORS, null);
            CursorAdapter adapter = (CursorAdapter) professorsListView.getAdapter();
            adapter.changeCursor(newPrCursor);
            cursor = newPrCursor;
        }
        catch (SQLiteException e){
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
