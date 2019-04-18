import java.util.*;
import java.io.*;
import java.math.*;


class Coordonnees {
    
    private int x;
    private int y;
    
    public Coordonnees(int x, int y){
        
        this.x = x;
        this.y = y;
        
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    

}

class Zombie {
    
    int id;
    private Coordonnees coordonnees;
    private Coordonnees coordonneesSuivantes;
    
    public Zombie(int id, Coordonnees coordonnees, Coordonnees coordonneesSuivantes){
        this.id = id;
        this.coordonnees = coordonnees;
        this.coordonneesSuivantes = coordonneesSuivantes;
        
    }
    
    public Coordonnees getCoordonnees(){
        return this.coordonnees;
    }
    
    public Coordonnees getCoordonneesSuivantes(){
        return this.coordonneesSuivantes;
    }
    
    public int getId(){
        return this.id;
    }
    
}


class Survivant {
    
    int id;
    private Coordonnees coordonnees;

    public Survivant(int id, Coordonnees coordonnees) {
        this.id = id;
        this.coordonnees = coordonnees;
    }
    
    public Coordonnees getCoordonnees(){
        return this.coordonnees;
    }
    
    public int getId(){
        return this.id;
    }
    
    
}




class Player {

    public static void main(String args[]) {
        
        Scanner in = new Scanner(System.in);

        List<Integer> listeDesMorts = new ArrayList<>();

        // game loop
        while (true) {
            int x = in.nextInt();
            int y = in.nextInt();
            int humanCount = in.nextInt();

            Map<Integer,Survivant> survivants = new HashMap<>();
            
            for (int i = 0; i < humanCount; i++) {
                int humanId = in.nextInt();
                int humanX = in.nextInt();
                int humanY = in.nextInt();


                survivants.put(humanId, new Survivant( humanId, new Coordonnees( humanX , humanY ) ) );

            }
            int zombieCount = in.nextInt();

            Map<Integer,Zombie> zombies = new HashMap<>();
            
            for (int i = 0; i < zombieCount; i++) {
                 int zombieId = in.nextInt();
                 int zombieX = in.nextInt();
                 int zombieY = in.nextInt();
                 int zombieXNext = in.nextInt();
                 int zombieYNext = in.nextInt();


                 zombies.put(zombieId, new Zombie( zombieId , new Coordonnees(zombieX, zombieY), new Coordonnees(zombieXNext, zombieYNext) ) );
  
            }
             
        
            Coordonnees destination = new Coordonnees(x , y);
            
            String message = "AhAhAhAh !";
            
            int nbrCoupZombieSurvivantMin = Integer.MAX_VALUE;
            boolean noTarget = true;

            for(Survivant eachSurvivant : survivants.values() ){
                
                int distanceSurvivantJoueur = calculDistance( eachSurvivant.getCoordonnees().getX(), x, eachSurvivant.getCoordonnees().getY(), y ); 
                int nbrCoupJoueurSurvivant = (int)(distanceSurvivantJoueur/1000) ;

                for(Zombie eachZombie : zombies.values() ){
                    
                    int distanceSurvivantZombie = calculDistance( eachSurvivant.getCoordonnees().getX(),  eachZombie.getCoordonnees().getX(), eachSurvivant.getCoordonnees().getY(),  eachZombie.getCoordonnees().getY() );
                    int distanceZombiesJoueur =  calculDistance( eachZombie.getCoordonnees().getX(), x, eachZombie.getCoordonnees().getY(), y );
                    
                    if( distanceZombiesJoueur <= 2400){
                        message += "DIE ! ";
                    } 
                    boolean zombieEnDirectionDuSurvivant = appartiensALaDroite(eachZombie.getCoordonnees(), eachSurvivant.getCoordonnees(), eachZombie.getCoordonneesSuivantes() );
                    
                    if( zombieEnDirectionDuSurvivant && !listeDesMorts.contains( eachSurvivant.getId() ) ){

                        int nbrCoupJoueurZombie = (int)(distanceZombiesJoueur/2000) ;
                        int nbrCoupZombieSurvivant = (int)(distanceSurvivantZombie/400) ;


                        if( (nbrCoupJoueurZombie-1) > nbrCoupZombieSurvivant && (nbrCoupJoueurSurvivant-1) > nbrCoupZombieSurvivant){
                            
                            message = "NOOOOOOO !";
                            listeDesMorts.add( eachSurvivant.getId() );
                            
                        }
                        else {

                            nbrCoupZombieSurvivantMin = Math.min( nbrCoupZombieSurvivantMin , nbrCoupZombieSurvivant  );

                            if ( nbrCoupZombieSurvivantMin == nbrCoupZombieSurvivant){

                                    if ( nbrCoupJoueurZombie <= nbrCoupJoueurSurvivant ){
                                        destination  = zombies.get(eachZombie.getId()).getCoordonnees();
                                    }
                                    else {
                                        destination = survivants.get(eachSurvivant.getId()).getCoordonnees();
                                    }
                                    
                                    noTarget = false;
                            }

                        }
                    } else {

                                
                            if(noTarget){
                                destination =  zombies.get(eachZombie.getId()).getCoordonnees();
                            }
                    }

                }
           
            }
            

        System.out.println( destination.getX() + " " + destination.getY() + " " + message); 


        }

    }
    
    public static int calculDistance(int xa, int xb, int ya, int yb ){
        
        
        double xbxa =  xb - xa;
        double ybya =  yb - ya; 
        double powX =  Math.pow(xbxa,2);
        double powY =  Math.pow(ybya,2);
        double addition = powX + powY;
        double distance =  Math.sqrt(addition);
        
        return (int)distance;
        
        
    }
    
    public static boolean appartiensALaDroite(Coordonnees A, Coordonnees B, Coordonnees C ){
        
        // recherche si C appartiens à (A,B)
        
        // calcul des coeffiscient :
        double a = 0; 
        if( B.getX() != A.getX() ){
            a = (B.getY() - A.getY()) / (B.getX() - A.getX() * 1.0 );
        }
            
        double b = 0;
        if (  B.getY() != A.getY() ){
            b = A.getY() - a*A.getX();
        }
        
        if ( B.getX() == A.getX() && B.getX() == C.getX() ){
            return Math.abs( B.getY() - A.getY() ) > Math.abs(B.getY() - C.getY() ) ? true : false;
        }
        
            
        if( B.getY() == A.getY() &&  B.getY() == C.getY()  ) {
            return Math.abs( B.getX() - A.getX() ) > Math.abs(B.getX() - C.getX() ) ? true : false;
   
        }
        
        return  (Math.abs( (int)(a*C.getX() + b - C.getY()) ) ) <= 2  ? true : false ;        
        
        
    }
    
}
