import java.io.*;
import java.util.*;

class alignment {
    public static int g = -2;
    public static Score mx[][];
    public static String x, y;
    public static String sol1, sol2;
    public static void main(String args[]) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input\\input.txt"));
        //System.out.println("Inserted Sequences:");
        x = in.nextLine();
        y = in.nextLine();
        mx = new Score[(x.length()+1)][(y.length()+1)];
        lineUp();

        //System.out.println(x+"\n"+y);

    }

    static void lineUp(){
        for(int i=0; i<x.length()+1;i++){ //iniciar 1ª coluna
            mx[i][0]= new Score(g*i, 0);
        }
        for(int j=0; j<y.length()+1;j++){ //iniciar 1ª linha
            mx[0][j]= new Score(g*j, 0);
        }
        int d, up, left;
        for(int i=1; i<x.length()+1; i++){
            for(int j=1; j<y.length()+1; j++){
                d=diagonalCost(i, j);
                up=upCost(i,j);
                left=leftCost(i,j);
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
        sol1+=x.charAt(x.length()-1);
        sol2+=y.charAt(y.length()-1);
        goBack(x.length(), y.length());
        System.out.println(sol1);
        System.out.println(sol2);


        return;
    }

    static int diagonalCost(int i, int j){
        return (mx[i-1][j-1].value + getPairScore(i,j));
    }

    static int leftCost(int i, int j){
        return (mx[i][j-1].value + g);
    }

    static int upCost(int i, int j){
        return (mx[i-1][j].value + g);
    }

    static int getPairScore(int i, int j){
        if(x.charAt(i-1)==y.charAt(j-1)) { // se são iguais
            if (x.charAt(i - 1) != '\0') // e nao gap
                return 1;
            else //se sao dois gaps
                return 0;
        }
        else if(x.charAt(i-1)!='\0' && y.charAt(j-1)!='\0' && x.charAt(i-1)!=y.charAt(j-1)){
            return -1;
        }
        else //gap
            return -2;
    }

    static void goBack(int i, int j){
        if(mx[i][j].path==0){ //diagonal
            if(i==1)
                sol1= '_' + sol1;
            else
                sol1= x.charAt(i-2) + sol1;
            if(j==1)
                sol2= '_' + sol2;
            else
                sol2= y.charAt(j-2) + sol2;
            if(i!=1 && j!=1)
                goBack(i-1,j-1);
        }
        else if(mx[i][j].path==1){
            if(i==1)
                sol1= '_' + sol1;
            else
                sol1= x.charAt(i-2) + sol1;
            sol2= '_' + sol2;
            if(i!=1 && j!=1)
                goBack(i-1,j);
        }
        else{
            sol1= '_' + sol1;
            if(j==1)
                sol2= '_' + sol2;
            else
                sol2= y.charAt(j-2) + sol2;
            if(i!=1 && j!=1)
                goBack(i,j-1);
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