package evo_assignment1;

import java.io.*;
import java.math.BigDecimal;

class Construction {

    static double[][] readFile(String path) throws IOException {

        double[][] location = new double[1][1];

        File f = new File(path);
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String tmp;

        // Get cities' number.
        while((tmp = br.readLine()) != null) {

            if(tmp.startsWith("DIM")) {
                String[] str = tmp.split(" ");
                String last = str[str.length - 1];
                int number = Integer.parseInt(last);
                location = new double[2][number + 1];
            }

            else if(tmp.startsWith("NOD"))
                break;
        }

        // Store each city's coordinate.
        while((tmp = br.readLine()) != null) {
            if(tmp.equals("EOF")) break;

            String[] str = tmp.split(" ");
            int number = Integer.parseInt(str[0]);

            if(str[1].contains("e") || str[1].contains("E")) {
                BigDecimal bx = new BigDecimal(str[1]);
                location[0][number] = bx.doubleValue();
                BigDecimal by = new BigDecimal(str[1]);
                location[1][number] = by.doubleValue();
            }

            else {
                location[0][number] = Double.valueOf(str[1]);
                location[1][number] = Double.valueOf(str[2]);
            }
        }


        return location;
    }

    static double getDistance(int start, int end, double[][] location) {
        double dist = Math.sqrt(Math.pow((location[0][start] - location[0][end]), 2) + Math.pow((location[1][start] - location[1][end]), 2));
        return dist;
    }
}