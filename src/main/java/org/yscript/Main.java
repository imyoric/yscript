package org.yscript;

import org.yscript.y.addons;
import org.yscript.y.scripts;

import java.io.*;
import java.util.*;


public class Main {
    public static HashMap<String, String> Globalvar = new HashMap<>();
    public static int Version = 4;
    public static String path = "test.txt";
    //public static int iset = 0;
    public static void main(String[] args) {
        try {
            if(args.length < 2) {
                if(path == null) {
                    addons.errorMsg("Не все агрументы запуска введены!");
                    addons.infoMsg("Все агрументы:\n" +
                            "-yscr <Путь до скрипта> - Запустить скрипт YScript \n" +
                            "-ver 0 - Версия YScript");
                    return;
                }
            }else if(args[0].equals("-ver")){
                addons.infoMsg("YScript Версия: Alpha 0.4ru");
                return;
            }else if(args[0].equals("-print")){
                addons.infoMsg(args[1]);
            }else if(args[0].equals("-yscr")){
                path = args[1];
            }
            if(!Objects.equals(getFileExtension(path), ".yscr")){
                addons.warningMsg("Файл не имеет расширения <.yscr>! Возможно он не поддерживается!");
            }

              File file = null;
              try {
                  file = new File(path);
              }catch (Exception e){
                  addons.errorMsg("Не удалось открыть Yscript файл.");
                  return;
              }
            FileReader fr = null;
              try {
                  fr = new FileReader(file);
              }catch (Exception e){
                  addons.errorMsg("Не удалось открыть Yscript файл!");
                  return;
              }
              BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            //System.out.print(line);
              if(!line.contains("<yscript>")){
                  addons.errorMsg("Ошибка синтаксиса! Судя по всему это не скрипт! Отсутствует '<Yscript>'!");
                  return;
              }
             line = reader.readLine();
                String[] code = new String[0];
                if(line != null){
                    code = line.split("\\|");
                    if(!code[1].contains("scriptVer")){
                        addons.errorMsg("Ошибка синтаксиса! Судя по всему это не скрипт! Отсутствует '<scriptVer>'!");
                    }else{
                        try {
                            int ver = Integer.parseInt(code[2]);
                            if (ver > Version) {
                                addons.errorMsg("Этот скрипт не возможно открыть! Он предназначен для более высокой версии: "+ver);
                                return;
                            }
                        }catch (Exception e){
                            addons.errorMsg("Неожиданное исключение! Код 1.");
                        }
                    }
                }else {
                    System.exit(0);
                }


              code(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Hello world!");
    }

    public static void code(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        while (line != null) {
            if(line.equals("END")){
                line = reader.readLine();
            }
            String[] code = new String[0];
            if(line != null){
                 code = line.split("\\|");
            }else {
                System.exit(0);
            }

            if(code[0].contains("print")){
                if(Character.toString(code[1].charAt(0)).equals("'")){
                    System.out.print(code[1].replaceAll("'", "")+"");
                } else{
                    System.out.print(Globalvar.get(code[1])+"");
                }

                line = reader.readLine();
            }
            if(code[0].contains("var")){
                if(code[1].contains("set")){
                    if(Character.toString(code[3].charAt(0)).equals("'")){
                        Globalvar.put(code[2], code[3].replaceAll("'", ""));
                    }else{
                        Globalvar.put(code[2], Globalvar.get(code[3]));
                    }
                }
                if(code[1].contains("del")){
                    Globalvar.put(code[2], null);
                }
                line = reader.readLine();
            }
            if(code[0].contains("cmd")){
                if(code[1].contains("setPref")){
                    if(Character.toString(code[2].charAt(0)).equals("'")){
                        addons.cmdPref = code[2].replaceAll("'", "");
                    }else{
                        addons.cmdPref = Globalvar.get(code[2]);
                    }
                    if(Character.toString(code[3].charAt(0)).equals("'")){
                        addons.cmdPrefColor = code[3].replaceAll("'", "");
                    }else{
                        addons.cmdPrefColor = Globalvar.get(code[3]);
                    }
                }
                if(code[1].contains("Pref")){
                    if(code[2].contains("color")){
                        if(Character.toString(code[3].charAt(0)).equals("'")){
                            addons.cmdPrefColor = code[3].replaceAll("'", "");
                        }else{
                            addons.cmdPrefColor = Globalvar.get(code[3]);
                        }
                    }
                    if(code[2].contains("text")){
                        if(Character.toString(code[3].charAt(0)).equals("'")){
                            addons.cmdPref = code[3].replaceAll("'", "");
                        }else{
                            addons.cmdPref = Globalvar.get(code[3]);
                        }
                    }
                }
                if(code[1].contains("printByPref")){
                    if(String.valueOf(code[2].charAt(0)).equals("'")){
                        addons.sendMsgByPref(code[2].replaceAll("'", ""));
                    } else{
                        addons.sendMsgByPref(Globalvar.get(code[2]));
                    }
                }
                if(code[1].contains("allowCommand")){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while ( true ) // ввод числа строк
                            {
                                if(Globalvar.get("ifAllowCommand") == null){
                                    Globalvar.put("ifAllowCommand","true");
                                }
                                if(Globalvar.get("ifAllowCommand").equals("true")) {
                                    //System.out.println("Введите число строк");
                                    Scanner sc1 = new Scanner(System.in);
                                    try {
                                        String n = sc1.nextLine();
                                        String[] cd = new String[0];
                                        try {
                                            cd = n.split("\\|");
                                        } catch (Exception e) {
                                            addons.errorMsg("Неожиданное Исключение. Код 3.");
                                        }
                                        if (cd[0].equalsIgnoreCase("print")){
                                            if(cd.length > 1) {
                                                System.out.print(cd[1]);
                                            }else{
                                                addons.errorMsg("Не все аргументы введены! Введите аргумент 2 (print|<Арг 2>)");
                                                //addons.infoMsg("");
                                            }
                                        }else if(cd[0].equalsIgnoreCase("help")){
                                            addons.errorMsg("HELP - Меню еще не готово!");
                                        }else if(Globalvar.get("Cmd."+cd[0]) != null){

                                        }
                                            break;
                                    } catch (InputMismatchException fg) {
                                        addons.errorMsg("Неожиданное Исключение. Код 2.");
                                        return;
                                    }
                                } else return;
                            }
                        }
                    }).start();
                }
                line = reader.readLine();
            }
            if(code[0].contains("if")){
                try {
                    String a = null;
                    String b = null;
                    if (code[2].equals("==")) {
                        if(code[1].contains("'")){
                            a = code[1];
                        }else a = Globalvar.get(code[1]);
                        if(code[3].contains("'")){
                            b = code[3];
                        }else b = Globalvar.get(code[3]);
                        if(a.equals(b)){
                            code(reader);
                        }else{
                            while (!line.contains("END")) {
                                line = reader.readLine();
                                if(line == null){
                                    addons.errorMsg("ОШИБКА! END - Конец скрипта не найден!\n");
                                    return;
                                }
                                if(line.contains("END")){
                                    //line = reader.readLine();
                                    code(reader);
                                }
                            }
                        }
                    }
                    else if (code[2].equals("!=")) {
                        if(code[1].contains("'")){
                            a = code[1];
                        }else a = Globalvar.get(code[1]);
                        if(code[3].contains("'")){
                            b = code[3];
                        }else b = Globalvar.get(code[3]);
                        if(!a.equals(b)){
                            code(reader);
                        }else{
                            while (!line.contains("END")) {
                                line = reader.readLine();
                                if(line == null){
                                    addons.errorMsg("ОШИБКА! END - Конец скрипта не найден!\n");
                                    return;
                                }
                                if(line.contains("END")){
                                    //line = reader.readLine();
                                    code(reader);
                                }
                            }
                        }
                    }
                    else if (code[2].equals("<")) {
                        int ai = 0;
                        int bi = 0;
                        if(code[1].contains("'")){
                            ai = Integer.parseInt(code[1].replaceAll("'", ""));
                        }else ai = Integer.parseInt(Globalvar.get(code[1]));
                        if(code[3].contains("'")){
                            bi = Integer.parseInt(code[3].replaceAll("'", ""));
                        }else bi = Integer.parseInt(Globalvar.get(code[3]));
                        if(ai < bi){
                            code(reader);
                        }else{
                            while (!line.contains("END")) {
                                line = reader.readLine();
                                if(line == null){
                                    addons.errorMsg("ОШИБКА! END - Конец скрипта не найден!\n");
                                    return;
                                }
                                if(line.contains("END")){
                                    //line = reader.readLine();
                                    code(reader);
                                }
                            }
                        }
                    } else if (code[2].equals(">")) {
                        int ai = 0;
                        int bi = 0;
                        if(code[1].contains("'")){
                            ai = Integer.parseInt(code[1].replaceAll("'", ""));
                        }else ai = Integer.parseInt(Globalvar.get(code[1]));
                        if(code[3].contains("'")){
                            bi = Integer.parseInt(code[3].replaceAll("'", ""));
                        }else bi = Integer.parseInt(Globalvar.get(code[3]));
                        if(ai > bi){
                            code(reader);
                        }else{
                            while (!line.contains("END")) {
                                line = reader.readLine();
                                if(line == null){
                                    addons.errorMsg("ОШИБКА! END - Конец скрипта не найден!\n");
                                    return;
                                }
                                if(line.contains("END")){
                                    //line = reader.readLine();
                                    code(reader);
                                }
                            }
                        }
                    }
                    else{
                        while (!line.contains("END")) {
                            line = reader.readLine();
                            if(line == null){
                                addons.errorMsg("ОШИБКА! END - Конец скрипта не найден!\n");
                                return;
                            }
                            if(line.contains("END")){
                                //line = reader.readLine();
                                code(reader);
                            }
                        }
                    }

                }
                catch (Exception e){
                    addons.errorMsg("Ошибка в коде!\n");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void codeif(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        while (line != null) {
            if(line == "END"){
                reader.readLine();
                code(reader);
                return;
            }
            String[]  code = line.split("\\|");
            if(code[0].contains("prnt")){
                if(code[1].contains("'")){
                    addons.infoMsg(code[1].replaceAll("'", "")+"\n");
                } else{
                    addons.infoMsg(Globalvar.get(code[1])+"\n");
                }
            }
            if(code[0].contains("var")){
                if(code[1].contains("set")){
                    Globalvar.put(code[2], code[3].replaceAll("'", ""));
                }
                if(code[1].contains("del")){
                    Globalvar.put(code[2], null);
                }
            }
        }
    }
    private static String getFileExtension(String mystr) {
        int index = mystr.indexOf('.');
        return index == -1? null : mystr.substring(index);
    }
}