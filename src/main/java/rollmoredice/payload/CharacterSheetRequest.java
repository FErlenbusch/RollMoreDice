package rollmoredice.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "Required Format of JSON object to create a new Character Sheet")
public class CharacterSheetRequest {
	
	@NotBlank
	@ApiModelProperty("Required")
	private String charName;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String charClass;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String level;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String background;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String race;
	
	private String subrace;
	
	private String traits;
	
	private String savingThrows;
	
	private String armorClass;
	
	private String speed;
	
	private String alignment;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String xp;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String str;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String dex;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String con;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String intel;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String wis;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String chr;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String hp;
	
	private String hitDie;
	
	private String exhaustion;
	
	private String deathSaves;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String languages;
	
	private String skills;
	
	private String equipment;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String ideals;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String bonds;
	
	@NotBlank
	@ApiModelProperty("Required")
	private String flaws;
	
	private String activeAbilities;
	
	private String passiveAbilities;
	
	private Set<ItemSummary> items = new HashSet<>();
	
	private Set<SpellSummary> spells = new HashSet<>();

	
	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	public String getCharClass() {
		return charClass;
	}

	public void setCharClass(String charClass) {
		this.charClass = charClass;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getSubrace() {
		return subrace;
	}

	public void setSubrace(String subrace) {
		this.subrace = subrace;
	}

	public String getTraits() {
		return traits;
	}

	public void setTraits(String traits) {
		this.traits = traits;
	}

	public String getSavingThrows() {
		return savingThrows;
	}

	public void setSavingThrows(String savingThrows) {
		this.savingThrows = savingThrows;
	}

	public String getArmorClass() {
		return armorClass;
	}

	public void setArmorClass(String armorClass) {
		this.armorClass = armorClass;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public String getXp() {
		return xp;
	}

	public void setXp(String xp) {
		this.xp = xp;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getDex() {
		return dex;
	}

	public void setDex(String dex) {
		this.dex = dex;
	}

	public String getCon() {
		return con;
	}

	public void setCon(String con) {
		this.con = con;
	}

	public String getIntel() {
		return intel;
	}

	public void setIntel(String intel) {
		this.intel = intel;
	}

	public String getWis() {
		return wis;
	}

	public void setWis(String wis) {
		this.wis = wis;
	}

	public String getChr() {
		return chr;
	}

	public void setChr(String chr) {
		this.chr = chr;
	}

	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public String getHitDie() {
		return hitDie;
	}

	public void setHitDie(String hitDie) {
		this.hitDie = hitDie;
	}

	public String getExhaustion() {
		return exhaustion;
	}

	public void setExhaustion(String exhaustion) {
		this.exhaustion = exhaustion;
	}

	public String getDeathSaves() {
		return deathSaves;
	}

	public void setDeathSaves(String deathSaves) {
		this.deathSaves = deathSaves;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getIdeals() {
		return ideals;
	}

	public void setIdeals(String ideals) {
		this.ideals = ideals;
	}

	public String getBonds() {
		return bonds;
	}

	public void setBonds(String bonds) {
		this.bonds = bonds;
	}

	public String getFlaws() {
		return flaws;
	}

	public void setFlaws(String flaws) {
		this.flaws = flaws;
	}

	public String getActiveAbilities() {
		return activeAbilities;
	}

	public void setActiveAbilities(String activeAbilities) {
		this.activeAbilities = activeAbilities;
	}

	public String getPassiveAbilities() {
		return passiveAbilities;
	}

	public void setPassiveAbilities(String passiveAbilities) {
		this.passiveAbilities = passiveAbilities;
	}

	public Set<ItemSummary> getItems() {
		return items;
	}

	public void setItems(Set<ItemSummary> items) {
		this.items = items;
	}

	public Set<SpellSummary> getSpells() {
		return spells;
	}

	public void setSpells(Set<SpellSummary> spells) {
		this.spells = spells;
	}
	
	
}
