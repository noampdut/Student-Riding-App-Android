package trempApplication.app;

import static trempApplication.app.Date.itsTooLate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
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
import trempApplication.databinding.ActivityOfferBinding;


public class OfferRideActivity extends AppCompatActivity {
    private ActivityOfferBinding binding;
    private Passenger activeUser;
    private Context context;
    private UserAPI userAPI;
    private MyAppDatabase db;

    CarsDao carDao;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOfferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userAPI = new UserAPI();
        this.context = OfferRideActivity.this;
        db = MyAppDatabase.getInstance(this);
        carDao = MyAppDatabase.getInstance(context).carsDao();

        AutoCompleteTextView autoCompleteTextView_to = findViewById(R.id.to_et_offer);
        AutoCompleteTextView autoCompleteTextView_from = findViewById(R.id.from_et_offer);
        autoCompleteTextView_to.setThreshold(1);
        autoCompleteTextView_from.setThreshold(1);
        autoCompleteTextView_to.setAdapter(new PlaceAutoSuggestAdapter(OfferRideActivity.this, android.R.layout.simple_list_item_1));
        autoCompleteTextView_from.setAdapter(new PlaceAutoSuggestAdapter(OfferRideActivity.this, android.R.layout.simple_list_item_1));

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            activeUser = (Passenger) intent.getSerializableExtra("activeUser");

            List<Car> cars = carDao.getCarsByUserId(activeUser.Id);
            List<String> carsNickNames = new ArrayList<String>();
            for (int i = 0; i < cars.size(); i++) {
                carsNickNames.add(cars.get(i).getNickName());
            }
            ArrayAdapter<String> carsAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, carsNickNames);
            Spinner spinner = findViewById(R.id.spinner_car);
            spinner.setAdapter(carsAdapter);

            binding.swapBtn.setOnClickListener(v -> {
                AutoCompleteTextView fromText = findViewById(R.id.from_et_offer);
                String from = fromText.getText().toString();
                String fromHint = fromText.getHint().toString();
                AutoCompleteTextView toText = findViewById(R.id.to_et_offer);
                String to = toText.getText().toString();
                String toHint = toText.getHint().toString();
                binding.fromEtOffer.setText(to);
                binding.fromEtOffer.setHint(toHint);
                binding.toEtOffer.setText(from);
                binding.toEtOffer.setHint(fromHint);
            });
            binding.homeFromOffer.setOnClickListener(v -> {
                Intent i = new Intent(OfferRideActivity.this, StartActivity.class);
                i.putExtra("activeUser", (Serializable) activeUser);
                startActivity(i);
            });

            binding.submitOffer.setOnClickListener(v -> {
                AutoCompleteTextView from_et_offer = findViewById(R.id.from_et_offer);
                AutoCompleteTextView to_et_offer = findViewById(R.id.to_et_offer);
                EditText et_number = findViewById(R.id.et_number);
                EditText et_time = findViewById(R.id.et_time);
                Spinner spinner_car = (Spinner) findViewById(R.id.spinner_car);

                String date = "";
                // get selected radio button from radioGroup
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.when_group);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                switch (selectedId) {
                    case R.id.today_radio:
                        date = "Today";
                        break;
                    case R.id.tomorrow_radio:
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

                String from = from_et_offer.getText().toString();
                String to = to_et_offer.getText().toString();
                int capacity = Integer.parseInt(et_number.getText().toString());
                String time = et_time.getText().toString();
                if (from.equals("") || to.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Please fill the all fields")
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
                if (spinner_car.getSelectedItem() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Please Insert Car")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface alert, int id) {
                                    alert.cancel();
                                    Intent i = new Intent(context, MyCarsActivity.class);
                                    i.putExtra("activeUser", (Serializable) activeUser);
                                    startActivity(i);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return;
                }
                String carSTR = spinner_car.getSelectedItem().toString();
                Car car = carDao.getCarByNameAndUserId(activeUser.Id, carSTR);
                if (itsTooLate(date, time)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Its too late")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface alert, int id) {
                                    alert.cancel();
                                    Intent i = new Intent(context, OfferRideActivity.class);
                                    i.putExtra("activeUser", (Serializable) activeUser);
                                    startActivity(i);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return;

                }
                Ride ride = new Ride(activeUser, car.getId(), capacity, from, to, time, date);

                Call<Ride> call = userAPI.getWebServiceApi().offerRide(ride);
                call.enqueue(new Callback<Ride>() {
                    @Override
                    public void onResponse(Call<Ride> call, Response<Ride> response) {
                        int returnValue = response.code();
                        if (returnValue >= 200 && returnValue < 300) {
                            Ride rideFromServer = response.body();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Thank you for your suggest, will be in touch")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            Intent i = new Intent(OfferRideActivity.this, StartActivity.class);
                                            i.putExtra("activeUser", (Serializable) activeUser);
                                            startActivity(i);
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Ride> call, Throwable t) {
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
}

