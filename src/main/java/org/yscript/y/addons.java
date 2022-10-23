package org.yscript.y;

public class addons {
    public static String cmdPref;
    public static String cmdPrefColor = null;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void errorMsg(String msg){
        System.out.print("\n"+ANSI_RED+"(ERROR!):"+msg+ANSI_RESET+"");
    }
    public static void infoMsg(String msg){
        System.out.print("\n"+ANSI_WHITE+"(INFO):"+msg+ANSI_RESET+"");
    }
    public static void warningMsg(String msg){
        System.out.print("\n"+ANSI_YELLOW+"(WARNING!):"+msg+ANSI_RESET+"");
    }

    public static void sendMsgByPref(String msg){
        if(cmdPref == null){
            errorMsg("Невозможно отправить сообщение через префикс! Префикс не инициализирован!");
            return;
        }
        if(cmdPrefColor.equals("CLR_BLACK")) System.out.print("\n"+ANSI_BLACK+cmdPref+": "+ANSI_RESET+msg+"");
        else if(cmdPrefColor.equals("CLR_RED")) System.out.print("\n"+ANSI_RED+cmdPref+": "+ANSI_RESET+msg+"");
        else if(cmdPrefColor.equals("CLR_GREEN")) System.out.print("\n"+ANSI_GREEN+cmdPref+": "+ANSI_RESET+msg+"");
        else if(cmdPrefColor.equals("CLR_BLUE")) System.out.print("\n"+ANSI_BLUE+cmdPref+": "+ANSI_RESET+msg+"");
        else if(cmdPrefColor.equals("CLR_PURPLE")) System.out.print("\n"+ANSI_PURPLE+cmdPref+": "+ANSI_RESET+msg+"");
        else if(cmdPrefColor.equals("CLR_CYAN")) System.out.print("\n"+ANSI_CYAN+cmdPref+": "+ANSI_RESET+msg+"");
        else if(cmdPrefColor.equals("CLR_WHITE")) System.out.print("\n"+ANSI_WHITE+cmdPref+": "+ANSI_RESET+msg+"");
        else{
            errorMsg("Невозможно отправить сообщение через префикс! Неверный цвет префикса!");
            return;
        }
    }
}
