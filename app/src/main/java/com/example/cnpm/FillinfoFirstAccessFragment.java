package com.example.cnpm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FillinfoFirstAccessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FillinfoFirstAccessFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextInputEditText yourName;
    private TextInputEditText companyName;
    private TextInputEditText phoneNumber;
    private TextInputEditText department;
    private TextInputEditText yourPosition;
    private TextInputEditText workingAddress;
    private TextInputEditText workingHours;
    private TextInputEditText address;
    private TextInputEditText language;
    private TextInputEditText bankAccount;
    private TextInputEditText studyField;
    private TextInputEditText nationality;
    private TextInputEditText gender;
    private TextInputEditText identity;
    private TextInputEditText birthday;
    private TextInputEditText certificate;

    public FillinfoFirstAccessFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FillinfoFirstAccessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FillinfoFirstAccessFragment newInstance(String param1, String param2) {
        FillinfoFirstAccessFragment fragment = new FillinfoFirstAccessFragment();
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
        return inflater.inflate(R.layout.fragment_fillinfo_first_access, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    public void initView(View view)
    {
        yourName = view.findViewById(R.id.txtInputEditText_Name);
        companyName = view.findViewById(R.id.txtInputEditText_CompanyName);
        phoneNumber = view.findViewById(R.id.txtInputEditText_PhoneNumber);
        department = view.findViewById(R.id.txtInputEditText_Department);
        yourPosition = view.findViewById(R.id.txtInputEditText_JobPosition);
        workingAddress = view.findViewById(R.id.txtInputEditText_WorkAddress);
        workingHours = view.findViewById(R.id.txtInputEditText_WorkHours);
        address = view.findViewById(R.id.txtInputEditText_Address);
        language = view.findViewById(R.id.txtInputEditText_Language);
        bankAccount = view.findViewById(R.id.txtInputEditText_BankAccount);
        studyField = view.findViewById(R.id.txtInputEditText_StudyField);
        nationality = view.findViewById(R.id.txtInputEditText_Nationality);
        identity = view.findViewById(R.id.txtInputEditText_IdentityNumber);
        gender = view.findViewById(R.id.txtInputEditText_Gender);
        birthday = view.findViewById(R.id.txtInputEditText_Birth);
        certificate = view.findViewById(R.id.txtInputEditText_Certificate);
    }


}