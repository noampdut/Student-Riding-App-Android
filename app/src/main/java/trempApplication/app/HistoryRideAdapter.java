package trempApplication.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import API.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trempApplication.R;


public class HistoryRideAdapter extends BaseAdapter {
    Context context;
    List<Ride> rides;
    LayoutInflater inflater;
    Passenger activeUser;
    private UserAPI userAPI;

    public HistoryRideAdapter(Context context, List<Ride> rides, Passenger activeUser) {
        this.context = context;
        this.rides = rides;
        this.inflater = LayoutInflater.from(context);
        this.activeUser = activeUser;
        userAPI = new UserAPI();
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


    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_history_ride_item,null);
        TextView driver_name_tv = (TextView) convertView.findViewById(R.id.driver_name_tv_his);
        TextView date_tv = (TextView) convertView.findViewById(R.id.date_tv_his);
        TextView dest_tv_his = (TextView) convertView.findViewById(R.id.dest_tv_his);

        String dest = rides.get(position).Dest;

        Passenger driver = rides.get(position).Driver;
        driver_name_tv.setText("Driver name: " + driver.UserName);
        date_tv.setText("Date: " + rides.get(position).getDate().getDate());
        dest_tv_his.setText("Destination: "+ dest);
        Ride ride = rides.get(position);
        Button info_btn_history = convertView.findViewById(R.id.info_btn_history);
        info_btn_history.setOnClickListener(v -> {
            if (!isDriver(ride)) {
                Call<Car> call = userAPI.getWebServiceApi().getCar(ride.CarId);
                call.enqueue(new Callback<Car>() {
                    @Override
                    public void onResponse(Call<Car> call, Response<Car> response) {
                        int returnValue = response.code();
                        if (returnValue >= 200 && returnValue < 300) {
                            Car car = response.body();
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                            StringBuilder sb = new StringBuilder("This is the Ride details:\n");
                            sb.append("Driver name: ").append(driver.UserName).append("\n");
                            sb.append("Driver phone number: ").append(driver.PhoneNumber).append("\n");
                            sb.append("Ride date: ").append(ride.getDate().getDate()).append("\n");
                            String pickupTime = getPickUpTime(ride);
                            sb.append("Ride time: ").append(pickupTime).append("\n");
                            String pickupPoint = getPickUpPoint(ride);
                            sb.append("Pickup Point: ").append(pickupPoint).append("\n");
                            sb.append("Destination: ").append(ride.Dest).append("\n");
                            sb.append("Car number: ").append(car.getCarNumber()).append("\n");
                            sb.append("Car color: ").append(car.getColor()).append("\n");
                            sb.append("Car: ").append(car.getType()).append("\n");
                            builder2.setMessage(sb.toString())
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface alert, int id) {
                                            alert.cancel();
                                            Intent i = new Intent(context, AvailableRidesActivity.class);
                                            i.putExtra("activeUser", (Serializable) activeUser);
                                            context.startActivity(i);
                                        }
                                    });
                            AlertDialog alert2 = builder2.create();
                            alert2.show();

                        } else {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setMessage("Something wrong, could not find the details")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface alert, int id) {
                                            alert.cancel();
                                            Intent i = new Intent(context, AvailableRidesActivity.class);
                                            i.putExtra("activeUser", (Serializable) activeUser);
                                            context.startActivity(i);
                                        }
                                    });
                            AlertDialog alert = builder1.create();
                            alert.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Car> call, Throwable t) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Something wrong, could not find the details")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface alert, int id) {
                                        alert.cancel();
                                        Intent i = new Intent(context, AvailableRidesActivity.class);
                                        i.putExtra("activeUser", (Serializable) activeUser);
                                        context.startActivity(i);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
            }
            else {
                List<String> ids = getPassengers(ride);
                Call<List<Passenger>> call2 = userAPI.getWebServiceApi().getPassengers(ids);
                call2.enqueue(new Callback<List<Passenger>>() {
                    @Override
                    public void onResponse(Call<List<Passenger>> call, Response<List<Passenger>> response) {
                        int returnValue = response.code();
                        if (returnValue >= 200 && returnValue < 300) {
                            List<Passenger> passengerList = response.body();
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(context);
                            StringBuilder sb = new StringBuilder("This is the Ride details:\n");
                            sb.append("\n");
                            sb.append("Ride date: ").append(ride.getDate().getDate()).append("\n");
                            sb.append("departure time: ").append(ride.getDate().getTime()).append("\n");
                            for (int i = 0; i < ride.pickUpPoints.size(); i++) {
                                PickUpPoint pickUpPoint = ride.pickUpPoints.get(i);
                                sb.append("Pick up point: ").append(i).append(" :\n");
                                sb.append("address: ").append(pickUpPoint.Address).append("\n");
                                sb.append("pick up time: ").append(pickUpPoint.Time).append("\n");
                                ////TO add passanger name and phone
                                sb.append("Passenger name: ").append(passengerList.get(i).UserName).append("\n");
                                sb.append("Passenger phone: ").append(passengerList.get(i).PhoneNumber).append("\n");
                            }
                            builder3.setMessage(sb.toString())
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface alert, int id) {
                                            alert.cancel();
                                            Intent i = new Intent(context, AvailableRidesActivity.class);
                                            i.putExtra("activeUser", (Serializable) activeUser);
                                            context.startActivity(i);
                                        }
                                    });
                            AlertDialog alert3 = builder3.create();
                            alert3.show();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Passenger>> call, Throwable t) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Something wrong, could not find the details")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface alert, int id) {
                                        alert.cancel();
                                        Intent i = new Intent(context, AvailableRidesActivity.class);
                                        i.putExtra("activeUser", (Serializable) activeUser);
                                        context.startActivity(i);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
            }
        });
        return convertView;
    }

    public boolean isDriver(Ride ride) {
        return ride.getDriver().IdNumber.equals(activeUser.IdNumber);
    }
    //to check this
    public String getPickUpPoint(Ride ride) {
        String pickupPoint ="";
        for (int i = 0; i < ride.pickUpPoints.size(); i++) {
            if (ride.pickUpPoints.get(i).PassengerId.equals(activeUser.IdNumber)) {
                pickupPoint =  ride.pickUpPoints.get(i).Address;
                break;
            }
        }
        return pickupPoint;
    }

    public String getPickUpTime(Ride ride) {
        String pickupTime ="";
        for (int i = 0; i < ride.pickUpPoints.size(); i++) {
            if (ride.pickUpPoints.get(i).PassengerId.equals(activeUser.IdNumber)) {
                pickupTime =  ride.pickUpPoints.get(i).Time;
                break;
            }
        }
        return pickupTime;
    }

    public List<String> getPassengers(Ride ride) {
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < ride.pickUpPoints.size(); i++) {
            ids.add(ride.pickUpPoints.get(i).PassengerId);
        }
        return ids;
    }

}
