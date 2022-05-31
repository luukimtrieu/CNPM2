package com.example.cnpm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.dewinjm.monthyearpicker.MonthFormat;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
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
    Button btnSelectDate;
    SearchView mySearchView;
    ArrayList<CustomListViewPayroll> listMember = new ArrayList<>();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView_Employee = view.findViewById(R.id.listMember);
        btnSelectDate = view.findViewById(R.id.btnSelectDate);
        mySearchView = view.findViewById(R.id.searchView);
        String[] listMonth = getListMonth();

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

//                calendar.clear();
//                calendar.set(2010, 0, 1);
//                long minDate = calendar.getTimeInMillis();
//                calendar.clear();
//                calendar.set(2022, 11, 31);
//                long maxDate = calendar.getTimeInMillis();

                int yearSelected = calendar.get(Calendar.YEAR);
                int monthSelected = calendar.get(Calendar.MONTH);
                MonthFormat monthFormat = MonthFormat.LONG;
                MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
                        .getInstance(monthSelected, yearSelected, null, new Locale("en", "US"), monthFormat);
                dialogFragment.show(getChildFragmentManager(), null);

                dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int year, int monthOfYear) {
                        btnSelectDate.setText(listMonth[monthOfYear] + " " + Integer.toString(year));

                        //Xử lý liên quan đến Database

                    }
                });
            }
        });

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        CustomListViewPayroll customs1 = new CustomListViewPayroll("Chicky", "0001", "Đã thanh toán");
        CustomListViewPayroll customs2 = new CustomListViewPayroll("Chaos", "0002", "Đã thanh toán");
        CustomListViewPayroll customs3 = new CustomListViewPayroll("Jimmy", "0003", "Chưa thanh toán");
        CustomListViewPayroll customs4 = new CustomListViewPayroll("Crush", "0004", "Đã thanh toán");
        CustomListViewPayroll customs5 = new CustomListViewPayroll("Hugo", "0005", "Chưa thanh toán");
        CustomListViewPayroll customs6 = new CustomListViewPayroll("Tommy", "0006", "Chưa thanh toán");
        CustomListViewPayroll customs7 = new CustomListViewPayroll("Buu", "0007", "Đã thanh toán");
        CustomListViewPayroll customs8 = new CustomListViewPayroll("Kite", "0008", "Đã thanh toán");
        listMember.add(customs1);
        listMember.add(customs2);
        listMember.add(customs3);
        listMember.add(customs4);
        listMember.add(customs5);
        listMember.add(customs6);
        listMember.add(customs7);
        listMember.add(customs8);
        CustomListViewPayrollAdapter adapter = new CustomListViewPayrollAdapter(getContext(), R.layout.payroll_adapter_view_layout, listMember);
        listView_Employee.setAdapter(adapter);
        listView_Employee .setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Kiểm tra và mở fragment theo Database

            }
        });
    }

    public String[] getListMonth() {
        return getResources().getStringArray(R.array.month_name);
    }
}