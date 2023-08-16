package trempApplication.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import API.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trempApplication.R;
import trempApplication.databinding.ActivityLoginBinding;

public class CarsAdapter extends BaseAdapter {

    Context context;
    List<Car> cars;
    LayoutInflater inflater;
    Passenger activeUser;
    private MyAppDatabase db;
    private CarsDao carsDao;
    private UserAPI userAPI;

    public CarsAdapter(Context context, List<Car> cars, Passenger activeUser) {
        this.context = context;
        this.cars = cars;
        this.inflater = LayoutInflater.from(context);
        this.activeUser = activeUser;
        userAPI = new UserAPI();
        db = MyAppDatabase.getInstance(context);
        carsDao = db.carsDao();
    }
    @Override
    public int getCount() {
        return cars.size();
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
        convertView = inflater.inflate(R.layout.activity_custom_car_item,null);
        TextView car_name_tv = (TextView) convertView.findViewById(R.id.car_name_tv);
        TextView car_number_tv = (TextView) convertView.findViewById(R.id.car_number_tv);
        TextView car_type_tv = (TextView) convertView.findViewById(R.id.car_type_tv);
        car_name_tv.setText(cars.get(position).getNickName());
        car_number_tv.setText(cars.get(position).getCarNumber());
        car_type_tv.setText(cars.get(position).getType());

        Button editButton = convertView.findViewById(R.id.edit_btn_car);
        editButton.setOnClickListener( v -> {
            Car selectedCar = cars.get(position);
            CarAndPassenger carAndPassenger= new CarAndPassenger(selectedCar, activeUser);
            Intent i = new Intent(context, EditCarActivity.class);
            i.putExtra("carAndPassenger", carAndPassenger);
            context.startActivity(i);

        });

        Button deleteBtn = convertView.findViewById(R.id.delete_car_btn);
        deleteBtn.setOnClickListener(v -> {
            Car selectedCar = cars.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Car");
            StringBuilder sb = new StringBuilder("Are you sure you want to delete ");
            sb.append(selectedCar.getNickName());
            builder.setMessage(sb);

            // Add the buttons
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Yes button
                            // Implement the delete functionality here
                            Call<Void> call = userAPI.getWebServiceApi().deleteCar(selectedCar.getId());
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    int returnValue = response.code();
                                    if (returnValue >= 200 && returnValue < 300) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setMessage("The car removed")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface alert, int id) {
                                                        alert.cancel();
                                                        Intent i = new Intent(context, MyCarsActivity.class);
                                                        i.putExtra("activeUser", (Serializable) activeUser);
                                                        context.startActivity(i);
                                                    }
                                                });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                        carsDao.delete(selectedCar);
                                        for (int i = 0; i < activeUser.CarIds.size(); i++) {
                                            if (activeUser.CarIds.get(i).equals(selectedCar.getId())){
                                                activeUser.CarIds.remove(i);
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage("failed to delete the car")
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
