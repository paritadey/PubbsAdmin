package admin.pubbs.in.pubbsadminnew.List;

public class BankList {
    private String bank_name, branch_name, ifsc, account_number;
    int active;

    public BankList() {
    }

    public BankList(String bank_name, String branch_name, String ifsc, String account_number, int active) {
        this.bank_name = bank_name;
        this.branch_name = branch_name;
        this.ifsc = ifsc;
        this.account_number = account_number;
        this.active = active;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
