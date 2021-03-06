package com.example.joinchat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.joinchat.Models.SignUpBody;
import com.example.joinchat.Models.SignUpResponse;
import com.example.joinchat.R;
import com.example.joinchat.utils.JsonApiHolder;
import com.example.joinchat.utils.RetrofitInstance;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {


    EditText email_edit_text;
    EditText password_edit_text;
    EditText name_edit_text;
    private JsonApiHolder jsonApiHolder;
    public static String user_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);
        jsonApiHolder = RetrofitInstance.getRetrofitInstance(getContext()).create(JsonApiHolder.class);
        Button sign_up_button = view.findViewById(R.id.sign_up_button);
        email_edit_text = view.findViewById(R.id.email_sign_up_edit_text);
        password_edit_text = view.findViewById(R.id.password_sign_up_edit_text);
        name_edit_text = view.findViewById(R.id.name_edit_text);
        TextView login_text_view = view.findViewById(R.id.login_text_view);

        sign_up_button.setOnClickListener(v -> sign_up());

        login_text_view.setOnClickListener(v -> getFragmentManager().
                beginTransaction().replace(R.id.fragment_container_login_sign_up,
                new LoginFragment()).commit());

        return view;
    }

    private void sign_up() {
        closeKeyboard();
        String name = name_edit_text.getText().toString().trim();
        String email = email_edit_text.getText().toString().trim();
        String password = password_edit_text.getText().toString().trim();
        SignUpBody sign_up = new SignUpBody(email, password, name);

        Call<SignUpResponse> call = jsonApiHolder.signUp(sign_up);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(response.isSuccessful()){
                    SignUpResponse signUpResponse = response.body();
                    user_id = signUpResponse.getUser_id();
                    getFragmentManager().
                            beginTransaction().replace(R.id.fragment_container_login_sign_up,
                            new OtpFragment()).commit();
                }
                else{
                    Toast.makeText(getContext(), "Something wrong!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void closeKeyboard() {

        View view = getActivity().getCurrentFocus();
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken() , 0);
        }
    }

}
