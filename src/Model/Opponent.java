package Model;

public class Opponent {
    private int damage;
    private int undamaged_forts;


    public Opponent() {
    }
    
    public int getUndamaged_forts() {
        return undamaged_forts;
    }

    public int getDamage() {
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
