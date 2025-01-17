package my.photomanager.photo.metadata;

import lombok.NonNull;

public record MetaData(int photoWidth,int photoHeight,@NonNull String photoCreationTimeStamp,double photoGPSLongitude,double photoGPSLatitude) {
    
}
