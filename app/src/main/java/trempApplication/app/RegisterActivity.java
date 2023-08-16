package trempApplication.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import API.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trempApplication.R;
import trempApplication.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private UserAPI userAPI;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userAPI = new UserAPI();
        context = RegisterActivity.this;

        binding.registerButton.setOnClickListener(v -> {
            EditText et_ID = findViewById(R.id.et_ID_reg);
            EditText et_username = findViewById(R.id.et_username);
            EditText et_password = findViewById(R.id.et_password_reg);
            EditText et_validate_password = findViewById(R.id.et_validation_password);
            EditText et_phone_number = findViewById(R.id.et_phone_number_reg);
            EditText et_faculty = findViewById(R.id.et_faculty);

            String ID = et_ID.getText().toString();
            String username = et_username.getText().toString();
            String password = et_password.getText().toString();
            String validate_password = et_validate_password.getText().toString();
            String phone_number = et_phone_number.getText().toString();
            String faculty = et_faculty.getText().toString();

            if (ID.equals("") || username.equals("") || password.equals("") || validate_password.equals("") ||
            phone_number.equals("") || faculty.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("Please fill all fields")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface alert, int id) {
                                alert.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return;
            }

            if(password.equals(validate_password)){
                String token = TrempFirebaseMessagingService.getToken(context);
                Call<Passenger> call = userAPI.getWebServiceApi().register(ID,username,faculty,phone_number,password, token);
                call.enqueue(new Callback<Passenger>() {
                    @Override
                    public void onResponse(Call<Passenger> call, Response<Passenger> response) {
                        int returnValue = response.code();
                        if (returnValue >= 200 && returnValue < 300) {
                            Passenger passenger = response.body();
                            Intent i = new Intent(RegisterActivity.this, BioActivity.class);
                            i.putExtra("activeUser", (Serializable) passenger);
                            startActivity(i);
                        }
                    }
                    @Override
                    public void onFailure(Call<Passenger> call, Throwable t) {
                        int returnValue = t.hashCode();
                    }
                });
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("The passwords are not equal")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface alert, int id) {
                                alert.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
