package org.yscript.y;

import java.io.*;

public class scripts {

    public static String path;
    public static void prt_c(String[] code){
        File file = new File(path);
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("Произошла ошибка!");
        }
        BufferedReader reader = new BufferedReader(fr);
            System.out.println(code[1]);
        String[] codeprt = new String[0];
        try {
            codeprt = reader.readLine().split("\\.");
        } catch (IOException e) {
            System.out.println("Произошла ошибка!");
        }
        if(codeprt[0].contains("E")){
                return;
        }else if(codeprt[0].contains("END")){
                return;
        }
        else{
            System.out.println("Скрипт не завершен!");
            return;
        }
    }
}
