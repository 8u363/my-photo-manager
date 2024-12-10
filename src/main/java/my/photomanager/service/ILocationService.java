package my.photomanager.service;

public interface ILocationService<T extends  ILocation> {

	T buildLocationFromLongitudeAndLatitude(double longitude, double latitude);
}
