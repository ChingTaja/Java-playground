/*
# 絕對、永遠不要使用『可變物件（Mutable Object）』作為 Map 的 Key！

想讓這段程式碼安全無虞，有兩個做法：

1. **Key 改用不可變物件（Immutable）**：將 `Map<StringBuilder, Integer>` 改為 `Map<String, Integer>`
2. 因為 `String` 具備天然不可變性，誰都無法在半路串改它
    
2. **清洗時進行防禦性複製（Defensive Copying）**：在 `standardizeNames` 方法中，不要直接對傳入的 `name` 進行 `replace`，而是應該先 `new StringBuilder(name.toString())` 複製一份全新的複本進行清洗，如此一來，副作用便永遠無法波及到 Map 的 Key
*/
package finalExplore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainMailer {

	public static void main(String[] args) {

		String[] names = {
				"Ann Jones",
				"Ann Jones Ph.D.",
				"Bob Jones M.D.",
				"Carol Jones",
				"Ed Green Ph.D.",
				"Ed Green M.D.",
				"Ed Black"
		};

		// =====================================================
		// 1. 建立原始名單
		// =====================================================
		//
		// 注意：
		// List 裡裝的是可變物件 StringBuilder
		//
		List<StringBuilder> population = getNames(names);

		// =====================================================
		// 2. 建立統計 Map
		// =====================================================
		//
		// ⚠️ 危險設計：
		// 使用可變物件（Mutable Object）當作 TreeMap Key
		//
		// TreeMap 依賴 Key 的內容進行排序，
		// 一旦 Key 被修改，
		// 整個紅黑樹結構可能遭到破壞。
		//
		Map<StringBuilder, Integer> counts = new TreeMap<>();

		population.forEach(name -> counts.merge(name, 1, Integer::sum));

		System.out.println(counts);

		StringBuilder annJonesPhd = new StringBuilder("Ann Jones Ph.D.");

		System.out.println(
				"There are "
						+ counts.get(annJonesPhd)
						+ " records for "
						+ annJonesPhd);

		// =====================================================
		// 核心引爆點
		// =====================================================
		//
		// standardizeNames()
		//
		// 看似只是清洗資料，
		// 實際上直接修改了 TreeMap Key 本體。
		//
		List<StringBuilder> cleanedNames = standardizeNames(population);

		System.out.println(cleanedNames);

		// =====================================================
		// 靈異現象 #1
		// =====================================================
		//
		// 為什麼變成 null？
		//
		// 因為原本的：
		//
		// "Ann Jones Ph.D."
		//
		// 已經被改成：
		//
		// "Ann Jones"
		//
		// TreeMap 的搜尋路徑失效。
		//
		System.out.println(
				"There are "
						+ counts.get(annJonesPhd)
						+ " records for "
						+ annJonesPhd);

		System.out.println(counts);

		// =====================================================
		// 靈異現象 #2
		// =====================================================
		//
		// Map 裡竟然出現重複 Key：
		//
		// Ann Jones
		//
		// 這理論上不應該發生。
		//
		// 原因：
		// TreeMap 節點建立完成後，
		// Key 內容被偷偷修改。
		//
		// 導致排序規則被破壞。
		//
		StringBuilder annJones = new StringBuilder("Ann Jones");

		// =====================================================
		// 靈異現象 #3
		// =====================================================
		//
		// get() 只能找到其中一筆。
		//
		// 另一筆已經「迷失」在紅黑樹中。
		//
		System.out.println(
				"There are "
						+ counts.get(annJones)
						+ " records for "
						+ annJones);

		// =====================================================
		// 實驗 A
		// =====================================================
		//
		// forEach() 直接遍歷 EntrySet
		//
		// 能看見所有節點。
		//
		System.out.println("-----------------------");

		counts.forEach(
				(k, v) -> System.out.println(k + " : " + v));

		// =====================================================
		// 實驗 B
		// =====================================================
		//
		// keySet() + get(k)
		//
		// 重新走搜尋流程，
		// 可能找到錯誤節點。
		//
		System.out.println("-----------------------");

		counts.keySet().forEach(
				k -> System.out.println(
						k + " : " + counts.get(k)));
	}

	/**
	 * 產生測試資料
	 *
	 * 故意製造不同數量的重複姓名。
	 */
	private static List<StringBuilder> getNames(String[] names) {

		List<StringBuilder> list = new ArrayList<>();

		int index = 3;

		for (String name : names) {

			for (int i = 0; i < index; i++) {

				// 關鍵：
				// 同一個 StringBuilder 物件
				// 之後會同時存在於：
				//
				// 1. population
				// 2. TreeMap Key
				//
				list.add(new StringBuilder(name));
			}

			index++;
		}

		return list;
	}

	/**
	 * 移除學位稱謂
	 *
	 * ⚠️ 此方法包含嚴重副作用（Side Effect）
	 */
	private static List<StringBuilder> standardizeNames(
			List<StringBuilder> list) {

		List<StringBuilder> newList = new ArrayList<>();

		for (StringBuilder name : list) {

			for (String suffix : new String[] {
					"Ph.D.",
					"M.D."
			}) {

				int startIndex;

				if ((startIndex = name.indexOf(suffix)) > -1) {

					// ❌ 危險操作
					//
					// StringBuilder.replace()
					//
					// 直接修改原物件內容
					//
					// 若該物件同時是 TreeMap Key，
					// 就會破壞 TreeMap 的排序結構。
					//
					name.replace(
							startIndex - 1,
							startIndex + suffix.length(),
							"");
				}
			}

			newList.add(name);
		}

		return newList;
	}
}