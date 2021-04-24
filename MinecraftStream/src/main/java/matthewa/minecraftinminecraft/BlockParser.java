package matthewa.minecraftinminecraft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.javatuples.Pair;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class BlockParser extends Thread {
    public static ArrayList<byte[]> queue = new ArrayList<byte[]>();
    public static ArrayList<Pair<Integer,Integer>> sizes = new ArrayList<>();
    public int conv256To10(ArrayList<Integer> c){
        Collections.reverse(c);
        int num = 0;
        for (int i = 0; i < c.size(); i++) {
            num += Math.pow(256,i) * c.get(i);
        }
        return num;
    }
    @Override
    public void run() {
        while(true) {
            try {
                byte[] body = queue.get(0);
                Pair<Integer, Integer> size = sizes.get(0);
                queue.remove(0);
                sizes.remove(0);
                if(body == null){
                    kill();
                }
                String[] matStr = new String(body, StandardCharsets.UTF_8).split(",");
                int index = matStr.length - 1;
                for (int y = 0; y < size.getValue1(); y++) {
                    for (int x = 0; x < size.getValue0(); x++) {
                        int finalY = y;
                        int finalX = x;
                        int finalIndex = index;
                        Runnable placeBlock = () -> MinecraftInMinecraft.placeBlock(Material.valueOf(String.valueOf(MinecraftInMinecraft.colors.get(Integer.parseInt(matStr[finalIndex])))), finalX, finalY);
                        if(!MinecraftInMinecraft.self.isEnabled()){
                            kill();
                        }
                        Bukkit.getScheduler().runTask(MinecraftInMinecraft.self,placeBlock);
                        index--;
                    }
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }
    public void kill(){
        System.exit(0);
    }
}
