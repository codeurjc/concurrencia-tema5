package ejercicio.ejer_t5_descarga;

public class DownloadedWeb {

	public String url;
	public String webContent;
	public Exception exception;

	public DownloadedWeb(String url, String webContent) {
		super();
		this.url = url;
		this.webContent = webContent;
	}
	
	public DownloadedWeb(String web, Exception exception) {
		super();
		this.url = web;
		this.exception = exception;
	}

	public String getUrl() {
		return url;
	}

	public String getWebContent() {
		return webContent;
	}
	
	public Exception getException() {
		return exception;
	}
}
