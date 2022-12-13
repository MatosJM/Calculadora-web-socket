import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

public class Utils {
    public static String getFuncPattern(){
        String num = "[+-]?[0-9]+([.][0-9]+)?(E[+-]?[0-9]+)?";
        String func = "(raiz)" + "\\(" + num + "\\)"; //raiz(num)

        return func;
    }

    public static String getPercPattern(){
        String num = "[+-]?[0-9]+([.][0-9]+)?(E[+-]?[0-9]+)?";
        String op = "[+\\-*/]";
        return op + num + "%";
    }

    public static String getPowerPattern(){
        String num = "[+-]?[0-9]+([.][0-9]+)?(E[+-]?[0-9]+)?";
        String op = "\\^";
        return num + op + num ;
    }
    public static boolean isOperator(String opr){
        String op = "[+\\-*/]";

        return opr.matches(op);
    }
    public static boolean isPowerOperator(String opr){
        String op = "\\^";

        return opr.matches(op);
    }
    public static boolean isNum(String num){
        String numRegex = "[+-]?[0-9]+([.][0-9]+)?(E[+-]?[0-9]+)?";

        return num.matches(numRegex);
    }
    public static boolean isSignedNum(String num){
        String numRegex = "[+-]?[0-9]+([.][0-9]+)?(E[+-]?[0-9]+)?";
        String op = "[+\\-*/]";
        String sNumRegex = "\\(" + numRegex + "\\)" + "|" + "(" + op + numRegex + ")"; //(num) | op num
        return num.matches(sNumRegex);
    }
    public static boolean isSimpleExpr(String expr){
        boolean ret;
        String num = "[+-]?[0-9]+([.][0-9]+)?(E[+-]?[0-9]+)?";
        String op = "[+\\-*/]";
        String exprS = "\\(" + num + "("+ op + num + ")+" + "\\)";
        try {
            ret = expr.matches(exprS);
        }catch (Exception e){
            ret = false;
        }
        return ret;

    }

    public static boolean isPunct(char c){
        boolean b = c == '.' || c == ',';
        return b;
    }

    public static boolean isValidExpr(String expr){
        boolean ret;
        String num = "[+-]?[0-9]+([.][0-9]+)?(E[+-]?[0-9]+)?";
        String op = "[+\\-*/]";
        /*String expr = "\\(" + num + "+" + "" + "\\)";
        String pattern = num + "(" + opr + num + ")*" + opr + num;*/

        String exprS = "\\(" + num + "("+ op + num + ")*" + "\\)"; // \(num (op num)*\)
        String exprC = "\\(" + "(" + num + "|" + exprS + ")"+ "(" +op + "("+ num +"|"+ exprS + ")" + ")+\\)"; // \([num exprS] (op [num exprS])+\)
        String pattern = "("+num+ "|" + exprS + "|" + exprC + ")" + "("+ op+"("+ num + "|" + exprS + "|" + exprC+ "))+"; // \([num exprS exprC] (op [num exprS exprC])+\)
        //String pattern = exprC;
        try {
            ret = expr.matches(pattern);
        }catch (Exception e){
            ret = false;
        }
        return ret;
    }

    public static boolean isValidExprAdvanced(String expr){
        boolean ret;
        String num = "[+-]?[0-9]+([.][0-9]+)?(E[+-]?[0-9]+)?";
        String op = "[+\\-*/^]";
        String func = "(raiz)" + "\\(" + num + "\\)"; //raiz(num)
        String exprS = "\\(" + "(" + num + "|" + func + ")" + "("+ op + num + "%?"+ "|" + func + ")*" + "\\)"; // ((num|func) (op num%? | func)*)
        String exprC = "\\(" + "(" + num + "|" + exprS + ")"+ "(" +op + "("+ num +"|"+ exprS + ")" + ")+\\)"; // \([num exprS] (op [num exprS])+\)
        String pattern = func + "|" + "("+num+ "|" + exprS + "|" + exprC + "|" + func + ")" + "("+ op+"("+ num +"%?" + "|" + exprS + "|" + exprC + "|" + func +"))+"; // func | [num exprS exprC func] (op [num exprS exprC func])+
        //String pattern = exprC;
        try {
            ret = expr.matches(pattern);
        }catch (Exception e){
            ret = false;
        }

        return ret;
    }

    public static int precedence(char ch)
    {
        switch (ch) {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;

            case '%':
                return 4;
        }
        return -1;
    }

    public static ArrayList<String> toArrayList(String expr)
    {
        ArrayList<String> exprPosF = new ArrayList<>();
        Deque<Character> stackAux= new ArrayDeque<>();
        StringBuilder sbStr = new StringBuilder();
        char cAux;

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            //sbStr.append(c);
            stackAux.add(c);
            sbStr.append(c);
            //a pilha auxiliar possui um operador no topo
            //if(isOperator(stackAux.peekLast().toString()) || stackAux.peekLast()==')'){
            if(isOperator(String.valueOf(c)) || c==')'){
                //opererador retirado do topo da pilha
                //cAux = stackAux.removeLast();
                if(c!=')')
                    sbStr.deleteCharAt(sbStr.length()-1);

//#################################################################################
                if(isSignedNum(sbStr.toString())){
                    exprPosF.add( sbStr.toString().replaceAll("[()]",""));
                    sbStr = new StringBuilder();

                }else if(isSimpleExpr(sbStr.toString())){
                    exprPosF.add(sbStr.toString());
                    sbStr = new StringBuilder();
                }else if(isNum(sbStr.toString())){
                    exprPosF.add(sbStr.toString());
                    sbStr = new StringBuilder();
                }else if(isOperator(sbStr.toString())){
                    exprPosF.add(sbStr.toString());
                    sbStr = new StringBuilder();
                }
                //adiciona à expressão pósfixa quando for um operador
                if(sbStr.toString().isEmpty() && isOperator(String.valueOf(c))) exprPosF.add(String.valueOf(c));
                else if(!sbStr.toString().isEmpty() && sbStr.length()>1) {
                    if(sbStr.charAt(0)=='('){
                    //adiciona '(' a expressão pósfixa
                    exprPosF.add(String.valueOf(sbStr.charAt(0)));
                    sbStr.deleteCharAt(0);
                    //adiciona o número a expressão pósfixa
                    exprPosF.add(sbStr.toString());
                    exprPosF.add(String.valueOf(c)); //aqui pode dar erro
                        sbStr = new StringBuilder();
                    }else if(sbStr.charAt(sbStr.length()-1)==')'){
                        //adiciona o número a expressão pósfixa
                        sbStr.deleteCharAt(sbStr.length()-1);
                        exprPosF.add(sbStr.toString());

                        //adiciona ')' a expressão pósfixa
                        exprPosF.add(String.valueOf(c));
                        sbStr = new StringBuilder();

                    }
                }else if(!sbStr.toString().isEmpty()) sbStr.append(c);
            }else if(sbStr.toString().equals("((")){
                //adiciona '(' a expressão pósfixa
                exprPosF.add(String.valueOf(sbStr.charAt(0)));
                sbStr.deleteCharAt(0);
            }

        }

        //#################################################################################
        if(!sbStr.toString().isEmpty()){
            exprPosF.add(sbStr.toString());
        }
        //#################################################################################


        return exprPosF;
    }

    public static ArrayList<String> infixToPostfix(ArrayList<String> expr){
        ArrayList<String> exprPosF = new ArrayList<>();
        Deque<String> stackAux= new ArrayDeque<>();

        for(String op : expr){
  //          System.out.println(op);

            if (isNum(op))
                exprPosF.add(op);
//            else if(isNum(op))
//                stackAux.push(op);
            else if(op.equals("("))
                stackAux.push(op);
            else if(op.equals(")")){
                while (!stackAux.isEmpty() && !stackAux.peek().equals("(")) {
//                    result += stackAux.peek();
                    exprPosF.add(stackAux.peek());
                    stackAux.pop();
                }
                //remove '(' da pilha
                if(!stackAux.isEmpty())stackAux.pop();
            }else{// an operator is searched
                while (!stackAux.isEmpty()
                        && precedence(op.charAt(0)) <= precedence(stackAux.peek().charAt(0))) {
                    // result += stackAux.peek();
                    exprPosF.add(stackAux.peek());
                    stackAux.pop();
                }
                stackAux.push(op);
            }

        }
        // pop all the operators from the stack
        while (!stackAux.isEmpty()) {

            if (stackAux.peek().equals("(")){
                String str = "Invalid Expression";
                ArrayList <String> ret = new ArrayList<>();
                ret.add(str);
                exprPosF = ret;
                return exprPosF;
            }

            exprPosF.add(stackAux.peek());
            stackAux.pop();
        }

        return exprPosF;
    }

    public static ArrayList<String> infixToPostfix(String exp)
    {
        ArrayList<String> exprPosF = new ArrayList<>();
        Deque<Character> stackAux= new ArrayDeque<>();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (Character.isDigit(c) || isPunct(c))
                result.append(c);

            else if (c == '(')
                stackAux.push(c);

            else if (c == ')') {
                if(!result.toString().equals("")){
                    exprPosF.add(result.toString());
                    result = new StringBuilder();
                }
                while (!stackAux.isEmpty() && stackAux.peek() != '(') {
//                    result += stackAux.peek();
                    exprPosF.add(stackAux.peek().toString());
                    stackAux.pop();
                }

                stackAux.pop();
            }
            else // an operator is encountered
            {
                if(!result.toString().equals("")){
                    exprPosF.add(result.toString());
                    result = new StringBuilder();
                }
                //so desempilho se a precendencia for menor ou igual
                while (!stackAux.isEmpty()
                        && precedence(c) <= precedence(stackAux.peek())) {
                   // result += stackAux.peek();
                    exprPosF.add(stackAux.peek().toString());
                    stackAux.pop();
                }
                stackAux.push(c);
            }
        }

        // pop all the operators from the stack
        while (!stackAux.isEmpty()) {
            if(!result.toString().equals("")){
                exprPosF.add(result.toString());
                result = new StringBuilder();
            }
            if (stackAux.peek() == '('){
                String str = "Invalid Expression";
                ArrayList <String> ret = new ArrayList<>();
                ret.add(str);
                exprPosF = ret;
                return exprPosF;
            }

            exprPosF.add(stackAux.peek().toString());
            stackAux.pop();
        }

        if(result.toString().equals(exp)){
            exprPosF.add(result.toString());
        }

        return exprPosF;
    }

}
