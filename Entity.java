public class Entity{
	String name;
	int maxHealth;
	int health;
	byte armour;
	byte energy;
	
	public void setName(String name){
		this.name=name;
	}
	public void setHealth(int health){
		if (health>=maxHealth){
			this.health=maxHealth;
		}else if(health<=0){
			this.health=0;
		}else{
			this.health=health;
		}
	}
	public void setMax(int maximumHealth){
		if (maximumHealth>=1){
			this.maxHealth=maximumHealth;
		}else{
			this.maxhealth=1;
		}
	}
	public void setArmour(byte armour){
		if (armour>=0){
			this.armour=armour;
		}else{
			this.armour=0;
		}
	}
	public void setEnergy(byte energy){
		if (energy>=0){
			this.energy=energy;
		}else{
			this.energy=0;
		}