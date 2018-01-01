package cn.zhangcy.mua;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Function.BuildInFunctionLoader;
import cn.zhangcy.mua.Value.MValue;
import cn.zhangcy.mua.Value.MWord;

import java.util.HashMap;

/**
 * Created by zcy on 27/09/2017.
 */
public class SymbolTable {

    private HashMap<String, MValue> buildInTable = new HashMap<String, MValue>();
    private HashMap<String, MValue> globalTable = new HashMap<String, MValue>();
    private HashMap<String, MValue> localTable = new HashMap<String, MValue>();

    public SymbolTable() {
        BuildInFunctionLoader.load(this.buildInTable);
    }

    public HashMap<String, MValue> getGlobalTable() {
        return globalTable;
    }

    public HashMap<String, MValue> getLocalTable() {
        return localTable;
    }

    public MValue getSymbol(String name){
        MValue var = localTable.get(name);
        if(var == null) var = globalTable.get(name);
        if(var == null) var = buildInTable.get(name);
        return var;
    }

    public void setSymbol(String name, MValue value) throws Error{
        if(buildInTable.get(name) != null){
            throw new RuntimeError("You cannot re-assign a build-in word");
        }
        MValue var = localTable.get(name);
        if(var == null){
            globalTable.put(name, value);
        }
        else{
            localTable.put(name, value);
        }
    }

    public void removeSymbol(String name) throws Error {
        MValue var = localTable.get(name);
        if(var == null){
            var = globalTable.get(name);
            if( var == null){
                throw new RuntimeError("`" + name + "` is undefined .");
            }
            else{
                globalTable.remove(name);
            }
        }
        else{
            localTable.remove(name);
        }
    }

    public void setLocalTable(HashMap<String, MValue> localTable) {
        this.localTable = localTable;
    }

    public void displayAll(){
        for(String key : globalTable.keySet()){
            System.out.println(key + " : " + globalTable.get(key).toString());
        }
        for(String key : localTable.keySet()){
            System.out.println(key + " : " + localTable.get(key).toString());
        }
    }

    public void cleanAll(){
        globalTable = new HashMap<String, MValue>();
        localTable = new HashMap<String, MValue>();
    }

}
