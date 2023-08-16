package trempApplication.app;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import API.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trempApplication.R;
import trempApplication.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private MyAppDatabase db;
    private CarsDao carsDao;
    private ActivityLoginBinding binding;
    private UserAPI userAPI;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userAPI = new UserAPI();
        db = MyAppDatabase.getInstance(this);
        carsDao = db.carsDao();
        carsDao.clearAllTables();
        this.context = LoginActivity.this;

        binding.btnToLogin.setOnClickListener(v -> {
            EditText et_ID = findViewById(R.id.et_Id);
            EditText et_password = findViewById(R.id.et_password);
            String ID = et_ID.getText().toString();
            String password = et_password.getText().toString();
            if (ID.equals("") || password.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
            String token = TrempFirebaseMessagingService.getToken(context);
            User user = new User(ID, password, token);

            Call<Passenger> call = userAPI.getWebServiceApi().login(user);
            call.enqueue(new Callback<Passenger>() {
                @Override
                public void onResponse(Call<Passenger> call, Response<Passenger> response) {
                    int returnValue = response.code();
                    if (returnValue >= 200 && returnValue < 300) {
                        Passenger passenger = response.body();
                        Intent i = new Intent(LoginActivity.this, StartActivity.class);
                        i.putExtra("activeUser", (Serializable) passenger);
                        // TODO: Send token to server TrempFirebaseMessagingService.getToken(context);
                        startActivity(i);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Wrong ID or Password")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface alert, int id) {
                                        alert.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }

                @Override
                public void onFailure(Call<Passenger> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Something Wrong")
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
        });

        binding.btnToRegister.setOnClickListener(v-> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });
    }

}