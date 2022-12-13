import org.json.JSONObject;
import spark.Request;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Calculate {
    private Socket socket;

//    private InputStream i;
 //   private OutputStream o;

    private byte[] line;

    //instancia um novo JSONObject passando a string como entrada
    JSONObject exprJson;
    String expr;


    Calculate(Request req){
        this.expr = req.body();
        this.line = new byte[100];
        this.exprJson = new JSONObject(this.expr);
    }

    public void connect(){
        try {
            if (Utils.isValidExpr(exprJson.getString("expressao"))) {
                this.socket = new Socket("127.0.0.1", 9999);
            }else if(Utils.isValidExprAdvanced(exprJson.getString("expressao"))){
                this.socket = new Socket("127.0.0.1", 9997);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendExpression(){
        try {
            this.line = this.exprJson.getString("expressao").getBytes();
           OutputStream o = new DataOutputStream(socket.getOutputStream());
            o.write(this.line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String,Object> resultExpression(){
        Map<String, Object> model = new HashMap<>();
        String resultado;
        byte[] ln = new byte[100];
        try {
            InputStream  i = new DataInputStream(socket.getInputStream());
            Arrays.fill(ln, (byte) 0);
            i.read(ln);
            resultado = new String(ln).trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        model.put("resultado",resultado);
        return model;
    }




}
