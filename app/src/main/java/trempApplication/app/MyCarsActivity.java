package trempApplication.app;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import trempApplication.R;
import trempApplication.databinding.ActivityMyCarsBinding;


public class MyCarsActivity extends AppCompatActivity {
    private ActivityMyCarsBinding binding;
    private MyAppDatabase db;
    private CarsDao carsDao;
    private Passenger activeUser;
    private List<Car> cars;
    CarsAdapter carsAdapter;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyCarsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //context = this.context;
        listView = (ListView) findViewById(R.id.carsListView);
        db = MyAppDatabase.getInstance(this);
        carsDao = db.carsDao();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            activeUser = (Passenger) intent.getSerializableExtra("activeUser");
            cars = carsDao.getCarsByUserId(activeUser.Id);
            carsAdapter = new CarsAdapter(MyCarsActivity.this, cars, activeUser);
            listView.setAdapter(carsAdapter);
        }

        binding.AddNewCar.setOnClickListener(v -> {
            Intent i = new Intent(MyCarsActivity.this, AddNewCarActivity.class);
            i.putExtra("activeUser", (Serializable) activeUser);
            startActivity(i);
        });
        binding.homeFromCars.setOnClickListener(v -> {
            Intent i = new Intent(MyCarsActivity.this, StartActivity.class);
            i.putExtra("activeUser", (Serializable) activeUser);
            startActivity(i);
        });

    }

/*    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume(){
        super.onResume();
        cars = carsDao.getCarsByUserId(activeUser.id);
        carsAdapter.notifyDataSetChanged();
        listView.setAdapter(carsAdapter);
        binding.carsListView.setVisibility(View.VISIBLE);
    }*/
}
