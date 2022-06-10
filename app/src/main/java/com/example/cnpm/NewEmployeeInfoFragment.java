package com.example.cnpm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cnpm.Database.AppDataBase;
import com.example.cnpm.Database.Department;
import com.example.cnpm.Database.Employee;
import com.example.cnpm.Database.HR;
import com.example.cnpm.Database.Private_Info;
import com.example.cnpm.Database.Work_Info;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewEmployeeInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewEmployeeInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewEmployeeInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewEmployeeInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewEmployeeInfoFragment newInstance(String param1, String param2) {
        NewEmployeeInfoFragment fragment = new NewEmployeeInfoFragment();
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
        return inflater.inflate(R.layout.fragment_new_employee_info, container, false);
    }

    ActivityResultLauncher<String> permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (!result)
                        Toast.makeText(getContext(),
                                "You won't be able to load photos from external files",
                                Toast.LENGTH_SHORT).show();
                }
            });

    ActivityResultLauncher<String> pickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null)
                        loadImageFromUri(result);
                }
            });

    ImageView imageView;
    Bitmap currentBitmap;
    private void loadImageFromUri(Uri uri) {
        try (InputStream imageStream = getActivity().getContentResolver().openInputStream(uri)) {
            currentBitmap = BitmapFactory.decodeStream(imageStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(currentBitmap);
    }
    Button btnSave;
    Button btnBack;
    Button btnpickFromGallery;
    Button btnDelete;
    Image Avt;
    TextInputEditText textInputEditText_Name;
    TextInputEditText textInputEditText_PhoneNumber;
    TextInputEditText textInputEditText_WorkEmail;
    TextInputEditText textInputEditText_Department;
    TextInputEditText textInputEditText_Manager;
    TextInputEditText textInputEditText_Coach;
    TextInputEditText textInputEditText_JobPosition;
    TextInputEditText textInputEditText_BasicSalary;
    TextInputEditText textInputEditText_EmployeeType;
    TextInputEditText textInputEditText_WorkAddress;
    TextInputEditText textInputEditText_WorkHours;
    TextInputEditText textInputEditText_PrAddress;
    TextInputEditText textInputEditText_PrEmail;
    TextInputEditText textInputEditText_PrLanguage;
    TextInputEditText textInputEditText_PrBirth;
    TextInputEditText textInputEditText_PrGender;
    TextInputEditText textInputEditText_PrCertificate;
    TextInputEditText textInputEditText_PrStudyField;
    TextInputEditText textInputEditText_PrBankAcc;
    boolean check = false;
    public int work_info_id;
    public int private_info_id;
    public int hr_id;
    AppDataBase dataBase;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBase = new AppDataBase(getContext(), "database5.db", null, 1);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        btnpickFromGallery = view.findViewById(R.id.btnsetPicture);
        imageView = view.findViewById(R.id.imageAvt);
        textInputEditText_Name = (TextInputEditText) view.findViewById(R.id.txtInputEditText_Name);
        textInputEditText_PhoneNumber = (TextInputEditText) view.findViewById(R.id.txtInputEditText_PhoneNumber);
        textInputEditText_WorkEmail = (TextInputEditText) view.findViewById(R.id.txtInputEditText_WorkEmail);
        textInputEditText_Department = (TextInputEditText) view.findViewById(R.id.txtInputEditText_Department);
        textInputEditText_Manager = (TextInputEditText) view.findViewById(R.id.txtInputEditText_Manager);
        textInputEditText_Coach = (TextInputEditText) view.findViewById(R.id.txtInputEditText_Coach);
        textInputEditText_JobPosition = (TextInputEditText) view.findViewById(R.id.txtInputEditText_JobPosition);
        textInputEditText_BasicSalary = (TextInputEditText) view.findViewById(R.id.txtInputEditText_BasicSalary);
        textInputEditText_EmployeeType = (TextInputEditText) view.findViewById(R.id.txtInputEditText_EmployeeType);
        textInputEditText_WorkAddress = (TextInputEditText) view.findViewById(R.id.txtInputEditText_WorkAddress);
        textInputEditText_WorkHours = (TextInputEditText) view.findViewById(R.id.txtInputEditText_WorkHours);
        textInputEditText_PrAddress = (TextInputEditText) view.findViewById(R.id.txtInputEditText_Address);
        textInputEditText_PrBankAcc = (TextInputEditText) view.findViewById(R.id.txtInputEditText_BankAccount );
        textInputEditText_PrBirth = (TextInputEditText) view.findViewById(R.id.txtInputEditText_Birth );
        textInputEditText_PrCertificate = (TextInputEditText) view.findViewById(R.id.txtInputEditText_Certificate );
        textInputEditText_PrEmail = (TextInputEditText) view.findViewById(R.id.txtInputEditText_Email );
        textInputEditText_PrGender = (TextInputEditText) view.findViewById(R.id.txtInputEditText_Gender );
        textInputEditText_PrLanguage = (TextInputEditText) view.findViewById(R.id.txtInputEditText_Language );
        textInputEditText_PrStudyField = (TextInputEditText) view.findViewById(R.id.txtInputEditText_StudyField );

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_newEmployeeInfoFragment_to_employeeFragment);
            }
        });

        btnpickFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickerLauncher.launch("image/*");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textInputEditText_Name.getText().toString().equals("")){
                    textInputEditText_Name.setError("Please fill out information!");
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                }else if (textInputEditText_JobPosition.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_JobPosition.setError("Please fill out information!");
                } else if (textInputEditText_PhoneNumber.getText().toString().equals("") || textInputEditText_PhoneNumber.getText().length() < 10) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_PhoneNumber.setError("Please fill out information!");
                } else if (textInputEditText_WorkEmail.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_WorkEmail.setError("Please fill out information!");
                } else if (textInputEditText_Department.getText().toString().equals("") ) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_Department.setError("Please fill out information!");
                } else if (textInputEditText_Manager.getText().toString().equals("") ) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_Manager.setError("Please fill out information!");
                } else if (textInputEditText_Coach.getText().toString().equals("") ) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_Coach.setError("Please fill out information!");
                } else if (textInputEditText_BasicSalary.getText().toString().equals("") ) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_BasicSalary.setError("Please fill out information!");
                } else if (textInputEditText_EmployeeType.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_EmployeeType.setError("Please fill out information!");
                } else if (textInputEditText_WorkAddress.getText().toString().equals("") ) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_WorkAddress.setError("Please fill out information!");
                } else if (textInputEditText_WorkHours.getText().toString().equals("") ) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_WorkHours.setError("Please fill out information!");
                } else if (textInputEditText_PrAddress.getText().toString().equals("") ) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_PrAddress.setError("Please fill out information!");
                } else if (textInputEditText_PrEmail.getText().toString().equals("") ) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_PrEmail.setError("Please fill out information!");
                } else if (textInputEditText_PrLanguage.getText().toString().equals("") ) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_PrLanguage.setError("Please fill out information!");
                } else if (textInputEditText_PrBankAcc.getText().toString().equals("") ) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_PrBankAcc.setError("Please fill out information!");
                } else if (textInputEditText_PrStudyField.getText().toString().equals("") ) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_PrStudyField.setError("Please fill out information!");
                } else if (textInputEditText_PrGender.getText().toString().equals("") ) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_PrGender.setError("Please fill out information!");
                } else if (textInputEditText_PrBirth.getText().toString().equals("") ) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_PrBirth.setError("Please fill out information!");
                } else if (textInputEditText_PrCertificate.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please fill out information!", Toast.LENGTH_LONG).show();
                    textInputEditText_PrCertificate.setError("Please fill out information!");
                } else {
                    check = true;
                }
                if (check) {
                    Employee employee = new Employee(textInputEditText_Name.getText().toString(),textInputEditText_Department.getText().toString(),textInputEditText_PhoneNumber.getText().toString(),textInputEditText_WorkEmail.getText().toString(),
                            textInputEditText_Manager.getText().toString(),textInputEditText_Coach.getText().toString(),null,Integer.parseInt(textInputEditText_BasicSalary.getText().toString()),work_info_id,private_info_id,hr_id);
                    HR hr = new HR(textInputEditText_JobPosition.getText().toString(), textInputEditText_EmployeeType.getText().toString());
                    Work_Info work_info = new Work_Info(textInputEditText_WorkAddress.getText().toString(), Integer.parseInt(textInputEditText_WorkHours.getText().toString()));
                    Private_Info private_info = new Private_Info(textInputEditText_PrAddress.getText().toString(), textInputEditText_PrEmail.getText().toString(), textInputEditText_PrLanguage.getText().toString(),
                            textInputEditText_PrBankAcc.getText().toString(),textInputEditText_PrStudyField.getText().toString(),textInputEditText_PrGender.getText().toString(),textInputEditText_PrBirth.getText().toString(),textInputEditText_PrCertificate.getText().toString());
                    Department department = new Department(textInputEditText_Department.getText().toString(),134, textInputEditText_Manager.getText().toString());
                    dataBase.addOne(employee);
                    dataBase.addOne(hr);
                    dataBase.addOne(work_info);
                    dataBase.addOne(private_info);
                    dataBase.addOne(department);
                    Toast.makeText(getContext(), "Successful!", Toast.LENGTH_SHORT).show();
                    //Navigation.findNavController(view).navigate(R.id.action_newEmployeeInfoFragment_to_employeeFragment);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Employee employee = new Employee(textInputEditText_Name.getText().toString(),textInputEditText_Department.getText().toString(),textInputEditText_PhoneNumber.getText().toString(),textInputEditText_WorkEmail.getText().toString(),
                        textInputEditText_Manager.getText().toString(),textInputEditText_Coach.getText().toString(),null,Integer.parseInt(textInputEditText_BasicSalary.getText().toString()),work_info_id,private_info_id,hr_id);
                dataBase.deleteOne(employee);
                Toast.makeText(getContext(), "Deleted!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}