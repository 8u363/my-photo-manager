package my.photomanager.service.metaData;

public record MetaData(int width,
					   int height,
					   String creationTimeStamp,
					   double longitude,
					   double latitude) implements IMetaData {

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public String getCreationTimeStamp() {
		return creationTimeStamp;
	}

	@Override
	public double getLongitude() {
		return longitude;
	}

	@Override
	public double getLatitude() {
		return latitude;
	}

}
