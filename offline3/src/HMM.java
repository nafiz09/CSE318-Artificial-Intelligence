import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.System.exit;

public class HMM {
    public static int n,m,k;
    public static void main(String[] args) {
        File myObj = new File("input.txt");
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
            String data = myReader.nextLine();
            String [] dataArray = data.split(" ");
            n = Integer.parseInt(dataArray[0]);
            m = Integer.parseInt(dataArray[1]);
            k = Integer.parseInt(dataArray[2]);
            float[][] matrix = new float[n][m];
            float[][] matrix2 = new float[n][m];
            float p = (n*m) - k;
            p = (1/p);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    matrix[i][j] = p*100;
                }
            }
            for(int i = 0;i < k;i++)
            {
                String st = myReader.nextLine();
                //System.out.println(st);
                String[] stArray = st.split(" ");
                int a = Integer.parseInt(stArray[0]);
                int b = Integer.parseInt(stArray[1]);
                matrix[a][b] = 0;
            }
            System.out.println("Initial Probability:");

            printMatrix(matrix);
            int c = 1;

            while(myReader.hasNext())
            {
                String st = myReader.nextLine();
                String[] stArray = st.split(" ");
                if(stArray[0].equals("R"))
                {
                    int a = Integer.parseInt(stArray[1]);
                    int b = Integer.parseInt(stArray[2]);
                    int c1 = Integer.parseInt(stArray[3]);

                    System.out.println("Probability Update (Reading " + c + "):");
                    c++;

                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < m; j++) {
                            bdash(matrix, matrix2, i, j);
                        }
                    }

                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < m; j++) {
                            matrix[i][j] = matrix2[i][j];
                        }
                    }

                    if(c1 == 1)
                        positiveevidence(matrix, a, b);
                    else
                        negativeevidence(matrix, a, b);
                    normalize(matrix);

                    printMatrix(matrix);
                }

                else if(stArray[0].equals("C"))
                {
                    float max = 0;
                    int d = 0,e = 0;
                    for(int i = 0;i < n;i++)
                    {
                        for(int j = 0;j < m;j++)
                        {
                            if(max < matrix[i][j]) {
                                max = matrix[i][j];
                                d = i;
                                e = j;
                            }
                        }
                    }
                    System.out.println("Casper is most probably at ("+d+","+e+")");
                    System.out.println();
                }
                else if(stArray[0].equals("Q")) {
                    System.out.println("Bye Casper!");
                    exit(1);
                }
            }

    }

    static float edgecount(float[][] matrix, int a, int b)
    {
        float count = 0;
        if(a-1 > -1 && matrix[a-1][b] != 0)
            count++;
        if(a+1 < n && matrix[a+1][b] != 0)
            count++;
        if(b-1 > -1 && matrix[a][b-1] != 0)
            count++;
        if(b+1 < m && matrix[a][b+1] != 0)
            count++;

        return count;
    }

    static float cornercount(float[][] matrix, int a, int b)
    {
        float count = 0;
        if(a-1 > -1 && b-1 > -1 && matrix[a-1][b-1] != 0)
            count++;
        if(a-1 > -1 && b+1 < m && matrix[a-1][b+1] != 0)
            count++;
        if(a+1 < n && b-1 > -1 && matrix[a+1][b-1] != 0)
            count++;
        if(a+1 < n && b+1 < m && matrix[a+1][b+1] != 0)
            count++;


        return count+1;
    }

    static void bdash(float[][] matrix,float[][] temp, int a,int b)
    {
        float p = 0;
        //float[][] temp = new float[n][m];
        if(a-1 > -1 && b-1 > -1 && matrix[a-1][b-1] != 0)
        {
            float corner = cornercount(matrix,a-1,b-1);
            //System.out.println(corner);
            p = p + 1/corner * 1/10 * matrix[a-1][b-1]/100;
           // System.out.println(p);
        }
        if(a-1 > -1 && b+1 < m && matrix[a-1][b+1] != 0)
        {
            float corner = cornercount(matrix,a-1,b+1);
            ///System.out.println(corner);
            p = p + 1/corner * 1/10 * matrix[a-1][b+1]/100;
           // System.out.println(p);
        }
        if(a+1 < n && b-1 > -1 && matrix[a+1][b-1] != 0)
        {
            float corner = cornercount(matrix,a+1,b-1);
            //System.out.println(corner);
            p = p + 1/corner * 1/10 * matrix[a+1][b-1]/100;
            //System.out.println(p);
        }
        if(a+1 < n && b+1 < m && matrix[a+1][b+1] != 0)
        {
            float corner = cornercount(matrix,a+1,b+1);
            //System.out.println(corner);
            p = p + 1/corner * 1/10 * matrix[a+1][b+1]/100;
           // System.out.println(p);
        }
        if(a-1 > -1 && matrix[a-1][b] != 0)
        {
            float edge = edgecount(matrix,a-1,b);
            //System.out.println(edge);
            p = p + 1/edge * 9/10 * matrix[a-1][b]/100;
            //System.out.println(p);
        }
        if(a+1 < n && matrix[a+1][b] != 0)
        {
            float edge = edgecount(matrix,a+1,b);
            //System.out.println(edge);
            p = p + 1/edge * 9/10 * matrix[a+1][b]/100;
            //System.out.println(p);
        }
        if(b-1 > -1 && matrix[a][b-1] != 0)
        {
            float edge = edgecount(matrix,a,b-1);
            //System.out.println(edge);
            p = p + 1/edge * 9/10 * matrix[a][b-1]/100;
            //System.out.println(p);
        }
        if(b+1 < m && matrix[a][b+1] != 0)
        {
            float edge = edgecount(matrix,a,b+1);
            //System.out.println("p"+edge);
            p = p + 1/edge * 9/10 * matrix[a][b+1]/100;
            //System.out.println(p);
        }
        if(matrix[a][b] != 0)
            {
                p = p + 1/cornercount(matrix,a,b) * 1/10 * matrix[a][b]/100;
                //System.out.println(p);
            }
        if(matrix[a][b] == 0)
            temp[a][b] = 0;
        else
            temp[a][b] = p;
    }

    static void positiveevidence(float[][] matrix, int a, int b)
    {
        for(int i = 0;i < n;i++)
        {
            for(int j = 0;j < m;j++)
            {
                if(matrix[i][j] != 0)
                {
                    if(i == a && j == b)
                        matrix[i][j] = matrix[i][j] * 85/100;
                    else if(i == a-1 && j == b)
                        matrix[i][j] = matrix[i][j] * 85/100;
                    else if(i == a+1 && j == b)
                        matrix[i][j] = matrix[i][j] * 85/100;
                    else if(j == b-1 && i == a)
                        matrix[i][j] = matrix[i][j] * 85/100;
                    else if(j == b+1 && i == a)
                        matrix[i][j] = matrix[i][j] * 85/100;
                    else if(i == a-1 && j == b-1)
                        matrix[i][j] = matrix[i][j] * 85/100;
                    else if(i == a-1 && j == b+1)
                        matrix[i][j] = matrix[i][j] * 85/100;
                    else if(i == a+1 && j == b-1)
                        matrix[i][j] = matrix[i][j] * 85/100;
                    else if(i == a+1 && j == b+1)
                        matrix[i][j] = matrix[i][j] * 85/100;
                    else
                        matrix[i][j] = matrix[i][j] * 15/100;
                }
            }
        }
    }

    static void negativeevidence(float[][] matrix, int a, int b)
    {
        for(int i = 0;i < n;i++)
        {
            for(int j = 0;j < m;j++)
            {
                if(matrix[i][j] != 0)
                {
                    if(i == a && j == b)
                        matrix[i][j] = matrix[i][j] * 15/100;
                    else if(i == a-1 && j == b)
                        matrix[i][j] = matrix[i][j] * 15/100;
                    else if(i == a+1 && j == b)
                        matrix[i][j] = matrix[i][j] * 15/100;
                    else if(j == b-1 && i == a)
                        matrix[i][j] = matrix[i][j] * 15/100;
                    else if(j == b+1 && i == a)
                        matrix[i][j] = matrix[i][j] * 15/100;
                    else if(i == a-1 && j == b-1)
                        matrix[i][j] = matrix[i][j] * 15/100;
                    else if(i == a-1 && j == b+1)
                        matrix[i][j] = matrix[i][j] * 15/100;
                    else if(i == a+1 && j == b-1)
                        matrix[i][j] = matrix[i][j] * 15/100;
                    else if(i == a+1 && j == b+1)
                        matrix[i][j] = matrix[i][j] * 15/100;
                    else
                        matrix[i][j] = matrix[i][j] * 85/100;
                }
            }
        }
    }

    static void normalize(float[][] matrix)
    {
        float sum = 0;
        for(int i = 0;i < n;i++)
        {
            for(int j = 0;j < m;j++)
            {
                sum = sum + matrix[i][j];
            }
        }
        for(int i = 0;i < n;i++)
        {
            for(int j = 0;j < m;j++)
            {
                matrix[i][j] = matrix[i][j] / sum;
                matrix[i][j] = matrix[i][j] * 100;
            }
        }
    }

    static void printMatrix(float[][] matrix)
    {
        for(int i = 0;i < n;i++)
        {
            for(int j = 0;j < m;j++)
            {
                System.out.print(String.format("%.4f", matrix[i][j]) + "\t");
            }
            System.out.println();
        }
        System.out.println();

    }
}
