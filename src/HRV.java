import java.util.Arrays;

public class HRV {


    public static void main(String[] args) {
        double log = 0;

        for (int i = 0; i < 288; i++) {
            double alle = 0;
            double gennemsnit;
            double gennem = 0;
            double hej = 0;

            double[] gen = new double[375];
            double[] puls = new double[375];
            for (int n = 0; n < 375; n++) {
                gennemsnit = 750 + Math.random() * 300;
                gen[n] = gennemsnit;
                gennem += gen[n];
            }
            double gennesnitsHRV = gennem / 375;

            for (int n = 0; n < 375; n++) {
                double abe = 750 + Math.random() * 300;
                puls[n] = abe;
                hej = puls[n];
                alle += Math.pow(puls[n] - gennesnitsHRV, 2);
            }


            //System.out.println(Arrays.toString(puls));
            double beregn = Math.sqrt(alle / (375 - 1));
            log += beregn;

            //double hr = 1/beregn*60;
            //System.out.println(Arrays.toString(puls));
            //System.out.println(gennesnitsHRV);
            //System.out.println(alle);
            System.out.println(beregn);
            //System.out.println(log);
            //System.out.println(hr);
            //375
        }
        double HRVgen = log/288;
        System.out.println(HRVgen);
    }
}

