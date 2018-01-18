package cn.zhangcy.mua;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Exception.SyntaxError;
import cn.zhangcy.mua.Value.MList;
import cn.zhangcy.mua.Value.MLiteral;
import cn.zhangcy.mua.Value.MValue;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by zcy on 07/10/2017.
 */
public class Interpreter {


    private SymbolTable symbolTable = new SymbolTable();
    private Tokenizer tokenizer = new Tokenizer();
    private Parser parser = new Parser(this, symbolTable);
    private Runtime runtime = new Runtime(tokenizer, symbolTable);

    public Interpreter() {

    }

    ArrayList<MValue> tokens = null;
    Scanner scanner = new Scanner(System.in);

    public void requireInput(boolean isNew) throws Error{
        tokens = null;
        while(tokens == null){
            if( isNew ) System.out.print("MUA>");
            String code = scanner.nextLine();
            if( code.equals("exit") || code.equals("quit") ) return;
            if( code.equals("")) break;
            tokens = tokenizer.tokenize(code, true);
            isNew = false;
        }
        if( tokens == null) throw new SyntaxError(" Break Input ");
    }

    public void run(){
        runtime.loadPresetCode();
        while(true){
            try {
                requireInput(true);
                MValue ret;
                do {
                    Parser.ASTNode astNodes = parser.parse(tokens);
                    ret = runtime.run(astNodes);
                }while(!parser.isComplete());
                while( ret instanceof MList){
                    ret = runtime.run(((MList)ret).getValue(), 1);
                }
                System.out.println(">>>" + ret.toString());
                tokenizer.cleanUp();
                parser.cleanUp();
            }catch (Error e){
                System.out.println(e.getMessage());
                tokenizer.cleanUp();
                parser.cleanUp();
            }
        }
    }

}
