package admin.pubbs.in.pubbsadminnew;
/*created by Parita Dey*/

public class UserProfileWalletList {
    private String dateTime, debit, credit;

    public UserProfileWalletList() {

    }

    public UserProfileWalletList(String dateTime, String debit, String credit) {
        this.dateTime = dateTime;
        this.debit = debit;
        this.credit = credit;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getDebit() {
        return debit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCredit() {
        return credit;
    }
}
