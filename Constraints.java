/**
 * All the constraints of the program.
 */

public class Constraints {
    private CustomerData customerData;
    private Setting setting;


    public Constraints(CustomerData customerData, Setting setting) {
        this.customerData = customerData;
        this.setting = setting;
    }

    public CustomerData getCustomerData() {
        return customerData;
    }

    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
}
