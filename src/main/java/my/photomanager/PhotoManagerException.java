package my.photomanager;

public class PhotoManagerException extends Exception {

    public PhotoManagerException(String msg) {
        super(msg);
    }

    public PhotoManagerException(Throwable e) {
        super(e);
    }

}
