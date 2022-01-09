import framework.*;
import parkingLot.*;

public class Main {
    public static void main(int slotCnt){
        // parkingLotのインスタンスを生成する
        Factory factory = new ParkingCardFactory(slotCnt);

        // 3台の車が入場
        Product card1 = factory.createCard();
        Product card2 = factory.createCard();
        Product card3 = factory.createCard();

        // 3台の車が退場
        card1.exit();
        card2.exit();
        card3.exit();


    }
}