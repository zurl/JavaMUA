package cn.zhangcy.mua.Function;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.Value.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zcy on 27/09/2017.
 */
public class CustomFunction implements Function {

    private ArrayList<MValue> program;
    private ArrayList<String> argList;
    private Class[] argTypes;
    private MList mList;

    public CustomFunction(ArrayList<MValue> argList, ArrayList<MValue> program, MList mList) {
        this.mList = mList;
        this.program = program;
        this.argList = new ArrayList<String>();
        argTypes = new Class[argList.size()];
        for(int i = 0; i < argList.size(); i++){
            if( argList.get(i) instanceof MLiteral ){
                this.argList.add(((MLiteral)argList.get(i)).getValue());
            }
            else{
                this.argList.add(((MWord)argList.get(i)).getValue());
            }
            argTypes[i] = MValue.class;
        }
    }

    public MValue run(Runtime ctx, MValue[] args) throws Error {
        ctx.getSymbolTable().createSubSymbolTable();
        for(int i = 0 ; i < argList.size(); i++){
            ctx.getSymbolTable().setSymbol(argList.get(i), args[i]);
        }
        MValue savedOutputValue = ctx.getOutputValue();
        ctx.setOutputValue(new MNull());
        ctx.run(program, 1);
        MValue returnValue = ctx.getOutputValue();
        ctx.setOutputValue(savedOutputValue);
        ctx.getSymbolTable().removeSubSymbolTable();
        return returnValue;
    }

    public Class[] getArgTypes() {
        return argTypes;
    }

    public String getName() {
        return "Lambda: " + mList.toString();
    }

}
