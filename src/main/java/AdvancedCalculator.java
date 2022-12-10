import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

public class AdvancedCalculator extends CalculatorBasic{
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
    public double getResultAdvOpr(){
        double result = 0.0;
        if (this.getOpr().charAt(0)=='^'){
            result = Math.pow(getOp1(), getOp2());
        }
        return result;
    }

    public double getResultOpr(String expr){
        ArrayList<String> operacao; //armazena a operação posfixada passada pelo usuário
        ArrayList<String> operandos;
        operandos = new ArrayList<>();

        operacao = Utils.infixToPostfix(expr);

        for(String op : operacao){

            if (!operandos.isEmpty()) {
                if (Utils.isOperator(operandos.get(operandos.size() - 1)) || Utils.isAdvOperator(operandos.get(operandos.size() - 1)) ) {//o topo da lista é um operador

                    if (operandos.size() > 3 && !Character.isDigit(op.charAt(0))) { //a lista tiver pelo menos 3 operandos e
                        //o próximo caracter a ser adicionado a lista é um operador
                        recalcListTwoOpr(operandos);

                        System.out.println("isNaD twoOpr: " + operandos);
                    } else if (!Character.isDigit(op.charAt(0))) {//a lista tem até 2 operandos
                        recalcListOneOpr(operandos);
                        System.out.println("isNaD oneOpr: " + operandos);

                    } else if (operandos.size() >= 3 && Character.isDigit(op.charAt(0))) {//o próximo caracter a ser adicionado a lista é um operando
                        //a lista tem pelo menos 02
                        recalcListTwoOpr(operandos);

                    } else if (Character.isDigit(op.charAt(0))) {//a lista só tem um operando
                        recalcListOneOpr(operandos);

                    }

                } else {//o topo da lista é um operando
                }
            } else {//o topo da lista é um operando
            }
            operandos.add(op);


        }
        if(operandos.size()==3){
            recalcListTwoOpr(operandos);
        }

        return Double.parseDouble(operandos.get(operandos.size()-1));
    }

    private void recalcListOneOpr(ArrayList<String> operandos) {
        String opr;
        //double op2;
        double result;
        // opr = operandos.get(operandos.size() - 1);
        this.setOpr(operandos.get(operandos.size() - 1));
        operandos.remove(operandos.size()-1);

        //op2 = Double.parseDouble(operandos.get(operandos.size()-1));
        this.setOp2(operandos.get(operandos.size()-1));
        operandos.remove(operandos.size()-1);
        result =  this.getResultOpr();

        operandos.add(Double.toString(result));
    }

    private void recalcListTwoOpr(ArrayList<String> operandos) {
        double result;

        this.setOpr(operandos.get(operandos.size() - 1));
        operandos.remove(operandos.size()-1);

        this.setOp2(operandos.get(operandos.size()-1));
        operandos.remove(operandos.size()-1);

        this.setOp1(operandos.get(operandos.size()-1));
        operandos.remove(operandos.size()-1);

        if(Utils.isOperator(getOpr())){
            result =  super.getResultOpr();
        }else{
            result = this.getResultAdvOpr();
        }

        operandos.add(Double.toString(result));
    }
    
    public String toSimpleSpr(String expr){
        String rs = null;
        String rsfunc = null;
        String func = null;
        Scanner sc = new Scanner(expr);
        while(sc.hasNext(Utils.getFuncPattern())){
            func = sc.next(Utils.getFuncPattern());
            rsfunc = this.resultFunc(func);
            rs =  expr.replace(func, rsfunc);
            //rs = expr;
            sc = new Scanner(rs);
        }
        while(sc.hasNext(Utils.getPercPattern())){
            func = sc.next(Utils.getPercPattern());
            rsfunc = this.resultPerc(func);
            rs =  expr.replace(func, rsfunc);
            sc = new Scanner(rs);
        }
        if (rs == null) {
            rs = expr;
        }
        return rs;
    }

    public String resultFunc(String func){
        Double ret=0.0;
        String[] exp = func.split("[()]");
        if(exp[0].equals("raiz")){
            if (!Utils.isNum(exp[1]))
                ret = Math.sqrt(super.getResultOpr(exp[1]));
            else ret = Math.sqrt(Double.parseDouble(exp[1]));
        }

        return Double.toString(ret);
    }

    public String resultPerc(String func){
        Double ret=0.0;
        ArrayList<String> exp = Utils.infixToPostfix(func);
        ArrayList<String> oprs = new ArrayList<>();
        Deque<Character> stackAux= new ArrayDeque<>();
        
        for(String op : exp){
            if(Utils.isOperator(op)){
                switch (op.charAt(0)){
                    case '+':
                    case '-':
                        if(oprs.size()>1){
                            super.setOp1(oprs.get(oprs.size()-2));
                            super.setOp2(Double.toString(Double.parseDouble(oprs.get(oprs.size()-1))/100*super.getOp1()));
                        }else{
                            super.setOp1(Integer.toString(0));
                            super.setOp2(oprs.get(oprs.size()-1));
                        }
                        super.setOpr(op);
                        ret = super.getResultOpr();
                        break;
                    case '*':
                    case '/':

                        if(oprs.size()>1){
                            super.setOp1(oprs.get(oprs.size()-2));
                            super.setOp2(Double.toString(Double.parseDouble(oprs.get(oprs.size()-1))/100));
                        }else{
                            super.setOp1(Integer.toString(0));
                            super.setOp2(oprs.get(oprs.size()-1));
                        }
                        super.setOpr(op);
                        ret = super.getResultOpr();
                        break;
                }
                if(oprs.size()>1){
                    oprs.remove(oprs.size()-1);
                    oprs.remove(oprs.size()-1);
                }else{
                    oprs.remove(oprs.size()-1);
                }

                oprs.add(ret.toString());
            }else if(Utils.isNum(op)){
                oprs.add(op);
            }
        }

        return Double.toString(ret);
    }


}
