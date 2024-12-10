package my.photomanager.service.location;

public interface ILocationService<T extends  ILocation> {

	T buildLocationFromLongitudeAndLatitude(double longitude, double latitude);
}
