package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MInteger;
import cn.zhangcy.mua.Value.MNull;
import cn.zhangcy.mua.Value.MValue;

/**
 * Created by zcy on 27/09/2017.
 */
public class Wait implements BuildInFunction {

    private static Class[] argTypes = {MInteger.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        try {
            Thread.sleep(((MInteger) args[0]).getValue() * 1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new MNull();
    }
}

