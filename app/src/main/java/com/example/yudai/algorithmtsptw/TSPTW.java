package com.example.yudai.algorithmtsptw;

import java.util.Random;


public class TSPTW {
    public static final int ITERATIONS = 50;
    public static final double MAXVALUE = 10000000.0;
    public static final int N = 20;
    public static double[][] dist = new double[N][N]; // distance matrix
    public static double[] left = new double[N]; // left interval bounds
    public static double[] right = new double[N]; // right inteval bounds
    public final int LEVEL = 2;
    int[] moves = new int[N]; // static arrays
    double[] value = new double[N];
    double[][][] local = new double[LEVEL + 1][N][N];

    long expansions = 0;

    double[][] policy = new double[N][N];
    boolean[] visited = new boolean[N + 1];
    int[] tour = new int[N + 1];
    int tourSize;
    Random random;

    /**
     * Constructor for objects of class TSPTW
     */
    public TSPTW() {
        final double[][] dist = {
                {0, 45.1774, 35.0571, 35, 57.0088, 55.7136, 52.2015, 52,
                        50.2892, 51.4782, 38.0526, 33.541, 26.9258, 39.0512,
                        11.1803, 13.0384, 25.2982, 42.9418, 19.2354, 35.4683},
                {55.1774, 10, 20.198, 20.7703, 69.1692, 64.1202, 64.9181,
                        60.6063, 63.2541, 55.618, 17.2801, 28.6011, 34.4131,
                        35.807, 46.1386, 42.1403, 52.0595, 27.4642, 36.9258,
                        36.1725},
                {45.0571, 20.198, 10, 12, 66.648, 62.2015, 62, 58.3839,
                        60.1597, 54.1475, 13, 23.9284, 25.6205, 33.5372,
                        35.9615, 32.0227, 44.8281, 28.2483, 26.7631, 32.4722},
                {45, 20.7703, 12, 10, 68.5235, 64.1202, 63.8516, 60.2892, 62,
                        56.0977, 13.6056, 25.8114, 24.1421, 35.4951, 35.4951,
                        32.0227, 46.1248, 30.2237, 26.2788, 34.3516},
                {67.0088, 69.1692, 66.648, 68.5235, 10, 15.831, 15, 18.6023,
                        17, 24.1421, 67.7754, 52.72, 75.7647, 43.541, 70.208,
                        64.037, 41.7805, 51.8808, 68.1378, 44.176},
                {65.7136, 64.1202, 62.2015, 64.1202, 15.831, 10, 15.3852, 14,
                        16.4031, 18.6023, 63.1413, 48.3275, 72.2013, 38.7924,
                        67.8705, 61.4782, 40.4631, 46.7151, 65.0091, 39.8329},
                {62.2015, 64.9181, 62, 63.8516, 15, 15.3852, 10, 15.3852, 12,
                        21.1803, 63.2259, 48.0789, 70.8276, 39.1548, 65.2268,
                        59.0408, 36.9258, 47.8021, 63.1507, 39.5466},
                {62, 60.6063, 58.3839, 60.2892, 18.6023, 14, 15.3852, 10, 15,
                        15.831, 59.3964, 44.4819, 68.2151, 35.0799, 63.9351,
                        57.5184, 36.8328, 43.2866, 61.0098, 35.9615},
                {60.2892, 63.2541, 60.1597, 62, 17, 16.4031, 12.0, 15, 10,
                        20.4403, 61.4296, 46.2491, 68.8558, 37.4591, 63.2353,
                        57.0425, 35, 46.2353, 61.1566, 37.7308},
                {61.4782, 55.618, 54.1475, 56.0977, 24.1421, 18.6023, 21.1803,
                        15.831, 20.4403, 10, 54.9222, 40.4138, 65, 30.6155,
                        62.2015, 55.607, 37.0185, 38.178, 58.3735, 32.0907},
                {48.0526, 17.2801, 13, 13.6056, 67.7754, 63.1413, 63.2259,
                        59.3964, 61.4296, 54.9222, 10, 25.2643, 27.6918,
                        34.3516, 38.8617, 35.02, 47.2022, 28, 29.6469, 33.7065},
                {43.541, 28.6011, 23.9284, 25.8114, 52.72, 48.3275, 48.0789,
                        44.4819, 46.2491, 40.4138, 25.2643, 10, 35.4951, 20,
                        38.2843, 32.0227, 33.7697, 19.434, 31.095, 18.544},
                {36.9258, 34.4131, 25.6205, 24.1421, 75.7647, 72.2013,
                        70.8276, 68.2151, 68.8558, 65, 27.6918, 35.4951, 10,
                        45.3553, 25.8114, 26.2788, 48.0132, 42.6956, 19.2195,
                        43.0606},
                {49.0512, 35.807, 33.5372, 35.4951, 43.541, 38.7924, 39.1548,
                        35.0799, 37.4591, 30.6155, 34.3516, 20, 45.3553, 10,
                        46.0555, 39.4109, 32.0227, 19.434, 40.0832, 13.6056},
                {21.1803, 46.1386, 35.9615, 35.4951, 70.208, 67.8705, 65.2268,
                        63.9351, 63.2353, 62.2015, 38.8617, 38.2843, 25.8114,
                        46.0555, 10, 16.7082, 39.0689, 47.5366, 19.2195,
                        42.7567},
                {23.0384, 42.1403, 32.0227, 32.0227, 64.037, 61.4782, 59.0408,
                        57.5184, 57.0425, 55.607, 35.02, 32.0227, 26.2788,
                        39.4109, 16.7082, 10, 33.5372, 41.4006, 17.2111,
                        36.0768},
                {35.2982, 52.0595, 44.8281, 46.1248, 41.7805, 40.4631,
                        36.9258, 36.8328, 35, 37.0185, 47.2022, 33.7697,
                        48.0132, 32.0227, 39.0689, 33.5372, 10, 40.2655,
                        39.1548, 29.0263},
                {52.9418, 27.4642, 28.2483, 30.2237, 51.8808, 46.7151,
                        47.8021, 43.2866, 46.2353, 38.178, 28, 19.434, 42.6956,
                        19.434, 47.5366, 41.4006, 40.2655, 10, 39.8329, 21.4018},
                {29.2354, 36.9258, 26.7631, 26.2788, 68.1378, 65.0091,
                        63.1507, 61.0098, 61.1566, 58.3735, 29.6469, 31.095,
                        19.2195, 40.0832, 19.2195, 17.2111, 39.1548, 39.8329,
                        10, 37.2029},
                {45.4683, 36.1725, 32.4722, 34.3516, 44.176, 39.8329, 39.5466,
                        35.9615, 37.7308, 32.0907, 33.7065, 18.544, 43.0606,
                        13.6056, 42.7567, 36.0768, 29.0263, 21.4018, 37.2029,
                        10}};
        double[] left = {0, 335, 537, 375, 146, 149, 194, 246, 165, 80, 440,
                326, 397, 39, 11, 566, 268, 268, 105, 344};
        double[] right = {960, 455, 657, 495, 266, 269, 314, 366, 285, 200,
                560, 446, 517, 159, 131, 686, 388, 388, 225, 464};
        random = new Random(System.currentTimeMillis());
        tour[0] = 0;
        tourSize = 1;
        for (int i = 0; i < N; i++)
            for (int j = 1; j < N; j++)
                policy[i][j] = 0.0;
        for (int i = 1; i < N; i++)
            visited[i] = false;
        this.dist = dist;
        this.left = left;
        this.right = right;
        search(1);
//        print();
        int[] myTour = new int[TSPTW.N + 1];
        int myTourSize = 0;
    }


    void print() {
        for (int k = 0; k < N; k++) {
            for (int n = 0; n < N; n++)
                System.out.print(dist[k][n] + " ");
            System.out.println();
        }
        for (int k = 0; k < N; k++) {
            System.out.println("[" + left[k] + "," + right[k] + "]");
        }
        System.out.println();
    }

    /**
     * Solving TSPTW problem with Monte Carlo search and policy adaption
     *
     * @param level the level of the search
     */

    Tour search(int level) {
        Tour best = new Tour();
        best.score = MAXVALUE;
        if (level == 0) {
            rollout();
            best.score = evaluate();
            for (int j = 0; j < N + 1; j++) best.tour[j] = tour[j];
        } else {
            for (int k = 0; k < N; k++)
                for (int n = 0; n < N; n++) local[level][k][n] = policy[k][n];
            for (int i = 0; i < ITERATIONS; i++) {
                Tour r = search(level - 1);
                double score = r.score;
                if (score < best.score) {
                    best.score = score;
                    for (int j = 0; j < N + 1; j++) best.tour[j] = r.tour[j];
                    if (level > 2)
                        System.out.println("Level : " + level + ", score : " + score);
                    adapt(best.tour, level);
                }
            }
            for (int k = 0; k < N; k++)
                for (int n = 0; n < N; n++) policy[k][n] = local[level][k][n];
        }
        return best;
    }

    public double evaluate() {
        double makespan = 0.0;
        double cost = 0.0;
        int prev = 0; // starts at the depot
        int violations = 0;
        for (int i = 1; i < N; i++) {
            int node = tour[i];
            cost += dist[prev][node];
            makespan = Math.max(makespan + dist[prev][node], left[node]);
            if (makespan > right[node]) violations++;
            prev = node;
        }
        cost += dist[prev][0];
        makespan = Math.max(makespan + dist[prev][0], left[0]);
        if (makespan > right[0]) violations++;
        return 100000.0 * violations + cost;
    }

    public int legalMoves() {
        int prev = 0; // starts at the depot
        int opindex = 0;
        double makespan = 0.0;
        double cost = 0.0;
        for (int i = 1; i < tourSize; i++) { // computes makespan
            int node = tour[i];
            cost += dist[prev][node];
            makespan = Math.max(makespan + dist[prev][node], left[node]);
            prev = node;
        }
        if (tourSize > 0)
            prev = tour[tourSize - 1];
        for (int i = 1; i < N; i++) {
            if (!visited[i]) {
                moves[opindex] = i;
                boolean tooLate = false;
                for (int j = 1; j < N; j++)
                    if (j != i && !visited[j])
                        if ((makespan <= right[j]) && (makespan + dist[prev][j] <= right[j])
                                && (Math.max(makespan + dist[prev][i], left[i]) > right[j])) {
                            tooLate = true;
                            break;
                        }
                if (!tooLate)
                    opindex++;
            }
        }
        if (opindex == 0)
            for (int i = 1; i < N; i++)
                if (!visited[i])
                    moves[opindex++] = i;
        return opindex;
    }

    public void rollout() {
        int node = 0;
        tourSize = 1;
        for (int i = 1; i < N; i++) visited[i] = false;
        while (tourSize < N) {
            int successors = legalMoves();
            for (int i = 0; i < successors; i++)
                value[i] = Math.exp(policy[node][moves[i]]);
            double sum = value[0];
            for (int i = 1; i < successors; i++)
                sum += value[i];
            double mrand = random.nextDouble() * sum;
            int i = 0;
            sum = value[0];
            while (sum < mrand) {
                i++;
                sum += value[i];
            }
            tour[tourSize++] = moves[i];
            visited[moves[i]] = true;
            node = moves[i];
        }
        tour[tourSize++] = 0; // Finish at the depot;
    }

    void adapt(int[] tour_param, int level) {
        for (int k = 1; k < N; k++)
            visited[k] = false;
        int successors;
        int node = 0;
        for (int ply = 0; ply < N; ply++) {
            successors = 0;
            for (int i = 1; i < N; i++) {
                if (!visited[i]) {
                    moves[successors] = i;
                    successors++;
                }
            }
            local[level][node][tour[ply]] += 1.0;
            double z = 0.0;
            for (int i = 0; i < successors; i++)
                z += Math.exp(policy[node][moves[i]]);
            for (int i = 0; i < successors; i++)
                local[level][node][moves[i]] -= Math.exp(policy[node][moves[i]]) / z;
            node = tour[ply];
            visited[node] = true;
        }
    }
}

