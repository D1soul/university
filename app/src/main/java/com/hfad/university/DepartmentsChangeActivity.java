package com.hfad.university;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import android.database.sqlite.SQLiteException;

import static com.hfad.university.University_Database.DEPARTMENT;
import static com.hfad.university.University_Database.Dep_Name;
import static com.hfad.university.University_Database.Dep_Subjects;
import static com.hfad.university.University_Database.Dep_id;
import static com.hfad.university.University_Database.Head_of_Department;

public class DepartmentsChangeActivity extends Activity implements View.OnClickListener {
    SQLiteDatabase database;
    University_Database universityDatabase;
    Button add_dep, update_dep, del_dep;
    EditText dep_id, dep_name, dep_subjects, head_of_dep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments_change);

        universityDatabase = new University_Database(this);

        add_dep = findViewById(R.id.addDep);
        add_dep.setOnClickListener(this);
        update_dep = findViewById(R.id.updateDep);
        update_dep.setOnClickListener(this);
        del_dep = findViewById(R.id.delDep);
        del_dep.setOnClickListener(this);

        dep_id = findViewById(R.id.change_depId);
        dep_name = findViewById(R.id.change_depName);
        dep_subjects = findViewById(R.id.change_depSubjects);
        head_of_dep = findViewById(R.id.change_headOfDep);
    }

public void onClick(View view) {
    String dep_id_str = dep_id.getText().toString();
    String dep_name_str = dep_name.getText().toString();
    String dep_subjects_str = dep_subjects.getText().toString();
    String head_of_dep_str = head_of_dep.getText().toString();
    ContentValues redValues;

    try {
        database = universityDatabase.getWritableDatabase();
        redValues = new ContentValues();

        switch (view.getId()) {
            case R.id.addDep:
                redValues.put(Dep_id, dep_id_str);
                redValues.put(Dep_Name, dep_name_str);
                redValues.put(Dep_Subjects, dep_subjects_str);
                redValues.put(Head_of_Department, head_of_dep_str);
                database.insert(DEPARTMENT, null, redValues);
                break;

            case R.id.updateDep:
                if (dep_id_str.equalsIgnoreCase("")) {
                    break;
                }
                redValues.put(Dep_id, dep_id_str);
                redValues.put(Dep_Name, dep_name_str);
                redValues.put(Dep_Subjects, dep_subjects_str);
                redValues.put(Head_of_Department, head_of_dep_str);
                database.update(DEPARTMENT, redValues, Dep_id + " = ?",
                                                          new String[]{dep_id_str});
                break;


            case R.id.delDep:
                if (dep_id_str.equalsIgnoreCase("") &&
                    dep_name_str.equalsIgnoreCase("")){
                    break;
                }
                database.delete(DEPARTMENT,
                               Dep_id + " = ? OR " + Dep_Name + " = ?",
                                            new String[]{dep_id_str, dep_name_str});
                break;
            }
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this,
                                        "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
