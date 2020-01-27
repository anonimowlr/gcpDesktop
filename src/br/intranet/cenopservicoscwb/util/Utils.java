/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.intranet.cenopservicoscwb.util;

import br.com.intranet.cenopservicoscwb.model.entidade.Calculo;
import br.com.intranet.cenopservicoscwb.model.entidade.CalculoPcond;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.scene.control.Alert;

public class Utils {

    List<String> filesListInDir = new ArrayList<String>();

    public static String getDataAtual() {

        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
        return (formatarDate.format(data));

    }

    public static java.sql.Date getDataAtualFormatoMysql() throws Exception {

        String data = getDataAtual();
        java.sql.Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    public static java.sql.Date getDataDesconciliacaoFormatoMysql(Date d) throws Exception {

        SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
        String data = (formatarDate.format(d));

        java.sql.Date date = null;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        return date;
    }

    public static String getDataHoraAtualMysql() {
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(d);
    }

    public static Double converteParaDouble(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",")) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        Double n = Double.parseDouble(numeroTratado);

        return n;
    }

    public static String tratarVariavel(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",")) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return numeroTratado;
    }

    public static String tratarVariavelConta(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",")) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7")
                    || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;
            } else if ((i == (tamanhoValorRecebido - 1)) && (num.equals("x") || num.equals("X"))) {
                num = "X";
                numeroTratado = numeroTratado + num;
            }
        }

        return numeroTratado;
    }

    public static String tratarVariavelSaldoBase(String string) {
        String numeroTratado = "";

        String v = limparPontos(string);

        StringBuilder stringbuilder = new StringBuilder(v);
        stringbuilder.insert(stringbuilder.length() - 2, ".");

        int tamanhoValorRecebido = stringbuilder.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = stringbuilder.subSequence(i, i + 1).toString();

            if (num.equals(".") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return numeroTratado;
    }

    public static String limparPontos(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return numeroTratado;
    }

    public static String converterToMoney(String v) {
        String numeroTratado = "";

        String sinalNegativo = v.subSequence(0, 1).toString();
        if (sinalNegativo.equals("-")) {
            v = v.substring(1, v.length());
        }

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            double marcador = i / 3;
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && (i == tamanhoValorRecebido - 3 || i == tamanhoValorRecebido - 2)) {
                num = ",";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }

            if (tamanhoValorRecebido > 4) {
                if (i == tamanhoValorRecebido - 7 || i == tamanhoValorRecebido - 10 || i == tamanhoValorRecebido - 13) {
                    numeroTratado = numeroTratado + ".";
                }

            }
        }

        if (sinalNegativo.equals("-")) {
            numeroTratado = "-" + numeroTratado;
        }

        return numeroTratado;
    }

    public static String converterToMoneySaldoBase(String string) {
        String numeroTratado = "";

        String v = limparPontos(string);

        StringBuilder stringbuilder = new StringBuilder(v);
        stringbuilder.insert(stringbuilder.length() - 2, ",");

        int tamanhoValorRecebido = stringbuilder.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            double marcador = i / 3;
            String num = stringbuilder.subSequence(i, i + 1).toString();

            if (num.equals(",") && (i == tamanhoValorRecebido - 3 || i == tamanhoValorRecebido - 2)) {
                num = ",";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }

            if (tamanhoValorRecebido > 4) {
                if (i == tamanhoValorRecebido - 7 || i == tamanhoValorRecebido - 10 || i == tamanhoValorRecebido - 13 || i == tamanhoValorRecebido - 16) {
                    numeroTratado = numeroTratado + ".";
                }

            }
        }

        return numeroTratado;
    }

    public static String formatarNpj(String string) {
        String numeroTratado = "";

        String v = limparPontos(string);

        StringBuilder stringbuilder = new StringBuilder(v);
        stringbuilder.insert(stringbuilder.length() - 3, "-");
        stringbuilder.insert(stringbuilder.length() - 11, "/");

        int tamanhoValorRecebido = stringbuilder.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            double marcador = i / 3;
            String num = stringbuilder.subSequence(i, i + 1).toString();

            if (num.equals("-")) {
                num = "-";
                numeroTratado = numeroTratado + num;
            }
            if (num.equals("/")) {
                num = "/";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }

        }

        return numeroTratado;
    }

    public static Double converterStringParaDouble(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return Double.parseDouble(numeroTratado);
    }

    public static BigDecimal converterStringParaBigDecimal(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals(",") && i == tamanhoValorRecebido - 3) {
                num = ".";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        BigDecimal numeroConvertido = new BigDecimal(numeroTratado);

        return numeroConvertido;
    }

    public static String tratarVariavel(Double v) {
        String numeroTratado = "";

        String w = Double.toString(v);

        int tamanhoValorRecebido = w.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = w.subSequence(i, i + 1).toString();

            if (num.equals("E")) {
                num = "";
                numeroTratado = numeroTratado + num;
                i = tamanhoValorRecebido;
            }

            if (num.equals(".") || num.equals("E")) {
                num = "";
                numeroTratado = numeroTratado + num;

            }

            if (num.equals(",")) {
                num = "";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return numeroTratado;
    }

    public static String tratarNpj(Double v) {
        Long numero = v.longValue();
        System.out.println(numero);

        return numero.toString();

    }

    public static String tratarContaDepositaria(Double v) {

        Long numero = v.longValue();
        System.out.println(numero);

        return numero.toString();
    }

    public static String converterParteInteiraDouble(Double v) {
        String numeroTratado = "";

        String w = Double.toString(v);

        int tamanhoValorRecebido = w.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = w.subSequence(i, i + 1).toString();

            if (num.equals("E")) {
                num = "";
                numeroTratado = numeroTratado + num;
                i = tamanhoValorRecebido;
            }

            if (num.equals(".") || num.equals("E")) {
                num = "";
                numeroTratado = numeroTratado + num;
                i = tamanhoValorRecebido;

            }

            if (num.equals(",")) {
                num = "";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }
        }

        return numeroTratado;
    }

    public static java.sql.Date formataData(String data) throws Exception {
        if (data == null || data.equals("")) {
            return null;
        }
        java.sql.Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    public static String getAnoAtual() {

        String dataAtualTexto = getDataAtual();
        String ano = (String) dataAtualTexto.subSequence(6, 10);

        return ano;
    }

    public static String getDiaAtual() {

        String dataAtualTexto = getDataAtual();
        String dia = (String) dataAtualTexto.subSequence(0, 2);

        return dia;
    }

    public static String getMesAtual() {

        String dataAtualTexto = getDataAtual();
        String mes = (String) dataAtualTexto.subSequence(4, 5);

        return mes;
    }

    public static String formatDataTexto(String data) { // recebe data em texto no formafo yyyy - MM - dd e o converte para texto no formato dd/mm/yyyy

        String ano = (String) data.subSequence(0, 4);
        String mes = (String) data.subSequence(5, 7);
        String dia = (String) data.subSequence(8, 10);

        data = dia + "/" + mes + "/" + ano;
        return data;

    }

    public static Date converterNumeroEmData(Long numeroTipoLong) {

        long time1 = numeroTipoLong;
        Date date = new Date(time1);

        return date;

    }

    public static Long converterStringEmLong(String numeroTexto) {
        Long numeroRetornado = Long.parseLong(numeroTexto);

        return numeroRetornado;

    }

    public static void copyToClipBord(String npj) {

        int tamanhoValorRecebido = npj.length();

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String text = npj.subSequence(4, tamanhoValorRecebido).toString();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, null);
    }

    public static Date getDataHoraAtualMysqlDate() {
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();

        return (d);
    }

    public static String tratarConta(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (i == tamanhoValorRecebido - 2) {
                num = num + "-";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }

            if (tamanhoValorRecebido > 4) {
                if (i == tamanhoValorRecebido - 5 || i == tamanhoValorRecebido - 8 || i == tamanhoValorRecebido - 11 || i == tamanhoValorRecebido - 14) {
                    numeroTratado = numeroTratado + ".";
                }

            }
        }

        return numeroTratado;
    }

    public static String tratarContaTexto(String v) {
        String numeroTratado = "";

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (i == tamanhoValorRecebido - 2) {
                num = num + "-";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7")
                    || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;
            } else if ((i == (tamanhoValorRecebido - 1)) && (num.equals("x") || num.equals("X"))) {
                num = "X";
                numeroTratado = numeroTratado + num;
            }

            if (tamanhoValorRecebido > 4) {
                if (i == tamanhoValorRecebido - 5 || i == tamanhoValorRecebido - 8 || i == tamanhoValorRecebido - 11 || i == tamanhoValorRecebido - 14) {
                    numeroTratado = numeroTratado + ".";
                }

            }
        }

        return numeroTratado;
    }

    public static boolean getIntervaloCorrecao(BigDecimal correcaoEsperada, BigDecimal correcaoDigitata) {

        BigDecimal primeiroValor = (BigDecimal) correcaoEsperada.subtract(new BigDecimal(1));
        BigDecimal ultimoValor = (BigDecimal) correcaoEsperada.add(new BigDecimal(1));

        if (correcaoDigitata.compareTo(primeiroValor) >= 0 && correcaoDigitata.compareTo(ultimoValor) <= 0) {
            return true;
        } else {
            return false;
        }

    }

    public static void main(String[] args) throws Exception {
        diferencaDataMes2(new Date("01/25/2003"), new Date("01/01/2015"));
    }

    public static double diferencaDataMes(Date dataInicial, Date dataFinal) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date dataInicio = dataInicial;
        Date dataFim = dataFinal;

        /*
		 * valor de um mes em milisegundos, sendo que os valores sao:
		 * 30 dias no mes, 24 horas no dia, 60 minutos por hora, 60 segundos por minuto e 1000 milisegundos  
         */
        final double MES_EM_MILISEGUNDOS = 30.0 * 24.0 * 60.0 * 60.0 * 1000.0;
        //final double MES_EM_MILISEGUNDOS = 2592000000.0;

        double numeroDeMeses = (double) ((dataFim.getTime() - dataInicio.getTime()) / MES_EM_MILISEGUNDOS);

        System.out.println("numero de meses: " + numeroDeMeses);
        return numeroDeMeses;
    }

    public static int diferencaDataMes2(Date dataInicial, Date dataFinal) throws ParseException {
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.setTime(dataInicial);
        cal2.setTime(dataFinal);

        int numeroDeMeses = cal2.get(Calendar.MONTH) - cal.get(Calendar.MONTH);
        int numeroDeMAnos = (cal2.get(Calendar.YEAR) - cal.get(Calendar.YEAR)) * 12;

        numeroDeMAnos = numeroDeMeses + numeroDeMAnos;

        return numeroDeMAnos;
    }

    public static String converteData(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = dateFormat.format(date);
        return dataFormatada;
    }

    public static boolean criarDiretorio(Calculo calculo) {
        File diretorio;
        try {

            if (Utils.getIpHost().equals("172.20.0.33") || Utils.getIpHost().equals("172.20.0.154")) {
                diretorio = new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString() + "/" + calculo.getCliente().getCpf() + "/" + calculo.getNumeroConta());

            } else {
                diretorio = new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + calculo.getProtocoloGsv().getCdPrc().toString() + "\\" + calculo.getProtocoloGsv().getCdPrc().toString() + "\\" + calculo.getCliente().getCpf() + "\\" + calculo.getNumeroConta());

            }

            //File diretorio = new File("C:\\Users\\f7864599\\Desktop\\DistribuidorPoupancaTeste\\" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "\\" + calculo.getProtocoloGsv().getCdPrc().toString() + "\\" + calculo.getCliente().getCpf() + "\\" + calculo.getNumeroConta()) ;
            //File diretorio = new File("/home/jocimar/Área de Trabalho/TestePlanilha/" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString() + "/" + calculo.getCliente().getCpf() + "/" + calculo.getNumeroConta());
            diretorio.mkdirs();
            return true;
        } catch (Exception ex) {

            return false;
        }

    }

    public static boolean criarDiretorioPdf(Calculo calculo) {

        try {
            File diretorio;

            if (Utils.getIpHost().equals("172.20.0.33") || Utils.getIpHost().equals("172.20.0.154")) {
                diretorio = new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString());

            } else if (Utils.getIpHost().equals("192.168.1.101")) {
                diretorio = new File("C:\\Users\\PC_LENOVO\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString());

            } else {
                diretorio = new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString());

            }

            //File diretorio = new File("C:\\Users\\f7864599\\Desktop\\DistribuidorPoupancaTeste\\" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString()) ;
            //File diretorio = new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + "NPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString()) ;
            diretorio.mkdirs();
            return true;
        } catch (Exception ex) {

            return false;
        }

    }

    public static boolean criarDiretorioPcond(CalculoPcond calculo) {

        try {
            File diretorio;

            if (Utils.getIpHost().equals("172.20.0.33") || Utils.getIpHost().equals("172.20.0.154")) {

                diretorio = new File("/usr/local/apache-tomcat-8.0.15/webapps/docsPoupanca/" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString());

            } else if (Utils.getIpHost().equals("192.168.1.101")) {

                diretorio = new File("C:\\Users\\PC_LENOVO\\Desktop\\DistribuidorPoupancaTeste\\" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString());

            } else {
                diretorio = new File("C:\\Users\\f5078775\\Desktop\\DistribuidorPoupancaTeste\\" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString() + "/" + calculo.getProtocoloGsv().getCdPrc().toString());

            }

            //File diretorio = new File("C:\\Users\\f7864599\\Desktop\\DistribuidorPoupancaTeste\\" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString()) ;
            //File diretorio = new File("/opt/apache-tomcat-8.5.39/webapps/utilitario/" + "PCONDNPJ" + calculo.getProtocoloGsv().getNpj().getNrPrc().toString()) ;
            diretorio.mkdirs();
            return true;
        } catch (Exception ex) {

            return false;
        }

    }

    public static Date getDataPlanoVerao(String diaBase) throws Exception {

        if (diaBase.length() < 2) {

            diaBase = "0" + diaBase;
        }

        return formataData(diaBase + "/" + "02/1989");

    }

    public static Date getDataPlanoBresser(String diaBase) throws Exception {

        if (diaBase.length() < 2) {

            diaBase = "0" + diaBase;
        }

        return formataData(diaBase + "/" + "07/1987");

    }

    public static Date getDataPlanoCollorIAbril(String diaBase) throws Exception {

        if (diaBase.length() < 2) {

            diaBase = "0" + diaBase;
        }

        return formataData(diaBase + "/" + "05/1990");

    }

    public static Date getDataPlanoCollorIMaio(String diaBase) throws Exception {

        if (diaBase.length() < 2) {

            diaBase = "0" + diaBase;
        }

        return formataData(diaBase + "/" + "06/1990");

    }

    public static Date getDataPlanoCollorII(String diaBase) throws Exception {

        if (diaBase.length() < 2) {

            diaBase = "0" + diaBase;
        }

        return formataData(diaBase + "/" + "03/1991");

    }

    public static String converterToMoney5Casas(String v) {
        String numeroTratado = "";

        String sinalNegativo = v.subSequence(0, 1).toString();
        if (sinalNegativo.equals("-")) {
            v = v.substring(1, v.length());
        }

        int tamanhoValorRecebido = v.length();

        for (int i = 0; i < tamanhoValorRecebido; i++) {
            String num = v.subSequence(i, i + 1).toString();

            if (num.equals(".") && (i == tamanhoValorRecebido - 6 || i == tamanhoValorRecebido - 5)) {
                num = ",";
                numeroTratado = numeroTratado + num;
            }

            if (num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9")) {
                numeroTratado = numeroTratado + num;

            }

            if (tamanhoValorRecebido > 7) {
                if (i == tamanhoValorRecebido - 10 || i == tamanhoValorRecebido - 13 || i == tamanhoValorRecebido - 16) {
                    numeroTratado = numeroTratado + ".";
                }

            }
        }

        if (sinalNegativo.equals("-")) {
            numeroTratado = "-" + numeroTratado;
        }

        return numeroTratado;
    }

    public static Integer getDia(Date dataInicioCalculo) {

        Calendar c = Calendar.getInstance();
        c.setTime(dataInicioCalculo);

        Integer dia = c.get(Calendar.DATE);

        return dia;

    }

    public static String getIpHost() {

        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(addr.getHostAddress());
        System.out.println(addr.getHostName());

        return addr.getHostAddress();

    }

    public void compactarDiretorio(File dir, String zipDirName) {
        try {
            populateFilesList(dir);
            //now zip files one by one
            //create ZipOutputStream to write to the zip file
            FileOutputStream fos = new FileOutputStream(zipDirName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (String filePath : filesListInDir) {
                System.out.println("Zipping " + filePath);
                //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
                ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length() + 1, filePath.length()));
                zos.putNextEntry(ze);
                //read the file and write to ZipOutputStream
                FileInputStream fis = new FileInputStream(filePath);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void populateFilesList(File dir) throws IOException {

        try {
            File files[] = dir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    filesListInDir.add(file.getAbsolutePath());
                } else {
                    populateFilesList(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static void alertaGeral(String a, String b, String c) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(a);
        alert.setHeaderText(b);
        alert.setContentText(c);
        alert.show();

    }

    public static void alertaGeralInformacao(String a, String b, String c) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(a);
        alert.setHeaderText(b);
        alert.setContentText(c);
        alert.show();

    }

    public static Boolean isCpfCnpj(String value) {

        value = Utils.limparPontos((value).toString());
        String cpfCnpj = (String) value;

        if (cpfCnpj.length() > 11 && !isCNPJ(cpfCnpj)) {
            return false;
        } else if (cpfCnpj.length() <= 11) {
            if (cpfCnpj.length() != 11 || !calcularDigitoVerificador(cpfCnpj.substring(0, 9)).equals(cpfCnpj.substring(9, 11)) || cpfCnpj.equals("00000000000") || cpfCnpj.equals("11111111111") || cpfCnpj.equals("22222222222") || cpfCnpj.equals("33333333333") || cpfCnpj.equals("44444444444") || cpfCnpj.equals("55555555555") || cpfCnpj.equals("66666666666") || cpfCnpj.equals("77777777777") || cpfCnpj.equals("88888888888") || cpfCnpj.equals("99999999999")) {
                return false;
            }
        }

        return true;
    }

    private static String calcularDigitoVerificador(String num) {

        Integer primDig, segDig;
        int soma = 0;
        int peso = 10;
        for (int i = 0; i < num.length(); i++) {
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
        }
        if (soma % 11 == 0 | soma % 11 == 1) {
            primDig = new Integer(0);
        } else {
            primDig = new Integer(11 - (soma % 11));
        }
        soma = 0;
        peso = 11;
        for (int i = 0; i < num.length(); i++) {
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
        }
        soma += primDig.intValue() * 2;
        if (soma % 11 == 0 | soma % 11 == 1) {
            segDig = new Integer(0);
        } else {
            segDig = new Integer(11 - (soma % 11));
        }
        return primDig.toString() + segDig.toString();
    }

    public static boolean isCNPJ(String CNPJ) {
// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("11111111111111") || CNPJ.equals("00000000000000")
                || CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333")
                || CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555")
                || CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777")
                || CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999")
                || (CNPJ.length() != 14)) {
            return (false);
        }

        char dig13, dig14;
        int sm, i, r, num, peso;

// "try" - protege o código para eventuais erros de conversao de tipo (int)
        try {
// Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
// converte o i-ésimo caractere do CNPJ em um número:
// por exemplo, transforma o caractere '0' no inteiro 0
// (48 eh a posição de '0' na tabela ASCII)
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig13 = '0';
            } else {
                dig13 = (char) ((11 - r) + 48);
            }

// Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig14 = '0';
            } else {
                dig14 = (char) ((11 - r) + 48);
            }

// Verifica se os dígitos calculados conferem com os dígitos informados.
            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static String getMensagemErro(Exception e) {

        while (e.getCause() != null) {
            e = (Exception) e.getCause();
        }

        String retorno = e.getMessage();
        if (retorno.contains("foreign key")) {

            retorno = "Registro nao pode ser excluido, possui referencia no sistema";
        }

        return retorno;
    }

}
