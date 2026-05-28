package br.com.rpinfo.analuisa.shared;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static final Map<Integer, String> logs = new HashMap<>();

    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    static {
        logs.put(1, "Cadastro de produto(s)");
        logs.put(2, "Consulta de produto(s)");
        logs.put(3, "Atualização de produto(s)");
        logs.put(4, "Exclusão de produto(s)");

        logs.put(5, "Cadastro de cidade(s)");
        logs.put(6, "Consulta de cidade(s)");
        logs.put(7, "Atualização de cidade(s)");
        logs.put(8, "Exclusão de cidade(s)");

        logs.put(9, "Cadastro de endereço(s)");
        logs.put(10, "Consulta de endereço(s)");
        logs.put(11, "Atualização de endereço(s)");
        logs.put(12, "Exclusão de endereço(s)");

        logs.put(13, "Cadastro de cliente(s)");
        logs.put(14, "Consulta de cliente(s)");
        logs.put(15, "Atualização de cliente(s)");
        logs.put(16, "Exclusão de cliente(s)");
    }

    public static boolean logExiste(Integer codigoLog) {
        return logs.containsKey(codigoLog);
    }

    public static String retornarDescricaoLog(Integer codigoLog) {
        return logs.get(codigoLog);
    }

    public static LocalDate retornarDataAtual() {
        return LocalDate.now();
    }

    public static String retornarHoraAtualFormatada() {
        return LocalTime.now().format(FORMATO_HORA);
    }
}