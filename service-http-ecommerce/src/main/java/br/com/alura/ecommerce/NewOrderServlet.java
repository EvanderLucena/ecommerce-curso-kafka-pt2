package br.com.alura.ecommerce;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderServlet extends HttpServlet {
    private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();
    private final KafkaDispatcher<String> emailDispatcher = new KafkaDispatcher<>();

    @Override
    public void destroy() {
        orderDispatcher.close();
        emailDispatcher.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var email = req.getParameter("email");
            for (var i = 0; i < 10; i++) {

                var orderId = UUID.randomUUID().toString();
                var amount = new BigDecimal(req.getParameter("amount"));

                var order = new Order(orderId, amount, email);
                orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, order);

                var emailCode = "Thank you for your order! We are processing your order!";
                emailDispatcher.send("ECOMMERCE_SEND_EMAIL", email, emailCode);

                System.out.println("new order sent successfully");
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println("new order sent");
            }

        } catch (ExecutionException | InterruptedException e) {
            throw new ServletException(e);
        }
    }
}
