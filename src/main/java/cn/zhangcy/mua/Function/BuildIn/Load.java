package cn.zhangcy.mua.Function.BuildIn;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Function.BuildInFunction;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.MList;
import cn.zhangcy.mua.Value.MLiteral;
import cn.zhangcy.mua.Value.MNull;
import cn.zhangcy.mua.Value.MValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Load extends BuildInFunction {

    private static Class[] argTypes = {MLiteral.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        String name = ((MLiteral) args[0]).getValue();
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(name));
            String line = fileReader.readLine();
            while(line != null){
                sb.append(line);
                sb.append("\n");
                line = fileReader.readLine();
            }
            ctx.run(sb.toString());
        }catch (Exception e){
            throw new RuntimeError(e.getMessage());
        }
        return new MNull();

    }
}

