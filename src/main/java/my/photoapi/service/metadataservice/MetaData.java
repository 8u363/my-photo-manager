package my.photoapi.service.metadataservice;

import lombok.*;

@Builder(setterPrefix = "with", builderMethodName = "")
public record MetaData(int width,
                       int height,
                       @NonNull String creationTimeStamp,
                       double longitude,
                       double latitude) implements IMetaData {


    public static MetaDataBuilder builder(int width, int height, String creationTimeStamp) {
        return new MetaDataBuilder().withWidth(width)
                .withHeight(height)
                .withCreationTimeStamp(creationTimeStamp);
    }

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
