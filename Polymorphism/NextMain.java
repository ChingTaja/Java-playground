package Polymorphism;

public class NextMain {

    public static void main(String[] args) {

        Movie movie = Movie.getMovie("A", "Jaws");
        movie.watchMovie();

        // Adventure jaws = Movie.getMovie("A" , "Jaws") --> Complier 會報錯 , 編譯器很死板～他只看方法的「宣告回傳型別」（即 Movie）
        Adventure jaws = (Adventure) Movie.getMovie("A", "Jaws");
        jaws.watchMovie();

        Object comedy = Movie.getMovie("C", "Airplane");
        Comedy comedyMovie = (Comedy) comedy;
        comedyMovie.watchComedy();

        var airplane = Movie.getMovie("C", "Airplane");
        airplane.watchMovie();

        var plane = new Comedy("Airplane");
        plane.watchComedy();

        Object unknownObject = Movie.getMovie("C", "Airplane");

        if (unknownObject.getClass().getSimpleName() == "Comedy") {
            Comedy c = (Comedy) unknownObject;
            c.watchComedy();
        } else if (unknownObject instanceof Adventure) {
            ((Adventure) unknownObject).watchAdventure();
        } else if (unknownObject instanceof ScienceFiction syfy) {
            syfy.watchScienceFiction();
        }
    }

    
}

/*

1. 編譯器的「盲區」

- 靜態檢查：
編譯器只檢查「宣告型別」
如果方法宣告回傳 `Movie`，你就不能直接把它存進 `Adventure` 變數，即使你「確定」它回傳的是冒險片
- 不預測執行結果：編譯器不會去執行 `switch` 或任何邏輯來確認物件型別

2. 強制轉型 (Casting) 的風險

- 騙過編譯器：轉型是開發者對編譯器的承諾
- 執行時期錯誤：如果承諾破滅（例如你想把 `Comedy` 當成 `Adventure` 處理），程式會噴出 `ClassCastException` 然後直接當機
這比編譯錯誤更糟糕
因為它發生在使用者手上

3. `Object` 參考變數的限制

- 萬物皆 Object：任何物件都可以存放在 `Object` 型別的變數中
- 喪失特質：一旦宣告為 `Object`
編譯器就只允許你呼叫 `Object` 類別的方法（如 `toString`），即使該物件內部確實有 `watchMovie`，編譯器也「看不見」


 4. 參考型別決定「可視範圍」

這是本段最重要的觀念：＊＊編譯器只看變數型別，不看物件內容＊＊

- 如果你把 `Comedy` 物件存進 `Object` 變數，你就只能用 `Object` 的功能（如 `equals`）
- 如果你把 `Comedy` 物件存進 `Movie` 變數，你只能用 `Movie` 的功能（如 `watchMovie`），看不見 `watchComedy`
- 結論：參考型別越通用（越往父類別靠攏），你能直接呼叫的方法就越少

5.  過度通用的代價：頻繁轉型 (Excessive Casting)

- 為了呼叫子類別特有的方法（例如 `watchComedy`），你必須不斷地進行強制轉型：`(Comedy) myObject`
- 這會導致程式碼變得冗長且難以閱讀，並增加執行時期出錯 ClassCastException的風險
- 保持強型別：Java 依然是強型別語言，`var` 只是讓編譯器幫你寫出型別名稱，並不會變成像 JavaScript 那樣隨意變動型別

*/