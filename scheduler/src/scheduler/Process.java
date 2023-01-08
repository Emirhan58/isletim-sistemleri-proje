package scheduler;

import java.io.IOException;

/**
 * Prosesleri betimlemek için kullanılır.
 */
public class Process {
	public int Identity; // Tanımlayıcı kimlik numarası.
	public int Priority; // Öncelik bilgisi.
	public float Birth; // Başlangıç zamanı.
	public float Work; // Tamamlanmaya kalan zaman.
	public float Total; // Yürürlülükte olduğu toplam zaman.
	public Color Marker; // Özgün renk bilgisi.

	public Process(int Counter, int Priority, float Birth, float Work) {
		this.Identity = Counter + 1000;
		this.Priority = Priority;
		this.Birth = Birth;
		this.Work = Work;
		this.Marker = new Color();
	}

	/**
	 * Prosesin hazır olup olmadığını belirtir.
	 * 
	 * @param Time Zaman.
	 * @return Hazırsa 'true' değilse 'false'.
	 */
	public boolean Ready(float Time) {
		return Time >= Birth;
	}

	/**
	 * Prosesin tamamlanıp tamamlanmadığını belirtir.
	 * 
	 * @return Tamamlandıysa 'true' tamamlanmadıysa 'false'
	 */
	public boolean Done() {
		return Work <= 0;
	}

	/**
	 * Prosesin belirtilen zaman diliminde zaman aşımına uğrayıp uğramadığını
	 * belirtir.
	 * 
	 * @param Time  Zaman.
	 * @param Limit Zaman aşımı süresi.
	 * @return
	 */
	public boolean Dead(float Time, float Limit) {
		boolean Result = Time - Birth >= Limit;

		if (Result) {
			_Dead(Time);
			Work = 0;
		}

		return Result;
	}

	/**
	 * Prosesin yürütülmesini sağlar.
	 * 
	 * @param Time     Zaman.
	 * @param Duration Yürürlülük süresi.
	 * @param Alone    Kendi öncelik seviyesinde yalnız olup olmadığı.
	 * @return Eğer tamamlandıysa 'true' tamamlanmadıysa 'false'.
	 */
	public boolean Service(float Time, float Duration, boolean Alone) {
		if (!Ready(Time)) {
			return false;
		}

		Work -= Duration;

		boolean Return;

		if (Done()) {
			if (Total == 0) {
				_Started(Time);
			} else {
				_Processing(Time);
			}

			_Ended(Time);

			Return = true;
		} else {
			if (Total == 0) {
				_Started(Time);
			} else {
				_Processing(Time);
			}

			if (Priority != 0 && !Alone) {
				_Paused(Time);
			}

			Return = false;
		}

		Total += Duration;

		return Return;
	}

	/**
	 * Prosesin başladığını belirtir.
	 * 
	 * @param Time Zaman.
	 */
	private void _Started(float Time) {
		System.out.print(Marker.Get());
		System.out
				.println(String.format("%06.3fs proses başladı.        	(id: %04d  öncelik: %02d  kalan süre: %2.3fs)",
						Time, Identity, Priority, Work + 1f));
		System.out.print(Marker.Reset);
	}

	/**
	 * Prosesin bittiğini belirtir.
	 * 
	 * @param Time Zaman.
	 */
	private void _Ended(float Time) {
		System.out.print(Marker.Get());
		System.out
				.println(String.format("%06.3fs proses sonlandı.       	(id: %04d  öncelik: %02d  kalan süre: %2.3fs)",
						Time + 1f, Identity, Priority, Work));
		System.out.print(Marker.Reset);
	}

	/**
	 * Prosesin yürürlülükte olduğunu belirtir.
	 * 
	 * @param Time Zaman.
	 */
	private void _Processing(float Time) {
		System.out.print(Marker.Get());
		System.out
				.println(String.format("%06.3fs proses yürütülüyor.    	(id: %04d  öncelik: %02d  kalan süre: %2.3fs)",
						Time, Identity, Priority, Work + 1f));
		System.out.print(Marker.Reset);
	}

	/**
	 * Prosesin askıya alındığını belirtir.
	 * 
	 * @param Time Zaman.
	 */
	private void _Paused(float Time) {
		System.out.print(Marker.Get());
		System.out
				.println(String.format("%06.3fs proses askıda.         	(id: %04d  öncelik: %02d  kalan süre: %2.3fs)",
						Time + 1f, Identity, Priority, Work));
		System.out.print(Marker.Reset);
	}

	/**
	 * Prosesin zaman aşımına uğradığını belirtir.
	 * 
	 * @param Time Zaman.
	 */
	private void _Dead(float Time) {
		System.out.print(Marker.Get());
		System.out.println(
				String.format("%06.3fs proses zaman aşımına uğradı.	(id: %04d  öncelik: %02d  kalan süre: %2.3fs)",
						Time + 1f, Identity, Priority, Work));
		System.out.print(Marker.Reset);
	}
}
