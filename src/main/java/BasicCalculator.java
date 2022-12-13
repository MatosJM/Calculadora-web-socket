import java.util.ArrayList;

public class BasicCalculator {
    private double op1;//operand 1
    private double op2; //operand 2
    private String opr; //operator

    /*CalculatorBasic(){
    }*/

    BasicCalculator(double op, String opr){
        this.op1 = 0.0;
        this.op2 = op;
        this.opr = opr;
    }
    BasicCalculator(double op1, double op2, String opr){
        this.op1 = op1;
        this.op2 = op2;
        this.opr = opr;
    }

    public BasicCalculator() {
        this.op1 = 0.0;
        this.op2 = 0.0;
    }

  /*  public static CalculatorBasic getInstance(){
        return new CalculatorBasic();
    }*/

    public double getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = Double.parseDouble(op1);
    }

    public double getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = Double.parseDouble(op2);
    }

    public String getOpr() {
        return opr;
    }

    public void setOpr(String opr) {
        this.opr = opr;
    }

    public double getResultOpr(){
        double result = 0.0;
        switch (this.opr.charAt(0)){
            case '+':
                result = sum(this.op1, this.op2);
                break;
            case '-':
                result = sub(this.op1, this.op2);
                break;
            case '*':
                result = mult(this.op1, this.op2);
                break;
            case '/':
                result = div(this.op1, this.op2);
                break;
        }
        return result;
    }

    public double getResultOpr(String expr){
        ArrayList<String> operacao; //armazena a operação posfixada passada pelo usuário
        ArrayList<String> operandos;
        operacao = new ArrayList<>();
        operandos = new ArrayList<>();

        operacao = Utils.infixToPostfix(expr);

        for(String op : operacao){

            if (!operandos.isEmpty()) {
                if (Utils.isOperator(operandos.get(operandos.size() - 1))) {//o topo da lista é um operador

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
                    operandos.add(op);

                } else {//o topo da lista é um operando
                    operandos.add(op);
                }
            } else {//o topo da lista é um operando
                operandos.add(op);
            }


        }
        if(operandos.size()==3){
            recalcListTwoOpr(operandos);
        }

        return Double.parseDouble(operandos.get(operandos.size()-1));
    }

    public static Double sum(Double num1, Double num2){
        return num1 + num2;
    }

    public static Double sub(Double num1, Double num2){
        return num1 - num2;
    }

    public static Double mult(Double num1, Double num2){
        return num1 * num2;
    }

    public static Double div(Double num1, Double num2){
        return num1 / num2;
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
       // String opr;
       // double op2;
       // double op1;
        double result;
        //opr = operandos.get(operandos.size() - 1);
        this.setOpr(operandos.get(operandos.size() - 1));
        operandos.remove(operandos.size()-1);

        //op2 = Double.parseDouble(operandos.get(operandos.size()-1));
        this.setOp2(operandos.get(operandos.size()-1));
        operandos.remove(operandos.size()-1);

        //op1 = Double.parseDouble(operandos.get(operandos.size()-1));
        this.setOp1(operandos.get(operandos.size()-1));
        operandos.remove(operandos.size()-1);

        result =  this.getResultOpr();
        operandos.add(Double.toString(result));
    }
}


