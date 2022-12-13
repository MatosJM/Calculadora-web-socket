import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class Api {
    public static void main(String[] args) {
        exception(Exception.class, (e, req, res) -> e.printStackTrace()); // print all exceptions
        staticFiles.location("/public");
        port(8081);

        get("/calculadora", (req, res) -> {
            Calculate app = new Calculate(req);
            app.connect();
            app.sendExpression();
            return new VelocityTemplateEngine().render(new ModelAndView(app.resultExpression(), "velocity/index.vm"));
            //return app.resultExpression();
        });

    }
}
