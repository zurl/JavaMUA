package cn.zhangcy.mua;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.RuntimeError;
import cn.zhangcy.mua.Exception.SyntaxError;
import cn.zhangcy.mua.Function.BuildIn.*;
import cn.zhangcy.mua.Function.Function;
import cn.zhangcy.mua.Value.*;
import com.sun.org.apache.bcel.internal.generic.FNEG;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zcy on 22/11/2017.
 */
public class Parser {

    boolean isMultiline = true;

    private Interpreter interpreter;
    private SymbolTable symbolTable;


    public Parser(Interpreter interpreter, SymbolTable symbolTable) {
        this.interpreter = interpreter;
        this.symbolTable = symbolTable;
        if( interpreter == null) isMultiline = false;
    }

    public static class ASTNode {
        Function function;
        ASTNode[] arguments;

        public ASTNode(Function function, ASTNode[] arguments) {
            this.function = function;
            this.arguments = arguments;
        }

        public ASTNode(MValue value){
            this.function = new MValueFunction(value);
            this.arguments = new ASTNode[]{};
        }
    }


    ArrayList<MValue> program;
    int now = 0;

    private static ASTNode ZERO = new ASTNode(new MInteger(0));

    private void getNext() throws Error{
        now ++;
        if(isComplete()){
            if( isMultiline ) interpreter.requireInput(false);
            else throw new SyntaxError(" Unpredictable Input ");
        }
    }

    private void getThis() throws Error{
        if(isComplete()){
            if( isMultiline ) interpreter.requireInput(false);
            else throw new SyntaxError(" Unpredictable Input ");
        }
    }

    private ASTNode parseValue() throws Error {
        getThis();
        MValue value = program.get(now);
        if( value instanceof MOpe ) throw new Error("Illegal Operation");
        else if( value instanceof MWord ){
            MWord word = (MWord) value;
            MValue item = symbolTable.getSymbol(word.getValue());
            if( item == null ) throw new RuntimeError("`" + word.getValue() + "` is undefined .");
            Function func;
            if( item instanceof MList && ((MList) item).getCustomFunction() != null){
                func = ((MList) item).getCustomFunction();
            }
            else if( item instanceof MFunction){
                func = ((MFunction) item).getFunction();
            }
            else{
                throw new SyntaxError("`" + word.getValue() + "` is not a callable value.");
            }
            Class[] types = func.getArgTypes();
            ASTNode[] args = new ASTNode[types.length];
            if( args.length != 0 ) getNext();
            else now ++;
            for(int i = 0; i < args.length; i++){
                if(types[i] == MLiteral.class)args[i] = parseValue();
                else args[i] = parseSum();
            }
            return new ASTNode(func, args);
        }
        else { // Other Simple
            now++; // maybe end;
            return new ASTNode(value);
        }
    }

    private ASTNode parseItem() throws Error {
        getThis();
        if( program.get(now) instanceof MOpe){
            char op = ((MOpe) program.get(now)).value;
            if( op == '-'){
                getNext();
                ASTNode value = parseSum();
                return new ASTNode(
                        new Sub(),
                        new ASTNode[]{ ZERO, value}
                );
            }
            else if (op == '('){
                getNext();
                ASTNode value = parseSum();
                if( isComplete() || !(program.get(now) instanceof MOpe) ||
                        ((MOpe)program.get(now)).value != ')') throw new SyntaxError(" Bracket Not Match ");
                now ++; // Maybe at an end;
                return value;
            }
            else return parseValue();
        }
        else return parseValue();
    }

    private ASTNode parseProduct() throws Error{
        getThis();
        ASTNode node = parseItem();
        while(!isComplete()){
            if( program.get(now) instanceof MOpe){
                char op = ((MOpe) program.get(now)).value;
                if( op == '*' || op == '%' || op == '/'){
                    getNext();
                    ASTNode right = parseItem();
                    Function function;
                    if (op == '*' ) function = new Mul();
                    else if(op == '/' ) function = new Div();
                    else function = new Mod();
                    node = new ASTNode(
                            function,
                            new ASTNode[]{ node, right }
                    );
                }
                else break;
            }
            else break;
        }
        return node;
    }

    private ASTNode parseSum() throws Error{
        getThis();
        ASTNode node = parseProduct();
        while(!isComplete()){
            if( program.get(now) instanceof MOpe){
                char op = ((MOpe) program.get(now)).value;
                if( op == '+' || op == '-'){
                    getNext();
                    ASTNode right = parseProduct();
                    Function function;
                    if (op == '+' ) function = new Add();
                    else function = new Sub();
                    node = new ASTNode(
                            function,
                            new ASTNode[]{ node, right }
                    );
                }
                else break;
            }
            else break;
        }
        return node;
    }

    public void cleanUp(){
        now = 0;
    }

    public boolean isComplete(){
        return now >= this.program.size();
    }

    public ASTNode parse(ArrayList<MValue> program) throws Error{
        this.program = program;
        return parseSum();
    }

}
