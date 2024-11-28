package my.photoapi.service.locationservice;

import lombok.Builder;
import lombok.NonNull;

@Builder(setterPrefix = "with", builderMethodName = "")
public record Location(@NonNull String country,
                       @NonNull String city,
                       @NonNull String postalCode,
                       @NonNull String street,
                       @NonNull String houseNumber) implements ILocation {

    public static LocationBuilder builder() {
        return new LocationBuilder().withCountry("").withCity("").withPostalCode("").withStreet("").withHouseNumber("");
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getHouseNumber() {
        return houseNumber;
    }
}
