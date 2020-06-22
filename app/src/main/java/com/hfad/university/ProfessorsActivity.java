package com.hfad.university;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import static com.hfad.university.University_Database.PROFESSORS;
import static com.hfad.university.University_Database.Pr_id;
import static com.hfad.university.University_Database.Pr_Name;
import static com.hfad.university.University_Database.Post;
import static com.hfad.university.University_Database.Specialty;
import static com.hfad.university.University_Database.Work_Experience;
import static com.hfad.university.University_Database.Pr_Department_id;

public class ProfessorsActivity extends Activity {

    public static final String Professors_Number = "pr_Number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professors);

        int pr_Number = (Integer)getIntent().getExtras().get(Professors_Number);

        try {
            SQLiteOpenHelper universityDatabase= new University_Database(this);
            SQLiteDatabase database = universityDatabase.getWritableDatabase();

            Cursor cursor = database.rawQuery("SELECT* FROM " + PROFESSORS + " WHERE " + Pr_id + " =?",
                    new String[]{Integer.toString(pr_Number)});

            if (cursor.moveToNext()){
                String pr_name = cursor.getString(cursor.getColumnIndex(Pr_Name));
                String pr_post = cursor.getString(cursor.getColumnIndex(Post));
                String pr_specialty = cursor.getString(cursor.getColumnIndex(Specialty));
                String work_exp = cursor.getString(cursor.getColumnIndex(Work_Experience));
                String pr_dep_id = cursor.getString(cursor.getColumnIndex(Pr_Department_id));

                TextView prName = findViewById(R.id.prName);
                prName.append(Pr_Name + ":\n" + pr_name);

                TextView prPost = findViewById(R.id.prPost);
                prPost.append(Post + ":\n" + pr_post);

                TextView prSpesialty = findViewById(R.id.prSpec);
                prSpesialty.append(Specialty + ":\n" + pr_specialty);

                TextView wordExp = findViewById(R.id.workExp);
                wordExp.append(Work_Experience + ":\n" +work_exp);

                TextView prDepID = findViewById(R.id.prDepID);
                prDepID.append(Pr_Department_id + ":\n" + pr_dep_id);
            }
            cursor.close();
            database.close();
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}