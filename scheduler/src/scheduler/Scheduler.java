package scheduler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {
	private List<Queue> Stack; // Öncelik sıralarının listesi.
	private float Elapsed; // Başlangıçtan beri geçen süre.
	private float Difference; // Son döngüden beri geçen süre.
	private float Quantum; // Görevlendirici kuantumu.
	private float Limit; // Zaman aşımı limiti.
	private int Tasks; // Toplam proses sayısı.

	public Scheduler() {
		Quantum = 1f;
		Limit = 80f;
		Elapsed = 0f;
		Difference = 0f;
		Tasks = 0;
		Stack = new ArrayList<Queue>();
	}

	/**
	 * Görevlendiricinin ana döngüsü. Proseslerin yerleştirilmesi ve
	 * çalıştırılmasından sorumlu.
	 */
	public void Run() {
		Process Temporary;
		boolean Done, Alone;

		while (Tasks > 0) {
			Difference = Elapsed;

			// Gerçek zaman önceliğine sahip prosesler özel olarak değerlendirilir.

			if (Stack.get(0).Size() != 0) {
				Alone = Stack.get(0).Size() == 1;
				Temporary = Stack.get(0).Seek(0);

				Done = Temporary.Service(Elapsed, Quantum, Alone);

				if (Done) {
					Stack.get(0).Pick();
					Tasks--;
				}

				Elapsed += Quantum;
				continue;
			}

			// Gerçek zamanlı öncelik sırası dışındaki bütün öncelikleri değerlendirir.
			for (int Depth = 1; Depth < Stack.size(); Depth++) {
				for (int Index = 0; Index < Stack.get(Depth).Size(); Index++) {
					Alone = Stack.get(Depth).Size() == 1;
					Temporary = Stack.get(Depth).Pick();

					if (Temporary.Dead(Elapsed, Limit)) {
						Tasks--;
						continue;
					}

					Done = Temporary.Service(Elapsed, Quantum, Alone);

					Elapsed += Quantum;

					if (!Done) {
						Stack.get(Depth).Put(Temporary);
					} else {
						Tasks--;
					}
				}
			}

			// Eğer hiçbir işlem gerçekleştirilmediyse bir saniye bekler.

			if (Difference == Elapsed) {
				Elapsed += Quantum;
			}
		}
	}

	/**
	 * Bir metin dosyasından proses listesini içe aktarır.
	 * 
	 * @param Name Dosya ismi.
	 */
	public void Import(String Name) {
		try {
			String Data = Files.readString(Paths.get(Name));

			String[] Lines = Data.split("\n");

			int Counter = 0;
			for (String Line : Lines) {
				Extract(Counter++, Line);
			}

		} catch (Exception E) {
			E.printStackTrace();
			System.exit(2);
		}
	}

	/**
	 * Bir satırından proses içe aktarır.
	 * 
	 * @param Counter Kaç proses olduğunu belirtir.
	 * @param Text    Çözümlenecek metin.
	 * @throws ParseException
	 */
	private void Extract(int Counter, String Text) throws ParseException {
		String[] Parts = Text.split(",");

		if (Parts.length < 3) {
			throw new ParseException("Hatalı girdi tespit edildi!", 0);
		}

		int Priority = Integer.parseInt(Parts[1].replace(" ", ""));
		float Birth = Float.parseFloat(Parts[0].replace(" ", ""));
		float Work = Float.parseFloat(Parts[2].replace(" ", ""));

		Process Temporary = new Process(Counter, Priority, Birth, Work);

		while (Stack.size() <= Priority) {
			Stack.add(new Queue());
		}

		Stack.get(Priority).Put(Temporary);
		Tasks++;
	}
}
