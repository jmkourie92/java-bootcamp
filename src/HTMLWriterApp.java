public class HTMLWriterApp {

	static HTMLWriter hw = new HTMLWriter("webpage.html");		
			
	public static void main(String[] args) {
		
		hw.StartTag("html");
		hw.StartTag("body");
		hw.StartTag("p");
		hw.getConnection();
		hw.fillBodyTag();
		hw.EndTag();
		hw.EndTag();
		hw.EndTag();
		hw.CloseFile();
		}
}
