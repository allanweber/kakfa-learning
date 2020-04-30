package br.com.alura.ecommerce;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class GenerateAllReportsServlet  extends HttpServlet {
    private final KafkaDispatcher<User> userDispatcher = new KafkaDispatcher<>();

    @Override
    public void destroy() {
        super.destroy();
        userDispatcher.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            for (User user : users) {
                userDispatcher.send("USER_GENERATE_READING_REPORT", user.getId(), user);
            }

            System.out.println("Generated reports.");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("Generated reports.");

        } catch (ExecutionException | InterruptedException e) {
            throw new ServletException(e);
        }
    }
}
