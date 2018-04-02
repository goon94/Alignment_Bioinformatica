import java.io.*;
import java.util.*;

class alignment {
    public static int globalGap = -2;
    public static int localGap = -5;
    public static Score mx[][];
    public static String x, y;
    public static String sol1, sol2;
    public static int op;
    private static final int[][] aScore = {
            { 4, -1, -2, -2,  0, -1, -1,  0, -2, -1, -1, -1, -1, -2, -1,  1,  0, -3, -2,  0},
            {-1,  5,  0, -2, -3,  1,  0, -2,  0, -3, -2,  2, -1, -3, -2, -1, -1, -3, -2, -3},
            {-2,  0,  6,  1, -3,  0,  0,  0,  1, -3, -3,  0, -2, -3, -2,  1,  0, -4, -2, -3},
            {-2, -2,  1,  6, -3,  0,  2, -1, -1, -3, -4, -1, -3, -3, -1,  0, -1, -4, -3, -3},
            { 0, -3, -3, -3,  9, -3, -4, -3, -3, -1, -1, -3, -1, -2, -3, -1, -1, -2, -2, -1},
            {-1,  1,  0,  0, -3,  5,  2, -2,  0, -3, -2,  1,  0, -3, -1,  0, -1, -2, -1, -2},
            {-1,  0,  0,  2, -4,  2,  5, -2,  0, -3, -3,  1, -2, -3, -1,  0, -1, -3, -2, -2},
            { 0, -2,  0, -1, -3, -2, -2,  6, -2, -4, -4, -2, -3, -3, -2,  0, -2, -2, -3, -3},
            {-2,  0,  1, -1, -3,  0,  0, -2,  8, -3, -3, -1, -2, -1, -2, -1, -2, -2,  2, -3},
            {-1, -3, -3, -3, -1, -3, -3, -4, -3,  4,  2, -3,  1,  0, -3, -2, -1, -3, -1,  3},
            {-1, -2, -3, -4, -1, -2, -3, -4, -3,  2,  4, -2,  2,  0, -3, -2, -1, -2, -1,  1},
            {-1,  2,  0, -1, -3,  1,  1, -2, -1, -3, -2,  5, -1, -3, -1,  0, -1, -3, -2, -2},
            {-1, -1, -2, -3, -1,  0, -2, -3, -2,  1,  2, -1,  5,  0, -2, -1, -1, -1, -1,  1},
            {-2, -3, -3, -3, -2, -3, -3, -3, -1,  0,  0, -3,  0,  6, -4, -2, -2,  1,  3, -1},
            {-1, -2, -2, -1, -3, -1, -1, -2, -2, -3, -3, -1, -2, -4,  7, -1, -1, -4, -3, -2},
            { 1, -1,  1,  0, -1,  0,  0,  0, -1, -2, -2,  0, -1, -2, -1,  4,  1, -3, -2, -2},
            { 0, -1,  0, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -2, -1,  1,  5, -2, -2,  0},
            {-3, -3, -4, -4, -2, -2, -3, -2, -2, -3, -2, -3, -1,  1, -4, -3, -2, 11,  2, -3},
            {-2, -2, -2, -3, -2, -1, -2, -3,  2, -1, -1, -2, -1,  3, -3, -2, -2,  2,  7, -1},
            { 0, -3, -3, -3, -1, -2, -2, -3, -3,  3,  1, -2,  1, -1, -2, -2,  0, -3, -1,  4}};

    public static void main(String args[]) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Global(1) or Local(2) Alignment? ");
        op = in.nextInt();
        System.out.println("Compare Genes(1) or Proteins(2)? ");
        int gorp = in.nextInt();

        System.out.println("Input file name?");
        String filename= in.next();
        filename = "input\\"+filename;
        in = new Scanner(new File(filename));
        String xName, yName;
        xName=in.nextLine();
        x = in.nextLine();
        yName= in.nextLine();
        y = in.nextLine();
        mx = new Score[(x.length()+1)][(y.length()+1)];

        if(op==1 && gorp==1){ // genes global alignment

        }
        else if(op==1 && gorp==2){ // proteins global alignment
            lineUpProteinsGlobal();
        }
        else if(op==2 && gorp==1){ //genes local alignment

        }
        else if(op==2 && gorp==2){ //proteins local alignment
            lineUpProteinsLocal();
        }
        else{
            System.out.println("Incorrect Input, Bye...");
            return;
        }
        //System.out.println(x+"\n"+y);

    }

    static void lineUpProteinsGlobal() throws Exception {
        for(int i=0; i<x.length()+1;i++){ //iniciar 1ª coluna
            mx[i][0]= new Score(globalGap*i, 1);
        }
        for(int j=0; j<y.length()+1;j++){ //iniciar 1ª linha
            mx[0][j]= new Score(globalGap*j, -1);
        }
        mx[0][0].path=0;
        int d, up, left;
        for(int i=1; i<x.length()+1; i++){
            for(int j=1; j<y.length()+1; j++){
                d=diagonalCostGlobal(i, j);
                up=upCostGlobal(i,j);
                left=leftCostGlobal(i,j);
                if(d>=up && d>=left){ //diagonal
                    mx[i][j]=new Score(d, 0);
                }
                else if(up>=d && up>=left){ //cima
                    mx[i][j]=new Score(up, 1);
                }
                else{ //esquerda
                    mx[i][j]=new Score(left, -1);
                }
            }
        }

        for(int i=0; i<x.length()+1; i++) {
            for (int j = 0; j < y.length() + 1; j++)
                System.out.print(mx[i][j].value+" ");
            System.out.println();
        }

        //percorrer path e imprimir alinhamento

        sol1="";
        sol2="";
        //sol1+=x.charAt(x.length()-1);
        //sol2+=y.charAt(y.length()-1);
        System.out.println(mx[x.length()-1][y.length()-1].value);
        goBackGlobal(x.length(), y.length());
        System.out.println(sol1);
        System.out.println(sol2);


        return;
    }

    static void lineUpProteinsLocal() throws Exception {
        for(int i=0; i<x.length()+1;i++){ //iniciar 1ª coluna
            mx[i][0]= new Score(0, 1);
        }
        for(int j=0; j<y.length()+1;j++){ //iniciar 1ª linha
            mx[0][j]= new Score(0, -1);
        }
        mx[0][0].path=0;
        int d, up, left;
        int max, iMax, jMax;
        max=0;
        iMax=0;
        jMax=0;
        for(int i=1; i<x.length()+1; i++){
            for(int j=1; j<y.length()+1; j++){
                d=diagonalCostLocal(i, j);
                up=upCostLocal(i,j);
                left=leftCostLocal(i,j);
                if(d>=up && d>=left){ //diagonal
                    if(d<0)
                        mx[i][j]=new Score(0, 0);
                    else
                        mx[i][j]=new Score(d, 0);
                    if(d>max) {
                        max = d;
                        iMax = i;
                        jMax = j;
                    }
                }
                else if(up>=d && up>=left){ //cima
                    if(up<0)
                        mx[i][j]=new Score(0, 1);
                    else
                        mx[i][j]=new Score(up, 1);
                    if(up>max) {
                        max = up;
                        iMax = i;
                        jMax = j;
                    }
                }
                else{ //esquerda
                    if(left<0)
                        mx[i][j]=new Score(0, -1);
                    else
                        mx[i][j]=new Score(left, -1);
                    if(left>max) {
                        max = left;
                        iMax = i;
                        jMax = j;
                    }
                }
            }
        }
        for(int i=0; i<x.length()+1; i++) {
            for (int j = 0; j < y.length() + 1; j++)
                System.out.print(mx[i][j].value+" ");
            System.out.println();
        }
        System.out.println(max);
        sol1="";
        sol2="";
        goBackLocal(iMax, jMax);

        System.out.println(sol1);
        System.out.println(sol2);

    }

    static int diagonalCostLocal(int i, int j) throws Exception {
        return (mx[i-1][j-1].value + getPairScore(i,j));
    }

    static int upCostLocal(int i, int j) throws Exception {
        return (mx[i-1][j].value + localGap);
    }
    static int leftCostLocal(int i, int j) throws Exception {
        return (mx[i][j-1].value + localGap);
    }

    static int getScoreIndex(char a) throws Exception {
        switch ((String.valueOf(a)).toUpperCase().charAt(0)) {
            case 'A': return 0;
            case 'R': return 1;
            case 'N': return 2;
            case 'D': return 3;
            case 'C': return 4;
            case 'Q': return 5;
            case 'E': return 6;
            case 'G': return 7;
            case 'H': return 8;
            case 'I': return 9;
            case 'L': return 10;
            case 'K': return 11;
            case 'M': return 12;
            case 'F': return 13;
            case 'P': return 14;
            case 'S': return 15;
            case 'T': return 16;
            case 'W': return 17;
            case 'Y': return 18;
            case 'V': return 19;
            default: throw new Exception();
        }
    }

    static int diagonalCostGlobal(int i, int j) throws Exception {
        return (mx[i-1][j-1].value + getPairScore(i,j));
    }

    static int leftCostGlobal(int i, int j){
        return (mx[i][j-1].value + globalGap);
    }

    static int upCostGlobal(int i, int j){
        return (mx[i-1][j].value + globalGap);
    }

    static int getPairScore(int i, int j) throws Exception {
        if (x.charAt(i - 1) != '\0' && y.charAt(j - 1) != '\0') // nao sao dois gap
                return aScore[getScoreIndex(x.charAt(i-1))][getScoreIndex(y.charAt(j-1))];
        else if (op==2){
            return localGap;
        }
        return 0;
    }

    static void goBackGlobal(int i, int j){
        if(i==0 && j==0)
            return;
        else if(mx[i][j].path==0){ //diagonal
            if(i==0)
                sol1= '_' + sol1;
            else
                sol1= x.charAt(i-1) + sol1;
            if(j==0)
                sol2= '_' + sol2;
            else
                sol2= y.charAt(j-1) + sol2;
            if(i!=0 && j!=0)
                goBackGlobal(i-1,j-1);
        }
        else if(mx[i][j].path==1){
            if(i==0)
                sol1= '_' + sol1;
            else
                sol1= x.charAt(i-1) + sol1;
            sol2= '_' + sol2;
            if(i!=0)
                goBackGlobal(i-1,j);
        }
        else{
            sol1= '_' + sol1;
            if(j==0)
                sol2= '_' + sol2;
            else
                sol2= y.charAt(j-1) + sol2;
            if(j!=0)
                goBackGlobal(i,j-1);
        }
    }

    static void goBackLocal(int i, int j){
        //System.out.println(i+" "+j);
        if(i==0 && j==0)
            return;
        else if(mx[i][j].path==0){ //diagonal
            if(i==0)
                sol1= '_' + sol1;
            else
                sol1= x.charAt(i-1) + sol1;
            if(j==0)
                sol2= '_' + sol2;
            else
                sol2= y.charAt(j-1) + sol2;
            if(i!=0 && j!=0 && mx[i-1][j-1].value!=0)
                goBackLocal(i-1,j-1);
        }
        else if(mx[i][j].path==1){
            if(i==0)
                sol1= '_' + sol1;
            else
                sol1= x.charAt(i-1) + sol1;
            sol2= '_' + sol2;
            if(i!=0 && j!=0 && mx[i-1][j].value!=0)
                goBackLocal(i-1,j);
        }
        else{
            sol1= '_' + sol1;
            if(j==0)
                sol2= '_' + sol2;
            else
                sol2= y.charAt(j-1) + sol2;
            if(i!=0 && j!=0 && mx[i][j-1].value!=0)
                goBackLocal(i,j-1);
        }
    }

}

class Score {
    int value;
    int path; // 0 if diagonal, -1 if left or 1 if up;
    Score(int value, int path){
        this.value=value;
        this.path=path;
    }
}