package trempApplication.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import API.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trempApplication.R;
import trempApplication.databinding.ActivityAddNewCarBinding;

public class AddNewCarActivity extends AppCompatActivity {
    private ActivityAddNewCarBinding binding;
    private Passenger activeUser;
    private Context context = AddNewCarActivity.this;
    private UserAPI userAPI;
    private MyAppDatabase db;
    private CarsDao carsDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userAPI = new UserAPI();
        db = MyAppDatabase.getInstance(this);
        carsDao = db.carsDao();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            activeUser = (Passenger) intent.getSerializableExtra("activeUser");
        }

        binding.addNewCar.setOnClickListener(v -> {
            EditText et_carNumber_new = findViewById(R.id.et_carNumber_new);
            EditText et_nickname_new = findViewById(R.id.et_nickname_new);
            EditText et_type_new = findViewById(R.id.et_type_new);
            EditText et_color_new = findViewById(R.id.et_color_new);
            String carNumber = et_carNumber_new.getText().toString();
            String car_nickName = et_nickname_new.getText().toString();
            String car_type = et_type_new.getText().toString();
            String car_color = et_color_new.getText().toString();

            if (carNumber.equals("") || car_nickName.equals("") || car_type.equals("")
            || car_color.equals("")) {
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

            Car car = new Car(carNumber, car_nickName, car_type, car_color, activeUser.Id);

            Call<Car> call = userAPI.getWebServiceApi().addNewCar(car);
            call.enqueue(new Callback<Car>() {
                @Override
                public void onResponse(Call<Car> call, Response<Car> response) {
                    int returnValue = response.code();
                    if (returnValue >= 200 && returnValue < 300) {
                        Car new_car = response.body();
                        carsDao.insert(new_car);
                        activeUser.CarIds.add(new_car.getId());
                        Intent i = new Intent(AddNewCarActivity.this, MyCarsActivity.class);
                        i.putExtra("activeUser", (Serializable) activeUser);
                        startActivity(i);
                    }
                }

                @Override
                public void onFailure(Call<Car> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Something failed")
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
    }
}
