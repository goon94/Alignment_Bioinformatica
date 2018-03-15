import java.io.*;
import java.util.*;

class alignment {

    public static void main(String args[]) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input\\input.txt"));
        Score mx[][];
        String x, y;

        System.out.println("Inserted Sequences:");
        x = in.nextLine();
        y = in.nextLine();

        lineUp(x,y);

        System.out.println(x+"\n"+y);

    }

    static void lineUp(String x, String y){


        return;
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