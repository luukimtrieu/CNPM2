package com.example.cnpm.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AppDataBase extends SQLiteOpenHelper {

    public AppDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE USER(email varchar(30) PRIMARY KEY," +
                "company_name varchar(50), user_name varchar(20), password varchar(20))");

        sqLiteDatabase.execSQL("CREATE TABLE TASK(task_id INTEGER PRIMARY KEY AUTOINCREMENT, employee_id integer, " +
                                                    "task_name varchar(20), date_start varchar(15), date_end varchar(15)," +
                                                    "organizer varchar(50), description varchar(50), location varchar(100)," +
                                                    "url varchat(100))");
        sqLiteDatabase.execSQL("CREATE TABLE PAYROLL(payroll_id INTEGER PRIMARY KEY AUTOINCREMENT, employee_id integer," +
                                                    "month_year varchar(10), is_paid int, basic_salary varchar(10), OT_hour int," +
                                                    " OT_pay varchar(10), day_off int, bonus_salary varchar(10)," +
                                                    " total varchar(100))");

        sqLiteDatabase.execSQL("CREATE TABLE WORK_INFO (work_id INTEGER PRIMARY KEY AUTOINCREMENT, work_address varchar(50)," +
                                                    "work_hour integer)");

        sqLiteDatabase.execSQL("CREATE TABLE PRIVATE_INFO (info_id INTEGER PRIMARY KEY AUTOINCREMENT, address varchar(100)," +
                                                    "email varchar(20), language varchar(50), bank_account varchar(20)," +
                                                    "maritual_status varchar(8), study_field varchar(100), school varchar(100)," +
                                                    "nationality varchar(50), gender varchar(10), birth varchar(15)," +
                                                    "certificate_level varchar(20))");

        sqLiteDatabase.execSQL("CREATE TABLE TIME_OFF(time_off_id INTEGER PRIMARY KEY AUTOINCREMENT, employee_id integer" +
                                                    ", date_start varchar(15), date_end varchar(15), description text)");

        sqLiteDatabase.execSQL("CREATE TABLE HR(hr_id INTEGER PRIMARY KEY AUTOINCREMENT, job_position varchar(50)," +
                                                    "employee_type varchar(20))");

        sqLiteDatabase.execSQL("CREATE TABLE DEPARTMENT (department_name varchar(50) PRIMARY KEY, manager_id integer, " +
                                                    "manager_name varchar(50))"); // parent department

        sqLiteDatabase.execSQL("CREATE TABLE EMPLOYEE (employee_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                    "employee_name VARCHAR(50), department VARCHAR(50), work_mobile varchar(20), work_email varchar(50)," +
                                                    "manager varchar(50), coach varchar(50), photo_url varchar(200),payroll_id INTEGER, work_info_id INTEGER, " +
                                                    "private_info INTEGER, hr_id INTEGER)");

        sqLiteDatabase.execSQL("PRAGMA foreign_keys=off");
        sqLiteDatabase.execSQL("BEGIN TRANSACTION");
        sqLiteDatabase.execSQL("ALTER TABLE EMPLOYEE RENAME TO _employees_old");
        sqLiteDatabase.execSQL("CREATE TABLE employee_new (employee_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "employee_name VARCHAR(50), department VARCHAR(50), work_mobile varchar(20), work_email varchar(50)," +
                "manager varchar(50), coach varchar(50), photo_url varchar(200),payroll_id INTEGER, work_info_id INTEGER, " +
                "private_info_id INTEGER, hr_id INTEGER," +
                "CONSTRAINT fk_departments FOREIGN KEY(department) REFERENCES DEPARTMENT(department_name)," +
                "CONSTRAINT fk_payroll FOREIGN KEY(payroll_id) REFERENCES PAYROLL(payroll_id)," +
                "CONSTRAINT fk_work_info FOREIGN KEY(work_info_id) REFERENCES WORK_INFO(work_id)," +
                "CONSTRAINT fk_private_info FOREIGN KEY(private_info_id) REFERENCES PRIVATE_INFO(info_id)," +
                "CONSTRAINT fk_hr_id FOREIGN KEY(hr_id) REFERENCES HR(hr_id))");

//        sqLiteDatabase.execSQL("INSERT INTO employee_new (employee_name, department, payroll_id, photo_url) VALUES ('Chicky', 'HR Manager', '20000000', '')");
//        sqLiteDatabase.execSQL("INSERT INTO employee_new (employee_name, department, payroll_id, photo_url) VALUES ('Chaos', 'C# Developer', '18000000', '')");
//        sqLiteDatabase.execSQL("INSERT INTO employee_new (employee_name, department, payroll_id, photo_url) VALUES ('Jimmy', 'Business Analysis', '15000000', '')");
//        sqLiteDatabase.execSQL("INSERT INTO employee_new (employee_name, department, payroll_id, photo_url) VALUES ('Crush', 'Seller', '21000000', '')");
//        sqLiteDatabase.execSQL("INSERT INTO employee_new (employee_name, department, payroll_id, photo_url) VALUES ('Hugo', 'Java Developer', '19500000', '')");
//        sqLiteDatabase.execSQL("INSERT INTO employee_new (employee_name, department, payroll_id, photo_url) VALUES ('Timmy', 'Boss', '22000000', '')");
//        sqLiteDatabase.execSQL("INSERT INTO employee_new (employee_name, department, payroll_id, photo_url) VALUES ('Buu', 'Fresher', '12000000', '')");
//        sqLiteDatabase.execSQL("INSERT INTO employee_new (employee_name, department, payroll_id, photo_url) VALUES ('Kite', 'Python Developer', '20500000', '')");
//        sqLiteDatabase.execSQL("INSERT INTO employee_new (employee_name, department, payroll_id, photo_url) VALUES ('Boo', 'C++ Developer', '26000000', '')");

        sqLiteDatabase.execSQL("INSERT INTO employee_new SELECT * FROM _employees_old");
        sqLiteDatabase.execSQL("DROP TABLE _employees_old");

        sqLiteDatabase.execSQL("ALTER TABLE DEPARTMENT RENAME TO _department_old");
        sqLiteDatabase.execSQL("CREATE TABLE department_new (department_name varchar(50) PRIMARY KEY, " +
                "manager_id integer, " +
                "manager_name varchar(50)," +
                "CONSTRAINT fk_manager FOREIGN KEY(manager_id) REFERENCES employee_new(employee_id))");
        sqLiteDatabase.execSQL("DROP TABLE _department_old");

        sqLiteDatabase.execSQL("ALTER TABLE TASK RENAME TO _task_old");
        sqLiteDatabase.execSQL("CREATE TABLE task_new (task_id INTEGER PRIMARY KEY AUTOINCREMENT, employee_id integer, " +
                "task_name varchar(20), date_start varchar(15), date_end varchar(15)," +
                "organizer varchar(50), description varchar(50), location varchar(100)," +
                "url varchat(100)," +
                "CONSTRAINT fk_employee_id_task FOREIGN KEY(employee_id) REFERENCES employee_new(employee_id))");
        sqLiteDatabase.execSQL("DROP TABLE _task_old");

        sqLiteDatabase.execSQL("ALTER TABLE TIME_OFF RENAME TO _time_off_old");
        sqLiteDatabase.execSQL("CREATE TABLE time_off_new (time_off_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "employee_id integer" +
                ", date_start varchar(15), " +
                "date_end varchar(15), " +
                "description text," +
                "CONSTRAINT fk_employee_id_time_off FOREIGN KEY(employee_id) REFERENCES employee_new(employee_id))");
        sqLiteDatabase.execSQL("DROP TABLE _time_off_old");

        sqLiteDatabase.execSQL("ALTER TABLE PAYROLL RENAME TO _pay_roll_old");
        sqLiteDatabase.execSQL("CREATE TABLE pay_roll_new (payroll_id INTEGER PRIMARY KEY AUTOINCREMENT, employee_id integer," +
                "month varchar(7), is_paid int, basic_salary varchar(10), OT_hour int," +
                "OT_pay varchar(10), day_off int, bonus_salary varchar(10)," +
                "total varchar(100))");
        sqLiteDatabase.execSQL("DROP TABLE _pay_roll_old");

        sqLiteDatabase.execSQL("COMMIT;");
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=on");

    }


    public void addOne(Work_Info work_info)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("work_address", work_info.getWork_address());
        cv.put("work_hour", work_info.getWork_hour());

        db.insert("WORK_INFO", null, cv);
    }

    public void addOne(Time_off time_off)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("employee_id", time_off.getEmployee_id());
        cv.put("description", time_off.getDescription());
        cv.put("date_start", time_off.getDate_start());
        cv.put("date_end", time_off.getDate_end());

        db.insert("department_new", null, cv);
    }

    public void addOne(Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("employee_id", task.getEmployee_id());
        cv.put("task_name", task.getTask_name());
        cv.put("date_start", task.getDate_start());
        cv.put("date_end", task.getDate_end());
        cv.put("organizer", task.getOrganizer());
        cv.put("description", task.getDescription());

        db.insert("department_new", null, cv);
    }

    public void addOne(Private_Info private_info)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("address", private_info.getAddress());
        cv.put("email", private_info.getEmail());
        cv.put("language", private_info.getLanguage());
        cv.put("bank_account", private_info.getBank_account());
        cv.put("study_field", private_info.getStudy_field());
        cv.put("gender", private_info.getGender());
        cv.put("birth", private_info.getBirth());
        cv.put("certificate_level", private_info.getCertificate_level());


        db.insert("PRIVATE_INFO", null, cv);
    }

    public void addOne(Payroll payroll)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("employee_id", payroll.getEmployee_id());
        cv.put("month_year", payroll.getMonth_year());
        cv.put("is_paid", payroll.getIs_paid());
        cv.put("basic_salary", payroll.getBasic_salary());
        cv.put("OT_hour", payroll.getOT_hour());
        cv.put("OT_pay", payroll.getOT_pay());
        cv.put("day_off", payroll.getDay_off());
        cv.put("bonus_salary", payroll.getBonus_salary());
        cv.put("total", payroll.getTotal());

        db.insert("pay_roll_new", null, cv);
    }

    public void addOne(Department department)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("department_name", department.getName());
        cv.put("manager_id", department.getManager_id());
        cv.put("manager_name", department.getManager_name());

        db.insert("department_new", null, cv);
    }

    public void addOne(HR hr)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("job_position", hr.getJob_position());
        cv.put("employee_type", hr.getEmployee_type());

        db.insert("HR", null, cv);
    }

    public void addOne(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("employee_name", employee.getName());
        cv.put("department", employee.getDepartment());
        cv.put("work_mobile", employee.getWork_mobile());
        cv.put("work_email", employee.getWork_email());
        cv.put("manager", employee.getManager());
        cv.put("coach", employee.getCoach());
        cv.put("photo_url", employee.getPhoto_url());
        cv.put("payroll_id", employee.getPayroll_id());
        cv.put("work_info_id", employee.getWork_info_id());
        cv.put("private_info_id", employee.getPrivate_info_id());
        cv.put("hr_id", employee.getHr_id());

        db.insert("employee_new", null, cv);
    }

    public void deleteOne(Employee employee){
        SQLiteDatabase db = this.getWritableDatabase();
        String name = employee.getName();
        String queryString = " DELETE FROM employee_new WHERE employee_name = " + name;
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getPayollInfo(String sql) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql, null);
    }
    public void updatePayroll(String sql) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
    }
    public void addOnePayroll(Payroll payroll) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int _id = payroll.getEmployee_id();
        String _month = payroll.getMonth_year();
        int _isPaid = payroll.getIs_paid();
        String _basicSalary = payroll.getBasic_salary();
        int _otHour = payroll.getOT_hour();
        String _otPay = payroll.getOT_pay();
        int _dayOff = payroll.getDay_off();
        String _bonusSalary = payroll.getBonus_salary();
        String _total = payroll.getTotal();
        String sql = "INSERT INTO pay_roll_new (employee_id, month, is_paid, basic_salary, OT_hour, OT_pay, day_off, bonus_salary, total) " +
                "VALUES ('" + _id + "', '" + _month + "', '" + _isPaid + "', '" + _basicSalary + "', '" + _otHour + "', '" + _otPay + "', '"
                + _dayOff + "', '" + _bonusSalary + "', '" + _total + "')";
        sqLiteDatabase.execSQL(sql);
    }
}
