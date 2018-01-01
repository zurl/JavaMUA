package cn.zhangcy.mua.Exception;

/**
 * Created by zcy on 27/09/2017.
 */
public class RuntimeError extends Error {

    public RuntimeError(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Runtime Error: " + message;
    }
}
