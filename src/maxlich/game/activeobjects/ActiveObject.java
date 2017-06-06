package maxlich.game.activeobjects;

/**
 * Created by Максим on 23.03.2017.
 */
abstract class ActiveObject { //активный объект - враг, игрок, нейтральные люди и существа
    protected HealthPoints healthPoints = new HealthPoints();// массив, 1-ый элемент - текущее ХП, второй - общее, максимальное, начальное
    protected Damage dmg = new Damage(); //объект Damage с полями: минимальный урон, максимальный урон, текущий урон
    protected int location = 5;
    public static final int MAXLOCATION = 5;

    ActiveObject() {
        this(30, 2, 5);
    }

    ActiveObject(int hp, int minDamage, int maxDamage) {
        this.healthPoints.current = hp;
        this.healthPoints.current = hp;
        this.dmg.min = minDamage;
        this.dmg.max = maxDamage;
    }

    public void setMaxHealth(int maxHealth) {
        if (this.healthPoints.current == this.healthPoints.max) this.healthPoints.current = maxHealth;
        this.healthPoints.max = maxHealth;
    }

    public int getMaxHeath() {
        return healthPoints.max;
    }

    public void setCurrentHealth(int currentHealth) {
        this.healthPoints.current = currentHealth;
    }

    public int getCurrentHeath() {
        return healthPoints.current;
    }

    public void setMinDamage(int minDamage) {
        this.dmg.min = minDamage;
    }

    public int getMinDamage() {
        return dmg.min;
    }

    public void setMaxDamage(int maxDamage) {
        this.dmg.max = maxDamage;
    }

    public int getMaxDamage() {
        return dmg.max;
    }

    /*    public void setCuttentDamage(int maxDamage) {
            this.dmg[2] = maxDamage;
        }*/
    public int getCurrentDamage() {
        return dmg.current;
    }

    @Override
    public String toString() {
        return "очки здоровья - " + this.healthPoints.max + ", урон - " + this.dmg.min + "-" + this.dmg.max;
    }

    public void printCharacteristics() {
        System.out.println(this.toString());
    }

    public void printHealth() {
        System.out.print(this.healthPoints.current + "/" + this.healthPoints.max);
        System.out.println();
    }


    public int damage(ActiveObject ao) {
        int tempMaxDamage = this.dmg.min - this.dmg.max;
        this.dmg.current = (int) (Math.random() * ++tempMaxDamage) + this.dmg.min;
        //ao.healthPoints[0] -= this.dmg[1];
        ao.setCurrentHealth(ao.getCurrentHeath() - this.dmg.current);
        return this.dmg.current;
    }

    public int getRandomLocation() {
        this.location = (int) (Math.random() * MAXLOCATION);
        return this.location;
    }

    public int getCurrentLocation() {
        return this.location;

    }

    abstract public boolean attackOrMove();

    abstract public int attack(ActiveObject ao);

    public boolean checkLocation(int loc) {
        return (this.location == loc);
    }

    protected int motion(String delta) {
        if (delta.equals("+")) {
            if (this.location == MAXLOCATION) this.location--;
            else this.location++;
        } else if (delta.equals("-")) {
            if (this.location == 0) this.location++;
            else this.location--;
        }

        return this.location;
    }

    protected class Damage {
        public int min;
        public int max;
        public int current;
    }

    protected class HealthPoints {
        public int current;
        public int max;
    }

}
