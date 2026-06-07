package StreamIntermediate;

// 使用  Record 來快速定義不變的座位資料結構
public record Seat(char rowMarker, int seatNumber, boolean isReserved) {

    // 自訂的精簡建構子（Custom Constructor）
    public Seat(char rowMarker, int seatNumber) {
        // 三種實驗切換：
        // 實驗 A：this(rowMarker, seatNumber, new java.util.Random().nextBoolean()); ->
        // 隨機預約 (hasBookings=true, fullyBooked=false, eventWashedOut=false)

        // 實驗 B：this(rowMarker, seatNumber, true); -> 全部客滿 (hasBookings=true,
        // fullyBooked=true, eventWashedOut=false)

        // 實驗 C：this(rowMarker, seatNumber, false); -> 毫無預約 (hasBookings=false,
        // fullyBooked=false, eventWashedOut=true)

        this(rowMarker, seatNumber, false);
    }
}