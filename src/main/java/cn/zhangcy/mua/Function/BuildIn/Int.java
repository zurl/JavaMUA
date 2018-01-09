package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.*;

public class Int extends BuildInFunction {

    private static Class[] argTypes = {MNumber.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        if( args[0] instanceof MInteger) return args[0];
        else{
            MReal real = (MReal) args[0];
            Integer i = (int)real.getValue();
            return new MInteger(i);
        }
    }
}
