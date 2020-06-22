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

import static com.hfad.university.University_Database.STUDENTS;
import static com.hfad.university.University_Database.St_Age;
import static com.hfad.university.University_Database.St_Debt;
import static com.hfad.university.University_Database.St_Department_id;
import static com.hfad.university.University_Database.St_Group;
import static com.hfad.university.University_Database.St_Name;
import static com.hfad.university.University_Database.St_id;

public class StudentsChangeActivity extends Activity implements View.OnClickListener {
    SQLiteDatabase database;
    University_Database universityDatabase;
    Button add_st, update_st, del_st;
    EditText st_id, st_name, st_age, st_group, st_debt, st_dep_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_change);

        universityDatabase = new University_Database(this);

        add_st = findViewById(R.id.addSt);
        add_st.setOnClickListener(this);
        update_st = findViewById(R.id.updateSt);
        update_st.setOnClickListener(this);
        del_st = findViewById(R.id.delSt);
        del_st.setOnClickListener(this);

        st_id = findViewById(R.id.change_stId);
        st_name = findViewById(R.id.change_stName);
        st_age = findViewById(R.id.change_stAge);
        st_group = findViewById(R.id.change_stGroup);
        st_debt = findViewById(R.id.change_stDebt);
        st_dep_id = findViewById(R.id.change_stDepId);
    }

    public void onClick(View view){
        String st_id_str = st_id.getText().toString();
        String st_name_str = st_name.getText().toString();
        String st_age_str = st_age.getText().toString();
        String st_group_str = st_group.getText().toString();
        String st_debt_str = st_debt.getText().toString();
        String st_dep_id_str = st_dep_id.getText().toString();
        ContentValues redValues;

        try {
            database = universityDatabase.getWritableDatabase();
            redValues = new ContentValues();

            switch (view.getId()){
                case R.id.addSt:
                    redValues.put(St_Name, st_name_str);
                    redValues.put(St_Age, st_age_str);
                    redValues.put(St_Group, st_group_str);
                    redValues.put(St_Debt, st_debt_str);
                    redValues.put(St_Department_id, st_dep_id_str);
                    database.insert(STUDENTS, null, redValues);
                    break;

                case R.id.updateSt:
                    if (st_id_str.equalsIgnoreCase("")){
                        break;
                    }
                    redValues.put(St_id , st_id_str);
                    redValues.put(St_Name, st_name_str);
                    redValues.put(St_Age, st_age_str);
                    redValues.put(St_Group, st_group_str);
                    redValues.put(St_Debt, st_debt_str);
                    redValues.put(St_Department_id, st_dep_id_str);
                    database.update(STUDENTS, redValues, St_id
                                        + " = ?", new String[]{st_id_str});
                    break;

                case R.id.delSt:
                    if (st_id_str.equalsIgnoreCase("")&&
                        st_age_str.equalsIgnoreCase("")){
                        break;
                    }

                    database.delete(STUDENTS, St_id + " = ? OR " + St_Age
                                      + " = ?", new String[]{st_id_str, st_age_str});
                    break;
            }
            universityDatabase.close();
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
