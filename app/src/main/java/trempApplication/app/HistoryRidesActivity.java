package trempApplication.app;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;
import API.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trempApplication.R;
import trempApplication.databinding.ActivityHistoryRidesBinding;

public class HistoryRidesActivity extends AppCompatActivity {
    private ActivityHistoryRidesBinding binding;
    private MyAppDatabase db;
    private Passenger activeUser;
    private ListView listView;
    private List<Ride> rides;
    private UserAPI userAPI;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryRidesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listView = (ListView) findViewById(R.id.history_rides_list_view);
        userAPI = new UserAPI();
        db = MyAppDatabase.getInstance(this);
        context = HistoryRidesActivity.this;
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            activeUser = (Passenger) intent.getSerializableExtra("activeUser");
        }
        binding.homeFromHistory.setOnClickListener(v -> {
            Intent i = new Intent(context, StartActivity.class);
            i.putExtra("activeUser", (Serializable) activeUser);
            startActivity(i);
        });

        Call<List<Ride>> call = userAPI.getWebServiceApi().getRidesForUser(activeUser.IdNumber, false);
        call.enqueue(new Callback<List<Ride>>() {
            @Override
            public void onResponse(Call<List<Ride>> call, Response<List<Ride>> response) {
                int returnValue = response.code();
                if (returnValue >= 200 && returnValue < 300) {
                    rides = response.body();
                    HistoryRideAdapter historyRideAdapter = new HistoryRideAdapter(HistoryRidesActivity.this, rides,activeUser);
                    listView.setAdapter(historyRideAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Ride>> call, Throwable t) {

            }
        });
    }
}
