package scheduler;

import java.util.Random;

public class Color {
	public int Red, Green, Blue;
	
	public final String Reset = "\u001B[0m";
	
	public Color() {
		Random Generator = new Random();
		
		Red = Generator.nextInt(64, 255);
		Green = Generator.nextInt(64, 255);
		Blue = Generator.nextInt(64, 255);
	}
	
	/**
	 * ANSI renk kodunu oluşturur.
	 * @return ANSI renk kodu.
	 */
	public String Get() {
		String Temporary = new String();
		
		Temporary = String.format("\033[38;2;%d;%d;%dm", Red, Green, Blue);
		
		return Temporary;
	}
}
