package my.photoapi.service.locationservice;

public interface ILocationService<T extends ILocation> {

    T createLocation(double latitude, double longitude);
}
