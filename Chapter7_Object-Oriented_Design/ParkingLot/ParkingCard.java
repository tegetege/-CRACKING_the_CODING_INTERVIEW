package parkingLot;
import framework.*;
import java.util.Date;

public class ParkingCard extends Product {
    private Date entryTime;
    private Date exitTime;
    private int payment;

    ParkingCard(){
        System.out.println("パーキングカードを発券します");
    }

    public void entry() {
        System.out.println("パーキングに入場しました");
        entryTime = new Date();
    }

    public void exit() {
        System.out.println("パーキングに退場しました");
        exitTime = new Date();
        // 料金計算
        calcuration();
    }

    private void calcuration() {
        payment = exitTime - entryTime;
        System.out.println("料金は " + str(payment) + "円です。");
    }


}