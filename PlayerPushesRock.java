import java.util.Random;
import java.util.Scanner;

public class PlayerPushesRock {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        Random rand = new Random();
        //check difficulty
        int rows = 0;
        int cols = 0;
        int bushes = 0;
        System.out.println("Please Enter what difficulty you want to play at from 1 to 3");
        int difficulty = sc.nextInt();
        if (difficulty == 1){
            rows = 20;
            cols = 20;
            bushes = 20;
        }else if (difficulty == 2){
            rows = 40;
            cols = 40;
            bushes = 160;
        }else if (difficulty == 3){
            rows = 50;
            cols = 50;
            bushes = 500;
        }
        //create matrix with rows and columns depending on difficulty
        char[][] matrix = new char[rows][cols];
        //Initiate player with position
        char player = 'ᗣ';
        int playerPositioningRow = 0;
        int playerPositioningCol = 0;
        //initiate rock with position
        char rock = '◕';
        int rockPositioningRow = 1;
        int rockPositioningCol = 1;
        //initiate exit
        char exit = 'E';
        int exitPositionRow = matrix.length -1;
        int exitPositionCol = matrix[0].length - 1;
        //Make flags that will check if the player has stuck the rock or if he has won
        boolean isStuck = false;
        boolean hasWon = false;
        int randomRow;
        int randomCol;
        boolean isTaken;
        //place the objects in the game
        matrix[playerPositioningRow][playerPositioningCol] = player;
        matrix[rockPositioningRow][rockPositioningCol] = rock;
        matrix[exitPositionRow][exitPositionCol] = exit;
        //This gives random position for the bushes
        for (int i = 0; i < bushes; i++) {
            do{
                randomRow = rand.nextInt(rows);
                randomCol = rand.nextInt(cols - 1);
                //here I am making sure that the character and the rock aren't stuck from the beginning
                // and that the exit is not surrounded with bushes
                isTaken = (matrix[randomRow][randomCol] != 0) ||
                        ((randomRow == 1 && randomCol == 0) ||
                        (randomRow == 0 && randomCol == 1) ||
                        (randomRow == matrix.length - 2 ||
                        randomCol == matrix[0].length - 2));
                if (!isTaken){
                    matrix[randomRow][randomCol] = '*';
                }
            }while (isTaken);
        }
        System.out.println("Controls are w for Up , s for down , a for left and d for right");
        //Start a cycle that will refresh the game image after every iteration
       outerloop: do {

            //print the game image
            for (int row = 0; row < matrix.length; row++) {
                if (row == 0){
                    for (int col = 0; col < matrix[row].length; col++) {
                        System.out.print("_");
                    }
                    System.out.println();
                }
                for (int col = 0; col < matrix[row].length; col++) {
                    if (col == 0){
                        System.out.print("|");
                    }
                    System.out.print(matrix[row][col]);
                    if ( col == matrix[row].length - 1){
                        System.out.print("|");
                    }
                }
                if (row == matrix.length - 1){
                    System.out.println();
                    for (int col = 0; col < matrix[row].length; col++) {
                        System.out.print("_");
                    }
                }
                System.out.println();
            }
           //checking if the rock is stuck
           if (matrix[rockPositioningRow][rockPositioningCol] == matrix[0][0] ||
                   matrix[rockPositioningRow][rockPositioningCol] == matrix[0][matrix[0].length - 1] ||
                   matrix[rockPositioningRow][rockPositioningCol] == matrix[matrix.length-1][0]){
               isStuck = true;
               break;
           }else if (matrix[rockPositioningRow][rockPositioningCol] != matrix[0][rockPositioningCol] &&
                     matrix[rockPositioningRow][rockPositioningCol] != matrix[rockPositioningRow][0] &&
                     matrix[rockPositioningRow][rockPositioningCol] != matrix[matrix.length - 1][rockPositioningCol] &&
                     matrix[rockPositioningRow][rockPositioningCol] != matrix[rockPositioningRow][matrix[0].length - 1]) {
               if(matrix[rockPositioningRow - 1][rockPositioningCol] == '*' && matrix[rockPositioningRow][rockPositioningCol - 1] == '*'){
                   isStuck = true;
                   break;
               }else if (matrix[rockPositioningRow - 1][rockPositioningCol] == '*' && matrix[rockPositioningRow][rockPositioningCol + 1] == '*'){
                   isStuck = true;
                   break;
               }else if (matrix[rockPositioningRow + 1][rockPositioningCol] == '*' && matrix[rockPositioningRow][rockPositioningCol + 1] == '*'){
                   isStuck = true;
                   break;
               }else if (matrix[rockPositioningRow + 1][rockPositioningCol] == '*' && matrix[rockPositioningRow][rockPositioningCol - 1] == '*'){
                   isStuck = true;
                   break;
               }
           }else if (matrix[rockPositioningRow][rockPositioningCol] == matrix[rockPositioningRow][0] &&
                   (matrix[rockPositioningRow + 1][rockPositioningCol] == '*'||
                           matrix[rockPositioningRow -1][rockPositioningCol] == '*')){
               isStuck = true;
               break;
           }else if (matrix[rockPositioningRow][rockPositioningCol] == matrix[0][rockPositioningCol] &&
                   (matrix[rockPositioningRow][rockPositioningCol + 1] == '*' ||
                           matrix[rockPositioningRow][rockPositioningCol - 1] == '*')){
               isStuck = true;
               break;
           }else if (matrix[rockPositioningRow][rockPositioningCol] == matrix[rockPositioningRow][matrix[0].length - 1] &&
                   (matrix[rockPositioningRow - 1][rockPositioningCol] == '*' ||
                           matrix[rockPositioningRow + 1][rockPositioningCol] == '*')){
               isStuck = true;
               break;
           }else if (matrix[rockPositioningRow][rockPositioningCol] == matrix[matrix.length - 1][rockPositioningCol] &&
                   (matrix[rockPositioningRow][rockPositioningCol + 1] == '*' ||
                           matrix[rockPositioningRow][rockPositioningCol - 1] == '*')){
               isStuck = true;
               break ;
           }
            System.out.println("Please enter which way you want to move ");
            char move = sc.next().charAt(0);
            //FROM HERE
            if (move == 'w'){
                if (playerPositioningRow == 0 || matrix[playerPositioningRow - 1][playerPositioningCol] == '*'){
                    continue;
                }else if (matrix[playerPositioningRow - 1][playerPositioningCol] == matrix[rockPositioningRow][rockPositioningCol]){
                    if (rockPositioningRow == 0 || matrix[rockPositioningRow - 1][rockPositioningCol] == '*'){
                        continue;
                    }else {
                        matrix[rockPositioningRow][rockPositioningCol] = 0;
                        rockPositioningRow--;
                        matrix[rockPositioningRow][rockPositioningCol] = '◕';
                        matrix[playerPositioningRow][playerPositioningCol] = 0;
                        playerPositioningRow--;
                        matrix[playerPositioningRow][playerPositioningCol] = player;
                    }
                } else {
                    matrix[playerPositioningRow][playerPositioningCol] = 0;
                    playerPositioningRow--;
                    matrix[playerPositioningRow][playerPositioningCol] = player;
                }
            }else if (move == 's'){
                if (playerPositioningRow == matrix.length - 1 || matrix[playerPositioningRow + 1][playerPositioningCol] == '*'){
                    continue;
                }else if (matrix[playerPositioningRow + 1][playerPositioningCol] == matrix[rockPositioningRow][rockPositioningCol]) {
                    if (rockPositioningRow == matrix.length - 1 || matrix[rockPositioningRow + 1][rockPositioningCol] == '*') {
                        continue;
                    } else {
                        if (matrix[rockPositioningRow + 1][rockPositioningCol] == matrix[exitPositionRow][exitPositionCol]){
                            hasWon = true;
                            break outerloop;
                        }else {
                            matrix[rockPositioningRow][rockPositioningCol] = 0;
                            rockPositioningRow++;
                            matrix[rockPositioningRow][rockPositioningCol] = '◕';
                            matrix[playerPositioningRow][playerPositioningCol] = 0;
                            playerPositioningRow++;
                            matrix[playerPositioningRow][playerPositioningCol] = player;
                        }
                    }
                } else {
                    matrix[playerPositioningRow][playerPositioningCol] = 0;
                    playerPositioningRow++;
                    matrix[playerPositioningRow][playerPositioningCol] = player;
                }
            }else if (move == 'a'){
                if (playerPositioningCol == 0 || matrix[playerPositioningRow][playerPositioningCol - 1] == '*'){
                    continue;
                }else if (matrix[playerPositioningRow][playerPositioningCol - 1] == matrix[rockPositioningRow][rockPositioningCol]) {
                    if (rockPositioningCol == 0 || matrix[rockPositioningRow][rockPositioningCol - 1] == '*' ) {
                        continue;
                    } else {
                        matrix[rockPositioningRow][rockPositioningCol] = 0;
                        rockPositioningCol--;
                        matrix[rockPositioningRow][rockPositioningCol] = '◕';
                        matrix[playerPositioningRow][playerPositioningCol] = 0;
                        playerPositioningCol--;
                        matrix[playerPositioningRow][playerPositioningCol] = player;
                    }
                } else {
                    matrix[playerPositioningRow][playerPositioningCol] = 0;
                    playerPositioningCol--;
                    matrix[playerPositioningRow][playerPositioningCol] = player;
                }
            }else if (move == 'd'){
                if (playerPositioningCol == matrix[0].length - 1  || matrix[playerPositioningRow][playerPositioningCol + 1] == '*'){
                    continue;
                }else if (matrix[playerPositioningRow][playerPositioningCol + 1] == matrix[rockPositioningRow][rockPositioningCol]) {
                    if (rockPositioningCol == matrix[0].length - 1 || matrix[rockPositioningRow][rockPositioningCol + 1] == '*' ) {
                        continue;
                    } else {
                        if (matrix[rockPositioningRow][rockPositioningCol + 1] == matrix[exitPositionRow][exitPositionCol]){
                            hasWon = true;
                            break outerloop;
                        }else {
                            matrix[rockPositioningRow][rockPositioningCol] = 0;
                            rockPositioningCol++;
                            matrix[rockPositioningRow][rockPositioningCol] = '◕';
                            matrix[playerPositioningRow][playerPositioningCol] = 0;
                            playerPositioningCol++;
                            matrix[playerPositioningRow][playerPositioningCol] = player;
                        }
                    }
                } else {
                    matrix[playerPositioningRow][playerPositioningCol] = 0;
                    playerPositioningCol++;
                    matrix[playerPositioningRow][playerPositioningCol] = player;
                }
            }
            //TO HERE ARE ALL THE MOVEMENTS OF THE ROCK AND THE CHARACTER

        }while (!isStuck || !hasWon);
        if (hasWon){
            System.out.println("Congratulations you brought the rock to the exit and WON !!! ");
        }else if(isStuck){
            System.out.println("Noooo you got stuck");
        }
        System.out.println("If you want to restart the game press y or no if you dont want to");
        char restart = sc.next().charAt(0);
        if (restart == 'y'){
            PlayerPushesRock.main(new String[0]);
        }else {
            System.out.println("Bye");
        }
    }
}
