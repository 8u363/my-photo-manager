package my.photomanager.service.photo;

import my.photomanager.service.IDatabaseEntity;

public interface ILabel extends IDatabaseEntity {

	/**
	 * @return the label text
	 */
	String getText();
}
