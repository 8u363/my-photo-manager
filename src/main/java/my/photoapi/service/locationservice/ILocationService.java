package my.photoapi.service.locationservice;

public interface ILocationService<T extends ILocation> {

    T createLocationFromLatitudeAndLongitude(double latitude, double longitude);
}
