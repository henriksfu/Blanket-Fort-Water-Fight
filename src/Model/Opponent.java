package Model;

public class Opponent {
    private char ID;
    private int damage;
    private int undamaged_forts = 5;


    public Opponent() {
    }

    public Opponent(char ID) {
        this.ID = ID;
    }
    
    public int getUndamaged_forts() {
        return undamaged_forts;
    }

    public void setUndamaged_forts(int undamaged_forts) {
        this.undamaged_forts = undamaged_forts;
    }

    public void decreaseUndamaged_forts() {
        if(getUndamaged_forts() > 0) {
            undamaged_forts--;
        }
        else {
            System.out.println("All forts of this opponent are already damaged");
        }
    }

    public char getID() {
        return ID;
    }

    public int getDamage() {
        switch(getUndamaged_forts()) {
            case 0:
                damage = 0;
                break;  
            case 1:
                damage = 1;
                break;
            case 2:
                damage = 2;
                break;
            case 3:
                damage = 5;
                break;
            case 4:
            case 5:
                damage = 20;
                break;
            default:
                break;
        }
        return damage;
    }

    public void damageDone() {
        switch(getUndamaged_forts()) {
            case 0:
            case 1:
                damage = 20;
                break;  
            case 2:
                damage = 5;
                break;
            case 3:
                damage = 2;
                break;
            case 4:
                damage = 1;
                break;
            case 5:
                damage = 0;
                break;
            default:
                break;
        }
    }

}
