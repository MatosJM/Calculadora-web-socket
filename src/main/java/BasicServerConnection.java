import java.io.*;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class BasicServerConnection extends Thread{
    private InputStream i;
    private OutputStream o;
    private Socket sc;
    private byte[] line;

    //ArrayList<String> operacao;

    public BasicServerConnection(Socket clientS){
        try {
            this.sc = clientS;
            this.i = new DataInputStream(sc.getInputStream());
            this.o = new DataOutputStream(sc.getOutputStream());
            this.line = new byte[100];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(){
        try {

            ArrayList<String> infixOperation;
            ArrayList<String> posfixOperation = new ArrayList<>();

            i.read(line);
            System.out.println(new String(this.line));
           // operacao = Utils.infixToPostfix(new String(line).trim());
            infixOperation = Utils.toArrayList(new String(line).trim());
            System.out.println(infixOperation);
            posfixOperation = Utils.infixToPostfix(infixOperation);

            System.out.println(resultOperation(posfixOperation));

            o.write(Double.toString(resultOperation(posfixOperation)).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static double resultOperation(ArrayList<String> operation){
        Deque<String> operandos;
        Double num1, num2;
        Double result=0.0 ;

        System.out.println(operation);
        operandos= new ArrayDeque<>();

        for(String op : operation){
            if(Utils.isOperator(op)){
                if(operandos.size()<2){
                    //colocar exceção;

                }else{
                    num2 = Double.parseDouble(operandos.pop());
                    num1 = Double.parseDouble(operandos.pop());
                    result = new BasicCalculator(num1, num2, op).getResultOpr();
                    operandos.push(result.toString());//adiciona no inicio
                }
            }else{
                operandos.push(op);
            }

        }

        result = Double.parseDouble(operandos.peek());
        System.out.println("result"+result);
        System.out.println(""+Double.toString(result));

        return result;
    }


}
