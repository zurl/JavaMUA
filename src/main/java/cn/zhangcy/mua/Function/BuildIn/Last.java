package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MList;
import cn.zhangcy.mua.Value.MLiteral;
import cn.zhangcy.mua.Value.MValue;

import java.util.ArrayList;

/**
 * Created by zcy on 27/09/2017.
 */
public class Last extends BuildInFunction {

    private static Class[] argTypes = {MValue.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        try {
            if (args[0] instanceof MLiteral){
                String value = ((MLiteral) args[0]).getValue();
                return new MLiteral(value.substring(value.length() - 1, value.length()));
            }
            else if (args[0] instanceof MList){
                ArrayList<MValue> value = ((MList) args[0]).getValue();
                return value.get(value.size() - 1);
            }
            else throw new RuntimeError("The type of arguments of function `last` is incorrect.");
        }
        catch (IndexOutOfBoundsException e){
            throw new RuntimeError("Command `last` cannot be applied for NULL list.");
        }
    }
}
