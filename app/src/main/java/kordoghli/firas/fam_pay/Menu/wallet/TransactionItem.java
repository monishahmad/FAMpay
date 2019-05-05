package kordoghli.firas.fam_pay.Menu.wallet;

public class TransactionItem {
    private String sender,receiver,ammount,date;

    public TransactionItem(String sender, String receiver, String ammount, String date) {
        this.sender = sender;
        this.receiver = receiver;
        this.ammount = ammount;
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TransactionItem{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", ammount='" + ammount + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
