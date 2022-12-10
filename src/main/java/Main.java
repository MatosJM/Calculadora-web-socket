import org.json.JSONObject;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;


public class Main {

    public static void main(String[] args) {
        Socket s = null;
        Socket t = null;
        try {
            s = new Socket("127.0.0.1", 9999);//Server to Basic Calculators
            t = new Socket("127.0.0.1", 9997);//Server to Advanced Calculators
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        exception(Exception.class, (e, req, res) -> e.printStackTrace()); // print all exceptions
        staticFiles.location("/public");
        port(8081);

//        get("/calculadora", (req, res) -> calculate(req));
        Socket finalS = s;
        Socket finalT = t;
        get("/calculadora", (req, res) -> calculate(req, finalS, finalT));

        get("/hello", (req, res) -> "Hello World");
    }

    private static String calculate(Request req, Socket s, Socket t) {
        try {

            InputStream i = null;
            OutputStream o = null;
            Map<String, Object> model = new HashMap<>();

            String expr = req.body();
            String str = "";
            Formatter fmt;
            boolean validExpr = false;

            //instancia um novo JSONObject passando a string como entrada
            JSONObject exprJson = new JSONObject(expr);

            if (Utils.isValidExpr(exprJson.getString("expressao"))) {
                //chamar socket básico
                byte[] line = new byte[100];
                line = exprJson.getString("expressao").getBytes();
                i = s.getInputStream();
                o = s.getOutputStream();
                o.write(line);
                Arrays.fill(line, (byte) 0);
                i.read(line);

                str = new String(line).trim();

                if (Double.parseDouble(str) % 1 == 0) {
                    String s1 = str.replaceAll("\\.\\d+", "");
                    model.put("resultado", str.replaceAll("\\.\\d+", ""));

                } else {
                    //fmt.format("Resultado: %,.2f", Double.parseDouble(str));
                    model.put("resultado", String.format("%.2f", Double.parseDouble(str)));
                }


            } else if(Utils.isValidExprAdvanced(exprJson.getString("expressao"))){
                //chamar socket avançado
                byte[] line = new byte[100];
                line = exprJson.getString("expressao").getBytes();
                i = t.getInputStream();
                o = t.getOutputStream();
                o.write(line);
                Arrays.fill(line, (byte) 0);
                i.read(line);

                str = new String(line).trim();

                if (Double.parseDouble(str) % 1 == 0) {
                    String s1 = str.replaceAll("\\.\\d+", "");
                    model.put("resultado", str.replaceAll("\\.\\d+", ""));

                } else {
                    //fmt.format("Resultado: %,.2f", Double.parseDouble(str));
                    model.put("resultado", String.format("%.2f", Double.parseDouble(str)));
                }

            }else {
                model.put("erro", "Erro! Expressão Inválida.");
            }
            return new VelocityTemplateEngine().render(new ModelAndView(model, "velocity/index.vm"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
