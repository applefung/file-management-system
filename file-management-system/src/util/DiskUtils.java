package util;

public class DiskUtils {
	
    public static int calNeedBlock(String Data) {
        if (Data != null) {
        	//at most 64 char for one block
            double need = Data.length() * 1.0 / 64;
            return (int) Math.ceil(need);
        } 
        else {
            return 0;
        }
    }
}
