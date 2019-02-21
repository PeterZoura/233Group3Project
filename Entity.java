public class Entity{
	private String name;
	private int maxHealth;
	private int health;
	private byte armour;
	private byte energy;
	
	public Entity(String name, int maxHealth, int health, int armour, int energy){
		this.name = name;
		this.maxHealth = maxHealth;
		this.health = health;
		this.armour = armour;
		this.energy = energy;
	}
	
	public Entity(Entity anEntity){
		this(anEntity.getName(), anEntity.getMax(), anEntity.getHealth(), anEntity.getArmour(), anEntity.getEnergy());
	}
	
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
			this.maxHealth=1;
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
	}
	public String getName(){
		return name;
	}
	public int getMax(){
		return maxHealth;
	}
	public int getHealth(){
		return health;
	}
	public byte getArmour(){
		return armour;
	}
	public byte getEnergy(){
		return energy;
	}
	
	public boolean Alive()
	{
		if(health>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
