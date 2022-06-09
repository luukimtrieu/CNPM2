package com.example.cnpm;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cnpm.Database.AppDataBase;
import com.example.cnpm.Database.Payroll;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserPayrollFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserPayrollFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserPayrollFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserPayrollFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserPayrollFragment newInstance(String param1, String param2) {
        UserPayrollFragment fragment = new UserPayrollFragment();
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
        return inflater.inflate(R.layout.fragment_user_payroll, container, false);
    }

    EditText edtMonthUPR, edtDepartmentUPR, edtBasicSalaryUPR, edtOTSalaryUPR;
    EditText edtOTHour, edtDayOffUPR, edtBonusSalaryUPR, edtTotalUPR;
    TextView textNameUPR, textIdUPR, textWorkNameUPR;
    ImageView imvAvtUPR;
    Button btnExitUPR, btnConfirmUPR;

    AppDataBase appDataBase;
    ArrayList<Payroll> payrollArrayList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imvAvtUPR = view.findViewById(R.id.imvAvtUPR);
        textNameUPR = view.findViewById(R.id.textNameUPR);
        textIdUPR = view.findViewById(R.id.textIdUPR);
        textWorkNameUPR = view.findViewById(R.id.textWorkNameUPR);

        edtMonthUPR = view.findViewById(R.id.edtMonthUPR);
        edtDepartmentUPR = view.findViewById(R.id.edtDepartmentUPR);
        edtBasicSalaryUPR = view.findViewById(R.id.edtBasicSalaryUPR);
        edtOTSalaryUPR = view.findViewById(R.id.edtOTSalaryUPR);

        edtOTHour = view.findViewById(R.id.edtOTHour);
        edtDayOffUPR = view.findViewById(R.id.edtDayOffUPR);
        edtBonusSalaryUPR = view.findViewById(R.id.edtBonusSalaryUPR);
        edtTotalUPR = view.findViewById(R.id.edtTotalUPR);

        btnExitUPR = view.findViewById(R.id.btnExitUPR);
        btnConfirmUPR = view.findViewById(R.id.btnConfirmUPR);

        disableEditText(edtMonthUPR);
        disableEditText(edtDepartmentUPR);
        disableEditText(edtBasicSalaryUPR);
        disableEditText(edtDayOffUPR);

        appDataBase = new AppDataBase(getContext(), "myDatabase", null, 1);
        payrollArrayList = new ArrayList<>();

        Bundle arg = getArguments();
        String id_employee = arg.getString("idEmployee");
        String date = arg.getString("date");
        String url_Image = arg.getString("url");
        if (url_Image.length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeFile(url_Image);
            imvAvtUPR.setImageBitmap(bitmap);
        } else {
            imvAvtUPR.setImageResource(R.drawable.hacker);
        }

        textIdUPR.setText("ID: " + id_employee);
        edtMonthUPR.setText(date);

        String sql = "Select employee_name, department from employee_new where employee_id = '" + id_employee + "'";
        Cursor cursor = appDataBase.getPayollInfo(sql);
        while (cursor.moveToNext()) {
            textNameUPR.setText(cursor.getString(0));
            textWorkNameUPR.setText(cursor.getString(1));
            edtDepartmentUPR.setText(cursor.getString(1));
        }

        if (arg.getString("status").equals("Paid")) {

            sql = "Select day_off, OT_hour, OT_pay, bonus_salary, total from pay_roll_new where employee_id = '" +
                    id_employee + "' and month = '" + date + "'";
            cursor = appDataBase.getPayollInfo(sql);
            while (cursor.moveToNext()) {
                edtDayOffUPR.setText(cursor.getString(0));
                edtOTHour.setText(cursor.getString(1));
                edtOTSalaryUPR.setText(cursor.getString(2));
                edtBonusSalaryUPR.setText(cursor.getString(3));
                edtTotalUPR.setText(cursor.getString(4));
            }

            disableEditText(edtOTHour);
            disableEditText(edtOTSalaryUPR);
            disableEditText(edtBonusSalaryUPR);
            disableEditText(edtTotalUPR);
        }

        btnExitUPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_userPayrollFragment_to_payrollFragment);
            }
        });

        btnConfirmUPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arg.getString("status").equals("Paid")) {
                    Navigation.findNavController(view).navigate(R.id.action_userPayrollFragment_to_payrollFragment);
                } else {
                    defaultDataIfEmpty();
                    String sql = "Update pay_roll_new set is_paid = '1', bonus_salary = '" + edtBonusSalaryUPR.getText().toString() + "', " +
                            "total = '" + edtTotalUPR.getText().toString() + "' where employee_id = '" + id_employee + "' and month = '" + date + "'";
                    appDataBase.updatePayroll(sql);
                    Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_userPayrollFragment_to_payrollFragment);
                }
            }
        });
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(true);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    private void defaultDataIfEmpty() {
        if (edtOTHour.getText().toString().isEmpty())
            edtOTHour.setText("0");
        if (edtOTSalaryUPR.getText().toString().isEmpty())
            edtOTSalaryUPR.setText("0");
        if (edtBonusSalaryUPR.getText().toString().isEmpty())
            edtBonusSalaryUPR.setText("0");
        if (edtTotalUPR.getText().toString().isEmpty())
            edtTotalUPR.setText("Have a nice day!");
    }
}