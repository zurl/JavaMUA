package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MInteger;
import cn.zhangcy.mua.Value.MNumber;
import cn.zhangcy.mua.Value.MReal;
import cn.zhangcy.mua.Value.MValue;

/**
 * Created by zcy on 27/09/2017.
 */
public class Sqrt implements BuildInFunction {

    private static Class[] argTypes = {MNumber.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        if( args[0] instanceof MInteger){
            return new MReal(Math.sqrt(((MInteger)args[0]).getValue()));
        }
        else{
            return new MReal(Math.sqrt(((MReal)args[0]).getValue()));
        }
    }
}


