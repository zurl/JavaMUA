package org.zhangcy.mua;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Parser;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.SymbolTable;
import cn.zhangcy.mua.Tokenizer;
import cn.zhangcy.mua.Value.MReal;
import cn.zhangcy.mua.Value.MValue;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zcy on 27/09/2017.
 */
public class TestMain {

    private SymbolTable symbolTable = new SymbolTable();
    private Tokenizer tokenizer = new Tokenizer();
    private Runtime runtime = new Runtime(tokenizer, symbolTable);

    private String test(String source) throws Error{
        tokenizer.cleanUp();
        return runtime.run(tokenizer.tokenize(source, false), 1).toString();
    }
    private MValue testRaw(String source) throws Error{
        tokenizer.cleanUp();
        return runtime.run(tokenizer.tokenize(source, false), 1);
    }
    
    private void testEqual(String source, String expected) throws Error{
        System.out.println("TEST: " + source);
        String acutal = test(source);
        System.out.println("Expect: " + expected + ", Actual: " + acutal);
        Assert.assertEquals(expected, acutal);
    }

    @Test
    public void testValue() throws Error{
        testEqual("1", "1");
        testEqual("\"hello", "hello");
        testEqual("true", "true");
        testEqual("[ 1 2 3 ]", "[ 1 2 3 ]");
        testEqual("[ [ 1 2 ] [ 3 4 ] ]", "[ [ 1 2 ] [ 3 4 ] ]");
    }

    @Test
    public void testCalculation() throws Error{
        testEqual("add 1 2", "3");
        testEqual("sub 1 2", "-1");
        testEqual("mul 1 2", "2");
        testEqual("div 4 2", "2");
        testEqual("mod 4 2", "0");
        Assert.assertEquals(((MReal)testRaw("sqrt 4")).getValue(), 2, 1e-6);
        Assert.assertNotEquals(test("random 1024"), "1025");
    }

    @Test
    public void testCompare() throws Error{
        testEqual("gt 1 2", "false");
        testEqual("eq \"abc \"abc", "true");
        testEqual("eq 1.1 1.1", "true");
        testEqual("lt \"a \"b", "true");
    }

    @Test
    public void testLogic() throws Error{
        testEqual("and true false", "false");
        testEqual("or true true", "true");
        testEqual("not true", "false");
    }

    @Test
    public void testIs() throws Error{
        testEqual("isempty [ ]", "true");
        testEqual("isbool 1", "false");
        testEqual("isnumber 1.1", "true");
        testEqual("islist [ [ ] ]", "true");
        testEqual("isword \"a", "true");
        testEqual("isempty [ 1 ]", "false");
        testEqual("isbool true", "true");
        testEqual("isnumber \"aaa", "false");
        testEqual("islist \"a[a", "false");
        testEqual("isword 1", "false");
    }

    @Test
    public void testList() throws Error{
        testEqual("first [ 1 2 3 ]", "1");
        testEqual("last [ 1 2 3 ]", "3");
        testEqual("butfirst [ 1 2 3 ]", "[ 2 3 ]");
        testEqual("butlast [ 1 2 3 ]", "[ 1 2 ]");
        testEqual("list [ 1 ] [ 2 ]", "[ 1 2 ]");
        testEqual("join [ 1 ] [ 2 ]", "[ 1 [ 2 ] ]");
        testEqual("item 2 [ 2 4 6 8 ]", "6");
    }

    @Test
    public void testWord() throws Error{
        testEqual("first \"abc", "a");
        testEqual("last \"abc", "c");
        testEqual("butfirst \"abc", "bc");
        testEqual("butlast \"abc", "ab");
        testEqual("word \"abc \"abc", "abcabc");
        testEqual("item 2 \"abc", "c");
    }

    @Test
    public void testVar() throws Error{
        testEqual("make \"a 1 thing \"a", "1");
        testEqual("make \"a [ 1 ] thing \"a", "[ 1 ]");
        testEqual("make \"a 1 erase \"a isname \"a", "false");
    }

    @Test
    public void testControl() throws Error{
        runtime.loadPresetCode();
        test("test true iftrue [ make \"b 1 ]");
        testEqual("thing \"b", "1");
        test("test false iftrue [ make \"b 2 ]");
        testEqual("thing \"b", "1");
        test("test true iffalse [ make \"b 3 ]");
        testEqual("thing \"b", "1");
        test("test false iffalse [ make \"b 4 ]");
        testEqual("thing \"b", "4");
        test("make \"a 1 run [ make \"a add thing \"a 1 ]");
        testEqual("thing \"a", "2");
        test("make \"a 1 repeat 4 [ make \"a add thing \"a 1 ]");
        testEqual("thing \"a", "5");
   }

    @Test
    public void testExpr() throws Error{
        testEqual("1 + 1", "2");
        testEqual("2 * (2+2*3)", "16");
        testEqual("1+6/3%2+1", "2");
        test("make \"a 4 ");
        test("make \"b 5 ");
        testEqual(":a+:b", "9");
        test("make \"double [ [ \"x ] [ output 2 * :x ] ]");
        testEqual("double double 2 * double double 2", "64");
        testEqual("double double 2 + double double 2", "40");
        testEqual(":a+:a+:a+:a", "16");
    }
}
