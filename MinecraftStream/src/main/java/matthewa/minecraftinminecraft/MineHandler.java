package matthewa.minecraftinminecraft;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MineHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        handleRequest(exchange);
        String res = "Received";
        OutputStream os = exchange.getResponseBody();
        exchange.sendResponseHeaders(200, res.length());
        os.write(res.getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.close();

    }
    public void handleRequest(HttpExchange e){
        String is = e.getRequestHeaders().get("text").toString();
        System.out.println(is);
        System.out.println("Received Connection");
        MinecraftInMinecraft.sendMessage(is);
    }
}
