package examples.devrec;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class JaccardSimilarity {
    private final int i;
    private final int j;
    private final double[][] rup;

    /**
     * Compare similarity between user i toward user j.
     *
     * @param i   user to compared
     * @param j   user to compare with
     * @param rup user project relation matrix.
     */
    public JaccardSimilarity(int i, int j, double[][] rup) {
        this.i = i;
        this.j = j;
        this.rup = rup;
    }

    public double val() {
        double[] projectsI = rup[i];
        double[] projectsJ = rup[j];
        double sum = 0d;
        double sampleI = 0d;
        double sampleJ = 0d;
        for (int k = 0; k < projectsI.length; k++) {
            sum += projectsI[k] + projectsJ[k] == 2d ? 1 : 0;
            sampleI += projectsI[k];
            sampleJ += projectsJ[k];
        }
        if (sampleI + sampleJ == 0) {
            return 0;
        } else {
            return sum / (sampleI + sampleJ);
        }
    }
}
