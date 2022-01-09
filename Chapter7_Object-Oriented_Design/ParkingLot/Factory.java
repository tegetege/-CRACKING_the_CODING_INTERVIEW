package framework;

public abstract class Factory {
    public final Product createCard() {
        Product p = createCard();
        registerCard();
        return p;
    }

    // parkingCardを登録するときに利用します
    protected abstract void registerCard();

    // parkingCardを削除するときに利用します
    protected abstract void deleteCard();
}