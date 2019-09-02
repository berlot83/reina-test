package snya.reina.serviciomodelo.movimiento;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class EscritorNarrativoDeHistoria {

	public static String extractoBreve(String textoOriginal) {
		String textoDefinitivo = "";
		Document dom = prepararDOM(textoOriginal);
		
		Element nodo = dom.select("p").first();
		if (nodo != null) textoDefinitivo = nodo.text().substring(0, (nodo.text().length() < 99) ? nodo.text().length() : 99) + "...";
		
		return textoDefinitivo;
	}
	
	
	public String sumarA(String textoOriginal, String id, String texto) {
		String textoDefinitivo = (textoOriginal == null) ? "" : textoOriginal;
		Document dom = prepararDOM(textoDefinitivo);
				
		dom.body().appendElement("p").attr("id", id).text(texto);		
		textoDefinitivo = dom.body().html();
		
		return textoDefinitivo;
	}
	
	public String quitarDe(String textoOriginal, String id) {
		String textoDefinitivo = (textoOriginal == null) ? "" : textoOriginal;
		Document dom = prepararDOM(textoDefinitivo);
				
		dom.body().select("#" + id).remove();
		textoDefinitivo = dom.body().html();
		
		return textoDefinitivo;
	}

	public String reemplazarEn(String textoOriginal, String id, String texto) {
		String textoDefinitivo = (textoOriginal == null) ? "" : textoOriginal;
		Document dom = prepararDOM(textoDefinitivo);
				
		Element nodo = dom.body().select("#" + id).first();
		if (nodo != null) nodo.text(texto);
		textoDefinitivo = dom.body().html();
		
		return textoDefinitivo;
	}
	
	public String concatenar(String textoOriginal, String textoSiguiente) {
		String textoDefinitivo = (textoOriginal == null) ? "" : textoOriginal;
		Document dom = prepararDOM(textoDefinitivo);
		Document domSig = prepararDOM(textoSiguiente);
		
		for (org.jsoup.nodes.Node child : domSig.childNodes()) {
			dom.body().appendChild(child);	
		}
		textoDefinitivo = dom.body().html();
		
		return textoDefinitivo;
	}
	
	
	
	private static Document prepararDOM(String textoDefinitivo) {
		String html = "<html><head></head><body>" + textoDefinitivo + "</body></html>";
		Document dom = Jsoup.parse(html);
		return dom;
	}
}
