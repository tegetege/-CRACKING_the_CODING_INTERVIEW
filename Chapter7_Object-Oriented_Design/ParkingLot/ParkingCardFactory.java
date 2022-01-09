package parkingLot;
import framework.*;
import java.util.*;

public class ParkingCardFactory extends Factory(int slotCnt) {
    private List slots = new ArrayList();

    protected Product createCard() {
        if (slots.size() <= slotCnt) {
            return new ParkingCard();
        } else {
            System.out.println("ただいま満車です");
            return null;
        }
    }

    // 登録時の処理を入力
    protected void registerCard(ParkingCard parkingcard) {
        parkingcard.entry(); // 入場時間を記録
        slots.add((ParkingCard)parkingcard); // スロットに記録
    }

    // 解除時の処理を入力
    protected void deleteCard() {
        slots.remove(-1);
    }
}

