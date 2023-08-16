package trempApplication.app;
import static trempApplication.app.Date.itsTooLate;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;
import API.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trempApplication.R;
import trempApplication.databinding.ActivityGetBinding;


public class GetRideActivity extends AppCompatActivity {
    private ActivityGetBinding binding;
    private UserAPI userAPI;
    private Passenger activeUser;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.context = GetRideActivity.this;
        if (intent.getExtras() != null) {
            activeUser = (Passenger) intent.getSerializableExtra("activeUser");
        }
        userAPI = new UserAPI();
        binding = ActivityGetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AutoCompleteTextView autoCompleteTextView_to = findViewById(R.id.to_et_get);
        AutoCompleteTextView autoCompleteTextView_from = findViewById(R.id.from_et_get);
        autoCompleteTextView_to.setThreshold(1);
        autoCompleteTextView_from.setThreshold(1);
        autoCompleteTextView_to.setAdapter(new PlaceAutoSuggestAdapter(GetRideActivity.this, android.R.layout.simple_list_item_1));
        autoCompleteTextView_from.setAdapter(new PlaceAutoSuggestAdapter(GetRideActivity.this, android.R.layout.simple_list_item_1));

        binding.swapBtn.setOnClickListener(v -> {
            //AutoCompleteTextView fromText = findViewById(R.id.from_et_get);
            String from = autoCompleteTextView_from.getText().toString();
            String fromHint = autoCompleteTextView_from.getHint().toString();
            //AutoCompleteTextView toText = findViewById(R.id.to_et_get);
            String to = autoCompleteTextView_to.getText().toString();
            String toHint = autoCompleteTextView_to.getHint().toString();
            binding.fromEtGet.setText(to);
            binding.fromEtGet.setHint(toHint);
            binding.toEtGet.setText(from);
            binding.toEtGet.setHint(fromHint);
        });
        binding.homeFromGet.setOnClickListener(v -> {
            Intent i = new Intent(GetRideActivity.this, StartActivity.class);
            i.putExtra("activeUser", (Parcelable) activeUser);
            startActivity(i);
        });

        binding.getOptionalRides.setOnClickListener(v -> {
            EditText pickup_time_et = findViewById(R.id.editTextTime);

            String to = autoCompleteTextView_to.getText().toString();
            String from = autoCompleteTextView_from.getText().toString();
            String pickup_time = pickup_time_et.getText().toString();
            if (to.equals("") || from.equals("") || pickup_time.equals("")) {
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

            String date = "";
            // get selected radio button from radioGroup
            RadioGroup radioGroup = findViewById(R.id.when_group_get);
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton radioButton = findViewById(selectedId);
            switch(selectedId) {
                case R.id.today_radio_get:
                    date = "Today";
                    break;
                case R.id.tomorrow_radio_get:
                    date = "Tomorrow";
                    break;
                default:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Please choose date")
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
            if (itsTooLate(date, pickup_time)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Its too late")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface alert, int id) {
                                alert.cancel();
                                Intent i = new Intent(context, GetRideActivity.class);
                                i.putExtra("activeUser",(Serializable) activeUser);
                                startActivity(i);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return;
            }
            //create post request to the server with all the form, and move to the list with all the suggested ride
            Request request = new Request(to, from, pickup_time, date, activeUser.IdNumber);
            Call<List<SuggestedRide>> call = userAPI.getWebServiceApi().getSuggestedRides(request);
            call.enqueue(new Callback<List<SuggestedRide>>() {
                @Override
                public void onResponse(Call<List<SuggestedRide>> call, Response<List<SuggestedRide>> response) {
                    int returnValue = response.code();
                    if (returnValue >= 200 && returnValue < 300) {
                        List<SuggestedRide> suggestedRides = response.body();
                        if (suggestedRides == null || suggestedRides.size() == 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("no suggested rides")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                            return;
                        }
                        SuggestedRidesAndPassenger suggestedRidesAndPassenger = new SuggestedRidesAndPassenger(activeUser,suggestedRides, request.origin, request.destination, request.date.getDate(), request.date.getTime());
                        Intent i = new Intent(GetRideActivity.this, SuggestedRideActivity.class);
                        i.putExtra("suggestedRidesAndPassenger", suggestedRidesAndPassenger);
                        startActivity(i);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Something failed")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }

                @Override
                public void onFailure(Call<List<SuggestedRide>> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Something failed")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        });
    }
}


