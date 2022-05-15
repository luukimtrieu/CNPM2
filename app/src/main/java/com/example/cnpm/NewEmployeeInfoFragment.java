package com.example.cnpm;

import static android.content.ContentResolver.*;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

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
    Button btnDiscard;
    Button btnpickFromGallery;
    TextInputEditText textInputEditText_Name;
    TextInputEditText textInputEditText_Position;
    TextInputEditText textInputEditText_PhoneNumber;
    TextInputEditText textInputEditText_WorkEmail;
    TextInputEditText textInputEditText_Department;
    TextInputEditText textInputEditText_Manager;
    TextInputEditText textInputEditText_WorkingHours;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnDiscard = (Button) view.findViewById(R.id.btnDiscard);
        imageView = view.findViewById(R.id.imageAvt);
        btnpickFromGallery = view.findViewById(R.id.btnsetPicture);
        textInputEditText_Name = (TextInputEditText) view.findViewById(R.id.txtInputEditText_Name);
        textInputEditText_Position = (TextInputEditText) view.findViewById(R.id.txtInputEditText_JobPosition);
        textInputEditText_PhoneNumber = (TextInputEditText) view.findViewById(R.id.txtInputEditText_PhoneNumber);
        textInputEditText_WorkEmail = (TextInputEditText) view.findViewById(R.id.txtInputEditText_WorkEmail);
        textInputEditText_Department = (TextInputEditText) view.findViewById(R.id.txtInputEditText_Department);
        textInputEditText_Manager = (TextInputEditText) view.findViewById(R.id.txtInputEditText_Manager);
        textInputEditText_WorkingHours = (TextInputEditText) view.findViewById(R.id.txtInputEditText_WorkingHours);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_newEmployeeInfoFragment_to_employeeFragment);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_newEmployeeInfoFragment_to_employeeFragment);
            }
        });

        btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (TextInputEditText textInputEditText : Arrays.asList(textInputEditText_Name, textInputEditText_Position, textInputEditText_WorkEmail, textInputEditText_PhoneNumber, textInputEditText_Department, textInputEditText_Manager, textInputEditText_WorkingHours)) {
                    textInputEditText.getText().clear();
                }
            }
        });

        btnpickFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickerLauncher.launch("image/*");
            }
        });
    }


}