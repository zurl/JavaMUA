package cn.zhangcy.mua.Exception;

/**
 * Created by zcy on 27/09/2017.
 */
public class Error extends Throwable{
    String message;

    public Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
