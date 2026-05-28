package br.com.rpinfo.analuisa.shared;


import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Utils {

   private static final Map<Integer, String> logs = new HashMap<>();

   static {
       logs.put(1, "Cadastro de  produto(s)");
       logs.put(2, "Consulta de produto(s)");
       logs.put(3, "Atualização de produto(s)");
       logs.put(4, "Exclusão de produto(s)");

       logs.put(5, "Cadastro de cidade(s)");
       logs.put(6, "Consulta de cidade(s)");
       logs.put(7, "Atualização de cidade(s)");
       logs.put(8, "Exclusão de cidade(s)");

       logs.put(9, "Consulta de endereço(s)");
       logs.put(10, "Consulta de endereço(s)");
       logs.put(11, "Atualização de endereço(s)");
       logs.put(12, "Exclusão de endereço(s)");

       logs.put(13, "Cadastro de cliente(s)");
       logs.put(14, "Consulta de cliente(s)");
       logs.put(15, "Atualização de cliente(s)");
       logs.put(16, "Exclusão de cliente(s)");
   }

   public static boolean logExiste(Integer codigoLog){
       return logs.containsKey(codigoLog);
   }

   public static String retornarDescricaoLog(Integer codigoLog){
       return logs.get(codigoLog);
   }







//    public int lerInteiro(String mensagem) {
//        while (true) {
//            try {
//                System.out.print(mensagem);
//                return Integer.parseInt(scanner.nextLine().trim());
//            } catch (NumberFormatException e) {
//                System.out.println("Erro: digite um número inteiro válido.");
//            }
//        }
//    }

//    public int lerInteiroMinimo(String mensagem, int minimo) {
//        while (true) {
//            int valor = lerInteiro(mensagem);
//
//            if (valor >= minimo) {
//                return valor;
//            }
//
//            System.out.println("Erro: o valor deve ser maior ou igual a " + minimo + ".");
//        }
//    }

//    public String lerTextoObrigatorio(String mensagem) {
//        while (true) {
//            System.out.print(mensagem);
//            String texto = scanner.nextLine().trim();
//
//            if (!texto.isEmpty()) {
//                return texto;
//            }
//
//            System.out.println("Erro: esse campo não pode ficar vazio.");
//        }
//    }

//    public String lerTextoComPadrao(String mensagem, String regex, String erro) {
//        while (true) {
//            String texto = lerTextoObrigatorio(mensagem);
//
//            if (texto.matches(regex)) {
//                return texto;
//            }
//
//            System.out.println(erro);
//        }
//    }

//    public String lerTextoNaoNumerico(String mensagem, String erro) {
//        while (true) {
//            String texto = lerTextoObrigatorio(mensagem);
//
//            if (!texto.matches("\\d+")) {
//                return texto;
//            }
//
//            System.out.println(erro);
//        }
//    }
//
//    public String lerTextoOpcional(String mensagem) {
//        System.out.print(mensagem);
//        return scanner.nextLine().trim();
//    }
//
//    public String lerTextoOpcionalNaoNumerico(String mensagem, String erro) {
//        while (true) {
//            System.out.print(mensagem);
//            String texto = scanner.nextLine().trim();
//
//            if (texto.isEmpty()) {
//                return texto;
//            }
//
//            if (!texto.matches("\\d+")) {
//                return texto;
//            }
//
//            System.out.println(erro);
//        }
//    }


}