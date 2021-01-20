/** @author Amihai Kevin*/
public class RMS {
    public static void main(String[] args) {
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

        for (int n = 1; n < 375; n++) {
            double abe = 750 + Math.random() * 300;
            puls[n] = abe;
            hej = puls[n];
            alle += Math.pow(puls[n-1] - puls[n], 2);
        }


        //System.out.println(Arrays.toString(puls));
        double beregn = Math.sqrt(alle / (375));

        //double hr = 1/beregn*60;
        //System.out.println(Arrays.toString(puls));
        //System.out.println(gennesnitsHRV);
        //System.out.println(alle);
        System.out.println(beregn);
    }
}
