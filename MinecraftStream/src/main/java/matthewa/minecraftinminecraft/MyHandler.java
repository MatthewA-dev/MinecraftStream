package matthewa.minecraftinminecraft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.javatuples.Pair;
import org.rapidoid.http.Req;
import org.rapidoid.http.ReqHandler;

import java.nio.charset.StandardCharsets;

public class MyHandler implements ReqHandler {
    @Override
    public Object execute(Req req) throws Exception {
        //BlockParser.queue.add(req.body());
        String[] sizes = req.header("Size").split(":");
        //BlockParser.sizes.add(new Pair<Integer,Integer>(Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1])));
        doBlockPlacing(req.body(),new Pair<Integer,Integer>(Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1])));
        return req.response().result("");
    }
    public void doBlockPlacing(byte[] body, Pair<Integer,Integer> size){
        if(body == null){
            return;
        }
        String[] matStr = new String(body, StandardCharsets.UTF_8).split(",");
        int index = matStr.length - 1;
        if(!MinecraftInMinecraft.self.isEnabled()){
            return;
        }
        for (int y = 0; y < size.getValue1(); y++) {
            for (int x = 0; x < size.getValue0(); x++) {
                int finalY = y;
                int finalX = x;
                int finalIndex = index;
                Runnable placeBlock = () -> MinecraftInMinecraft.placeBlock(Material.valueOf(String.valueOf(MinecraftInMinecraft.colors.get(Integer.parseInt(matStr[finalIndex])))), finalX, finalY);
                Bukkit.getScheduler().runTask(MinecraftInMinecraft.self,placeBlock);
                index--;
            }
        }
    }
}
