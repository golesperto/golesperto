package com.sdp.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GxCreatorForm {

	public static final String DIRETORIO = "C:\\Users\\mathe\\git\\searchdrinkprice\\SearchDrinkPrice\\WebContent\\pages\\";

	public static final String TEMPLATE = DIRETORIO + "templateGenerator.xhtml";

	public static final String OUTPUT = DIRETORIO + "empresaproduto.xhtml";

	public static void main(String[] args) {
		try {
			String mb = "empresaProdutoMB";
			String entidade = "empresaProduto";
			String id = "eprIntCod";
			String label = "Valor";
			// Copy SQL IN no Squirrel
			String nome = "eprFltValor";
			String tamanho = "150";
			String form = GxCreatorForm.criarFormulario(mb, nome, tamanho, label);
			String colunas = GxCreatorForm.criarColunas(nome, label);
			String template = GxCreatorForm.getStringTemplate();
			template = template.replaceAll("#FORM", form);
			template = template.replaceAll("#COLUNAS", colunas);
			template = template.replaceAll("#MB", mb);
			template = template.replaceAll("#ENTIDADE", entidade);
			template = template.replaceAll("#ID", id);
			GxCreatorForm.salvarArquivo(template);
			System.out.println(template);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void salvarArquivo(String conteudo) throws Exception {
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT), "UTF-8"));
		out.write(conteudo.toCharArray());
		out.close();
	}

	public static String getStringTemplate() throws Exception {
		return new String(Files.readAllBytes(Paths.get(TEMPLATE)));
	}

	public static String criarFormulario(String mb, String nome, String tamanho, String label) {
		String[] nomes = nome.split(",");
		String[] tamanhos = tamanho.split(",");
		String[] labels = label.split(",");
		String out = "";
		for (int i = 0; i < nomes.length; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append("            <h:panelGroup styleClass=\"md-inputfield MarTop18\"> \n");
			sb.append("                 <p:inputText id=\"xxx\" value=\"#{" + mb
					+ ".entity.xxx}\" required=\"true\" maxlength=\"yyy\" styleClass=\"Wid100Percent\" /> \n");
			sb.append("                 <p:outputLabel for=\"xxx\" value=\"zzz\" /> \n");
			sb.append("             </h:panelGroup> \n");
			String n = nomes[i];
			n = n.toLowerCase();
			out += sb.toString().replaceAll("xxx", n).replaceAll("yyy", tamanhos[i]).replaceAll("zzz",
					labels[i].trim());
		}
		return out;
	}

	public static String criarColunas(String nome, String label) {
		String[] nomes = nome.split(",");
		String[] labels = label.split(",");
		String out = "";
		for (int i = 0; i < nomes.length; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append("                     <p:column filterBy=\"#{objeto.xxx}\" filterMatchMode=\"contains\" headerText=\"yyy\" sortBy=\"#{objeto.xxx}\"> \n");
			sb.append("                         <h:outputText value=\"#{objeto.xxx}\" /> \n");
			sb.append("                     </p:column> \n");
			String n = nomes[i];
			n = n.toLowerCase();
			out += sb.toString().replaceAll("xxx", n).replaceAll("yyy", labels[i].trim());
		}
		return out;
	}
}
