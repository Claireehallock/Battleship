import java.util.Scanner;
import java.lang.String;

class Main {

  //ensures the next output is at the bottom of the terminal
  static void printLines(){
    for(int i=0; i<25; i++){
      System.out.println("\n");
    }
  } 
 
  static void printGrid(String[][] arr){
    printLines();
    
    System.out.println("ӧ = Missed shot");
    System.out.println("x = Hit");
    System.out.println("~ = Sunken Battleship");
    String y = " ";
    for(int i=1; i<=10; i++){
      y= y + " " + i;
    }
    System.out.println(y);//prints out top line

    String x;
    for(int i=0; i<10; i++){//prints out rest of array
      x = String.valueOf((char)(i+65));
      for(int j=0; j<10; j++){
        x = x + " "  + arr[i][j];
      }
      System.out.println(x);
    }
  } 

  static void printGrid(int[][] arr){
    printLines();
    
    String y = " ";
    for(int i=1; i<=10; i++){
      y= y + " " + i;
    }
    System.out.println(y);//prints out top line
    String x;
    String z;
    for(int i=0; i<10; i++){//prints out rest of array
      x = String.valueOf((char)(i+65));
      for(int j=0; j<10; j++){
        if (arr[i][j] == 0){
          z = " ";
        }
        else{
          z = String.valueOf(arr[i][j]);
          //Character.toString((char)3486);
        }
        x = x + " "  + z;
      }
      System.out.println(x);
    }
  } 

  public static int[] takeCoords(){
    int[] coords = {-1, -1};

    Scanner scan = new Scanner(System.in);
      int c1 = -1;
      int c2 = -1;
      String c11 = "";
      String c22 = "";
      
      System.out.println("Enter starting column letter (A-J)");
      while(c1<1 || c1>10){
        c11=scan.next();
        if (c11.length() < 2){
          c1=(int)c11.charAt(0);
          if(c1<90){
            c1=c1-64;
          }
          else{
            c1=c1-96;
          }
        }
        if(c1<1||c1>10){
          System.out.println("Please enter a valid column letter (A-J)");
        }
      }

      System.out.println("Enter starting row number (1-10)");
      while(c2<1 || c2>10){
        c22 = scan.next(); 
        if(c22.length() < 2){
          c2=(int)c22.charAt(0);
          c2=c2-48;
        }
        else if (c22.length() == 2 && ((int)c22.charAt(0))-48 ==1 && ((int)c22.charAt(1)-48) ==0){
          c2=10;
        }

        if(c2<1||c2>10){
          System.out.println("Please enter a valid row number (1-10)");
        }
      }

      coords[0] = c1-1;
      coords[1] = c2-1;

    return coords;
  }

  public static void placeBoats(int[][] p, String pn){
    printGrid (p);
    int[] boats = {5, 4, 3, 3, 2};
    int[] coords = new int[2];

    while(boats[0]+boats[1]+boats[2]+boats[3]+boats[4]!=0){
      Scanner scan = new Scanner(System.in);

      System.out.println(pn + ", Please enter coordinates for the starting position of your boat");
      coords = takeCoords();
      String cord1 = String.valueOf((char)(coords[0]+65));
      String cord2 = String.valueOf(coords[1]+1);

      String check = "";
      while(!check.equals("Y") && !check.equals("N") &&!check.equals("y") && !check.equals("n")){
        System.out.println("\nIs this the correct starting square?(Y/N): ");
        System.out.println(cord1 +cord2);
        check = scan.nextLine();
        if (!check.equals("Y") && !check.equals("N") &&!check.equals("y") && !check.equals("n")){
          System.out.println("Invalid response. Please type \"Y\" or \"N\" ");
        }
      }
      if(check.equals("Y")|| check.equals("y")){
        if (p[coords[0]][coords[1]] != 0){
          System.out.println("There is already a boat placed in this location.\nPlease select another location\n");
        }
        else{
          System.out.println();
          int boatnum = -1;
          boolean blengthcheck = false;
          while(!blengthcheck){
            System.out.println("You have to following boat lengths left:");
            String boatList = "";
            for(int i = 0; i < boats.length; i++){
              int c = 0;
              for(int j = i+1; j< boats.length; j++){
                if(boats[j] != 0){
                  c = 1;
                }
              }
              if (i < boats.length - 1 && c != 0){
                if (boats[i] != 0){
                  boatList = boatList + boats[i] + ", ";
                }
              }
              else{
                if (boats[i] != 0){
                  boatList = boatList + boats[i];
                }
              }
            }
            System.out.println(boatList);
            System.out.println("What boat length would you like to select?");

            String blength = scan.nextLine();
            if(blength.length() < 2){
              int c1=(int)blength.charAt(0);
              c1=c1-48;
              boatnum = -1;
              for(int i = boats.length; i >0; i--){
                if (c1 == boats[i-1]){
                  boatnum = i-1;
                }
              }
              if(boatnum == -1){
                System.out.println("\nInvalid boat length");
              }
              else{
                blengthcheck = true;
              }
            }
            else{
              System.out.println("\nInvalid boat length");
            }
          }

          System.out.println();
          blengthcheck = false;
          while(!blengthcheck){
            System.out.println("What direction would you like the boat to go?(U/D/L/R)");
            System.out.println("If you would like to select a different boat or starting position, type \"S\"");
            String bdir = scan.nextLine();
            if(bdir.length() < 2){
              char d1=bdir.charAt(0);
              boolean bcheck = true;
              boolean onmap = true;
              if (d1 == 'u'||d1 == 'U'){//  up
                for(int i = coords[0]; i > coords[0]-boats[boatnum]; i--){
                  if(i >=0){
                    if (p[i][coords[1]] != 0){
                      bcheck = false;
                    }
                  }
                  else{
                    onmap = false;
                  }
                }
                if(onmap){
                  if (bcheck){
                    for(int i = coords[0]; i > coords[0]-boats[boatnum]; i--){
                      p[i][coords[1]] = boatnum + 1;
                    }
                    boats[boatnum]=0;
                    blengthcheck = true;
                    printGrid (p);
                  }
                  else{
                    System.out.println("\nThere is already a boat there.");                  
                  }
                }
                else{
                  System.out.println("\nThat direction goes off the map.");
                }
              }
              else if (d1 == 'd'||d1 == 'D'){//  down
                for(int i = coords[0]; i < coords[0]+boats[boatnum]; i++){
                  if(i <= 9){
                    if (p[i][coords[1]] != 0){
                      bcheck = false;
                    }
                  }
                  else{
                    onmap = false;
                  }
                }
                if(onmap){
                  if (bcheck){
                    for(int i = coords[0]; i < coords[0]+boats[boatnum]; i++){
                      p[i][coords[1]] = boatnum + 1;
                    }
                    boats[boatnum]=0;
                    blengthcheck = true;
                    printGrid (p);
                  }
                  else{
                    System.out.println("\nThere is already a boat there.");                  
                  }
                }
                else{
                  System.out.println("\nThat direction goes off the map.");
                }
              }
              else if (d1 == 'l'||d1 == 'L'){//  left
                for(int i = coords[1]; i > coords[1]-boats[boatnum]; i--){
                  if(i >=0){
                    if (p[coords[0]][i] != 0){
                      bcheck = false;
                    }
                  }
                  else{
                    onmap = false;
                  }
                }
                if(onmap){
                  if (bcheck){
                    for(int i = coords[1]; i > coords[1]-boats[boatnum]; i--){
                      p[coords[0]][i] = boatnum + 1;
                    }
                    boats[boatnum]=0;
                    blengthcheck = true;
                    printGrid (p);
                  }
                  else{
                    System.out.println("\nThere is already a boat there.");                  
                  }
                }
                else{
                  System.out.println("\nThat direction goes off the map.");
                }
              }
              else if (d1 == 'r'||d1 == 'R'){// right
                for(int i = coords[1]; i < coords[1]+boats[boatnum]; i++){
                  if(i <= 9){
                    if (p[coords[0]][i] != 0){
                      bcheck = false;
                    }
                  }
                  else{
                    onmap = false;
                  }
                }
                if(onmap){
                  if (bcheck){
                    for(int i = coords[1]; i < coords[1]+boats[boatnum]; i++){
                      p[coords[0]][i] = boatnum + 1;
                    }
                    boats[boatnum]=0;
                    blengthcheck = true;
                    printGrid (p);
                  }
                  else{
                    System.out.println("\nThere is already a boat there.");                  
                  }
                }
                else{
                  System.out.println("\nThat direction goes off the map.");
                }
              }
              else if (d1 == 's'||d1 == 'S'){
                blengthcheck = true;
                printGrid(p);
              }
              else{
                System.out.println("\nInvalid boat direction");
              }
            }
            else{
              System.out.println("\nInvalid boat direction");
            }

          }
        }
      }
      else{
        printGrid(p);
      }
    }
  } 

  public static int[] battleShip(int x, int y, String[][] pGrid, int[][] p){
    int boatnum = p[x][y];
    int[] arr = new int[11];
    for(int i = 0; i < arr.length; i++){
      arr[i] = -1;
    }
    arr[0] = 1;

    int k = 1;
    for(int i = 0; i < 10; i++){
      for(int j = 0; j < 10; j++){
        if(p[i][j] == boatnum){
          arr[2*k-1] = i;
          arr[2*k] = j;
          k++;
          if(pGrid[i][j].equals(" ")){
            arr[0] = 0;
          }
        }
      }
    }
    return arr;
  }


  //the main function
  public static void main(String[] args) {
    Scanner inp = new Scanner(System.in);
    //creates 2 boolean arrays to store data of where boats are
    int[][] p1;
    p1 = new int[10][10];
    int[][] p2;
    p2 = new int[10][10];
    
    for(int i=0; i< 10; i++){ //initializes p1 with 0's
      int[] arr = p1[i];
      for(int j=0; j<10; j++){
        arr[j] = 0;
      }
    }

    for(int i=0; i< 10; i++){ //initializes p2 with 0's
      int[] arr = p2[i];
      for(int j=0; j<10; j++){
        arr[j] = 0;
      }
    }
    
    //creates string arrays to be displayed on grid
    String[][] p1Grid;
    p1Grid = new String[10][10];
    String[][] p2Grid;
    p2Grid = new String[10][10];

    for(int i=0; i< 10; i++){ //initializes p1Grid with spaces's
      String[] arr = p1Grid[i];
      for(int j=0; j<10; j++){
        arr[j] = " ";
      }
    }

    for(int i=0; i< 10; i++){ //initializes p2Grid with spaces's
      String[] arr = p2Grid[i];
      for(int j=0; j<10; j++){
        arr[j] = " ";
      }
    }
    placeBoats(p1, "Player 1");
    placeBoats(p2, "Player 2");

//the WAR
    int round=1;
    int turn=2;
    int victory=0;
    int h1=0;
    //h1=number of times that boats got hit for p1
    int h2=0;
    //h2=number of times that boats got hit for p2
    int c1;
    int c2;
    int v=0;
    boolean check=false;
    while(victory<1)
    {
      turn=turn%2+1;
      if(turn == 2){
        printGrid(p1Grid);
      }
      else{
        printGrid(p2Grid);
      }
      if(round <= 150){
        System.out.println("Round: "+round);
      }
      else{
        System.out.println("Round: ???");
      }
      System.out.println("Player "+turn+"'s"+" turn");
      int[] coords = takeCoords();
      c1=coords[0];
      c2=coords[1];
      //user input fire coordinate
      while(check==false)
      {
        //checking if fired at spot already
        check=true;
        if(turn==1)
        {
          if(p2Grid[c1][c2]=="ӧ"||p2Grid[c1][c2]=="x")
          {
            check=false;
            System.out.println("??? Long Game ¿¿¿");
            coords = takeCoords();
            c1=coords[0];
            c2=coords[1];
          }
        }
        else
        {
          if(p1Grid[c1][c2]=="ӧ"||p1Grid[c1][c2]=="x")
          {
            check=false;
            System.out.println("??? Long Game ¿¿¿");
            coords = takeCoords();
            c1=coords[0];
            c2=coords[1];
          }
        }
      }
      check=false;
      //checking if hit
      if(turn==1)
      {
        v=battoe(p2Grid, p2, v, c1, c2);
      }
      else
      {
        v=battoe(p1Grid, p1, v, c1, c2);
      }
      if(v==0)
      {
        if(turn==1)
        {
          p2Grid[c1][c2]="ӧ";
        }
        else
        {
          p1Grid[c1][c2]="ӧ";
        }
      }
      else if(v==1)
      {
        int shipcoords[];
        if(turn==1)
        {
          p2Grid[c1][c2]="x";
          h2++;
          shipcoords = battleShip(c1, c2, p2Grid, p2);
          if(shipcoords[0] == 1){
            for(int i = 1; i < shipcoords.length; i+=2){
              if(shipcoords[i] != -1){
                p2Grid[shipcoords[i]][shipcoords[i+1]] = "~";
              }
            }
            System.out.println("~You sunk my Battleship!~\n");
          }
        }
        else
        {
          p1Grid[c1][c2]="x";
          h1++;
          shipcoords = battleShip(c1, c2, p1Grid, p1);
          if(shipcoords[0] == 1){
            for(int i = 1; i < shipcoords.length; i+=2){
              if(shipcoords[i] != -1){
                p1Grid[shipcoords[i]][shipcoords[i+1]] = "~";
              }
            }
            System.out.println("You sunk my Battleship!");
          }
        }
      }
      //move on?
      String chek = "";
      System.out.println("Please type \"Y\" to continue");
      while(!chek.equals("Y") &&!chek.equals("y")){
        chek = inp.nextLine();
        if (!chek.equals("Y") &&!chek.equals("y")){
          System.out.println("Invalid response. Please type \"Y\" to continue");
        }
      }
      if(h2==17)
      {
        victory=1;
      }
      if(h1==17)
      {
        victory=2;
      }
      round++;
    }
    System.out.println("Player "+victory+" wins!");
  }

  public static int battoe(String[][] pGrid, int[][] p, int v, int c1, int c2)
  {  
    if(p[c1][c2] == 0)
    {
      pGrid[c1][c2]="ӧ";
      v=0;
    }
    else
    {
      pGrid[c1][c2]="x";
      v=1;
    }
    printGrid(pGrid);
    if(v == 1){
      System.out.println("Hit! \n");
    }
    else{
      System.out.println("ඞ Maybe ඞ Next ඞ Time! ඞ \n");
    }
    return v;
  }

}