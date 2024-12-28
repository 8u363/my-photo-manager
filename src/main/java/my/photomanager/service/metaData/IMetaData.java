package my.photomanager.service.metaData;

public interface IMetaData {

	/**
	 * @return the width of the photo
	 */
	int getWidth();

	/**
	 * @return the height of the photo
	 */
	int getHeight();

	/**
	 * @return the creation time stamp of the photo
	 */
	String getCreationTimeStamp();

	/**
	 * @return the longitude of the gps coordinate
	 */
	double getLongitude();

	/**
	 * @return the latitude of the gps coordinate
	 */
	double getLatitude();
}
