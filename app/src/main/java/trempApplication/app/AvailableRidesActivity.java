package trempApplication.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import API.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trempApplication.R;
import trempApplication.databinding.ActivityAvailableRidesBinding;


public class AvailableRidesActivity extends AppCompatActivity {
    private ActivityAvailableRidesBinding binding;
    private Passenger activeUser;
    private ListView listView;
    private List<Ride> rides;
    private UserAPI userAPI;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAvailableRidesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listView = (ListView) findViewById(R.id.customAvailableRideListView);
        userAPI = new UserAPI();
        context = AvailableRidesActivity.this;
        //db = MyAppDatabase.getInstance(this);
        //ridesDao = db.ridesDao();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            activeUser = (Passenger) intent.getSerializableExtra("activeUser");
        }

        binding.homeFromAvailable.setOnClickListener(v -> {
            Intent i = new Intent(AvailableRidesActivity.this, StartActivity.class);
            i.putExtra("activeUser", (Serializable) activeUser);
            startActivity(i);
        });



        Call<List<Ride>> call = userAPI.getWebServiceApi().getRidesForUser(activeUser.IdNumber, true);
        call.enqueue(new Callback<List<Ride>>() {
            @Override
            public void onResponse(Call<List<Ride>> call, Response<List<Ride>> response) {
                int returnValue = response.code();
                if (returnValue >= 200 && returnValue < 300) {
                    rides = response.body();
                    AvailableRidesAdapter availableRidesAdapterAdapter = new AvailableRidesAdapter(AvailableRidesActivity.this, rides, activeUser);
                    listView.setAdapter(availableRidesAdapterAdapter);
                } else {
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
            }

            @Override
            public void onFailure(Call<List<Ride>> call, Throwable t) {
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
    }
}
