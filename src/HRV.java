import java.util.Arrays;

public class HRV {


    public static void main(String[] args) {
        double log = 0;

        for (int i = 0; i < 288; i++) {
            double alle = 0;
            double gennemsnit;
            double gennem = 0;
            int hej = 375;
            int standardR = 600;
            int randomR = 200;
            int randomRG= 200;

            double[] gen = new double[hej];
            double[] puls = new double[hej];
            for (int n = 0; n < hej; n++) {
                gennemsnit = standardR + Math.random() * randomR + Math.random()*randomR;
                gen[n] = gennemsnit;
                gennem += gen[n];
            }
            double gennesnitsHRV = gennem / hej;

            for (int n = 0; n < hej; n++) {
                double abe = standardR + Math.random() * randomR+ Math.random()*randomR;
                puls[n] = abe;
                alle += Math.pow(puls[n] - gennesnitsHRV, 2);
            }


            //System.out.println(Arrays.toString(puls));
            double beregn = Math.sqrt(alle / (hej - 1));
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

