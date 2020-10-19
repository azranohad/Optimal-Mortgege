import java.util.ArrayList;
import java.util.List;

public class MainCheck {


    public static void main(String[] args) throws Exception {

/*        int mortgage = 300000;
        int nper = 348;
        double percentage = 0.53;
        int rateCustomer = 4; // 1 is better.*/

        int property = 3800000;
        int mortgage = 2000000;
        double firstMonthlyRepayment = 8000;
        double maxMonthlyRepaymentWorst = 15000;
        double primeDecrease = 0.6;
        double diffNperFactor = 0.3;
        int rateCustomer = 6; // 1 is better.

        //setting.
        double sizeOfJump = 0.05;
        double minimumKalatz = 0.1;
        double maximumVar5 = 0.4;
        double maximumCPI = 0.5;
        double maximumNonPrime = 0.7;
        CustomerData customerData = new CustomerData( property, mortgage , firstMonthlyRepayment, maxMonthlyRepaymentWorst, rateCustomer,primeDecrease ,diffNperFactor);

        Setting setting = new Setting( sizeOfJump, minimumKalatz, maximumVar5, maximumCPI, maximumNonPrime);
        Constraints constraints = new Constraints( customerData, setting );
        DataList dataList = new DataList();



/*        MixBase mixBase = new MixBase("check", customerData, 0.35, 0.35, 0, 0, dataList);
        mixBase.optimizationAllRoute();
        mixBase.printData();
        mixBase.printRouteData();*/

/*        ScenarioGenerator allRoute = new ScenarioGenerator( constraints );
        allRoute.result5Routes();*/

        PrimeRoute primeRoute = new PrimeRoute(600000, 360,customerData, "prime"  );
        primeRoute.calculate( 2, dataList );
        primeRoute.calculate( 3, dataList );
        primeRoute.printData();
/*        MixBase var5withKatz = new MixBase("var 5 with katz", customerData, 0.25, 0.2,
                0.15, 0.1, primeDecrease, dataList);
        var5withKatz.optimizationAllRoute();

        var5withKatz.printData();
        var5withKatz.printRouteData();*/
    }
}
