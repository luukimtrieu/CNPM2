package com.example.cnpm;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

    Button btnPay, btnReport;
    Boolean isUserPayrollFragment;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnPay = getView().findViewById(R.id.btnPayPr);
        btnReport = getView().findViewById(R.id.btnReportPr);
        isUserPayrollFragment = true;

        FragmentManager fragManager = getChildFragmentManager();
        FragmentTransaction fragTrans = fragManager.beginTransaction();

        fragTrans.add(R.id.containerFragment, new PayFragment(), null);
        fragTrans.commit();

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUserPayrollFragment == false) {
                    isUserPayrollFragment = true;

                    Drawable btnDrawable = btnPay.getBackground();
                    btnDrawable = DrawableCompat.wrap(btnDrawable);
                    DrawableCompat.setTint(btnDrawable, Color.WHITE);
                    btnPay.setBackground(btnDrawable);

                    Drawable btnDrawable2 = btnReport.getBackground();
                    btnDrawable2 = DrawableCompat.wrap(btnDrawable2);
                    DrawableCompat.setTint(btnDrawable2, Color.TRANSPARENT);
                    btnReport.setBackground(btnDrawable2);

                    FragmentManager fragManager2 = getChildFragmentManager();
                    FragmentTransaction fragTrans2 = fragManager2.beginTransaction();
                    fragTrans2.replace(R.id.containerFragment, new PayFragment(), null);
                    fragTrans2.commit();
                }
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUserPayrollFragment == true) {
                    isUserPayrollFragment = false;

                    Drawable btnDrawable = btnPay.getBackground();
                    btnDrawable = DrawableCompat.wrap(btnDrawable);
                    DrawableCompat.setTint(btnDrawable, Color.TRANSPARENT);
                    btnPay.setBackground(btnDrawable);

                    Drawable btnDrawable2 = btnReport.getBackground();
                    btnDrawable2 = DrawableCompat.wrap(btnDrawable2);
                    DrawableCompat.setTint(btnDrawable2, Color.WHITE);
                    btnReport.setBackground(btnDrawable2);

                    FragmentManager fragManager2 = getChildFragmentManager();
                    FragmentTransaction fragTrans2 = fragManager2.beginTransaction();
                    fragTrans2.replace(R.id.containerFragment, new PayrollReportFragment(), null);
                    fragTrans2.commit();
                }
            }
        });
    }
}