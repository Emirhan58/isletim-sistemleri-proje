package scheduler;

import java.io.IOException;

public class App {

	/**
	 * Kullanım bilgisini belirtir.
	 */
	public static void Show() {
		System.out.println("KULLANIM:");
		System.out.println("	java -jar Scheduler.jar <SEÇENEKLER>");
		System.out.println("SEÇENEKLER:");
		System.out.println("    -h, --help     Yardım diyaloğunu gösterir.");
		System.out.println("    -f, --file     İçe aktarılacak dosyayı belirtir.");
	}

	/**
	 * Giriş fonksiyonu. Program başladığında çalıştırılır.
	 * @param Arguments Komut satırı argümanları.
	 */
	public static void main(String[] Arguments) {
		try {
			java.lang.Process process = Runtime.getRuntime().exec("reg add HKEY_CURRENT_USER\\Console /v VirtualTerminalLevel /t REG_DWORD /d 0x00000001 /f");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scheduler Dispatcher = new Scheduler();

		if (Arguments.length < 1) {
			Show();
			System.exit(1);
		}

		/**
		 * Komut satırı argümanlarını değerlendirir.
		 */
		switch (Arguments[0]) {
		case "-f":
		case "--file":
			if (Arguments.length > 1) {
				Dispatcher.Import(Arguments[1]);
				Dispatcher.Run();
			} else {
				Show();
			}
			break;

		case "-h":
		case "--help":
		default:
			Show();
			break;
		}
	}
}
