
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AdvancedCalculatorServer {

    public static void main(String[] args) 	{
        try {
            ServerSocket s = new ServerSocket(9997);
            String str;
            ArrayList<String> operacao; //armazena a operação posfixada passada pelo usuário
            ArrayList<String> operandos; //utilizada para realizar as operações entre os operandos. Segue o padrão [result, num1, num2, opr]
            double op1 = 0.0, op2 = 0.0;
            double result = 0.0;
            String expr;
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

                    expr = new AdvancedCalculator().toSimpleSpr(new String(line).trim());
                    System.out.println("adv Calc: exp =  " + expr);

                    if(Utils.isNum(expr)){
                        result = Double.parseDouble(expr);
                    }else{
                        result = new AdvancedCalculator().getResultOpr(expr);
                    }



                    str = new String(line);

                    System.out.println("result"+result);
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



}
