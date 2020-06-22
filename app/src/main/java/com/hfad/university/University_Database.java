package com.hfad.university;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class University_Database extends SQLiteOpenHelper {
    public static final String Database_name = "university";
    public static final int Database_version = 1;

    University_Database (Context context){
        super(context, Database_name, null, Database_version);
    }

    public static final String DEPARTMENT = "КАФЕДРЫ";
    public static final String Dep_id = "_id";
    public static final String Dep_Name = "Название";
    public static final String Dep_Subjects =  "Дисциплины";
    public static final String Head_of_Department = "Глава_кафедры";

    public static final String PROFESSORS = "ПРОФЕССОРСКО_ПРЕПОДАВАТЕЛЬСКИЙ_СОСТАВ";
    public static final String Pr_id = "_id";
    public static final String Pr_Name = "ФИО";
    public static final String Post = "Должность";
    public static final String Specialty = "Специальность";
    public static final String Work_Experience = "Стаж_работы";
    public static final String Pr_Department_id = "Номер_кафедры";

    public static final String STUDENTS = "Студенты";
    public static final String St_id = "_id";
    public static final String St_Name = "ФИО";
    public static final String St_Age  = "Возраст";
    public static final String St_Group = "Группа";
    public static final String St_Debt = "Задолжности";
    public static final String St_Department_id = "№_кафедры";

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("PRAGMA foreign_keys=ON;");

        //Создание таблицы DEPARTMENT при помощи языка SQLite

        database.execSQL("CREATE TABLE " + DEPARTMENT + "("
                + Dep_id + " INTEGER PRIMARY KEY,"
                + Dep_Name + " TEXT NOT NULL,"
                + Dep_Subjects + " TEXT NOT NULL,"
                + Head_of_Department + " TEXT NOT NULL)");

        insertDepTable(database, "Кафедра дифференциальных уравнений и геометрии\n",
              "Аналитическая геометрия\n"
                        + "Дифференциальные уравнения\n"
                        + "Дифференциальная геометрия и топология\n"
                        + "Проективная геометрия\n"
                        + "Уравнения математической физики\n"
                        + "Многомерная геометрия\n"
                        + "Дифференциальные уравнения с импульсным воздействием\n"
                        + "Вариационное исчисление и методы оптимизации\n",
                "Анашкин Олег Васильевич");

        insertDepTable(database, "Кафедра Информатики",
              "Анализ и оптимизация компьютерных сетей\n"
                        + "Архитектура  компьютеров\n"
                        + "Веб-программирование\n"
                        + "Дискретная математика\n"
                        + "Интеллектуальные системы и базы знаний\n"
                        + "Исследование операций\n"
                        + "Компьютерная графика\n"
                        + "Логическое программирование\n"
                        + "Практикум по программированию\n"
                        + "Прикладное программное обеспечение\n",
               "Донской Владимир Иосифович");

        insertDepTable(database, "Кафедра алгебры и функционального анализа\n",
              "Линейная алгебра\n"
                        + "Введение в специальность\n"
                        + "Алгебра и теория чисел\n"
                        + "Дискретная математика\n"
                        + "Методика преподавания математики\n"
                        + "Компьютерные технологии в математике\n"
                        + "Элементарная математика\n"
                        + "Функциональный анализ\n"
                        + "Теория интеграла и меры\n"
                        + "Дополнительные главы системного анализа\n"
                        + "Дифференциальное исчисление в пространствах Фреше\n",
              "Орлов Игорь Владимирович");

        insertDepTable(database, "Кафедра прикладной математики\n",
              "Программирование\n"
                        + "Программное обеспечение персональных компьютеров\n"
                        + "Объектно-ориентированное программирование\n"
                        + "Операционные системы\n"
                        + "Компьютерные сети\n"
                        + "Базы данных и языки СУБД\n"
                        + "Численные методы\n"
                        + "Численные методы математической физики\n"
                        + "Теоретическая механика\n"
                        + "Математические модели в механике\n"
                        + "Теория систем и математическое моделирование\n"
                        + "Моделирование экономических, экологических и социальных процессов\n",
              "Чехов Валерий Александрович");

        //Создание таблицы PROFESSORS при помощи языка SQLite

        database.execSQL("CREATE TABLE " + PROFESSORS + "("
                + Pr_id + " INTEGER PRIMARY KEY,"
                + Pr_Name + " TEXT NOT NULL,"
                + Post + " TEXT NOT NULL,"
                + Specialty + " TEXT NOT NULL,"
                + Work_Experience + " TEXT NOT NULL,"
                + Pr_Department_id + " INTEGER NOT NULL,"
                + " FOREIGN KEY (" + Pr_Department_id + ") REFERENCES "
                                    + DEPARTMENT + "(" + Dep_id + "));");

        insertPrTable(database, "Орлов Игорь Владимирович", "Профессор", "Функциональный анализ", 23, 3);
        insertPrTable(database, "Воронцов Борис Сергеевич", "Старший преподавалетель", "Веб-программирование", 12, 2);
        insertPrTable(database, "Хрипунов Сергей Владимирович", "Доцент", "Уравнения математической физики", 13,  1);
        insertPrTable(database, "Анашкин Олег Васильевич", "Профессор", "Проективная геометрия", 15, 1);
        insertPrTable(database, "Давыдова Марина Вадимовна", "Преподаватель", "Численные методы", 6, 4);
        insertPrTable(database, "Петрова Екатерина Юрьевна", "Доцент", "Прикладное программное обеспечение", 16, 2);
        insertPrTable(database, "Донской Владимир Иосифович", "Профессор", "Компьютерная графика", 25,  2);
        insertPrTable(database, "Малахова Евгения Борисовна", "Старший преподаватель", "Линейная алгебра", 9, 3);
        insertPrTable(database, "Чехов Валерий Александрович", "Профессор", "Компьютерные сети", 26, 4);
        insertPrTable(database, "Капустин Михаил Владимирович", "Преподаватель", "Дифференциальные уравнения", 4, 1);

        //Создание таблицы STUDENTS при помощи языка SQLite

        database.execSQL("CREATE TABLE " + STUDENTS + "("
                + St_id + " INTEGER PRIMARY KEY,"
                + St_Name + " TEXT NOT NULL,"
                + St_Age + " INTEGER NOT NULL,"
                + St_Group + " TEXT NOT NULL,"
                + St_Debt + " INTEGER NOT NULL,"
                + St_Department_id + " INTEGER NOT NULL,"
                + " FOREIGN KEY (" + St_Department_id + ") REFERENCES " + DEPARTMENT + "(" + Dep_id + "));");

        insertStTable(database, "Арбузов Николай Николаевич", 18, "ДУГ-21", 0, 1);
        insertStTable(database, "Киршин Петр Валерьевич", 19, "ПМ-31", 2, 2);
        insertStTable(database, "Кривинский Сергей Николаевич", 17, "АФА-11", 1, 1);
        insertStTable(database, "Крылова Елена Петровна", 20, "АОКС-31", 5, 3);
        insertStTable(database, "Соколова Наталия Петровна", 18, "АФА-21", 3, 2);
        insertStTable(database, "Древесная Юлия Олеговна", 18, "ДУГ-21", 0, 1);
        insertStTable(database, "Быстров Олег Александрович", 20, "ДУГ-31", 2, 2);
        insertStTable(database, "Нечаев Юрий Петрович", 19, "ПМ-31", 1, 1);
        insertStTable(database, "Васелькова Анна Сергеевна", 17, "АФА-11", 5, 3);
        insertStTable(database, "Бондарчук Степан Юрьевич", 20, "АОКС-31", 3, 2);
    }

    //Реализация метода insert() для добавления данных в таблицу DEPARTMENT.

    public void insertDepTable(SQLiteDatabase database, String dep_name, String dep_subjects,
                               String head_of_dep){

        ContentValues depValues = new ContentValues();
        depValues.put(Dep_Name, dep_name);
        depValues.put(Dep_Subjects, dep_subjects);
        depValues.put(Head_of_Department, head_of_dep);
        database.insert(DEPARTMENT, null, depValues);
    }

    //Реализация метода insert() для добавления данных в таблицу PROFESSORS.

    public void insertPrTable(SQLiteDatabase database, String pr_name, String post,
                              String specialty, int work_experience, int pr_dep_id){

        ContentValues prValues = new ContentValues();
        prValues.put(Pr_Name, pr_name);
        prValues.put(Post, post);
        prValues.put(Specialty, specialty);
        prValues.put(Work_Experience, work_experience);
        prValues.put(Pr_Department_id, pr_dep_id);
        database.insert(PROFESSORS, null, prValues);
    }

    //Реализация метода insert() для добавления данных в таблицу STUDENTS.

    public void insertStTable(SQLiteDatabase database, String st_name, int age,
                                String st_group, int st_debt, int st_dep_id){

        ContentValues stValues = new ContentValues();
        stValues.put(St_Name , st_name);
        stValues.put(St_Age, age);
        stValues.put(St_Group, st_group);
        stValues.put(St_Debt, st_debt);
        stValues.put(St_Department_id, st_dep_id);
        database.insert(STUDENTS, null, stValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int old_version, int new_version) {

    }
}