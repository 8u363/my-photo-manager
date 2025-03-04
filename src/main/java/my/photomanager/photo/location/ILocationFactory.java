package my.photomanager.photo.location;

@FunctionalInterface
public interface ILocationFactory {

    Location createLocation(double longitude, double latitude);

}
