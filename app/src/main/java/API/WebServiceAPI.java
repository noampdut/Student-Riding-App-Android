package API;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import trempApplication.app.Address;
import trempApplication.app.Car;
import trempApplication.app.Passenger;
import trempApplication.app.Request;
import trempApplication.app.Ride;
import trempApplication.app.SuggestedRide;
import trempApplication.app.User;

public interface WebServiceAPI {

    @POST("users")
    Call<Passenger> login(@Body User user);

    @POST("users/{IdNumber}/{UserName}/{Faculty}/{PhoneNumber}/{Token}")
    Call<Passenger> register(@Path("IdNumber") String IdNumber, @Path("UserName") String UserName, @Path("Faculty") String Faculty, @Path("PhoneNumber") String PhoneNumber, @Body String password, @Path("Token") String token);

    @POST("rides")
    Call<Ride> offerRide(@Body Ride ride);

    @GET("cars/{owner}/{id}")
    Call<List<Car>> getCars(@Path("owner")UUID uuid, @Path("id") String id);

    @POST("cars")
    Call<Car> addNewCar(@Body Car car);

    @PUT("cars/{id}")
    Call<Car> editCar(@Path("id")UUID id, @Body Car car);

    @DELETE("cars/{id}")
    Call<Void> deleteCar(@Path("id")UUID id);

    @GET("passengers/{id}")
    Call<Passenger> getPassenger(@Path("id")UUID id);

    @GET("rides/{id}/{getActiveRides}")
    Call<List<Ride>> getRidesForUser(@Path("id") String id, @Path("getActiveRides") boolean getActiveRides);

    @POST("route")
    Call<List<SuggestedRide>> getSuggestedRides(@Body Request request);

    @PUT("rides/{id}")
    Call<Integer> reserveRide(@Body SuggestedRide suggestedRide, @Path("id") String id);

    @GET("cars/{id}")
    Call<Car> getCar(@Path("id")UUID uuid);

    @PUT("passengers/{id}")
    Call<Passenger> editPassenger(@Path("id") UUID id, @Body Passenger passenger);

    @PUT("passengers")
    Call<List<Passenger>> getPassengers(@Body List<String> ids);

    @PUT("rides/{id}/{driveId}")
    Call<Void> cancelRide(@Path("id") String id, @Path("driveId") UUID driveId);

    @GET("rides/{rideId}/{id}/{passId}")
    Call<String> getURLGoogleMaps(@Path("rideId")UUID uuid, @Path("id")String id, @Path("passId")String passId);
}
