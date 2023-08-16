package trempApplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.io.Serializable;
import java.util.List;

import trempApplication.R;
import trempApplication.databinding.ActivityGetBinding;
import trempApplication.databinding.ActivitySuggestedListBinding;

public class SuggestedRideActivity extends AppCompatActivity {
    private ActivitySuggestedListBinding binding;
    Passenger activeUser;
    List<SuggestedRide> suggestedRides;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuggestedListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listView = (ListView) findViewById(R.id.SuggestedRideListView);
        Intent intent = getIntent();
        SuggestedRidesAndPassenger suggestedRidesAndPassenger;
        if (intent.getExtras() != null) {
            suggestedRidesAndPassenger= (SuggestedRidesAndPassenger) intent.getSerializableExtra("suggestedRidesAndPassenger");
            activeUser = suggestedRidesAndPassenger.passenger;
            suggestedRides = suggestedRidesAndPassenger.suggestedRides;
            //FragmentManager fragmentManager = getSupportFragmentManager();
            SuggestedRideAdapter suggestedRideAdapter = new SuggestedRideAdapter(SuggestedRideActivity.this, suggestedRides, activeUser);
            listView.setAdapter(suggestedRideAdapter);

            binding.homeFromSuggested.setOnClickListener(v -> {
                Intent i = new Intent(SuggestedRideActivity.this, StartActivity.class);
                i.putExtra("activeUser", (Serializable) activeUser);
                startActivity(i);
            });
            StringBuilder sb = new StringBuilder("There are suggests for ride \nfrom:");
            sb.append(suggestedRidesAndPassenger.source + "\n");
            sb.append("to: " + suggestedRidesAndPassenger.dest + "\n");
            sb.append("in " + suggestedRidesAndPassenger.date + "\n");
            sb.append("in " + suggestedRidesAndPassenger.time + "\n");
            binding.detailsFromSuggested.setText(sb);
        }

    }
}
