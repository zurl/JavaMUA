package org.zhangcy.mua;

import cn.zhangcy.mua.Exception.Error;
import cn.zhangcy.mua.Runtime;
import cn.zhangcy.mua.SymbolTable;
import cn.zhangcy.mua.Tokenizer;
import cn.zhangcy.mua.Value.MReal;
import cn.zhangcy.mua.Value.MValue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;

public class TestWK {

    private SymbolTable symbolTable = new SymbolTable();
    private Tokenizer tokenizer = new Tokenizer();
    private Runtime runtime = new Runtime(tokenizer, symbolTable);



    private MValue testRaw(String source) throws Error {
        tokenizer.cleanUp();
        try {
            return runtime.run(tokenizer.tokenize(source, false), 1);
        }catch (Error e){
            System.out.println("Runtime Stack: ");
            Stack<ArrayList<String>> errorStack = runtime.getErrorStack();
            while(!errorStack.empty()){
                ArrayList<String> pop = errorStack.pop();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("      AT: ");
                for(String x: pop){
                    stringBuilder.append(x);
                    stringBuilder.append(",");
                }
                System.out.println(stringBuilder.toString())    ;
            }
            throw e;
        }
    }

    private String test(String source) throws Error{
        return testRaw(source).toString();
    }

    @Before
    public void loadPreset() throws Error{
        runtime.enableDebug(true);
        test("make \"abs [[x][\n" +
                "    if :x < 0\n" +
                "    [ output -:x ]\n" +
                "    [ output :x]\n" +
                "]]\n");
    }


    @Test
    public void testSqrt() throws Error{
        test("make \"sqrt_iter [[guess x] [\n" +
                "if abs (:guess * :guess - :x) < 0.00001\n" +
                "[output :guess]\n" +
                " [output sqrt_iter ((:guess+:x/:guess)/2) :x]\n" +
                "]]");
        Assert.assertEquals(
                Math.abs(((MReal)testRaw("sqrt_iter 1.0 10")).getValue() -
                        Math.sqrt(10))
                < 1e-2,
                true
        );

    }

    @Test
    public void testSqrt2() throws Error{
        test("make \"average [[a b] [\n" +
                "    output ((:a+:b)/2)\n" +
                "]]\n" +
                "make \"mysqrt [[x] [\n" +
                "    make \"good_enough [[guess x] [\n" +
                "            output (abs (:guess * :guess - :x) < 0.001)\n" +
                "    ]]\n" +
                "    make \"improve [[guess x] [\n" +
                "            output average :guess :x/:guess\n" +
                "    ]]\n" +
                "    make \"sqrt_iter [[guess x] [\n" +
                "            if good_enough :guess :x\n" +
                "                [output :guess]\n" +
                "                [output sqrt_iter improve :guess :x :x]\n" +
                "    ]]\n" +
                "    output sqrt_iter 1.0 :x\n" +
                "]]");
        Assert.assertEquals(
                Math.abs(((MReal)testRaw("mysqrt 10")).getValue() -
                        Math.sqrt(10))
                        < 1e-2,
                true
        );
    }

    @Test
    public void testFactorial() throws Error{
        test("make \"factorial [[n][\n" +
                "    if :n < 2\n" +
                "\t \t [output 1]\n" +
                "\t \t [output (:n * factorial (:n - 1))]\n" +
                "]]");
        Assert.assertEquals(
                test("factorial 5"),
                 "120"
        );
    }


    @Test
    public void testFastExp() throws Error{
        test("make \"even? [[x] [\n" +
                "    if (:x % 2) = 0\n" +
                "        [output true ]\n" +
                "        [output false ]\n" +
                "]]\n" +
                "make \"square [[x] [\n" +
                "    output (:x * :x)\n" +
                "]]\n" +
                "make \"fast_exp [[b n] [\n" +
                "    if :n = 0\n" +
                "\t \t [output 1]\n" +
                "\t \t [if even? :n\n" +
                "\t \t \t [output square fast_exp :b (:n / 2)]\n" +
                "\t \t \t [output (:b * fast_exp :b (:n - 1))]\n" +
                "\t \t ]\n" +
                "]]");
        Assert.assertEquals(
                test("fast_exp 2 3"),
                "8"
        );
    }

    @Test
    public void testPI() throws Error{
        test("make \"sum [[term a next b] [\n" +
                "if :a > :b\n" +
                "\t \t [output 0]\n" +
                "\t \t [output ((term :a) + sum :term next :a :next :b)]\n" +
                "]]\n" +
                "make \"pi_sum_term [[a] [\n" +
                "output (1.0 / (:a * (:a + 2)))\n" +
                "]]\n" +
                "make \"pi_sum_next [[a] [\n" +
                "output (:a + 4)\n" +
                "]]\n");
        Assert.assertEquals(
                Math.abs(((MReal) testRaw("8 * sum :pi_sum_term 1.0 :pi_sum_next 1000 ")).getValue() -
                        3.1415)
                        < 1e-2,
                true
        );
    }

}
