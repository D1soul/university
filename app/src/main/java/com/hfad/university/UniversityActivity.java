package com.hfad.university;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class UniversityActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);


        ListView universityListView = findViewById(R.id.department);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {

                Intent intent;
                switch (position){
                    case 0:
                        intent = new Intent(UniversityActivity.this, DepartmentsListActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(UniversityActivity.this, ProfessorsListActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(UniversityActivity.this, StudentsListActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        universityListView.setOnItemClickListener(itemClickListener);
    }
}
