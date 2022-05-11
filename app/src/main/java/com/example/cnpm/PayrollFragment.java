package com.example.cnpm;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayrollFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayrollFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PayrollFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayrollFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayrollFragment newInstance(String param1, String param2) {
        PayrollFragment fragment = new PayrollFragment();
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
        return inflater.inflate(R.layout.fragment_payroll, container, false);
    }

    ArrayList<CustomListViewPayroll> listMember = new ArrayList<>();
    ArrayList<String> listMonth = new ArrayList<>(Arrays.asList("Tháng 1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
    Button btn_confirmAll;
    ListView listView_show;
    PieChart pieChart;
    Spinner spinner;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_confirmAll = view.findViewById(R.id.btnConfirmAll);
        listView_show = view.findViewById(R.id.listMember);
        pieChart = view.findViewById(R.id.pieChart);
        spinner = view.findViewById(R.id.monthSpinner);

        ArrayAdapter myAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listMonth);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Month " + adapterView.getItemAtPosition(i).toString() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setupPieChart();

        CustomListViewPayroll customs1 = new CustomListViewPayroll("Chicky", "3000$");
        CustomListViewPayroll customs2 = new CustomListViewPayroll("Cha Cha", "2500$");
        CustomListViewPayroll customs3 = new CustomListViewPayroll("Boom Boom", "2700$");
        CustomListViewPayroll customs4 = new CustomListViewPayroll("Lya Lya", "2800$");
        listMember.add(customs1);
        listMember.add(customs2);
        listMember.add(customs3);
        listMember.add(customs4);

        CustomListViewPayrollAdapter adapter = new CustomListViewPayrollAdapter(getContext(), R.layout.payroll_adapter_view_layout, listMember);
        listView_show.setAdapter(adapter);
        listView_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        btn_confirmAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setupPieChart() {

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(7000, "Cơ bản"));
        entries.add(new PieEntry(2000, "Tăng ca"));
        entries.add(new PieEntry(2000, "Thưởng"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Lương");
        dataSet.setSliceSpace(3f);
        //ataSet.setSelectionShift(5f);
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueTextSize(16f);
        //data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.setDrawEntryLabels(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleColor(Color.TRANSPARENT);
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }
}