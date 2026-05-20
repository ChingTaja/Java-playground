當你呼叫 fullSet.descendingSet()、headSet()、tailSet() 或 subSet() 時
Java 為了節省記憶體與提高效能
並不會複製一份新的資料出來

雙向連動：不論你是動了原集合，還是動了這個反向集合（descendingSet）
因為它們底層共享同一份記憶體資料，所以兩邊的變更都會即時同步