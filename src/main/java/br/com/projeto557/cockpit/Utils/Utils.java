package br.com.projeto557.cockpit.Utils;

public class Utils {

    public static String[] getArrayString(String string, String separador){
        String[] resultArray =  string.split(separador);
        return resultArray;
    }
}
