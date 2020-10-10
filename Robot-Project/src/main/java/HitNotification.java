/* A structure for a notifcation message */
public class HitNotification 
{
	private String attacker;
	public String getAttacker() {
		return attacker;
	}

	public String getVictim() {
		return victim;
	}

	private String victim;
	
	public HitNotification(String attack, String victim )
	{
	this.attacker = attack;
	this.victim = victim;
	}
}
