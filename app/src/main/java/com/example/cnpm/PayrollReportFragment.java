package com.example.cnpm;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anychart.core.gauge.pointers.Bar;
import com.example.cnpm.Database.AppDataBase;
import com.github.dewinjm.monthyearpicker.MonthFormat;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayrollReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayrollReportFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PayrollReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayrollReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayrollReportFragment newInstance(String param1, String param2) {
        PayrollReportFragment fragment = new PayrollReportFragment();
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
        return inflater.inflate(R.layout.fragment_payroll_report, container, false);
    }

    PieChart pieChart;
    BarChart barChart;
    LineChart lineChart;
    Button btnDateRP;

    int _monthNow, _yearNow;
    AppDataBase appDataBase;
    String[] listMonth;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appDataBase = new AppDataBase(getContext(), "myDatabase", null, 1);
        pieChart = view.findViewById(R.id.pieChart);
        barChart = view.findViewById(R.id.barChart);
        lineChart = view.findViewById(R.id.lineChart);
        btnDateRP = view.findViewById(R.id.btnDateRP);
        listMonth = getListMonth();

        initDate();
        setupPieChart();
        loadPieChartData(_monthNow, _yearNow);
        setupLineChart();
        loadLineChartData(_monthNow, _yearNow);
        setupBarChart();
        loadBarChartData(_monthNow, _yearNow);

        btnDateRP.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                final Calendar calendar_2 = Calendar.getInstance();
                int yearSelected_2 = calendar_2.get(Calendar.YEAR);
                int monthSelected_2 = calendar_2.get(Calendar.MONTH);
                MonthFormat monthFormat_2 = MonthFormat.SHORT;
                MonthYearPickerDialogFragment dialogSelectMonth = MonthYearPickerDialogFragment
                        .getInstance(monthSelected_2, yearSelected_2, "Select month:", new Locale("en", "US"), monthFormat_2);
                dialogSelectMonth.show(getChildFragmentManager(), null);

                dialogSelectMonth.setOnDateSetListener((year, monthOfYear) -> {
                    _monthNow = monthOfYear + 1;
                    _yearNow = year;
                    btnDateRP.setText(listMonth[monthOfYear] + " " + year);

                    //Xử lý liên quan đến Database
                    loadPieChartData(_monthNow, _yearNow);
                    loadLineChartData(_monthNow, _yearNow);
                    loadBarChartData(_monthNow, _yearNow);
                });
            }
        });
    }

    private String[] getListMonth() {
        return getResources().getStringArray(R.array.month_name);
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Month Salary");
        pieChart.setCenterTextSize(20);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData(int month, int year) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        int _totalBasicSalary = 0;
        int _totalBonusSalary = 0;
        int _totalOTSalary = 0;
        String sql = "Select employee_id, basic_salary, OT_hour, OT_pay, bonus_salary from pay_roll_new where month = '0" + month + "/" + year + "' and is_paid = '1'";
        Cursor cursor = appDataBase.getPayollInfo(sql);
        while (cursor.moveToNext()) {
            _totalBasicSalary += cursor.getInt(1);
            _totalBonusSalary += cursor.getInt(4);
            _totalOTSalary += cursor.getInt(2) * cursor.getInt(3);
        }

        entries.add(new PieEntry(_totalBasicSalary, "Basic"));
        entries.add(new PieEntry(_totalBonusSalary, "Bonus"));
        entries.add(new PieEntry(_totalOTSalary, "OT"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(false);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    private void setupLineChart() {
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getAxisLeft().setAxisMinimum(0f);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                String label = "";
                if (value == 0)
                    label = "Jan";
                if (value == 2)
                    label = "Mar";
                if (value == 4)
                    label = "May";
                if (value == 6)
                    label = "Jul";
                if (value == 8)
                    label = "Sep";
                if (value == 10)
                    label = "Nov";
                return label;
            }
        });
    }

    private void loadLineChartData(int month, int year) {
        ArrayList<Entry> entries = new ArrayList<>();
        int totalSalary;
        for (int i = 0; i < 12; i++) {
            totalSalary = 0;
            String sql = "Select employee_id, basic_salary, OT_hour, OT_pay, bonus_salary from pay_roll_new where month = '0" + (i + 1) + "/" + year + "' and is_paid = '1'";
            Cursor cursor = appDataBase.getPayollInfo(sql);
            while (cursor.moveToNext()) {
                totalSalary += cursor.getInt(1) + cursor.getInt(2) * cursor.getInt(3) + cursor.getInt(4);
            }
            entries.add(new Entry(i, totalSalary / 1000000));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Total Salary (million)");
        dataSet.setColors(Color.parseColor("#2880bd"));
        dataSet.setLineWidth(3f);
        dataSet.setCircleColor(Color.parseColor("#2880bd"));
        dataSet.setCircleRadius(5f);
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
    }

    private void setupBarChart() {
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(getListMonth()));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.getXAxis().setCenterAxisLabels(true);
        barChart.getXAxis().setGranularity(1);
        barChart.getXAxis().setGranularityEnabled(true);

        barChart.getDescription().setEnabled(false);

        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMinimum(3);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setAxisMinimum(0f);
        barChart.getAxisLeft().setAxisMinimum(0f);
    }

    private void loadBarChartData(int month, int year) {
        ArrayList<BarEntry> entriesBasic = new ArrayList<>();
        ArrayList<BarEntry> entriesBonus = new ArrayList<>();
        ArrayList<BarEntry> entriesOT = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            int basic = 0, _OT = 0, bonus = 0;
            String sql = "Select employee_id, basic_salary, OT_hour, OT_pay, bonus_salary from pay_roll_new where month = '0" + (i + 1) + "/" + year + "' and is_paid = '1'";
            Cursor cursor = appDataBase.getPayollInfo(sql);
            while (cursor.moveToNext()) {
                basic += cursor.getInt(1);
                _OT += cursor.getInt(2) * cursor.getInt(3);
                bonus += cursor.getInt(4);
            }
            entriesBasic.add(new BarEntry(i + 1, basic / 1000000));
            entriesOT.add(new BarEntry(i + 1, _OT / 1000000));
            entriesBonus.add(new BarEntry(i + 1, bonus / 1000000));
        }

        BarDataSet barDataSet_basic = new BarDataSet(entriesBasic, "Basic");
        barDataSet_basic.setColors(Color.CYAN);
        BarDataSet barDataSet_ot = new BarDataSet(entriesOT, "OT");
        barDataSet_ot.setColors(Color.BLUE);
        BarDataSet barDataSet_bonus = new BarDataSet(entriesBonus, "Bonus");
        barDataSet_bonus.setColors(Color.DKGRAY);

        BarData data = new BarData(barDataSet_basic, barDataSet_ot, barDataSet_bonus);

        barChart.setData(data);
        float groupSpace = 0.28f;
        float barSpace = 0.04f;
        float barWidth = 0.2f;
        int groupCount = 12;
        data.setBarWidth(barWidth);

        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);

        barChart.groupBars(0f, groupSpace, barSpace);

        barChart.invalidate();
    }

    @SuppressLint("SetTextI18n")
    private void initDate() {
        final Calendar calendar = Calendar.getInstance();
        _yearNow = calendar.get(Calendar.YEAR);
        _monthNow = calendar.get(Calendar.MONTH) + 1;
        btnDateRP.setText(listMonth[_monthNow - 1] + " " + _yearNow);
    }
}