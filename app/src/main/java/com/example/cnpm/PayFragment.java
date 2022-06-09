package com.example.cnpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cnpm.Database.AppDataBase;
import com.example.cnpm.Database.Employee;
import com.example.cnpm.Database.Payroll;
import com.github.dewinjm.monthyearpicker.MonthFormat;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayFragment newInstance(String param1, String param2) {
        PayFragment fragment = new PayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pay, container, false);
    }

    ListView listView_Employee;
    Button btnSelectDate, btnCreateNewPR, btnFastPayPR;
    SearchView mySearchView;
    RadioGroup radioGroup;
    RadioButton radBtnAll, radBtnIsPaid, radBtnIsUnpaid;
    ArrayList<CustomListViewPayroll> listMember = new ArrayList<>();
    AppDataBase appDataBase;
    Boolean showPaid, showUnPaid;
    String date_now;

    String[] listMonth;
    String[] listMonthNum;

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appDataBase = new AppDataBase(getContext(), "myDatabase", null, 1);

        listView_Employee = view.findViewById(R.id.listMember);
        mySearchView = view.findViewById(R.id.searchView);

        btnSelectDate = view.findViewById(R.id.btnSelectDate);
        btnCreateNewPR = view.findViewById(R.id.btnCreateNewPR);
        btnFastPayPR = view.findViewById(R.id.btnFastPayPR);

        radioGroup = view.findViewById(R.id.radGroup);
        radBtnAll = view.findViewById(R.id.radBtnShowAll);
        radBtnIsPaid = view.findViewById(R.id.radBtnIsPaid);
        radBtnIsUnpaid = view.findViewById(R.id.radBtnNotPaid);
        radBtnAll.setChecked(true);
        showPaid = showUnPaid = false;

        listMonth = getListMonth();
        listMonthNum = getListMonthNum();

        //Set text button Select Month-Year
        final Calendar calendar = Calendar.getInstance();
        int yearSelected = calendar.get(Calendar.YEAR);
        int monthSelected = calendar.get(Calendar.MONTH) + 1;
        date_now = "0" + monthSelected + "/" + yearSelected;
        btnSelectDate.setText(listMonth[monthSelected] + " " + yearSelected);

        //Add data to Listview
        //Test
        loadListviewData(date_now, showPaid, showUnPaid);
        Toast.makeText(getContext(), date_now, Toast.LENGTH_SHORT).show();

        //Click event button Month-Year
        btnSelectDate.setOnClickListener(view12 -> {
            final Calendar calendar_2 = Calendar.getInstance();
            int yearSelected_2 = calendar_2.get(Calendar.YEAR);
            int monthSelected_2 = calendar_2.get(Calendar.MONTH);
            MonthFormat monthFormat_2 = MonthFormat.LONG;

            MonthYearPickerDialogFragment dialogSelectMonth = MonthYearPickerDialogFragment
                    .getInstance(monthSelected_2, yearSelected_2, "Select salary month:", new Locale("en", "US"), monthFormat_2);
            dialogSelectMonth.show(getChildFragmentManager(), null);

            dialogSelectMonth.setOnDateSetListener((year, monthOfYear) -> {
                date_now = listMonthNum[monthOfYear] + "/" + year;
                btnSelectDate.setText(listMonth[monthOfYear] + " " + year);

                //Xử lý liên quan đến Database
                loadListviewData(date_now, showPaid, showUnPaid);
            });
        });

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!listMember.isEmpty()) {
                    ArrayList<CustomListViewPayroll> list = new ArrayList<>();
                    for (CustomListViewPayroll item : listMember) {
                        if (item.getName().contains(newText) || item.getId().contains(newText))
                            list.add(item);
                    }
                    CustomListViewPayrollAdapter adapter_temp = new CustomListViewPayrollAdapter(getContext(), R.layout.payroll_adapter_view_layout, list);
                    listView_Employee.setAdapter(adapter_temp);
                }
                return false;
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radBtnShowAll:
                        showPaid = showUnPaid = false;
                        loadListviewData(date_now, showPaid, showUnPaid);
                        break;
                    case R.id.radBtnIsPaid:
                        showPaid = true;
                        showUnPaid = false;
                        loadListviewData(date_now, showPaid, showUnPaid);
                        break;
                    case R.id.radBtnNotPaid:
                        showPaid = false;
                        showUnPaid = true;
                        loadListviewData(date_now, showPaid, showUnPaid);
                        break;
                }
            }
        });

        listView_Employee.setOnItemClickListener((adapterView, view1, i, l) -> {
            Bundle bundle = new Bundle();
            bundle.putString("idEmployee", listMember.get(i).getId());
            bundle.putString("date", date_now);
            bundle.putString("status", listMember.get(i).getStatus());
            bundle.putString("url", listMember.get(i).getUrlImage());
            Navigation.findNavController(view1).navigate(R.id.action_payrollFragment_to_userPayrollFragment, bundle);
        });

        btnCreateNewPR.setOnClickListener(view13 -> {
            final Calendar calendar_2 = Calendar.getInstance();
            int yearSelected_2 = calendar_2.get(Calendar.YEAR);
            int monthSelected_2 = calendar_2.get(Calendar.MONTH);
            MonthFormat monthFormat_2 = MonthFormat.LONG;
            MonthYearPickerDialogFragment dialogSelectMonth = MonthYearPickerDialogFragment
                    .getInstance(monthSelected_2, yearSelected_2, "Select new salary month:", new Locale("en", "US"), monthFormat_2);
            dialogSelectMonth.show(getChildFragmentManager(), null);

            dialogSelectMonth.setOnDateSetListener((year, monthOfYear) -> {
                String date = listMonthNum[monthOfYear] + "/" + year;
                String sql_test = "Select * from pay_roll_new where month = '" + date + "'";
                Cursor check = appDataBase.getPayollInfo(sql_test);
                if (check.getCount() == 0) {
                    if (createNewSalaryMonth(date))
                        Toast.makeText(getContext(), "Create new salary month successfully. " + date, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), "Salary month already exists", Toast.LENGTH_SHORT).show();
            });
        });

        btnFastPayPR.setOnClickListener(view14 -> openFastPayDialog(date_now));
    }

    private String[] getListMonth() {
        return getResources().getStringArray(R.array.month_name);
    }

    private String[] getListMonthNum() {
        return getResources().getStringArray(R.array.month_num);
    }

    int total = 0;
    int unpaid_employee = 0;
    String sql, sql1, sql2, sql3;

    @SuppressLint("SetTextI18n")
    private void openFastPayDialog(String date) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fast_pay);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);

        TextView tvSelectMonth = dialog.findViewById(R.id.tvMonthDialog);
        TextView tvNumEmpDialog = dialog.findViewById(R.id.tvNumEmpDialog);
        TextView tvTotalDialog = dialog.findViewById(R.id.tvTotalDialog);
        TextView tvTotalFinal = dialog.findViewById(R.id.tvTotalFinalDialog);
        EditText edtBonusDialog = dialog.findViewById(R.id.edtBonusDialog);
        EditText edtMessDialog = dialog.findViewById(R.id.edtMessDialog);
        Button btnCancelDialog = dialog.findViewById(R.id.btnCancelDialog);
        Button btnOkDialog = dialog.findViewById(R.id.btnOkDialog);

        tvSelectMonth.setText(date);
        sql = "Select employee_id from employee_new";
        Cursor cursor = appDataBase.getPayollInfo(sql);
        if (cursor != null) {

            sql1 = "Select employee_id from pay_roll_new where month = '" + date + "' and is_paid = '0'";
            cursor = appDataBase.getPayollInfo(sql1);

            if (cursor.getCount() != 0) {

                unpaid_employee = cursor.getCount();

                sql2 = "select basic_salary from pay_roll_new where month = '" + date + "' and is_paid = '0'";
                cursor = appDataBase.getPayollInfo(sql2);
                while (cursor.moveToNext()) {
                    total = total + cursor.getInt(0);
                }

                tvTotalDialog.setText(Integer.toString(total));
                tvTotalFinal.setText("Total: " + Integer.toString(total));

                sql3 = "Select employee_id from pay_roll_new where month = '" + date + "'";
                cursor = appDataBase.getPayollInfo(sql3);
                int max_employee = cursor.getCount();
                tvNumEmpDialog.setText(unpaid_employee + "/" + max_employee);
            } else {
                tvTotalDialog.setText("0");
                tvNumEmpDialog.setText("0/0");
                tvTotalFinal.setText("Total: 0");
            }
        } else
            Toast.makeText(getContext(), "Null database", Toast.LENGTH_SHORT).show();

        edtBonusDialog.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edtBonusDialog.getText().toString().isEmpty()) {
                    int t = Integer.parseInt(edtBonusDialog.getText().toString()) * unpaid_employee + total;
                    tvTotalFinal.setText("Total: " + t);
                }
            }
        });

        btnCancelDialog.setOnClickListener(view -> dialog.dismiss());

        btnOkDialog.setOnClickListener(view -> {
            if (!tvSelectMonth.getText().equals("Click here")) {
                if (tvNumEmpDialog.getText().equals("0") || tvTotalDialog.getText().equals("0")) {
                    Toast.makeText(getContext(), "No unpaid employees", Toast.LENGTH_SHORT).show();
                } else {
                    if (edtBonusDialog.getText().toString().isEmpty()) {
                        sql = "Update pay_roll_new set is_paid = '1', bonus_salary = '" + 0 + "', " +
                                "total = '" + edtMessDialog.getText().toString() + "' where is_paid = '0' and month = '" + date + "'";
                        appDataBase.updatePayroll(sql);
                    } else {
                        sql = "Update pay_roll_new set is_paid = '1', bonus_salary = '" + edtBonusDialog.getText().toString() + "', " +
                                "total = '" + edtMessDialog.getText().toString() + "' where is_paid = '0' and month = '" + date + "'";
                        appDataBase.updatePayroll(sql);
                    }
                    dialog.dismiss();
                    loadListviewData(date_now, showPaid, showUnPaid);
                }
            } else Toast.makeText(getContext(), "Choose salary month", Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }

    private void loadListviewData(String date, Boolean isPaid, Boolean isUnPaid) {
        listMember.clear();
        if (isPaid) {
            String sql = "Select employee_id, is_paid from pay_roll_new where month = '" + date + "' and is_paid = '" + "1'";
            Cursor list = appDataBase.getPayollInfo(sql);
            if (list.getCount() > 0) {
                while (list.moveToNext()) {
                    String status;
                    String id = list.getString(0);
                    if (list.getString(1).equals("0"))
                        status = "Unpaid";
                    else status = "Paid";

                    String sql2 = "Select employee_name, photo_url from employee_new where employee_id = '" + id + "'";
                    Cursor list_name = appDataBase.getPayollInfo(sql2);
                    while (list_name.moveToNext()) {
                        CustomListViewPayroll employee = new CustomListViewPayroll(list_name.getString(0),
                                list.getString(0), status, list_name.getString(1));
                        listMember.add(employee);
                    }
                }
            }
        } else if (isUnPaid) {
            String sql = "Select employee_id, is_paid from pay_roll_new where month = '" + date + "' and is_paid = '" + "0'";
            Cursor list = appDataBase.getPayollInfo(sql);
            if (list.getCount() > 0) {
                while (list.moveToNext()) {
                    String status;
                    String id = list.getString(0);
                    if (list.getString(1).equals("0"))
                        status = "Unpaid";
                    else status = "Paid";

                    String sql2 = "Select employee_name, photo_url from employee_new where employee_id = '" + id + "'";
                    Cursor list_name = appDataBase.getPayollInfo(sql2);
                    while (list_name.moveToNext()) {
                        CustomListViewPayroll employee = new CustomListViewPayroll(list_name.getString(0),
                                list.getString(0), status, list_name.getString(1));
                        listMember.add(employee);
                    }
                }
            }
        } else {
            String sql = "Select employee_id, is_paid from pay_roll_new where month = '" + date + "'";
            Cursor list = appDataBase.getPayollInfo(sql);
            if (list.getCount() > 0) {
                while (list.moveToNext()) {
                    String status;
                    String id = list.getString(0);
                    if (list.getString(1).equals("0"))
                        status = "Unpaid";
                    else status = "Paid";

                    String sql2 = "Select employee_name, photo_url from employee_new where employee_id = '" + id + "'";
                    Cursor list_name = appDataBase.getPayollInfo(sql2);
                    while (list_name.moveToNext()) {
                        CustomListViewPayroll employee = new CustomListViewPayroll(list_name.getString(0),
                                list.getString(0), status, list_name.getString(1));
                        listMember.add(employee);
                    }
                }
            }
        }
        CustomListViewPayrollAdapter adapter = new CustomListViewPayrollAdapter(getContext(), R.layout.payroll_adapter_view_layout, listMember);
        listView_Employee.setAdapter(adapter);
    }

    private boolean createNewSalaryMonth(String date) {
        String sql = "Select employee_id, payroll_id from employee_new";
        Cursor cursor = appDataBase.getPayollInfo(sql);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                Payroll p = new Payroll(cursor.getInt(0), date, 0, cursor.getString(1),
                        0, "0", 0, "0", "");
                appDataBase.addOnePayroll(p);
            }
            return true;
        } else {
            Toast.makeText(getContext(), "No employee in Database", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}