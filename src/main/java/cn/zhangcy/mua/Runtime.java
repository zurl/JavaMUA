package cn.zhangcy.mua;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Exception.StopInterrupt;
import cn.zhangcy.mua.Function.Function;
import cn.zhangcy.mua.Value.*;

import java.util.ArrayList;

/**
 * Created by zcy on 27/09/2017.
 */
public class Runtime {

    private Tokenizer tokenizer;
    private SymbolTable symbolTable;
    private Parser parser;

    public Runtime(Tokenizer tokenizer, SymbolTable symbolTable) {
        this.tokenizer = tokenizer;
        this.symbolTable = symbolTable;
        this.parser = new Parser(null, symbolTable);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    Boolean testFlag = false;

    public Boolean getTestFlag() {
        return testFlag;
    }

    public void setTestFlag(Boolean testFlag) {
        this.testFlag = testFlag;
    }

    public void loadPresetCode(){
        try{
            run("make \"pi 3.14159");
        }catch (Error e){
            e.printStackTrace();
        }
    }


    public MValue run(String code) throws Error {
        ArrayList<MValue> tokens = tokenizer.tokenize(code, false);
        return run(tokens, 1);
    }

    private MValue outputValue = new MNull();

    public MValue getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(MValue outputValue) {
        this.outputValue = outputValue;
    }

    public MValue run(ArrayList<MValue> tokens, int repeat) throws Error{
        ArrayList<Parser.ASTNode> programs = new ArrayList<Parser.ASTNode>();
        do{
            programs.add(parser.parse(tokens));
        }while(!parser.isComplete());
        parser.cleanUp();
        MValue returnValue = new MNull();
        for(int i = 0 ; i < repeat; i++){
            try {
                for(int j = 0; j < programs.size(); j++){
                    returnValue = run(programs.get(j));
                }
            }catch (StopInterrupt e){
                return new MNull();
            }
        }
        return returnValue;
    }

    public MValue run(Parser.ASTNode program) throws Error{
        Class[] types = program.function.getArgTypes();
        MValue[] arguments = new MValue[program.arguments.length];
        for(int i = 0; i < arguments.length; i++){
            arguments[i] = run(program.arguments[i]);
            if( !types[i].isInstance(arguments[i])){
                throw new RuntimeError("Type of function " + program.function.getClass().getSimpleName().toLowerCase()  + " is incorrect");
            }
        }
        return program.function.run(this, arguments);
    }
}
