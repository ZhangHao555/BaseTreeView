package basetreeview.ahao.com.basetreeviewpro;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<File> getFiles(){
        List<File> files = new ArrayList<>();
        files.add(new File("aa","1",""));
        files.add(new File("bb","2","1"));
        files.add(new File("cc","3","1"));
        files.add(new File("dd","4","2"));
        files.add(new File("ff","5","2"));
        files.add(new File("gg","6","1"));
        files.add(new File("hh","7",""));
        files.add(new File("jj","8",""));
        files.add(new File("qq","9","3"));
        files.add(new File("ww","10","9"));
        files.add(new File("jj","11","3"));
        files.add(new File("jj","12","4"));
        files.add(new File("jj","13","4"));
        files.add(new File("jj","14","4"));
        files.add(new File("jj","15","4"));
        files.add(new File("jj","16","7"));
        files.add(new File("jj","17","7"));
        files.add(new File("jj","18","7"));

        return files;
    }
}
