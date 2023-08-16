package trempApplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import API.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trempApplication.databinding.ActivityStartBinding;


public class StartActivity extends AppCompatActivity {
    private ActivityStartBinding binding;
    private MyAppDatabase db;
    private Passenger activeUser;
    private UserAPI userAPI;
    private CarsDao carsDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userAPI = new UserAPI();
        db = MyAppDatabase.getInstance(this);
        carsDao = db.carsDao();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            activeUser = (Passenger) intent.getSerializableExtra("activeUser");
            Call<List<Car>> call = userAPI.getWebServiceApi().getCars(activeUser.getId(), "1");
            call.enqueue(new Callback<List<Car>>() {
                @Override
                public void onResponse(Call<List<Car>> call2, Response<List<Car>> response) {
                    int returnValue = response.code();
                    if (returnValue >= 200 && returnValue < 300){
                        List<Car> carsList = response.body();
                        //check if there is something in the dao
                        //System.out.println(activeUser.id.toString());
                        if (carsList.size() != 0) {
                            carsDao.insertAll(carsList);
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<Car>> call2, Throwable t) {

                }
            });
        }
        StringBuilder sb = new StringBuilder("Hello ");
        sb.append(activeUser.UserName).append(",");
        binding.hello.setText(sb);

        binding.availableRidesBtn.setOnClickListener(v ->{
            Intent i = new Intent(this, AvailableRidesActivity.class);
            i.putExtra("activeUser", (Serializable) activeUser);
            startActivity(i);
        });

        binding.historyRidesBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, HistoryRidesActivity.class);
            i.putExtra("activeUser", (Serializable) activeUser);
            startActivity(i);
        });

        binding.myCarsBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, MyCarsActivity.class);
            i.putExtra("activeUser", (Serializable) activeUser);
            startActivity(i);
        });
        binding.needRideBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, GetRideActivity.class);
            i.putExtra("activeUser", (Serializable) activeUser);
            startActivity(i);
        });
        binding.offerRideBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, OfferRideActivity.class);
            i.putExtra("activeUser",(Serializable) activeUser);
            startActivity(i);
        });

        binding.editBioStart.setOnClickListener( v -> {
            Intent i = new Intent(this, BioActivity.class);
            i.putExtra("activeUser", activeUser);
            startActivity(i);
        });
    }
}
