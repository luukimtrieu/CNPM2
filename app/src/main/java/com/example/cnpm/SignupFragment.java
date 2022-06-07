package com.example.cnpm;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cnpm.Database.AppDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirm_password;
    private TextView textView;
    private Button btnSignUp;
    private AppDataBase db;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new AppDataBase(getContext(), "database6.db", null,1);
        editTextName = view.findViewById(R.id.edittextNameSignUp);
        editTextEmail = view.findViewById(R.id.edittextEmailSignup);
        editTextPassword = view.findViewById(R.id.edittextPasswordSignup);
        editTextConfirm_password = view.findViewById(R.id.edittextPasswordConfirm);
        textView = view.findViewById(R.id.textViewWrongEmailOrPasswordSignUp);
        btnSignUp = view.findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = true;
                if(!checkEmailFormat())
                {
                    editTextEmail.setError("Wrong email format");
                    flag = false;
                }
                if(!checkEmptyPassword()) {
                    editTextPassword.setError("Empty password");
                    flag = false;
                }
                if(!checkEmptyName()) {
                    editTextName.setError("Empty name");
                    flag = false;
                }
                if(!checkPasswordAndConfirmPassword())
                {
                    textView.setText("Password and confirm password does not match");
                    textView.setVisibility(View.VISIBLE);
                    flag = false;
                }
                if(flag) {
                    String email = editTextEmail.getText().toString();
                    String password = editTextPassword.getText().toString();
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                mAuth.signInWithEmailAndPassword(email, password);
                                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(editTextName.getText().toString()).build();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updateProfile(profileUpdate);
                                Intent intent = new Intent(getContext(), LoadingActivity.class);
                                intent.putExtra("new_account", true);
                                startActivity(intent);
                                requireActivity().finish();
                            }
                            else {
                                textView.setText("Email already registered");
                                textView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }
        });
    }

    public boolean checkEmailFormat()
    {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return editTextEmail.getText().toString().matches(regex);
    }

    public boolean checkEmptyPassword()
    {
        return !editTextPassword.getText().toString().equals("");
    }
    public boolean checkEmptyName()
    {
        return !editTextName.getText().toString().equals("");
    }

    public boolean checkPasswordAndConfirmPassword()
    {
        return editTextPassword.getText().toString().equals(editTextConfirm_password.getText().toString());
    }
}