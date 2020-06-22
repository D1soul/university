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
import static com.hfad.university.University_Database.PROFESSORS;
import static com.hfad.university.University_Database.Pr_id;
import static com.hfad.university.University_Database.Pr_Name;
import static com.hfad.university.University_Database.Post;
import static com.hfad.university.University_Database.STUDENTS;
import static com.hfad.university.University_Database.Specialty;
import static com.hfad.university.University_Database.St_Age;
import static com.hfad.university.University_Database.St_Debt;
import static com.hfad.university.University_Database.St_Department_id;
import static com.hfad.university.University_Database.St_Group;
import static com.hfad.university.University_Database.St_Name;
import static com.hfad.university.University_Database.St_id;
import static com.hfad.university.University_Database.Work_Experience;
import static com.hfad.university.University_Database.Pr_Department_id;

public class ProfessorsChangeActivity extends Activity implements View.OnClickListener {
    SQLiteDatabase database;
    University_Database universityDatabase;
    Button add_pr, update_pr, del_pr;
    EditText pr_id, pr_name, post, spec, work_exp, pr_dep_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professors_change);

        universityDatabase = new University_Database(this);

        add_pr = findViewById(R.id.addPr);
        add_pr.setOnClickListener(this);
        update_pr = findViewById(R.id.updatePr);
        update_pr.setOnClickListener(this);
        del_pr = findViewById(R.id.delPr);
        del_pr.setOnClickListener(this);

        pr_id = findViewById(R.id.change_prId);
        pr_name = findViewById(R.id.change_prName);
        post = findViewById(R.id.change_prPost);
        spec = findViewById(R.id.change_prSpec);
        work_exp = findViewById(R.id.change_workExp);
        pr_dep_id = findViewById(R.id.change_prDep);
    }

    public void onClick(View view){
        String pr_id_str = pr_id.getText().toString();
        String pr_name_str = pr_name.getText().toString();
        String post_str = post.getText().toString();
        String spec_str = spec.getText().toString();
        String work_exp_str = work_exp.getText().toString();
        String pr_dep_id_str = pr_dep_id.getText().toString();
        ContentValues redValues;

        try {
            database = universityDatabase.getWritableDatabase();
            redValues = new ContentValues();

            switch (view.getId()){
                case R.id.addPr:
                    redValues.put(Pr_id, pr_id_str);
                    redValues.put(Pr_Name, pr_name_str);
                    redValues.put(Post, post_str);
                    redValues.put(Specialty, spec_str);
                    redValues.put(Work_Experience, work_exp_str);
                    redValues.put(Pr_Department_id, pr_dep_id_str);
                    database.insert(PROFESSORS, null, redValues);
                    break;

                case R.id.updatePr:
                    if (pr_id_str.equalsIgnoreCase("")){
                        break;
                    }
                    redValues.put(Pr_id, pr_id_str);
                    redValues.put(Pr_Name, pr_name_str);
                    redValues.put(Post, post_str);
                    redValues.put(Specialty, spec_str);
                    redValues.put(Work_Experience, work_exp_str);
                    redValues.put(Pr_Department_id, pr_dep_id_str);
                    database.update(PROFESSORS, redValues, Pr_id
                            + " = ?", new String[]{pr_id_str});
                    break;


                case R.id.delPr:
                    if (pr_id_str.equalsIgnoreCase("")){
                        break;
                    }
                    database.delete(PROFESSORS, Pr_id + " =?",
                            new String[]{pr_id_str});
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
