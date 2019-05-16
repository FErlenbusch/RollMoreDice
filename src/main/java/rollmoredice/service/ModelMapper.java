package rollmoredice.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rollmoredice.entities.CharacterSheet;
import rollmoredice.entities.Game;
import rollmoredice.entities.Item;
import rollmoredice.entities.Spell;
import rollmoredice.entities.User;
import rollmoredice.exception.BadRequestException;
import rollmoredice.exception.ResourceNotFoundException;
import rollmoredice.payload.CharacterSheetRequest;
import rollmoredice.payload.CharacterSheetSummary;
import rollmoredice.payload.GameRequest;
import rollmoredice.payload.GameSummary;
import rollmoredice.payload.ItemRequest;
import rollmoredice.payload.ItemSummary;
import rollmoredice.payload.SpellRequest;
import rollmoredice.payload.SpellSummary;
import rollmoredice.payload.UserSummary;
import rollmoredice.repositories.CharacterSheetRepository;
import rollmoredice.repositories.ItemRepository;
import rollmoredice.repositories.SpellRepository;
import rollmoredice.util.AppConstants;

@Component
public class ModelMapper {
	
	@Autowired
	private CharacterSheetRepository characterRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private SpellRepository spellRepository;
	

	public CharacterSheetSummary mapCharacterSheetToSummary(CharacterSheet sheet) {
		CharacterSheetSummary summary = new CharacterSheetSummary();
		
		summary.setId(sheet.getId());
		summary.setPlayer(sheet.getPlayer());
		summary.setCharName(sheet.getCharName());
		summary.setCharClass(sheet.getCharClass());
		summary.setLevel(sheet.getLevel());
		summary.setBackground(sheet.getBackground());
		summary.setRace(sheet.getRace());
		summary.setSubrace(sheet.getSubrace());
		summary.setTraits(sheet.getTraits());
		summary.setSavingThrows(sheet.getSavingThrows());
		summary.setArmorClass(sheet.getArmorClass());
		summary.setSpeed(sheet.getSpeed());
		summary.setAlignment(sheet.getAlignment());
		summary.setXp(sheet.getXp());
		summary.setStr(sheet.getStr());
		summary.setDex(sheet.getDex());
		summary.setCon(sheet.getCon());
		summary.setIntel(sheet.getIntel());
		summary.setWis(sheet.getWis());
		summary.setChr(sheet.getChr());
		summary.setHp(sheet.getHp());
		summary.setHitDie(sheet.getHitDie());
		summary.setExhaustion(sheet.getExhaustion());
		summary.setDeathSaves(sheet.getDeathSaves());
		summary.setLanguages(sheet.getLanguages());
		summary.setSkills(sheet.getSkills());
		summary.setEquipment(sheet.getEquipment());
		summary.setIdeals(sheet.getIdeals());
		summary.setBonds(sheet.getBonds());
		summary.setFlaws(sheet.getFlaws());
		summary.setActiveAbilities(sheet.getActiveAbilities());
		summary.setPassiveAbilities(sheet.getPassiveAbilities());
		summary.setItems(itemSetToSummarySet(sheet.getItems()));
		summary.setSpells(spellSetToSummarySet(sheet.getSpells()));
		
		return summary;
	}
	
	public CharacterSheet mapRequestToCharacterSheet(CharacterSheetRequest request,
			User user) {
		
		CharacterSheet sheet = new CharacterSheet();
		
		sheet.setPlayer(user);
		sheet.setCharName(request.getCharName());
		sheet.setCharClass(request.getCharClass());
		sheet.setLevel(request.getLevel());
		sheet.setBackground(request.getBackground());
		sheet.setRace(request.getRace());
		sheet.setSubrace(request.getSubrace());
		sheet.setTraits(request.getTraits());
		sheet.setSavingThrows(request.getSavingThrows());
		sheet.setArmorClass(request.getArmorClass());
		sheet.setSpeed(request.getSpeed());
		sheet.setAlignment(request.getAlignment());
		sheet.setXp(request.getXp());
		sheet.setStr(request.getStr());
		sheet.setDex(request.getDex());
		sheet.setCon(request.getCon());
		sheet.setIntel(request.getIntel());
		sheet.setWis(request.getWis());
		sheet.setChr(request.getChr());
		sheet.setHp(request.getHp());
		sheet.setHitDie(request.getHitDie());
		sheet.setExhaustion(request.getExhaustion());
		sheet.setDeathSaves(request.getDeathSaves());
		sheet.setLanguages(request.getLanguages());
		sheet.setSkills(request.getSkills());
		sheet.setEquipment(request.getEquipment());
		sheet.setIdeals(request.getIdeals());
		sheet.setBonds(request.getBonds());
		sheet.setFlaws(request.getFlaws());
		sheet.setActiveAbilities(request.getActiveAbilities());
		sheet.setPassiveAbilities(request.getPassiveAbilities());
		sheet.setItems(itemSummarySetToItemSet(request.getItems()));
		sheet.setSpells(spellSummarySetToSpellSet(request.getSpells()));
		
		return sheet;
	}
	
	public CharacterSheet mapSummaryToCharacterSheet(CharacterSheet oldSheet,
			CharacterSheetSummary summary) {
	
		oldSheet.setCharName(summary.getCharName());
		oldSheet.setCharClass(summary.getCharClass());
		oldSheet.setLevel(summary.getLevel());
		oldSheet.setBackground(summary.getBackground());
		oldSheet.setRace(summary.getRace());
		oldSheet.setSubrace(summary.getSubrace());
		oldSheet.setTraits(summary.getTraits());
		oldSheet.setSavingThrows(summary.getSavingThrows());
		oldSheet.setArmorClass(summary.getArmorClass());
		oldSheet.setSpeed(summary.getSpeed());
		oldSheet.setAlignment(summary.getAlignment());
		oldSheet.setXp(summary.getXp());
		oldSheet.setStr(summary.getStr());
		oldSheet.setDex(summary.getDex());
		oldSheet.setCon(summary.getCon());
		oldSheet.setIntel(summary.getIntel());
		oldSheet.setWis(summary.getWis());
		oldSheet.setChr(summary.getChr());
		oldSheet.setHp(summary.getHp());
		oldSheet.setHitDie(summary.getHitDie());
		oldSheet.setExhaustion(summary.getExhaustion());
		oldSheet.setDeathSaves(summary.getDeathSaves());
		oldSheet.setLanguages(summary.getLanguages());
		oldSheet.setSkills(summary.getSkills());
		oldSheet.setEquipment(summary.getEquipment());
		oldSheet.setIdeals(summary.getIdeals());
		oldSheet.setBonds(summary.getBonds());
		oldSheet.setFlaws(summary.getFlaws());
		oldSheet.setActiveAbilities(summary.getActiveAbilities());
		oldSheet.setPassiveAbilities(summary.getPassiveAbilities());
		oldSheet.setItems(itemSummarySetToItemSet(summary.getItems()));
		oldSheet.setSpells(spellSummarySetToSpellSet(summary.getSpells()));
		
		return oldSheet;
	}
	
	public GameSummary mapGameToSummary(Game game) {
		GameSummary summary = new GameSummary();
		
		summary.setId(game.getId());
		summary.setDungeonMaster(game.getDungeonMaster());
		summary.setGameName(game.getGameName());
		summary.setPlayers(userSetToUserSummarySet(game.getPlayers()));
		summary.setPlayerCharacters(characterSheetSetToSummarySet(game.getPlayerCharacters()));
		summary.setNonPlayerCharacters(characterSheetSetToSummarySet(game.getNonPlayerCharacters()));
		summary.setItems(itemSetToSummarySet(game.getItems()));
		summary.setSpells(spellSetToSummarySet(game.getSpells()));
		
		return summary;
	}
	
	public Game mapRequestToGame(GameRequest request, User user) {
		Game game = new Game();
		
		game.setDungeonMaster(user);
		game.setGameName(request.getGameName());
		game.setPlayerCharacters(characterSummarySetToSheetSet(request.getPlayerCharacters()));
		game.setNonPlayerCharacters(characterSummarySetToSheetSet(request.getNonPlayerCharacters()));
		game.setItems(itemSummarySetToItemSet(request.getItems()));
		game.setSpells(spellSummarySetToSpellSet(request.getSpells()));
		
		return game;
	}
	
	public Game mapSummaryToGame(Game oldGame, GameSummary summary) {
		oldGame.setGameName(summary.getGameName());
		oldGame.setPlayerCharacters(characterSummarySetToSheetSet(summary.getPlayerCharacters()));
		oldGame.setNonPlayerCharacters(characterSummarySetToSheetSet(summary.getNonPlayerCharacters()));
		oldGame.setItems(itemSummarySetToItemSet(summary.getItems()));
		oldGame.setSpells(spellSummarySetToSpellSet(summary.getSpells()));
		
		return oldGame;
	}
	
	public ItemSummary mapItemToSummary(Item item) {
		ItemSummary summary = new ItemSummary();
		
		summary.setId(item.getId());
		summary.setCreator(item.getCreator());
		summary.setName(item.getName());
		summary.setType(item.getType());
		summary.setStats(item.getStats());
		summary.setAbilities(item.getAbilities());
		
		return summary;
	}
	
	public Item mapRequestToItem(ItemRequest request, User user) {
		Item item = new Item();
		
		item.setCreator(user);
		item.setName(request.getName());
		item.setType(request.getType());
		item.setStats(request.getStats());
		item.setAbilities(request.getAbilities());
		
		return item;
	}

	public Item mapSummaryToItem(Item oldItem, ItemSummary summary) {
		oldItem.setName(summary.getName());
		oldItem.setType(summary.getType());
		oldItem.setStats(summary.getStats());
		oldItem.setAbilities(summary.getAbilities());
		
		return oldItem;
	}
	
	public SpellSummary mapSpellToSummary(Spell spell) {
		SpellSummary summary = new SpellSummary();
		
		summary.setId(spell.getId());
		summary.setCreator(spell.getCreator());
		summary.setName(spell.getName());
		summary.setLevel(spell.getLevel());
		summary.setSpellRange(spell.getSpellRange());
		summary.setType(spell.getType());
		summary.setCastingTime(spell.getCastingTime());
		summary.setDuration(spell.getCastingTime());
		summary.setComponents(spell.getComponents());
		summary.setDamage(spell.getDamage());
		summary.setDamageType(spell.getDamageType());
		summary.setEffects(spell.getEffects());
		
		return summary;
	}
	
	public Spell mapRequestToSpell(SpellRequest request, User user) {
		Spell spell = new Spell();
		
		spell.setCreator(user);
		spell.setName(request.getName());
		spell.setLevel(request.getLevel());
		spell.setSpellRange(request.getSpellRange());
		spell.setType(request.getType());
		spell.setCastingTime(request.getCastingTime());
		spell.setDuration(request.getCastingTime());
		spell.setComponents(request.getComponents());
		spell.setDamage(request.getDamage());
		spell.setDamageType(request.getDamageType());
		spell.setEffects(request.getEffects());
		
		return spell;
	}
	
	public Spell mapSummaryToSpell(Spell oldSpell, SpellSummary summary) {
		oldSpell.setName(summary.getName());
		oldSpell.setLevel(summary.getLevel());
		oldSpell.setSpellRange(summary.getSpellRange());
		oldSpell.setType(summary.getType());
		oldSpell.setCastingTime(summary.getCastingTime());
		oldSpell.setDuration(summary.getCastingTime());
		oldSpell.setComponents(summary.getComponents());
		oldSpell.setDamage(summary.getDamage());
		oldSpell.setDamageType(summary.getDamageType());
		oldSpell.setEffects(summary.getEffects());
		
		return oldSpell;
	}
	
	private Set<UserSummary> userSetToUserSummarySet(Set<User> users) {
		Set<UserSummary> summaries = new HashSet<>();
		
		for (User user : users) {
			summaries.add(new UserSummary(user.getID(), user.getUsername(), user.getEmail()));
		}
		
		return summaries;
	}
	
	private Set<CharacterSheetSummary> characterSheetSetToSummarySet(Set<CharacterSheet> sheets) {
		Set<CharacterSheetSummary> summaries = new HashSet<>();
		
		for (CharacterSheet sheet : sheets) {
			summaries.add(mapCharacterSheetToSummary(sheet));
		}
		
		return summaries;
	}
	
	private Set<CharacterSheet> characterSummarySetToSheetSet(Set<CharacterSheetSummary> summaries) {
		Set<CharacterSheet> sheets = new HashSet<>();
		
		for (CharacterSheetSummary summary : summaries) {
			CharacterSheet sheet = characterRepository.findById(summary.getId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"CharacterSheet", "characterId", summary.getId()));
			
			sheets.add(mapSummaryToCharacterSheet(sheet, summary));
		}
		
		return sheets;
	}
	
	private Set<ItemSummary> itemSetToSummarySet(Set<Item> items) {
		Set<ItemSummary> summaries = new HashSet<>();
		
		for (Item item : items) {
			summaries.add(mapItemToSummary(item));
		}
		
		return summaries;
	}
	
	private Set<Item> itemSummarySetToItemSet(Set<ItemSummary> summaries) {
		Set<Item> items = new HashSet<>();
		
		for (ItemSummary summary : summaries) {
			Item item = itemRepository.findById(summary.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Item", "itemId", summary.getId()));
			
			items.add(mapSummaryToItem(item, summary));
		}
		
		return items;
	}
	
	private Set<SpellSummary> spellSetToSummarySet(Set<Spell> spells) {
		Set<SpellSummary> summaries = new HashSet<>();
		
		for (Spell spell : spells) {
			summaries.add(mapSpellToSummary(spell));
		}
		
		return summaries;
	}
	
	private Set<Spell> spellSummarySetToSpellSet(Set<SpellSummary> summaries) {
		Set<Spell> spells = new HashSet<>();
		
		for (SpellSummary summary : summaries) {
			Spell spell = spellRepository.findById(summary.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Spell", "spellId", summary.getId()));
			
			spells.add(mapSummaryToSpell(spell, summary));
		}
		
		return spells;
	}
	
//	private User userFromSummary(UserSummary summary) {
//		
//		System.out.println("PLAYER ID: " + summary.getId());
//		
//		User user = userRepository.findById(summary.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", summary.getId()));
//		
//		return user;
//	}
	
	public void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
