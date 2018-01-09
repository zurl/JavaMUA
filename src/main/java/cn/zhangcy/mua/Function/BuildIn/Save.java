package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MLiteral;
import cn.zhangcy.mua.Value.MNull;
import cn.zhangcy.mua.Value.MValue;

import java.io.FileWriter;

public class Save extends BuildInFunction{

    private static Class[] argTypes = {MLiteral.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        String name = ((MLiteral) args[0]).getValue();
        try {
            FileWriter fileWriter = new FileWriter(name);
            fileWriter.write(ctx.getSymbolTable().toString());
            fileWriter.close();
        }catch (Exception e){
            throw new RuntimeError(e.getMessage());
        }
        return new MNull();
    }
}
