
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SimpleJavaServer {

    public static void main(String[] args) 	{
        try {
            ServerSocket s = new ServerSocket(9999);//9999
            String str;
            ArrayList<String> operacao; //armazena a operação posfixada passada pelo usuário
            ArrayList<String> operandos; //utilizada para realizar as operações entre os operandos. Segue o padrão [result, num1, num2, opr]
            double op1 = 0.0, op2 = 0.0;
            double result = 0.0;
            String opr;
            while (true) {
                operacao = new ArrayList<String>();
                ArrayList<String> temp;
                Socket c = s.accept();
                InputStream i = c.getInputStream();
                OutputStream o = c.getOutputStream();
                do {
                    operacao = new ArrayList<String>();
                    result = 0;
                    byte[] line = new byte[100];
                    i.read(line);
                    System.out.println(line);
                    operacao = Utils.infixToPostfix(new String(line).trim());

                    System.out.println(operacao);

                    str = new String(line);

                    operandos = new ArrayList<String>();
                    //operandos.add(Double.toString(result)); //adiciona zero a base da lista
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
                    result = Double.parseDouble(operandos.get(operandos.size()-1));
                    System.out.println("result"+result);
                    System.out.println(""+Double.toString(result));
                    o.write(Double.toString(result).getBytes());

                    str = new String(line);
                } while ( !str.trim().equals("bye") );
                c.close();
            }
        }
        catch (Exception err){
            System.err.println(err);
        }
    }

    private static void recalcListOneOpr(ArrayList<String> operandos) {
        String opr;
        double op2;
        double result;
        opr = operandos.get(operandos.size() - 1);
        operandos.remove(operandos.size()-1);

        op2 = Double.parseDouble(operandos.get(operandos.size()-1));
        operandos.remove(operandos.size()-1);

        result =  new CalculatorBasic(op2,opr).getResultOpr();
        operandos.add(Double.toString(result));
    }

    private static void recalcListTwoOpr(ArrayList<String> operandos) {
        String opr;
        double op2;
        double op1;
        double result;
        opr = operandos.get(operandos.size() - 1);
        operandos.remove(operandos.size()-1);

        op2 = Double.parseDouble(operandos.get(operandos.size()-1));
        operandos.remove(operandos.size()-1);

        op1 = Double.parseDouble(operandos.get(operandos.size()-1));
        operandos.remove(operandos.size()-1);

        result =  new CalculatorBasic(op1,op2,opr).getResultOpr();
        operandos.add(Double.toString(result));
    }


}
