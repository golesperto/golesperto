package com.sdp.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.sdp.entity.Produto;

public class Utils {
	private static Logger log = Logger.getLogger(Utils.class);

	public static final String JANEIRO = "01";

	public static final String FEVEREIRO = "02";

	public static final String MARCO = "03";

	public static final String ABRIL = "04";

	public static final String MAIO = "05";

	public static final String JUNHO = "06";

	public static final String JULHO = "07";

	public static final String AGOSTO = "08";

	public static final String SETEMBRO = "09";

	public static final String OUTUBRO = "10";

	public static final String NOVEMBRO = "11";

	public static final String DEZEMBRO = "12";

	public static final String TAG_I = "<i class=\"[icone]\" aria-hidden=\"true\"></i>";

	public static final String HTML_DISPONIVEL = "<span style=\"color: #00FF00; font-weight: bold;\">Dispon�vel</span>";

	public static final String HTML_NAO_DISPONIVEL = "<span style=\"color: #FF0000 font-weight: bold;\">Sem Estoque</span>";

	public static String adicionaZeroEsquerda(int tamanhoTotal, Number campo) {
		int tamanho = tamanhoTotal - campo.toString().length();
		String result = "";
		for (int i = 0; i < tamanho; i++) {
			result = "0" + result;
		}
		result = result + campo.toString();
		return result;
	}

	public static StreamedContent getStreamedContent(byte[] bytes, String tipoImagem) {
		if (bytes != null && bytes.length > 0) {
			return new DefaultStreamedContent(new ByteArrayInputStream(bytes), "image/" + tipoImagem);
		} else {
			return null;
		}
	}

	public static boolean isNotNullOrNotEmpty(String string) {
		if (string != null && !string.isEmpty()) {
			return true;
		}
		return false;
	}

	public static StreamedContent getImageDefault() throws Exception {
		BufferedImage image = ImageIO.read(Produto.class.getResourceAsStream("image/sem-foto.jpg"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		return Utils.getStreamedContent(imageInByte, "jpg");
	}

	public static String getIconeImagem(String iconeFonte) {
		return TAG_I.replace("[icone]", iconeFonte);
	}

	public static String formataPorcentagem(Number valor) {
		NumberFormat nf = DecimalFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		return nf.format(valor);
	}

	public static String adicionaZeroEsquerda(String linha) {
		return String.format("%014d", Long.parseLong(linha));
	}

	public static String formataCNPJ(String linha) {
		return format("##.###.###/####-##", adicionaZeroEsquerda(linha));
	}

	public static String formataDocumento(String linha) {
		if (linha.length() == 11) {
			return formataCPF(linha);
		} else {
			if (linha.contains(".") || linha.contains("/")) {
				return linha;
			} else {
				return formataCNPJ(linha);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(Calendar.MONDAY);
		System.out.println(Calendar.TUESDAY);
		System.out.println(Calendar.WEDNESDAY);
		System.out.println(Calendar.THURSDAY);
		System.out.println(Calendar.FRIDAY);
		System.out.println(Calendar.SATURDAY);
		System.out.println(Calendar.SUNDAY);
	}

	public static String formataCPF(String linha) {
		return format("###.###.###-##", linha);
	}

	public static String formataCEP(String linha) {
		return format("##.###-###", linha);
	}

	public static String getDescricaoMes(Date date) {
		String string = dateToString(date, "dd/MM/yyyy");
		string = string.substring(0, 2) + "/" + encontraMes(string.substring(3, 5)) + "/" + string.substring(6);
		return string;
	}

	public static List<Number> getDivisaoCorChartByValorFinal(Double valorFinal) {
		List<Number> intervalos = new ArrayList<Number>();
		double valor = valorFinal / 20;
		intervalos.add(valor);
		valor = valorFinal / 9;
		intervalos.add(valor);
		valor = valorFinal / 5;
		intervalos.add(valor);
		valor = valorFinal / 3;
		intervalos.add(valor);
		return intervalos;
	}

	private static String encontraMes(String date) {
		switch (date) {
		case JANEIRO:
			return "JAN";
		case FEVEREIRO:
			return "FEV";
		case MARCO:
			return "MAR";
		case ABRIL:
			return "ABR";
		case MAIO:
			return "MAI";
		case JUNHO:
			return "JUN";
		case JULHO:
			return "JUL";
		case AGOSTO:
			return "AGO";
		case SETEMBRO:
			return "SET";
		case OUTUBRO:
			return "OUT";
		case NOVEMBRO:
			return "NOV";
		case DEZEMBRO:
			return "DEZ";
		default:
			return null;
		}
	}

	public static String format(String pattern, Object value) {
		MaskFormatter mask;
		try {
			mask = new MaskFormatter(pattern);
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object getValorParametro(String parametro) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parametro);
	}

	public static String getAnoMes(Date initialDate, Date finalDate) {
		Calendar ini = Calendar.getInstance();
		ini.setTime(initialDate);
		Calendar fim = Calendar.getInstance();
		fim.setTime(finalDate);
		String datas = "";
		while (true) {
			datas += ", '" + ini.get(Calendar.YEAR) + (ini.get(Calendar.MONTH) + 1) + "'";
			if (ini.get(Calendar.MONTH) == Calendar.DECEMBER) {
				ini.add(Calendar.YEAR, +1);
				ini.set(Calendar.MONTH, (Calendar.JANUARY) - 1);
			}
			if (ini.get(Calendar.MONTH) == fim.get(Calendar.MONTH)
					&& ini.get(Calendar.YEAR) == fim.get(Calendar.YEAR)) {
				break;
			}
			ini.add(Calendar.MONTH, +1);
		}
		return datas.replaceFirst(", ", "");
	}

	public static String removeZerosAesquerda(String linha) {
		if (linha != null)
			return linha.replaceFirst("0*", "");
		return null;
	}

	public static String informacoesExceptionConcat(Exception e, int concat) {
		String exp = ExceptionUtils.getStackTrace(e);
		return split(exp != null ? exp : "", concat);
	}

	public static Calendar zeraDatas(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static int deductDates(Date initialDate, Date finalDate) {
		if (initialDate == null || finalDate == null) {
			return 0;
		}
		int days = (int) ((finalDate.getTime() - initialDate.getTime()) / (24 * 60 * 60 * 1000));
		return (days > 0 ? days : 0);
	}

	public static int diferencaEmDias(Date dataInicial, Date dataFinal) {
		Long diferenca = dataFinal.getTime() - dataInicial.getTime();
		Double diferencaEmDias = (double) ((diferenca / 1000) / 60 / 60 / 24);
		return diferencaEmDias.intValue();
	}

	public static String split(String s, int t) {
		return s != null && s.length() > t ? s.substring(0, t) : s;
	}

	public static String dateToString(Date data, String pattern) {
		String dataStr = null;
		if (data != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			dataStr = sdf.format(data);
		}
		return dataStr;
	}

	public static Date stringToDate(String data, String pattern) {
		Date dataRet = null;
		if (data != null && !data.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			try {
				dataRet = sdf.parse(data);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return dataRet;
	}

	public static Date retornaDoisAnos() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -2);
		return c.getTime();
	}

	public static String listToString(List<String> lista) {
		String out = "";
		for (String string : lista) {
			out += "," + string;
		}
		out = out.replaceFirst(",", "");
		return out;
	}

	public static void setDataInicialEFinal(Date inicio, Date fim) {
		Calendar c = Calendar.getInstance();
		fim = c.getTime();
		c.add(Calendar.MONTH, -1);
		inicio = c.getTime();
	}

	public static Date setFimDia(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	public static Date setInicioDia(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 00);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 00);
		return c.getTime();
	}

	public static Date setDiaMenosUm() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}

	public static Date setMesMenosUm() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		return c.getTime();
	}

	public static Date setMesMenosUmDia() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		c.add(Calendar.MONTH, -1);
		return c.getTime();
	}

	private static String arredondaLatLng(double l) {
		String[] split = ("" + l).split("\\.");
		return split[0] + "." + split[1].substring(0, 3);
	}

	public static String removerCaracterEspecial(String string) {
		return string.replaceAll("\\P{Print}", "");
	}

	public static String removerMascaraTelefone(String telefone) {
		telefone = telefone.replace("(", "");
		telefone = telefone.replace(")", "");
		telefone = telefone.replace("-", "");
		telefone = telefone.replace(" ", "");
		return telefone;
	}

	public static String removerMarcaraCPF(String cpf) {
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");
		return cpf;
	}

	public static String removerMarcaraCEP(String cep) {
		cep = cep.replace(".", "");
		cep = cep.replace("-", "");
		return cep;
	}

	public static String substituirCaracterEspecial(String string) {
		return string.replaceAll("[ÃƒÂ£ÃƒÂ¢ÃƒÂ ÃƒÂ¡ÃƒÂ¤]", "a")
				.replaceAll("[ÃƒÂªÃƒÂ¨ÃƒÂ©ÃƒÂ«]", "e")
				.replaceAll("[ÃƒÂ®ÃƒÂ¬ÃƒÂ­ÃƒÂ¯]", "i")
				.replaceAll("[ÃƒÂµÃƒÂ´ÃƒÂ²ÃƒÂ³ÃƒÂ¶]", "o")
				.replaceAll("[ÃƒÂ»ÃƒÂºÃƒÂ¹ÃƒÂ¼]", "u")
				.replaceAll("[ÃƒÆ’Ãƒâ€šÃƒâ‚¬Ãƒï¿½Ãƒâ€ž]", "A")
				.replaceAll("[ÃƒÅ ÃƒË†Ãƒâ€°Ãƒâ€¹]", "E")
				.replaceAll("[ÃƒÅ½ÃƒÅ’Ãƒï¿½Ãƒï¿½]", "I")
				.replaceAll("[Ãƒâ€¢Ãƒâ€�Ãƒâ€™Ãƒâ€œÃƒâ€“]", "O")
				.replaceAll("[Ãƒâ€ºÃƒâ„¢ÃƒÅ¡ÃƒÅ“]", "U").replace("ÃƒÂ§", "c")
				.replace("Ãƒâ€¡", "C").replace("ÃƒÂ±", "n").replace("Ãƒâ€˜", "N").replaceAll("!", "")
				.replaceAll("\\[\\Ã‚Â´\\`\\?!\\@\\#\\$\\%\\Ã‚Â¨\\*", " ")
				.replaceAll("\\(\\)\\=\\{\\}\\[\\]\\~\\^\\]", " ")
				.replaceAll("[\\.\\;\\-\\_\\+\\'\\Ã‚Âª\\Ã‚Âº\\:\\;\\/]", " ").replaceAll(" ", "");
	}
}
