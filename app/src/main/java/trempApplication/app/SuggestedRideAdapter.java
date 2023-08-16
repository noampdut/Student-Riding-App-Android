package trempApplication.app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.SupportMapFragment;

import java.io.Serializable;
import java.util.List;

import API.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trempApplication.R;

public class SuggestedRideAdapter extends BaseAdapter{
    Context context;
    List<SuggestedRide> rides;
    LayoutInflater inflater;
    UserAPI userAPI;
    Passenger activeUser;

    //FragmentManager fragmentManager;

    //private MapsDisplay mapsDisplay;

    public SuggestedRideAdapter(Context context, List<SuggestedRide> rides, Passenger activeUser) {
        this.context = context;
        this.rides = rides;
        this.inflater = LayoutInflater.from(context);
        userAPI = new UserAPI();
        this.activeUser = activeUser;
        //this.fragmentManager = fragmentManager;

    }

    @Override
    public int getCount() {
        return rides.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_suggested_ride_item,null);
        TextView driver_name_tv = (TextView) convertView.findViewById(R.id.driver_name_tv);
        TextView pickup_time_tv = (TextView) convertView.findViewById(R.id.pickup_time_tv);
        TextView seats_tv = (TextView) convertView.findViewById(R.id.seats_tv);
        TextView rate_tv = (TextView) convertView.findViewById(R.id.rate_tv);
        SuggestedRide suggestedRide = rides.get(position);

        String name = "Driver name: " + suggestedRide.Driver.getUserName();
        String time = "Pick up time: " + suggestedRide.PickUpTime;
        String capacity = "Number of available seats: " + suggestedRide.Capacity;
        String rate = "Matching: " + suggestedRide.Similarity;
        driver_name_tv.setText(name);
        pickup_time_tv.setText(time);
        seats_tv.setText(capacity);
        rate_tv.setText(rate);

        Button reserveBtn = convertView.findViewById(R.id.reserve);
        reserveBtn.setOnClickListener(v -> {
            //SuggestedRide suggestedRide = rides.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Reserve Ride");
            StringBuilder sb = new StringBuilder("Are you sure you want to reserve this ride?" + "\n");
            sb.append("with: " + suggestedRide.Driver.getUserName() + "\n");
            sb.append("at: " + suggestedRide.PickUpTime + "\n");
            sb.append("from: " + suggestedRide.PickUpPoint + "\n");
            builder.setMessage(sb.toString());

            // Add the buttons
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Yes button
                            Call<Integer> call = userAPI.getWebServiceApi().reserveRide(suggestedRide, activeUser.IdNumber);
                            call.enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    int returnValue = response.code();
                                    if (returnValue >= 200 && returnValue < 300) {
                                        Integer result = response.body();
                                        ///already signed in the ride
                                        if (result == 2) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                            builder.setMessage("you are already signed to this ride, cannot reserved double seats")
                                                    .setCancelable(false)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface alert, int id) {
                                                            alert.cancel();
                                                        }
                                                    });
                                            AlertDialog alert = builder.create();
                                            alert.show();

                                            //success
                                        } else if (result == 0) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                            builder.setMessage("The Ride reserved")
                                                    .setCancelable(false)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface alert, int id) {
                                                            alert.cancel();
                                                            Intent i = new Intent(context, StartActivity.class);
                                                            i.putExtra("activeUser", (Serializable) activeUser);
                                                            context.startActivity(i);
                                                        }
                                                    });
                                            AlertDialog alert = builder.create();
                                            alert.show();
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                            builder.setMessage("failed to reserve the ride")
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
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage("failed to reserve the ride")
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
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked No button
                            dialog.cancel();
                        }
                    });

            // Create and show the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return convertView;
    }
}