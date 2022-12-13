import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdvancedCalculator extends BasicCalculator {
    private String expr;
    private double result;

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    AdvancedCalculator(double op, String opr){
        super(op, opr);
    }

    AdvancedCalculator(double op1, double op2, String opr){
        super(op1,op2, opr);
    }

    AdvancedCalculator(){
        super();
        this.expr = "";
    }
    
    public String toSimpleSpr(String expr){
        String rs = null;
        String rsfunc = null;
        String func = null;

        Pattern p = Pattern.compile(Utils.getPercPattern());
        Matcher m = p.matcher(expr);

        while(m.find()){
            func = m.group();
            rsfunc = this.resultPerc(func);
            rs =  expr.replace(func, rsfunc);

            m = p.matcher(rs);
        }


        if(rs!=null){
            p = Pattern.compile(Utils.getFuncPattern());
            m = p.matcher(rs);
        }else{
            p = Pattern.compile(Utils.getFuncPattern());
            m = p.matcher(expr);
        }

        while(m.find()){
            func = m.group();
            rsfunc = this.resultFunc(func);
            if(rs!=null){
                rs =  rs.replace(func, rsfunc);
            }else{
                rs =  expr.replace(func, rsfunc);
            }
            m = p.matcher(rs);
        }

        if(rs!=null){
            p = Pattern.compile(Utils.getPowerPattern());
            m = p.matcher(rs);
        }else{
            p = Pattern.compile(Utils.getPowerPattern());
            m = p.matcher(expr);
        }


        while(m.find()){
            func = m.group();
            rsfunc = this.resultPowerOpr(func);
            if(rs!=null){
                rs =  rs.replace(func, rsfunc);
            }else{
                rs =  expr.replace(func, rsfunc);
            }

            m = p.matcher(rs);
        }

        if (rs == null) {
            rs = expr;
        }
        return rs;
    }

    public String resultFunc(String func){
        double ret=0.0;
        String[] exp = func.split("[()]");
        if(exp[0].equals("raiz")){
            if (!Utils.isNum(exp[1]))
                ret = Math.sqrt(super.getResultOpr(exp[1]));
            else ret = Math.sqrt(Double.parseDouble(exp[1]));
        }

        return Double.toString(ret);
    }

    public String resultPerc(String func){
        char op = func.charAt(0);
        String ret = null;

        switch (op){
            case '+':
            case '-':
                func = func.replace(String.valueOf(op),"");
                func = func.replace("%","");
                ret = "*("+ "1" + op + func + "/100)";
                break;
            case '*':
            case'/':
                func = func.replace(String.valueOf(op),"");
                func = func.replace("%","");
                ret = op+ "("+ func + "/100)";
                return ret;
        }
        return ret;
    }

    public String resultPowerOpr(String expr){
        double result = 0.0;
        String[] num = expr.split("\\^");

        if (Utils.isNum(num[0])){
            result = Math.pow(Double.parseDouble(num[0]), Double.parseDouble(num[1]));
        }
        return Double.toString(result);
    }


}
