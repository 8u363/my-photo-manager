package my.photomanager.service.location;


public record Location(String country,
					   String city,
					   String postalCode,
					   String street,
					   String houseNumber) implements ILocation {



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
