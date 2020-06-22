package com.hfad.university;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import static com.hfad.university.University_Database.DEPARTMENT;
import static com.hfad.university.University_Database.Dep_Name;
import static com.hfad.university.University_Database.Dep_Subjects;
import static com.hfad.university.University_Database.Dep_id;
import static com.hfad.university.University_Database.Head_of_Department;

public class DepartmentsActivity extends Activity {

    public static final String Departments_Number = "dep_Number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);

        int dep_Number = (Integer)getIntent().getExtras().get(Departments_Number);
        try {
            SQLiteOpenHelper universityDatabase= new University_Database(this);
            SQLiteDatabase database = universityDatabase.getWritableDatabase();

            Cursor cursor = database.query(DEPARTMENT,
                   new String[]{Dep_Name, Dep_Subjects, Head_of_Department},
                    Dep_id + " = ?",
                   new String[]{Integer.toString(dep_Number)},
                   null, null, null);

           if (cursor.moveToNext()){
                String dep_name = cursor.getString(cursor.getColumnIndex(Dep_Name));
                String dep_subjects = cursor.getString(cursor.getColumnIndex(Dep_Subjects));
                String head_ofDep = cursor.getString(cursor.getColumnIndex(Head_of_Department));

                TextView depName = findViewById(R.id.depName);
                depName.append(Dep_Name +":\n" + dep_name);

                TextView depSubjects = findViewById(R.id.depSubjects);
                depSubjects.append(Dep_Subjects +":\n"+dep_subjects);

                TextView headOfDep = findViewById(R.id.headOfDep);
                headOfDep.append(Head_of_Department + ":\n" + head_ofDep);
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
