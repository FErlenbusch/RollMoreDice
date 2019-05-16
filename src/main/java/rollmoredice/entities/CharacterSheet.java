package rollmoredice.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import rollmoredice.entities.audit.UserDateAudit;


@Table(
	    uniqueConstraints=
	        @UniqueConstraint(columnNames={"charName", "user_id"})
	)
@Entity
public class CharacterSheet extends UserDateAudit {
	
	private static final long serialVersionUID = -1439075559308208086L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User player;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "character_item",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> items = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "spell_character",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "spell_id"))
    private Set<Spell> spells = new HashSet<>();
	
	@NotBlank
	private String charName;
	
	@NotBlank
	private String charClass;
	
	@NotBlank
	private String level;
	
	@NotBlank
	private String background;
	
	@NotBlank
	private String race;
	
	private String subrace;
	
	@Lob
	private String traits;
	
	@Lob
	private String savingThrows;
	
	private String armorClass;
	
	private String speed;
	
	private String alignment;
	
	@NotBlank
	private String xp;
	
	@NotBlank
	private String str;
	
	@NotBlank
	private String dex;
	
	@NotBlank
	private String con;
	
	@NotBlank
	private String intel;
	
	@NotBlank
	private String wis;
	
	@NotBlank
	private String chr;
	
	@NotBlank
	private String hp;
	
	@Lob
	private String hitDie;
	
	private String exhaustion;
	
	private String deathSaves;
	
	@Lob
	@NotBlank
	private String languages;
	
	@Lob
	private String skills;
	
	@Lob
	private String equipment;
	
	@NotBlank
	private String ideals;
	
	@NotBlank
	private String bonds;
	
	@NotBlank
	private String flaws;
	
	@Lob
	private String activeAbilities;
	
	@Lob
	private String passiveAbilities;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public User getPlayer() {
		return player;
	}

	public void setPlayer(User player) {
		this.player = player;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public Set<Spell> getSpells() {
		return spells;
	}

	public void setSpells(Set<Spell> spells) {
		this.spells = spells;
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
}
