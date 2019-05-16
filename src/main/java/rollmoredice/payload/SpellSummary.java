package rollmoredice.payload;

import io.swagger.annotations.ApiModel;
import rollmoredice.entities.User;


@ApiModel(description = "Format of JSON object returned from the API, and to be sent to the API "
		+ "for updates of a Spell")
public class SpellSummary {
	
	private long id;

	private UserSummary creator;
	
	private String name;
	
	private String level;
	
	private String spellRange;
	
	private String type;
	
	private String castingTime;
	
	private String duration;
	
	private String components;
	
	private String damage;
	
	private String damageType;
	
	private String effects;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserSummary getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = new UserSummary(creator.getID(),
				creator.getUsername(), creator.getEmail());;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSpellRange() {
		return spellRange;
	}

	public void setSpellRange(String spellRange) {
		this.spellRange = spellRange;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCastingTime() {
		return castingTime;
	}

	public void setCastingTime(String castingTime) {
		this.castingTime = castingTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getComponents() {
		return components;
	}

	public void setComponents(String components) {
		this.components = components;
	}

	public String getDamage() {
		return damage;
	}

	public void setDamage(String damage) {
		this.damage = damage;
	}

	public String getDamageType() {
		return damageType;
	}

	public void setDamageType(String damageType) {
		this.damageType = damageType;
	}

	public String getEffects() {
		return effects;
	}

	public void setEffects(String effects) {
		this.effects = effects;
	}
}
