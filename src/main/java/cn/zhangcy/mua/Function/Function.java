package cn.zhangcy.mua.Function;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MValue;

/**
 * Created by zcy on 27/09/2017.
 */
public interface Function {

    Class[] getArgTypes();
    MValue run(Runtime ctx, MValue[] args) throws Error;
    String getName();
}
