package cn.zhangcy.mua;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Function.BuildInFunctionLoader;
import cn.zhangcy.mua.Value.MList;
import cn.zhangcy.mua.Value.MValue;
import cn.zhangcy.mua.Value.MWord;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zcy on 27/09/2017.
 */
public class SymbolTable {

    static class SubSymbolTable{
        HashMap<String, MValue> data;
        SubSymbolTable prev;

        SubSymbolTable(SubSymbolTable prev) {
            this.data = new HashMap<String, MValue>();
            this.prev = prev;
        }
    }

    private SubSymbolTable root, globalTable;

    public SymbolTable() {
        root = new SubSymbolTable(null);
        BuildInFunctionLoader.load(root.data);
        root = new SubSymbolTable(root);
        globalTable = root;
    }

    public MValue getSymbol(String name) throws Error{
        SubSymbolTable now = root;
        while ( now != null){
            MValue mValue = now.data.get(name);
            if(mValue != null) return mValue;
            now = now.prev;
        }
        return null;
    }

    public void setSymbol(String name, MValue value) throws Error{
        if( globalTable.prev.data.get(name) != null)
            throw new RuntimeError("You cannot re-assign a build-in word");
        root.data.put(name, value);
    }

    public void setGlobalSymbol(String name, MValue value) throws Error{
        if( globalTable.prev.data.get(name) != null)
            throw new RuntimeError("You cannot re-assign a build-in word");
        globalTable.data.put(name, value);
    }
    public void removeSymbol(String name) throws Error {
        SubSymbolTable now = root;
        while ( now != null){
            MValue mValue = now.data.get(name);
            if(mValue != null){
                if( now.prev == null){
                    throw new RuntimeError("`" + name + "` cannot be removed ."); }
                else{
                    now.data.remove(name);
                }
            }
            now = now.prev;
        }
    }

    public void createSubSymbolTable(){
        root = new SubSymbolTable(root);
    }

    public void removeSubSymbolTable(){
        root = root.prev;
    }

    public void displayAll(){
        SubSymbolTable now = root;
        while ( now != null){
            for(String key : now.data.keySet()){
                System.out.println(key + " : " + now.data.get(key).toString());
            }
            now = now.prev;
        }
    }

    public void cleanAll(){
        root.data = new HashMap<String, MValue>();
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        SubSymbolTable now = root;
        while ( now.prev != null){
            for(String key : now.data.keySet()){
                stringBuilder.append("make \"");
                stringBuilder.append(key);
                stringBuilder.append(" ");
                stringBuilder.append(now.data.get(key).toRawString());
                stringBuilder.append("\n");
            }
            now = now.prev;
        }
        return stringBuilder.toString();
    }

    public SubSymbolTable getGlobalTable() {
        return globalTable;
    }
}
