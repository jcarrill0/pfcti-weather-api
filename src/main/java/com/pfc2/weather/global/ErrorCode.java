package com.pfc2.weather.global;

import java.util.ArrayList;
import java.util.List;

public class ErrorCode {
    public static String ERROR_503 = "503";
    public static String ERROR_503_DESC = "Error en los servidores";
    public static String ERROR_400 = "400";
    public static String ERROR_400_DESC = "Parámetros incorrectos";
    public static String ERROR_401 = "401";
    public static String ERROR_401_DESC = "Sin autorización";

    public static List<String> errorDescriptioMapping(String message, String code) {
        List<String> errorDescription  = new ArrayList<>();

        if(Integer.parseInt(code) >= 500) {
            errorDescription.add(ERROR_503_DESC);
        }

        if(message != null) {
            errorDescription.add(message);
        }

        return errorDescription;
    }
}
