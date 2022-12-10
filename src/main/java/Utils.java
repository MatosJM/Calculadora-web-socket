import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Utils {
    public static String getFuncPattern(){
        String num = "[+-]?[0-9]+([.][0-9]+)?(E[+-]?[0-9]+)?";
        String func = "(raiz)" + "\\(" + num + "\\)"; //raiz(num)

        return func;
    }

    public static String getPercPattern(){
        String num = "[+-]?[0-9]+([.][0-9]+)?(E[+-]?[0-9]+)?";
        String op = "[+\\-*/]";
        return num + op + num + "%";
    }
    public static boolean isOperator(String opr){
        String op = "[+\\-*/]";

        return opr.matches(op);
    }
    public static boolean isAdvOperator(String opr){
        String op = "\\^";

        return opr.matches(op);
    }
    public static boolean isNum(String num){
        String numRegex = "[+-]?[0-9]+([.][0-9]+)?(E[+-]?[0-9]+)?";

        return num.matches(numRegex);
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
