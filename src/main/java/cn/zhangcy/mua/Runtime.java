package cn.zhangcy.mua;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Exception.StopInterrupt;
import cn.zhangcy.mua.Function.Function;
import cn.zhangcy.mua.Tools.LRUCache;
import cn.zhangcy.mua.Value.*;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by zcy on 27/09/2017.
 */
public class Runtime {

    private Tokenizer tokenizer;
    private SymbolTable symbolTable;
    private Stack<ArrayList<String>> errorStack;

    private LRUCache<ArrayList<MValue>, ArrayList<Parser.ASTNode>> programCache;
    private boolean enableDebug;

    public Stack<ArrayList<String>> getErrorStack() {
        return errorStack;
    }

    public void enableDebug(boolean enableDebug) {
        this.enableDebug = enableDebug;
    }

    public boolean isEnableDebug() {
        return enableDebug;
    }

    public Runtime(Tokenizer tokenizer, SymbolTable symbolTable) {
        this.tokenizer = tokenizer;
        this.symbolTable = symbolTable;
        this.errorStack = new Stack<ArrayList<String>>();
        this.programCache = new LRUCache<ArrayList<MValue>, ArrayList<Parser.ASTNode>>();
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

    public MValue run(ArrayList<MValue> tokens, int repeat) throws Error {
        ArrayList<Parser.ASTNode> programs = programCache.find(tokens);
        MValue returnValue = new MNull();
        Parser parser = new Parser(null, symbolTable);
        if (programs == null) {
            programs = new ArrayList<Parser.ASTNode>();
            do {
                programs.add(parser.parse(tokens));
                try {
                    returnValue = run(programs.get(programs.size() - 1));
                } catch (StopInterrupt e) {
                    return new MNull();
                }
            } while (!parser.isComplete());
            parser.cleanUp();
            programCache.hit(tokens, programs);
        } else {
            repeat ++;
        }
        for(int i = 1; i < repeat;i ++){
            try {
                for (int j = 0; j < programs.size(); j++) {
                    returnValue = run(programs.get(j));
                }
            } catch (StopInterrupt e) {
                return new MNull();
            }
        }
        return returnValue;
    }

    public MValue run(Parser.ASTNode program) throws Error{
        Class[] types = program.function.getArgTypes();
        MValue[] arguments = new MValue[program.arguments.length];
        if(enableDebug) {
            errorStack.push( new ArrayList<String>() );
            errorStack.peek().add(program.function.getName());
        }
        for(int i = 0; i < arguments.length; i++){
            arguments[i] = run(program.arguments[i]);
            if(enableDebug){
                errorStack.peek().add(
                        i + "=" +
                        arguments[i].toString());
            }
            if( !types[i].isInstance(arguments[i])){
                throw new RuntimeError("Type of function "
                        + program.function.getClass().getSimpleName().toLowerCase()
                        + " is incorrect, "
                        + " require = " + types[i].getSimpleName()
                        + " fact = " + arguments[i].getClass().getSimpleName()
                        + " value = " + arguments[i].toString()
                );
            }
        }
        MValue result = program.function.run(this, arguments);
        if(enableDebug) errorStack.pop();
        return result;
    }
}
