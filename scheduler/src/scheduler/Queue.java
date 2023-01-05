package scheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * Proseslerin sıralanması için kullanılır.
 */
public class Queue {
	private List<Process> Stack; // Proseslerin işlem için tutulduğu liste.

	public Queue() {
		Stack = new ArrayList<Process>();
	}

	/**
	 * Prosesi sıranın sonuna yerleştirir.
	 * 
	 * @param Input Hedef proses.
	 */
	public void Put(Process Input) {
		Stack.add(Input);
	}

	/**
	 * Prosesi belirtilen sıraya yerleştirir.
	 * 
	 * @param Index İstenen lokasyon.
	 * @param Input Hedef proses.
	 */
	public void Place(int Index, Process Input) {
		Stack.add(Index, Input);
	}

	/**
	 * Listenin başındaki prosesi döndürür.
	 * @return Hedef proses.
	 */
	public Process Pick() {
		Process Temporary = Stack.get(0);

		Stack.remove(0);

		return Temporary;
	}

	/**
	 * Hedef lokasyondaki prosesi döndürür ve listeden çıkarır.
	 * @param Index Hedef prosesin lokasyonu.
	 * @return Hedef proses.
	 */
	public Process Take(int Index) {
		Process Temporary = Stack.get(Index);

		Stack.remove(Index);

		return Temporary;
	}

	/**
	 * Hedef lokasyondaki prosesi döndürür.
	 * @param Index Hedef prosesin lokasyonu.
	 * @return Hedef proses.
	 */
	public Process Seek(int Index) {
		return Stack.get(Index);
	}

	/**
	 * Sıranın uzunluğunu döndürür.
	 * @return Sıranın uzunluğu.
	 */
	public int Size() {
		return Stack.size();
	}
}
