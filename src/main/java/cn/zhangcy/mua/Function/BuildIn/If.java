package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MBoolean;
import cn.zhangcy.mua.Value.MList;
import cn.zhangcy.mua.Value.MNull;
import cn.zhangcy.mua.Value.MValue;

/**
 * Created by zcy on 27/11/2017.
 */
public class If  implements BuildInFunction {

    private static Class[] argTypes = {MBoolean.class, MList.class, MList.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        if(((MBoolean)args[0]).getValue()){
            return ctx.run(((MList)args[1]).getValue(), 1);
        }
        else{
            return ctx.run(((MList)args[2]).getValue(), 1);
        }
    }
}
