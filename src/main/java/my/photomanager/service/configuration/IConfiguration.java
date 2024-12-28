package my.photomanager.service.configuration;

import my.photomanager.service.IDatabaseEntity;

public interface IConfiguration  extends IDatabaseEntity {
	String getFolderPath();

	String getUpdateInterval();
}
