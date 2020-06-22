package com.hfad.university;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteException;

import static com.hfad.university.University_Database.DEPARTMENT;
import static com.hfad.university.University_Database.Dep_Name;
import static com.hfad.university.University_Database.Dep_Subjects;
import static com.hfad.university.University_Database.Dep_id;
import static com.hfad.university.University_Database.Head_of_Department;

import static com.hfad.university.University_Database.PROFESSORS;
import static com.hfad.university.University_Database.Post;
import static com.hfad.university.University_Database.Pr_Department_id;
import static com.hfad.university.University_Database.Pr_Name;
import static com.hfad.university.University_Database.Pr_id;

import static com.hfad.university.University_Database.STUDENTS;
import static com.hfad.university.University_Database.Specialty;
import static com.hfad.university.University_Database.St_Age;
import static com.hfad.university.University_Database.St_Debt;
import static com.hfad.university.University_Database.St_Department_id;
import static com.hfad.university.University_Database.St_Group;
import static com.hfad.university.University_Database.St_Name;
import static com.hfad.university.University_Database.St_id;
import static com.hfad.university.University_Database.Work_Experience;

public class UniversityQueryActivity extends Activity implements View.OnClickListener {

    SQLiteDatabase database;
    University_Database universityDatabase;
    Button show_res;
    EditText first_query, second_query;
    TextView first_lable, second_lable, first_result, second_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_query);

            universityDatabase = new University_Database(this);
            show_res = findViewById(R.id.showResult);
            show_res.setOnClickListener(this);

            first_lable = findViewById(R.id.first_lable);
            second_lable = findViewById(R.id.second_lable);

            first_query = findViewById(R.id.query_First);
            second_query = findViewById(R.id.query_Second);

            first_result = findViewById(R.id.result_First);
            second_result = findViewById(R.id.result_Second);
        }

    public void onClick(View view) {
        String getFirstResult = first_query.getText().toString();
        String getSecondResult = second_query.getText().toString();

        try {
            database = universityDatabase.getWritableDatabase();

            //8. Вывести имена всех преподавателей с опытом работы более вводимого числа.
            Cursor cursor = database.query(PROFESSORS,
                    new String[]{Pr_id, Pr_Name, Work_Experience}, Work_Experience + " > ?",
                    new String[]{getFirstResult}, null, null, null);

            first_result.setText(null);
            while (cursor.moveToNext() && cursor.getCount() >= 1) {
                String first = cursor.getString(cursor.getColumnIndex(Pr_Name));
                first_result.append(first + "\n");
            }


            //24. Вывести имена всех преподавателей находящихся в кафедре с вводимым именем заведующего кафедрой.
            cursor = database.rawQuery("SELECT " + PROFESSORS +"."+ Pr_Name
                            + " FROM " + PROFESSORS + " INNER JOIN " + DEPARTMENT
                            + " ON " + PROFESSORS +"."+ Pr_Department_id + " = "
                            + DEPARTMENT +"."+ Dep_id
                            + " WHERE " + DEPARTMENT +"."+ Head_of_Department + " =?; ",
                    new String[]{getSecondResult});


            second_result.setText(null);
            while (cursor.moveToNext()&& cursor.getCount() >= 1){
                String second = cursor.getString(cursor.getColumnIndex(Pr_Name));
                second_result.append(second + "\n");
            }



/*
              //3. Вывести Id всех студентов с количеством долгов менее вводимого числа.
              cursor = database.query(STUDENTS,
                       new String[]{St_id, St_Name, St_Debt}, St_Debt + " < ?",
                       new String[]{getSecondResult}, null, null, null);

              first_result.setText(null);
              while (cursor.moveToNext() && cursor.getCount() >= 1) {
                 String first = cursor.getString(cursor.getColumnIndex(Pr_id));
                 first_result.append(first + "\n");
              }



              //16. Вывести названия всех кафедр в которых количество долгов у студентов меньше вводимого числа.
              cursor = database.rawQuery("SELECT " + DEPARTMENT +"."+ Dep_Name
                               + " FROM " + STUDENTS + " INNER JOIN " + DEPARTMENT
                               + " ON " + STUDENTS +"."+ St_Department_id + " = "
                               + DEPARTMENT +"."+ Dep_id
                               + " WHERE " + STUDENTS +"."+ St_Debt + " <?; ",
                       new String[]{getThirdResult});

              second_result.setText(null);
              while (cursor.moveToNext()&& cursor.getCount() >= 1){
                  String second = cursor.getString(cursor.getColumnIndex(Dep_Name));
                  second_result.append(second + "\n");
              }




               //2. Вывести имена всех студентов с количеством долгов больше вводимого числа.
               cursor = database.query(STUDENTS,
                       new String[]{St_id, St_Name, St_Debt}, St_Debt + " > ?",
                       new String[]{getSecondResult}, null, null, null);

              first_result.setText(null);
              while (cursor.moveToNext() && cursor.getCount() >= 1) {
                  String first = cursor.getString(cursor.getColumnIndex(Pr_id));
                  first_result.append(first + "\n");
              }

              //13. Вывести имена всех студентов из кафедры с вводимым названием.
              cursor = database.rawQuery("SELECT " + STUDENTS +"."+ St_Name
                               + " FROM " + STUDENTS + " INNER JOIN " + DEPARTMENT
                               + " ON " + STUDENTS +"."+ St_Department_id + " = "
                               + DEPARTMENT +"."+ Dep_id
                               + " WHERE " + DEPARTMENT +"."+ Dep_Name + " =?; ",
                       new String[]{getThirdResult});

              second_result.setText(null);
              while (cursor.moveToNext()&& cursor.getCount() >= 1){
                  String second = cursor.getString(cursor.getColumnIndex(Pr_Name));
                  second_result.append(second + "\n");
              }



             //1. Вывести имена всех преподавателей с вводимым Id.
             cursor = database.query(PROFESSORS,
                     new String[]{Pr_id, Pr_Name}, Pr_id + " = ?",
                     new String[]{getFirstResult}, null, null, null);

             first_result.setText(null);
             while (cursor.moveToNext() && cursor.getCount() >= 1) {
                 String first = cursor.getString(cursor.getColumnIndex(Pr_id));
                 first_result.append(first + "\n");
             }


             //14. Вывести имена всех студентов из кафедры с вводимым Id.
             cursor = database.rawQuery("SELECT " + STUDENTS +"."+ St_Name
                             + " FROM " + STUDENTS + " INNER JOIN " + DEPARTMENT
                             + " ON " + STUDENTS +"."+ St_Department_id + " = "
                             + DEPARTMENT +"."+ Dep_id
                             + " WHERE " + DEPARTMENT +"."+ Dep_id + " =?; ",
                     new String[]{getThirdResult});

             second_result.setText(null);
             while (cursor.moveToNext()&& cursor.getCount() >= 1){
                 String second = cursor.getString(cursor.getColumnIndex(Pr_Name));
                 second_result.append(second + "\n");
             }



             //5. Вывести id всех преподавателей с вводимой должностью.
             cursor = database.query(PROFESSORS,
                     new String[]{Pr_id, Pr_Name, Post},
                     Post + " = ?",
                     new String[]{getThirdResult}, null, null, null);

             first_result.setText(null);
             while (cursor.moveToNext() && cursor.getCount() >= 1) {
                 String first = cursor.getString(cursor.getColumnIndex(Pr_id));
                 first_result.append(first + "\n");
             }


             //15. Вывести названия всех кафедр в которых стунедны старше вводимого числа.
             cursor = database.rawQuery("SELECT " + DEPARTMENT +"."+ Dep_Name
                             + " FROM " + STUDENTS + " INNER JOIN " + DEPARTMENT
                             + " ON " + STUDENTS +"."+ St_Department_id + " = "
                             + DEPARTMENT +"."+ Dep_id
                             + " WHERE " + STUDENTS +"."+ St_Age + " >?; ",
                     new String[]{getThirdResult});

             second_result.setText(null);
             while (cursor.moveToNext()&& cursor.getCount() >= 1){
                 String second = cursor.getString(cursor.getColumnIndex(Pr_Name));
                 second_result.append(second + "\n");
             }


             //4. Вывести имена всех преподавателей с вводимой должностью.
             cursor = database.query(PROFESSORS,
                     new String[]{Pr_id, Pr_Name, Post},
                     Post + " = ?",
                     new String[]{getThirdResult}, null, null, null);

             first_result.setText(null);
             while (cursor.moveToNext() && cursor.getCount() >= 1) {
                 String first = cursor.getString(cursor.getColumnIndex(Pr_id));
                 first_result.append(first + "\n");
             }


             //17. Вывести названия всех кафедр в которых есть группа с вводимым названием.
             cursor = database.rawQuery("SELECT " + DEPARTMENT +"."+ Dep_Name
                             + " FROM " + STUDENTS + " INNER JOIN " + DEPARTMENT
                             + " ON " + STUDENTS +"."+ St_Department_id + " = "
                             + DEPARTMENT +"."+ Dep_id
                             + " WHERE " + STUDENTS +"."+ St_Group + " =?; ",
                     new String[]{getThirdResult});


              second_result.setText(null);
              while (cursor.moveToNext()&& cursor.getCount() >= 1){
                  String second = cursor.getString(cursor.getColumnIndex(Pr_Name));
                  second_result.append(second + "\n");
              }




             //10. Вывести имена всех студентов из группы с вводимым названием.
             cursor = database.query(STUDENTS,
                     new String[]{St_id, St_Name, St_Group}, St_Group + " = ?",
                     new String[]{getSecondResult}, null, null, null);

            first_result.setText(null);
             while (cursor.moveToNext() && cursor.getCount() >= 1) {
                 String first = cursor.getString(cursor.getColumnIndex(Pr_id));
                 first_result.append(first + "\n");
             }


             //18. Вывести дисциплиы всех кафедр в которых есть преподаватели с вводимым именем.
             cursor = database.rawQuery("SELECT " + DEPARTMENT +"."+ Dep_Subjects
                             + " FROM " + PROFESSORS + " INNER JOIN " + DEPARTMENT
                             + " ON " + PROFESSORS +"."+ Pr_Department_id + " = "
                             + DEPARTMENT +"."+ Dep_id
                             + " WHERE " + PROFESSORS +"."+ Pr_Name + " =?; ",
                     new String[]{getThirdResult});

              second_result.setText(null);
              while (cursor.moveToNext()&& cursor.getCount() >= 1){
                   String second = cursor.getString(cursor.getColumnIndex(Pr_Name));
                   second_result.append(second + "\n");
              }




             //7. Вывести id всех студентов с вводимым количеством лет.
             cursor = database.query(STUDENTS,
                     new String[]{St_id, St_Name, St_Age},
                     St_Age + " = ?",
                     new String[]{getThirdResult}, null, null, null);

             first_result.setText(null);
             while (cursor.moveToNext() && cursor.getCount() >= 1) {
                 String first = cursor.getString(cursor.getColumnIndex(Pr_id));
                 first_result.append(first + "\n");
             }


             //23. Вывести имена всех преподавателей из кафедры с вводимым названием.
             cursor = database.rawQuery("SELECT " + PROFESSORS +"."+ Pr_Name
                             + " FROM " + PROFESSORS + " INNER JOIN " + DEPARTMENT
                             + " ON " + PROFESSORS +"."+ Pr_Department_id + " = "
                             + DEPARTMENT +"."+ Dep_id
                             + " WHERE " + DEPARTMENT +"."+ Dep_Name + " =?; ",
                     new String[]{getThirdResult});

             second_result.setText(null);
             while (cursor.moveToNext()&& cursor.getCount() >= 1){
                 String second = cursor.getString(cursor.getColumnIndex(Pr_Name));
                 second_result.append(second + "\n");
             }

             //9. Вывести Id всех преподавателей с опытом работы менее вводимого числа.
             cursor = database.query(PROFESSORS,
                     new String[]{Pr_id, Pr_Name, Work_Experience}, Work_Experience + " < ?",
                     new String[]{getSecondResult}, null, null, null);


             first_result.setText(null);
             while (cursor.moveToNext() && cursor.getCount() >= 1) {
                  String first = cursor.getString(cursor.getColumnIndex(Pr_id));
                  first_result.append(first + "\n");
             }


             //20. Вывести имена всех студентов находящихся в кафедре с вводимым именем заведующего кафедрой.
             cursor = database.rawQuery("SELECT " + STUDENTS +"."+ St_Name
                             + " FROM " + STUDENTS + " INNER JOIN " + DEPARTMENT
                             + " ON " + STUDENTS +"."+ St_Department_id + " = "
                             + DEPARTMENT +"."+ Dep_id
                             + " WHERE " + DEPARTMENT +"."+ Head_of_Department + " =?; ",
                     new String[]{getThirdResult});

              second_result.setText(null);
              while (cursor.moveToNext()&& cursor.getCount() >= 1){
                  String second = cursor.getString(cursor.getColumnIndex(Pr_Name));
                  second_result.append(second + "\n");
              }




             //12. Вывести имена всех преподавателей с вводимой специальностью.
             cursor = database.query(PROFESSORS,
                     new String[]{Pr_id, Pr_Name, Specialty},
                     Specialty + " = ?",
                     new String[]{getThirdResult}, null, null, null);

             first_result.setText(null);
             while (cursor.moveToNext() && cursor.getCount() >= 1) {
                 String first = cursor.getString(cursor.getColumnIndex(Pr_id));
                 first_result.append(first + "\n");
             }



             //22. Вывести имя заведующего кафедрой из кафедры в которой есть студенты старше вводимого числа.
             cursor = database.rawQuery("SELECT " + DEPARTMENT +"."+ Head_of_Department
                             + " FROM " + STUDENTS + " INNER JOIN " + DEPARTMENT
                             + " ON " + STUDENTS +"."+ Pr_Department_id + " = "
                             + DEPARTMENT +"."+ Dep_id
                             + " WHERE " + STUDENTS +"."+ St_Age + " >?; ",
                     new String[]{getThirdResult});


              second_result.setText(null);
              while (cursor.moveToNext()&& cursor.getCount() >= 1){
                   String second = cursor.getString(cursor.getColumnIndex(Pr_Name));
                   second_result.append(second + "\n");
              }




             //11. Вывести id всех студентов из группы с вводимым названием.
             cursor = database.query(STUDENTS,
                     new String[]{St_id, St_Name, St_Group}, St_Group + " = ?",
                     new String[]{getSecondResult}, null, null, null);

             first_result.setText(null);
             while (cursor.moveToNext() && cursor.getCount() >= 1) {
                 String first = cursor.getString(cursor.getColumnIndex(Pr_id));
                 first_result.append(first + "\n");
             }



             //21. Вывести имя заведующего кафедрой из каферды в которых есть преподаватель с вводимым именем.
             cursor = database.rawQuery("SELECT " + DEPARTMENT +"."+ Head_of_Department
                                + " FROM " + PROFESSORS + " INNER JOIN " + DEPARTMENT
                                + " ON " + PROFESSORS +"."+ Pr_Department_id + " = "
                                + DEPARTMENT +"."+ Dep_id
                                + " WHERE " + PROFESSORS +"."+ Pr_Name + " =?; ",
                        new String[]{getThirdResult});



             //6. Вывести имена всех студентов с вводимым количеством лет.
             cursor = database.query(STUDENTS,
                     new String[]{St_id, St_Name, St_Age},
                     St_Age + " = ?",
                     new String[]{getThirdResult}, null, null, null);

            first_result.setText(null);
            while (cursor.moveToNext() && cursor.getCount() >= 1) {
                 String first = cursor.getString(cursor.getColumnIndex(Pr_id));
                 first_result.append(first + "\n");
             }

             //19. Вывести названия всех кафедр в которых есть преподаватели с опытом работе более вводимого числа.
             cursor = database.rawQuery("SELECT " + DEPARTMENT +"."+ Dep_Name
                             + " FROM " + PROFESSORS + " INNER JOIN " + DEPARTMENT
                             + " ON " + PROFESSORS +"."+ Pr_Department_id + " = "
                             + DEPARTMENT +"."+ Dep_id
                             + " WHERE " + PROFESSORS +"."+ Work_Experience + " >?; ",
                     new String[]{getThirdResult});


             second_result.setText(null);
             while (cursor.moveToNext()&& cursor.getCount() >= 1){
                 String second = cursor.getString(cursor.getColumnIndex(Pr_Name));
                 second_result.append(second + "\n");
             }


             //8. Вывести имена всех преподавателей с опытом работы более вводимого числа.
             cursor = database.query(PROFESSORS,
                     new String[]{Pr_id, Pr_Name, Work_Experience}, Work_Experience + " > ?",
                     new String[]{getSecondResult}, null, null, null);

             first_result.setText(null);
             while (cursor.moveToNext() && cursor.getCount() >= 1) {
                 String first = cursor.getString(cursor.getColumnIndex(Pr_id));
                 first_result.append(first + "\n");
             }


             //24. Вывести имена всех преподавателей находящихся в кафедре с вводимым именем заведующего кафедрой.
             cursor = database.rawQuery("SELECT " + PROFESSORS +"."+ Pr_Name
                             + " FROM " + PROFESSORS + " INNER JOIN " + DEPARTMENT
                             + " ON " + PROFESSORS +"."+ Pr_Department_id + " = "
                             + DEPARTMENT +"."+ Dep_id
                             + " WHERE " + DEPARTMENT +"."+ Head_of_Department + " =?; ",
                     new String[]{getThirdResult});


              second_result.setText(null);
              while (cursor.moveToNext()&& cursor.getCount() >= 1){
                   String second = cursor.getString(cursor.getColumnIndex(Pr_Name));
                   second_result.append(second + "\n");
              }



                     */







            cursor.close();
            universityDatabase.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
