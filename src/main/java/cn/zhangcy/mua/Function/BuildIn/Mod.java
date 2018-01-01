package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MInteger;
import cn.zhangcy.mua.Value.MNumber;
import cn.zhangcy.mua.Value.MValue;

/**
 * Created by zcy on 27/09/2017.
 */
public class Mod implements BuildInFunction {

    private static Class[] argTypes = {MNumber.class, MNumber.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        if( args[0] instanceof MInteger && args[1] instanceof MInteger){
            return new MInteger(((MInteger)args[0]).getValue() % ((MInteger)args[1]).getValue());
        }
        throw new RuntimeError("mod only support integers");
    }
}
