package rollmoredice.payload;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Required Format of JSON object to create a new Spell")
public class SpellRequest {
	
	@NotBlank
	@ApiModelProperty("Required")
	private String name;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String level;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String spellRange;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String type;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String castingTime;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String duration;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String components;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String damage;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String damageType;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String effects;
	

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
