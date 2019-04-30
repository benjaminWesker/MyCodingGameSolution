import java.util.*;
import java.io.*;
import java.math.*;

class Coordonnee {

	private int x;
	private int y;

	public Coordonnee(int y, int x) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}

class Surface{
    
    private char[][] carte;

    public Surface(String[] rows, int H, int L){
        
        carte = new char[H][L];
        
        for (int i = 0; i < H; i++) {
            for(int j = 0; j < L ; j++){
                
                this.carte[i][j] = rows[i].charAt(j);
            }
        }
    }
    
    public long calculLac(int x, int y){
        
        long resultat = 0;
        //Stack lac = new Stack();  
        
//      Soit P une pile vide
        Deque<Coordonnee> lac = new ArrayDeque<>();
//      si couleur(pixel) ≠ colcible alors sortir de la fonction       
        if( carte[y][x] != 'O' ) return 0;

        Coordonnee depart = new Coordonnee(y,x);
//      Empiler pixel sur P        
        lac.push(depart);
//      Tant que P non vide       
        while(!lac.isEmpty() ){
//          Dépiler n de P            
            Coordonnee yx = lac.pop();
            
            int currentX = yx.getX();
            int currentY = yx.getY();
            
//            System.err.println("depart : " + currentX + "," + currentY + " : " + carte[currentY][currentX]);

//          si  couleur(n) = colcible            
            if( carte[currentY][currentX] == 'O'  ){

//              Déplacer w vers l'ouest jusqu'à ce que couleur(w) ≠ colcible
                Coordonnee w = new Coordonnee(currentY,currentX);
                
                int xWest = w.getX();
                int yWest = w.getY();      
                 
                int X = 0;    
                for(X = xWest; X >= 0; X-- ){
                    
//                    System.err.println("X, Y west : " + X + "," + yWest + " " + carte[ yWest ][ X ]);
                    
                    if( carte[ yWest ][ X ] != 'O' ){
                        break;
                    }

                }
                
                xWest = X+1;

//                System.err.println("X west final : " + xWest);
                
//              Déplacer e vers l'est   jusqu'à ce que couleur(e) ≠ colcible                
                Coordonnee e = new Coordonnee(currentY,currentX);
       
                int xEst = w.getX();
                int yEst = w.getY();      
                 
                X = 0;    
                for(X = xEst; X < carte[currentY].length; X++ ){
                    
//                    System.err.println("X, Y west : " + X + "," + yWest + " " + carte[ yWest ][ X ]);
                    
                    if( carte[ yWest ][ X ] != 'O' ){
                        break;
                    }

                }
                
                xEst = X-1;

//                System.err.println("X est final : " + xEst);          

                
//              Pour tout pixel p entre w et e               
                for(int i = xWest; i <= xEst; i++){
                    
                    resultat++;
                    carte[currentY][i] = '#';
                    
                    int yNord = currentY -1;
                    

//                  si couleur(p nord) = colcible alors Empiler p nord sur P finsi
                    if(  yNord >= 0  ){
                        
//                        System.err.println("X, Y  nord: " + i + "," + yNord + " " + carte[ yNord ][ i ]);
                        
                        if( carte[yNord][i] == 'O'  ){
                            
//                            System.err.println("push");
                            
                            lac.push( new Coordonnee(yNord,i) );
                        }
                        
                    }
                    
//                  si couleur(p sud ) = colcible alors Empiler p sud  sur P finsi                    
                    int ySud = currentY +1;
                    

                    
                    if(  ySud < carte.length  ){
                        
//                        System.err.println("X, Y  sud: " + i + "," + ySud + " " + carte[ ySud ][ i ]);
                        
                        if( carte[ySud][i] == 'O' ){
                            
//                            System.err.println("push");

                            lac.push( new Coordonnee(ySud,i) );
                        }
                        
                    }                    
                    
                }
                
            }// finsi
        }   //fintantque    
        
        return resultat ;
    }
    
    public void printCarte(){
        
            for (int k = 0; k < carte.length; k++) {
                
                for(int j = 0; j < carte[k].length ; j++){
                    
                   System.err.print(carte[k][j] + " ");
                   
                }
                
                System.err.print("\n");
                
            }        
    }

}


class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int L = in.nextInt();
        int H = in.nextInt();
        
        if (in.hasNextLine()) {
            in.nextLine();
        }
        
        String[] row = new String[H];
        for (int i = 0; i < H; i++) {
            
            row[i] = in.nextLine();
            
        }
        
        int N = in.nextInt();
        
        for (int i = 0; i < N; i++) {
            int X = in.nextInt();
            int Y = in.nextInt();
            
            Surface surface = new Surface( row, H, L );
                
 //           surface.printCarte();
        
            System.out.println(surface.calculLac(X,Y));
        }
        
    }
    

}
