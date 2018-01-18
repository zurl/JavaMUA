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
public class Random extends BuildInFunction {

    private static Class[] argTypes = {MNumber.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        java.util.Random random = new java.util.Random();
        if( args[0] instanceof MInteger){
            int value = ((MInteger)args[0]).getValue();
            return new MInteger(random.nextInt(value));
        }
        else{
            double value = ((MReal)args[0]).getValue();
            return new MReal(random.nextDouble() * value);
        }
    }
}

