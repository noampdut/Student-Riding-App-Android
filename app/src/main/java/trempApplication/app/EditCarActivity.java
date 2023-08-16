package trempApplication.app;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import API.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trempApplication.R;
import trempApplication.databinding.ActivityEditCarBinding;

public class EditCarActivity extends AppCompatActivity {
    private ActivityEditCarBinding binding;
    private Passenger activeUser;
    private Car selectedCar;
    //private Context context = EditCarActivity.this;
    private UserAPI userAPI;
    private MyAppDatabase db;
    private CarsDao carsDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userAPI = new UserAPI();
        db = MyAppDatabase.getInstance(this);
        carsDao = db.carsDao();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            CarAndPassenger carAndPassenger = (CarAndPassenger) intent.getSerializableExtra("carAndPassenger");
            if (carAndPassenger.getCar() != null){
                selectedCar = carAndPassenger.getCar();
            }
            if (carAndPassenger.getPassenger() != null){
                activeUser = carAndPassenger.getPassenger();
            }
        }

        EditText et_carNumber_edit = (EditText) findViewById(R.id.et_carNumber_edit);
        EditText et_nickname_edit = (EditText) findViewById(R.id.et_nickname_edit);
        EditText et_type_edit = (EditText) findViewById(R.id.et_type_edit);
        EditText et_color_edit = (EditText) findViewById(R.id.et_color_edit);
        et_carNumber_edit.setText(selectedCar.getCarNumber());
        et_nickname_edit.setText(selectedCar.getNickName());
        et_type_edit.setText(selectedCar.getType());
        et_color_edit.setText(selectedCar.getColor());

        binding.editCarSave.setOnClickListener(v -> {
            EditText et_carNumber = findViewById(R.id.et_carNumber_edit);
            EditText et_nickname = findViewById(R.id.et_nickname_edit);
            EditText et_type = findViewById(R.id.et_type_edit);
            EditText et_color = findViewById(R.id.et_color_edit);
            String carNumber = et_carNumber.getText().toString();
            String car_nickName = et_nickname.getText().toString();
            String car_type = et_type.getText().toString();
            String car_color = et_color.getText().toString();
            selectedCar.setCarNumber(carNumber);
            selectedCar.setNickName(car_nickName);
            selectedCar.setType(car_type);
            selectedCar.setColor(car_color);

            Call<Car> call = userAPI.getWebServiceApi().editCar(selectedCar.getId(), selectedCar);
            call.enqueue(new Callback<Car>() {
                @Override
                public void onResponse(Call<Car> call, Response<Car> response) {
                    int returnValue = response.code();
                    if (returnValue >= 200 && returnValue < 300) {
                        Car new_car = response.body();
                        carsDao.update(new_car);
                        Intent i = new Intent(EditCarActivity.this, MyCarsActivity.class);
                        i.putExtra("activeUser", (Serializable) activeUser);
                        startActivity(i);
                    }
                }

                @Override
                public void onFailure(Call<Car> call, Throwable t) {}
            });

        });
    }
}
