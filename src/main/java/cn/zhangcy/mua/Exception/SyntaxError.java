package cn.zhangcy.mua.Exception;

/**
 * Created by zcy on 27/09/2017.
 */
public class SyntaxError extends Error {

    public SyntaxError(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Syntax Error: " + message;
    }
}
