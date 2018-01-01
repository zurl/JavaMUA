package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MInteger;
import cn.zhangcy.mua.Value.MList;
import cn.zhangcy.mua.Value.MLiteral;
import cn.zhangcy.mua.Value.MValue;

import java.util.ArrayList;

/**
 * Created by zcy on 27/09/2017.
 */
public class Item implements BuildInFunction {

    private static Class[] argTypes = {MInteger.class, MValue.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        try {
            if (args[1] instanceof MLiteral){
                String value = ((MLiteral) args[1]).getValue();
                return new MLiteral(value.charAt(((MInteger) args[0]).getValue()) + "");
            }
            else if (args[1] instanceof MList){
                ArrayList<MValue> value = ((MList) args[1]).getValue();
                return value.get(((MInteger) args[0]).getValue());
            }
            else throw new RuntimeError("Illegal Type");
        }
        catch (RuntimeException e){
            throw new RuntimeError("The index exceed the size of list or string");
        }
    }
}
