package matthewa.minecraftinminecraft;

import org.rapidoid.setup.On;

public class WebserverThread extends Thread {
    @Override
    public void run() {
        On.port(5000);
        On.defaults().managed(false);
        On.post("/").plain(new MyHandler());
    }
    public void kill(){
        System.exit(0);
    }
}
