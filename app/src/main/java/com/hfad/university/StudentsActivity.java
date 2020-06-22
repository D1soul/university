package com.hfad.university;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import static com.hfad.university.University_Database.STUDENTS;
import static com.hfad.university.University_Database.St_Age;
import static com.hfad.university.University_Database.St_Debt;
import static com.hfad.university.University_Database.St_Group;
import static com.hfad.university.University_Database.St_Name;
import static com.hfad.university.University_Database.St_id;

public class StudentsActivity extends Activity {

    public static final String Students_Number = "st_Number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        int st_Number = (Integer) getIntent().getExtras().get(Students_Number);

        try {
            SQLiteOpenHelper universityDatabase = new University_Database(this);
            SQLiteDatabase database = universityDatabase.getWritableDatabase();

            Cursor cursor = database.query(STUDENTS,
                    new String[]{St_Name, St_Age, St_Group, St_Debt},
                   St_id + " = ?",
                    new String[]{Integer.toString(st_Number)},
                    null, null, null);

            if (cursor.moveToNext()) {
                String st_name = cursor.getString(cursor.getColumnIndex(St_Name));
                String st_age = cursor.getString(cursor.getColumnIndex(St_Age));
                String st_group = cursor.getString(cursor.getColumnIndex(St_Group));
                String st_debt = cursor.getString(cursor.getColumnIndex(St_Debt));

                TextView stName = findViewById(R.id.stName);
                stName.append(St_Name + ":\n" + st_name);

                TextView stAge = findViewById(R.id.stAge);
                stAge.append(St_Age + ":\n" + st_age);

                TextView stGroup = findViewById(R.id.stGroup);
                stGroup.append(St_Group + ":\n" + st_group);

                TextView stDebt = findViewById(R.id.stDebt);
                stDebt.append(St_Debt + ":\n" + st_debt);

            }
            cursor.close();
            database.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}