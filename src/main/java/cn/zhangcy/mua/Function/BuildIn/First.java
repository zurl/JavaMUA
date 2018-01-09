package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.*;

/**
 * Created by zcy on 27/09/2017.
 */
public class First extends BuildInFunction {

    private static Class[] argTypes = {MValue.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        try {
            if (args[0] instanceof MLiteral) return new MLiteral(((MLiteral) args[0]).getValue().substring(0, 1));
            else if (args[0] instanceof MList)return ((MList) args[0]).getValue().get(0);
            else throw new RuntimeError("The type of arguments of function `first` is incorrect.");
        }
        catch (IndexOutOfBoundsException e){
            throw new RuntimeError("Command `first` cannot be applied for NULL list.");
        }
    }
}
