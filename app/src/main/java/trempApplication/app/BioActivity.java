package trempApplication.app;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.CheckBox;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import API.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trempApplication.R;
import trempApplication.databinding.ActivityBioBinding;

public class BioActivity extends AppCompatActivity {

    private ActivityBioBinding binding;
    private UserAPI userAPI;
    private Context context;
    private Passenger activeUser;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userAPI = new UserAPI();
        this.context = BioActivity.this;
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            activeUser = (Passenger) intent.getSerializableExtra("activeUser");

            binding.skipBioButton.setOnClickListener(v -> {
                Intent i = new Intent(this, StartActivity.class);
                i.putExtra("activeUser", (Serializable) activeUser);
                startActivity(i);
            });
            if (!activeUser.Bio.equals("")) {
                binding.currentBio.setText("Your current bio is: \n" + activeUser.Bio);
            }

            binding.bioButton.setOnClickListener(v -> {
                CheckBox checkBox_dogs = findViewById(R.id.checkbox_dogs);
                CheckBox checkBox_movies = findViewById(R.id.checkbox_movies);
                CheckBox checkBox_music = findViewById(R.id.checkbox_music);
                CheckBox checkBox_sport = findViewById(R.id.checkbox_sport);
                CheckBox checkBox_cats = findViewById(R.id.checkbox_cats);
                CheckBox checkBox_shopping = findViewById(R.id.checkbox_shopping);
                String firstBio = "";
                if (checkBox_dogs.isChecked() || checkBox_movies.isChecked()
                || checkBox_music.isChecked() || checkBox_sport.isChecked() ||
                checkBox_cats.isChecked() || checkBox_shopping.isChecked()) {
                    StringBuilder sb = new StringBuilder("I Like ");
                    if (checkBox_dogs.isChecked()) {
                        sb.append("dogs, ");
                    }
                    if (checkBox_movies.isChecked()) {
                        sb.append("movies, ");
                    }
                    if (checkBox_music.isChecked()) {
                        sb.append("music, ");
                    }
                    if (checkBox_sport.isChecked()) {
                        sb.append("sport, ");
                    }
                    if (checkBox_cats.isChecked()) {
                        sb.append("cats, ");
                    }
                    if (checkBox_shopping.isChecked()) {
                        sb.append("shopping, ");
                    }
                    firstBio = sb.toString();
                }

                String secBio = binding.etBio.getText().toString();
                String bio = "";
                if (!secBio.equals("") && !firstBio.equals("")) {
                    secBio = ("and " + secBio);
                    bio = firstBio + secBio;
                } else if(firstBio.equals("") && secBio.equals("")){
                    bio = "";
                } else if (!firstBio.equals("")) {
                    bio = firstBio;
                } else if (secBio.equals("")){
                    bio = secBio;
                }
                    ///to update the Active user, and create put request to the server
                    activeUser.setBio(bio);
                    Call<Passenger> call = userAPI.getWebServiceApi().editPassenger(activeUser.Id, activeUser);
                    call.enqueue(new Callback<Passenger>() {
                        @Override
                        public void onResponse(Call<Passenger> call, Response<Passenger> response) {
                            int returnValue = response.code();
                            if (returnValue >= 200 && returnValue < 300) {
                                Passenger passenger = response.body();
                                Intent i = new Intent(context, StartActivity.class);
                                i.putExtra("activeUser", (Serializable) passenger);
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
                        public void onFailure(Call<Passenger> call, Throwable t) {
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
