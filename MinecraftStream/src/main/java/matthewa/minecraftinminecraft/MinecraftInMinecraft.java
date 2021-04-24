package matthewa.minecraftinminecraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public final class MinecraftInMinecraft extends JavaPlugin {
    public static WebserverThread webserver;
    public static BlockParser parser;
    public static ArrayList<String> colors = new ArrayList<>();
    public static Location startingLoc;
    public static MinecraftInMinecraft self;
    @Override
    public void onEnable() {
        self = this;
        File file = new File("txts.json");
        StringBuilder b = new StringBuilder();
        try {
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()){
                b.append(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSONArray jsonColor = new JSONArray(b.toString());
        //ArrayList<> jsonColor = JSON.parse(b.toString());
        ArrayList<Pair<String, Double>> listColor = new ArrayList<>();
        for (int i = 0; i < jsonColor.length(); i++) {
            JSONObject key = jsonColor.getJSONObject(i);
            listColor.add(new Pair<String, Double>((String) key.get("id"), (Double) key.get("num")));
        }
        Comparator<Pair> comp = (o1, o2) -> {
            if(((Double) o1.getValue1()) < ((Double)o2.getValue1())){
                return -1;
            }else if(((Double) o1.getValue1()) > ((Double)o2.getValue1())){
                return 1;
            }
            return 0;
        };
        listColor.sort(comp);
        for (Pair p: listColor) {
            colors.add((String) p.getValue0());
        }
        getCommand("startserver").setExecutor(new StartWebserver());
        redefineThreads();
    }
    public static void placeBlock(Material mat,int x, int y){
        Location loc = startingLoc.clone();
        loc.add(x,y,0.0D);
        loc.getWorld().getBlockAt(loc).setType(mat);
    }
    public static void redefineThreads(){
        webserver = new WebserverThread();
        parser = new BlockParser();
    }
    @Override
    public void onDisable() {
        ((WebserverThread) MinecraftInMinecraft.webserver).kill();
        ((BlockParser) MinecraftInMinecraft.parser).kill();
    }
    // Used for testing the web server
    public static void sendMessage(String str){
        Bukkit.getServer().broadcastMessage(str);
    }
}
