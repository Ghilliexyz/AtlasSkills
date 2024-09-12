package com.atlasplugins.atlasskills.guis;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.managers.levelsystem.LevelManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SkillsGui extends Gui {

    private Main main;
    private Player player;
    private LevelManager levelManager;

    public SkillsGui(Main main, Player player, LevelManager levelManager) {
        // Directly pass the fetched values to super()
        super(player,
                Main.color(main.getSkillsConfig().getString("Skills-Gui.Skills-Menu.Skills-Menu-Title")), 36);

        this.player = player;
        this.levelManager = levelManager;

        // Continue with the rest of your constructor logic
        this.main = main;
        setupItems();
    }



    @Override
    public void setupItems() {
        int acrobaticsLevel = levelManager.getLevel(player.getUniqueId(), LevelManager.Skill.ACROBATICS);
        int acrobaticsXp = levelManager.getXP(player.getUniqueId(), LevelManager.Skill.ACROBATICS);
        int acrobaticsXpToNextLvl = levelManager.getXPForNextLevel(acrobaticsLevel);

        int alchemyLevel = levelManager.getLevel(player.getUniqueId(), LevelManager.Skill.ALCHEMY);
        int alchemyXp = levelManager.getXP(player.getUniqueId(), LevelManager.Skill.ALCHEMY);
        int alchemyXpToNextLvl = levelManager.getXPForNextLevel(alchemyLevel);

        int archeryLevel = levelManager.getLevel(player.getUniqueId(), LevelManager.Skill.ARCHERY);
        int archeryXp = levelManager.getXP(player.getUniqueId(), LevelManager.Skill.ARCHERY);
        int archeryXpToNextLvl = levelManager.getXPForNextLevel(archeryLevel);

        int axesLevel = levelManager.getLevel(player.getUniqueId(), LevelManager.Skill.AXES);
        int axesXp = levelManager.getXP(player.getUniqueId(), LevelManager.Skill.AXES);
        int axesXpToNextLvl = levelManager.getXPForNextLevel(axesLevel);

        int excavationLevel = levelManager.getLevel(player.getUniqueId(), LevelManager.Skill.EXCAVATION);
        int excavationXp = levelManager.getXP(player.getUniqueId(), LevelManager.Skill.EXCAVATION);
        int excavationXpToNextLvl = levelManager.getXPForNextLevel(excavationLevel);

        int fishingLevel = levelManager.getLevel(player.getUniqueId(), LevelManager.Skill.FISHING);
        int fishingXp = levelManager.getXP(player.getUniqueId(), LevelManager.Skill.FISHING);
        int fishingXpToNextLvl = levelManager.getXPForNextLevel(fishingLevel);

        int herbalismLevel = levelManager.getLevel(player.getUniqueId(), LevelManager.Skill.HERBALISM);
        int herbalismXp = levelManager.getXP(player.getUniqueId(), LevelManager.Skill.HERBALISM);
        int herbalismXpToNextLvl = levelManager.getXPForNextLevel(herbalismLevel);

        int miningLevel = levelManager.getLevel(player.getUniqueId(), LevelManager.Skill.MINING);
        int miningXp = levelManager.getXP(player.getUniqueId(), LevelManager.Skill.MINING);
        int miningXpToNextLvl = levelManager.getXPForNextLevel(miningLevel);

        int swordLevel = levelManager.getLevel(player.getUniqueId(), LevelManager.Skill.SWORDS);
        int swordXp = levelManager.getXP(player.getUniqueId(), LevelManager.Skill.SWORDS);
        int swordXpToNextLvl = levelManager.getXPForNextLevel(swordLevel);

        int tamingLevel = levelManager.getLevel(player.getUniqueId(), LevelManager.Skill.TAMING);
        int tamingXp = levelManager.getXP(player.getUniqueId(), LevelManager.Skill.TAMING);
        int tamingXpToNextLvl = levelManager.getXPForNextLevel(tamingLevel);

        int unarmedLevel = levelManager.getLevel(player.getUniqueId(), LevelManager.Skill.UNARMED);
        int unarmedXp = levelManager.getXP(player.getUniqueId(), LevelManager.Skill.UNARMED);
        int unarmedXpToNextLvl = levelManager.getXPForNextLevel(unarmedLevel);

        int woodcuttingLevel = levelManager.getLevel(player.getUniqueId(), LevelManager.Skill.WOODCUTTING);
        int woodcuttingXp = levelManager.getXP(player.getUniqueId(), LevelManager.Skill.WOODCUTTING);
        int woodcuttingXpToNextLvl = levelManager.getXPForNextLevel(woodcuttingLevel);

        // Set up the specific items for this GUI \\
        // ---------- Acrobatics Skill ---------- \\
        // Get Config Stats \\
        int AcrobaticsSlot = main.getSkillsConfig().getInt("Skills-Gui.Acrobatics-Slot.Acrobatics-InventorySlot");
        String AcrobaticsTitle = main.getSkillsConfig().getString("Skills-Gui.Acrobatics-Slot.Acrobatics-Title");
        Material AcrobaticsConfigItem = Material.valueOf(main.getSkillsConfig().getString("Skills-Gui.Acrobatics-Slot.Acrobatics-Item"));
        // Create Item \\
        ItemStack AcrobaticsItem = new ItemStack(AcrobaticsConfigItem);
        ItemMeta AcrobaticsMeta = AcrobaticsItem.getItemMeta();
        // Set Title \\
        String AcrobaticsDisplayName = Main.color(AcrobaticsTitle).replace("{Player}", player.getName());
        String AcrobaticsDisplayNamePAPISet = main.setPlaceholders(player, AcrobaticsDisplayName);
        assert AcrobaticsMeta != null;
        AcrobaticsMeta.setDisplayName(Main.color(AcrobaticsDisplayNamePAPISet));
        // Set Lore \\
        ArrayList<String> AcrobaticsLore = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Skills-Gui.Acrobatics-Slot.Acrobatics-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            AcrobaticsLore.add(Main.color(withPAPISet)
                    .replace("{acrobaticsLevel}", String.valueOf(acrobaticsLevel))
                    .replace("{acrobaticsXp}", String.valueOf(acrobaticsXp))
                    .replace("{acrobaticsXpToNextLvl}", String.valueOf(acrobaticsXpToNextLvl)));
        }
        // Set all values \\
        AcrobaticsMeta.setLore(AcrobaticsLore);
        AcrobaticsItem.setItemMeta(AcrobaticsMeta);
        // Place Item in correct slot \\
        inventory.setItem(AcrobaticsSlot, AcrobaticsItem);
        // ---------- Alchemy Skill ---------- \\
        // Get Config Stats \\
        int AlchemySlot = main.getSkillsConfig().getInt("Skills-Gui.Alchemy-Slot.Alchemy-InventorySlot");
        String AlchemySlotTitle = main.getSkillsConfig().getString("Skills-Gui.Alchemy-Slot.Alchemy-Title");
        Material AlchemyConfigItem = Material.valueOf(main.getSkillsConfig().getString("Skills-Gui.Alchemy-Slot.Alchemy-Item"));
        // Create Item \\
        ItemStack AlchemyItem = new ItemStack(AlchemyConfigItem);
        ItemMeta AlchemyMeta = AlchemyItem.getItemMeta();
        // Set Title \\
        String AlchemyDisplayName = Main.color(AlchemySlotTitle).replace("{Player}", player.getName());
        String AlchemyDisplayNamePAPISet = main.setPlaceholders(player, AlchemyDisplayName);
        assert AlchemyMeta != null;
        AlchemyMeta.setDisplayName(Main.color(AlchemyDisplayNamePAPISet));
        // Set Lore \\
        ArrayList<String> AlchemySlotLore = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Skills-Gui.Alchemy-Slot.Alchemy-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            AlchemySlotLore.add(Main.color(withPAPISet)
                    .replace("{alchemyLevel}", String.valueOf(alchemyLevel))
                    .replace("{alchemyXp}", String.valueOf(alchemyXp))
                    .replace("{alchemyXpToNextLvl}", String.valueOf(alchemyXpToNextLvl)));
        }
        // Set all values \\
        AlchemyMeta.setLore(AlchemySlotLore);
        AlchemyItem.setItemMeta(AlchemyMeta);
        // Place Item in correct slot \\
        inventory.setItem(AlchemySlot, AlchemyItem);
        // ---------- Archery Skill ---------- \\
        // Get Config Stats \\
        int ArcherySlot = main.getSkillsConfig().getInt("Skills-Gui.Archery-Slot.Archery-InventorySlot");
        String ArcherySlotTitle = main.getSkillsConfig().getString("Skills-Gui.Archery-Slot.Archery-Title");
        Material ArcheryConfigItem = Material.valueOf(main.getSkillsConfig().getString("Skills-Gui.Archery-Slot.Archery-Item"));
        // Create Item \\
        ItemStack ArcheryItem = new ItemStack(ArcheryConfigItem);
        ItemMeta ArcheryMeta = ArcheryItem.getItemMeta();
        // Set Title \\
        String ArcheryDisplayName = Main.color(ArcherySlotTitle).replace("{Player}", player.getName());
        String ArcheryDisplayNamePAPISet = main.setPlaceholders(player, ArcheryDisplayName);
        assert ArcheryMeta != null;
        ArcheryMeta.setDisplayName(Main.color(ArcheryDisplayNamePAPISet));
        // Set Lore \\
        ArrayList<String> ArcherySlotLore = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Skills-Gui.Archery-Slot.Archery-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            ArcherySlotLore.add(Main.color(withPAPISet)
                    .replace("{archeryLevel}", String.valueOf(archeryLevel))
                    .replace("{archeryXp}", String.valueOf(archeryXp))
                    .replace("{archeryXpToNextLvl}", String.valueOf(archeryXpToNextLvl)));
        }
        // Set all values \\
        ArcheryMeta.setLore(ArcherySlotLore);
        ArcheryItem.setItemMeta(ArcheryMeta);
        // Place Item in correct slot \\
        inventory.setItem(ArcherySlot, ArcheryItem);
        // ---------- Axes Skill ---------- \\
        // Get Config Stats \\
        int AxeSlot = main.getSkillsConfig().getInt("Skills-Gui.Axes-Slot.Axes-InventorySlot");
        String AxeSlotTitle = main.getSkillsConfig().getString("Skills-Gui.Axes-Slot.Axes-Title");
        Material AxeConfigItem = Material.valueOf(main.getSkillsConfig().getString("Skills-Gui.Axes-Slot.Axes-Item"));
        // Create Item \\
        ItemStack AxeItem = new ItemStack(AxeConfigItem);
        ItemMeta AxeMeta = AxeItem.getItemMeta();
        // Set Title \\
        String AxeDisplayName = Main.color(AxeSlotTitle).replace("{Player}", player.getName());
        String AxeDisplayNamePAPISet = main.setPlaceholders(player, AxeDisplayName);
        assert AxeMeta != null;
        AxeMeta.setDisplayName(Main.color(AxeDisplayNamePAPISet));
        // Set Lore \\
        ArrayList<String> AxeSlotLore = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Skills-Gui.Axes-Slot.Axes-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            AxeSlotLore.add(Main.color(withPAPISet)
                    .replace("{axesLevel}", String.valueOf(axesLevel))
                    .replace("{axesXp}", String.valueOf(axesXp))
                    .replace("{axesXpToNextLvl}", String.valueOf(axesXpToNextLvl)));
        }
        // Set all values \\
        AxeMeta.setLore(AxeSlotLore);
        AxeItem.setItemMeta(AxeMeta);
        // Place Item in correct slot \\
        inventory.setItem(AxeSlot, AxeItem);
        // ---------- Excavation Skill ---------- \\
        // Get Config Stats \\
        int ExcavationSlot = main.getSkillsConfig().getInt("Skills-Gui.Excavation-Slot.Excavation-InventorySlot");
        String ExcavationSlotTitle = main.getSkillsConfig().getString("Skills-Gui.Excavation-Slot.Excavation-Title");
        Material ExcavationConfigItem = Material.valueOf(main.getSkillsConfig().getString("Skills-Gui.Excavation-Slot.Excavation-Item"));
        // Create Item \\
        ItemStack ExcavationItem = new ItemStack(ExcavationConfigItem);
        ItemMeta ExcavationMeta = ExcavationItem.getItemMeta();
        // Set Title \\
        String ExcavationDisplayName = Main.color(ExcavationSlotTitle).replace("{Player}", player.getName());
        String ExcavationDisplayNamePAPISet = main.setPlaceholders(player, ExcavationDisplayName);
        assert ExcavationMeta != null;
        ExcavationMeta.setDisplayName(Main.color(ExcavationDisplayNamePAPISet));
        // Set Lore \\
        ArrayList<String> ExcavationSlotLore = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Skills-Gui.Excavation-Slot.Excavation-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            ExcavationSlotLore.add(Main.color(withPAPISet)
                    .replace("{excavationLevel}", String.valueOf(excavationLevel))
                    .replace("{excavationXp}", String.valueOf(excavationXp))
                    .replace("{excavationXpToNextLvl}", String.valueOf(excavationXpToNextLvl)));
        }
        // Set all values \\
        ExcavationMeta.setLore(ExcavationSlotLore);
        ExcavationItem.setItemMeta(ExcavationMeta);
        // Place Item in correct slot \\
        inventory.setItem(ExcavationSlot, ExcavationItem);
        // ---------- Fishing Skill ---------- \\
        // Get Config Stats \\
        int FishingSlot = main.getSkillsConfig().getInt("Skills-Gui.Fishing-Slot.Fishing-InventorySlot");
        String FishingSlotTitle = main.getSkillsConfig().getString("Skills-Gui.Fishing-Slot.Fishing-Title");
        Material FishingConfigItem = Material.valueOf(main.getSkillsConfig().getString("Skills-Gui.Fishing-Slot.Fishing-Item"));
        // Create Item \\
        ItemStack FishingItem = new ItemStack(FishingConfigItem);
        ItemMeta FishingMeta = FishingItem.getItemMeta();
        // Set Title \\
        String FishingDisplayName = Main.color(FishingSlotTitle).replace("{Player}", player.getName());
        String FishingDisplayNamePAPISet = main.setPlaceholders(player, FishingDisplayName);
        assert FishingMeta != null;
        FishingMeta.setDisplayName(Main.color(FishingDisplayNamePAPISet));
        // Set Lore \\
        ArrayList<String> FishingSlotLore = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Skills-Gui.Fishing-Slot.Fishing-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            FishingSlotLore.add(Main.color(withPAPISet)
                    .replace("{fishingLevel}", String.valueOf(fishingLevel))
                    .replace("{fishingXp}", String.valueOf(fishingXp))
                    .replace("{fishingXpToNextLvl}", String.valueOf(fishingXpToNextLvl)));
        }
        // Set all values \\
        FishingMeta.setLore(FishingSlotLore);
        FishingItem.setItemMeta(FishingMeta);
        // Place Item in correct slot \\
        inventory.setItem(FishingSlot, FishingItem);
        // ---------- Herbalism Skill ---------- \\
        // Get Config Stats \\
        int HerbalismSlot = main.getSkillsConfig().getInt("Skills-Gui.Herbalism-Slot.Herbalism-InventorySlot");
        String HerbalismSlotTitle = main.getSkillsConfig().getString("Skills-Gui.Herbalism-Slot.Herbalism-Title");
        Material HerbalismConfigItem = Material.valueOf(main.getSkillsConfig().getString("Skills-Gui.Herbalism-Slot.Herbalism-Item"));
        // Create Item \\
        ItemStack HerbalismItem = new ItemStack(HerbalismConfigItem);
        ItemMeta HerbalismMeta = HerbalismItem.getItemMeta();
        // Set Title \\
        String HerbalismDisplayName = Main.color(HerbalismSlotTitle).replace("{Player}", player.getName());
        String HerbalismDisplayNamePAPISet = main.setPlaceholders(player, HerbalismDisplayName);
        assert HerbalismMeta != null;
        HerbalismMeta.setDisplayName(Main.color(HerbalismDisplayNamePAPISet));
        // Set Lore \\
        ArrayList<String> HerbalismSlotLore = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Skills-Gui.Herbalism-Slot.Herbalism-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            HerbalismSlotLore.add(Main.color(withPAPISet)
                    .replace("{herbalismLevel}", String.valueOf(herbalismLevel))
                    .replace("{herbalismXp}", String.valueOf(herbalismXp))
                    .replace("{herbalismXpToNextLvl}", String.valueOf(herbalismXpToNextLvl)));
        }
        // Set all values \\
        HerbalismMeta.setLore(HerbalismSlotLore);
        HerbalismItem.setItemMeta(HerbalismMeta);
        // Place Item in correct slot \\
        inventory.setItem(HerbalismSlot, HerbalismItem);
        // ---------- Mining Skill ---------- \\
        // Get Config Stats \\
        int MiningSlot = main.getSkillsConfig().getInt("Skills-Gui.Mining-Slot.Mining-InventorySlot");
        String MiningSlotTitle = main.getSkillsConfig().getString("Skills-Gui.Mining-Slot.Mining-Title");
        Material MiningConfigItem = Material.valueOf(main.getSkillsConfig().getString("Skills-Gui.Mining-Slot.Mining-Item"));
        // Create Item \\
        ItemStack MiningItem = new ItemStack(MiningConfigItem);
        ItemMeta MiningMeta = MiningItem.getItemMeta();
        // Set Title \\
        String MiningDisplayName = Main.color(MiningSlotTitle).replace("{Player}", player.getName());
        String MiningDisplayNamePAPISet = main.setPlaceholders(player, MiningDisplayName);
        assert MiningMeta != null;
        MiningMeta.setDisplayName(Main.color(MiningDisplayNamePAPISet));
        // Set Lore \\
        ArrayList<String> MiningSlotLore = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Skills-Gui.Mining-Slot.Mining-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            MiningSlotLore.add(Main.color(withPAPISet)
                    .replace("{miningLevel}", String.valueOf(miningLevel))
                    .replace("{miningXp}", String.valueOf(miningXp))
                    .replace("{miningXpToNextLvl}", String.valueOf(miningXpToNextLvl)));
        }
        // Set all values \\
        MiningMeta.setLore(MiningSlotLore);
        MiningItem.setItemMeta(MiningMeta);
        // Place Item in correct slot \\
        inventory.setItem(MiningSlot, MiningItem);
        // ---------- Sword Skill ---------- \\
        // Get Config Stats \\
        int SwordSlot = main.getSkillsConfig().getInt("Skills-Gui.Swords-Slot.Swords-InventorySlot");
        String SwordSlotTitle = main.getSkillsConfig().getString("Skills-Gui.Swords-Slot.Swords-Title");
        Material SwordConfigItem = Material.valueOf(main.getSkillsConfig().getString("Skills-Gui.Swords-Slot.Swords-Item"));
        // Create Item \\
        ItemStack SwordItem = new ItemStack(SwordConfigItem);
        ItemMeta SwordMeta = SwordItem.getItemMeta();
        // Set Title \\
        String SwordDisplayName = Main.color(SwordSlotTitle).replace("{Player}", player.getName());
        String SwordDisplayNamePAPISet = main.setPlaceholders(player, SwordDisplayName);
        assert SwordMeta != null;
        SwordMeta.setDisplayName(Main.color(SwordDisplayNamePAPISet));
        // Set Lore \\
        ArrayList<String> SwordSlotLore = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Skills-Gui.Swords-Slot.Swords-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            SwordSlotLore.add(Main.color(withPAPISet)
                    .replace("{swordLevel}", String.valueOf(swordLevel))
                    .replace("{swordXp}", String.valueOf(swordXp))
                    .replace("{swordXpToNextLvl}", String.valueOf(swordXpToNextLvl)));
        }
        // Set all values \\
        SwordMeta.setLore(SwordSlotLore);
        SwordItem.setItemMeta(SwordMeta);
        // Place Item in correct slot \\
        inventory.setItem(SwordSlot, SwordItem);
        // ---------- Taming Skill ---------- \\
        // Get Config Stats \\
        int TamingSlot = main.getSkillsConfig().getInt("Skills-Gui.Taming-Slot.Taming-InventorySlot");
        String TamingSlotTitle = main.getSkillsConfig().getString("Skills-Gui.Taming-Slot.Taming-Title");
        Material TamingConfigItem = Material.valueOf(main.getSkillsConfig().getString("Skills-Gui.Taming-Slot.Taming-Item"));
        // Create Item \\
        ItemStack TamingItem = new ItemStack(TamingConfigItem);
        ItemMeta TamingMeta = TamingItem.getItemMeta();
        // Set Title \\
        String TamingDisplayName = Main.color(TamingSlotTitle).replace("{Player}", player.getName());
        String TamingDisplayNamePAPISet = main.setPlaceholders(player, TamingDisplayName);
        assert TamingMeta != null;
        TamingMeta.setDisplayName(Main.color(TamingDisplayNamePAPISet));
        // Set Lore \\
        ArrayList<String> TamingSlotLore = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Skills-Gui.Taming-Slot.Taming-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            TamingSlotLore.add(Main.color(withPAPISet)
                    .replace("{tamingLevel}", String.valueOf(tamingLevel))
                    .replace("{tamingXp}", String.valueOf(tamingXp))
                    .replace("{tamingXpToNextLvl}", String.valueOf(tamingXpToNextLvl)));
        }
        // Set all values \\
        TamingMeta.setLore(TamingSlotLore);
        TamingItem.setItemMeta(TamingMeta);
        // Place Item in correct slot \\
        inventory.setItem(TamingSlot, TamingItem);
        // ---------- Unarmed Skill ---------- \\
        // Get Config Stats \\
        int UnarmedSlot = main.getSkillsConfig().getInt("Skills-Gui.Unarmed-Slot.Unarmed-InventorySlot");
        String UnarmedSlotTitle = main.getSkillsConfig().getString("Skills-Gui.Unarmed-Slot.Unarmed-Title");
        Material UnarmedConfigItem = Material.valueOf(main.getSkillsConfig().getString("Skills-Gui.Unarmed-Slot.Unarmed-Item"));
        // Create Item \\
        ItemStack UnarmedItem = new ItemStack(UnarmedConfigItem);
        ItemMeta UnarmedMeta = UnarmedItem.getItemMeta();
        // Set Title \\
        String UnarmedDisplayName = Main.color(UnarmedSlotTitle).replace("{Player}", player.getName());
        String UnarmedDisplayNamePAPISet = main.setPlaceholders(player, UnarmedDisplayName);
        assert UnarmedMeta != null;
        UnarmedMeta.setDisplayName(Main.color(UnarmedDisplayNamePAPISet));
        // Set Lore \\
        ArrayList<String> UnarmedSlotLore = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Skills-Gui.Unarmed-Slot.Unarmed-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            UnarmedSlotLore.add(Main.color(withPAPISet)
                    .replace("{unarmedLevel}", String.valueOf(unarmedLevel))
                    .replace("{unarmedXp}", String.valueOf(unarmedXp))
                    .replace("{unarmedXpToNextLvl}", String.valueOf(unarmedXpToNextLvl)));
        }
        // Set all values \\
        UnarmedMeta.setLore(UnarmedSlotLore);
        UnarmedItem.setItemMeta(UnarmedMeta);
        // Place Item in correct slot \\
        inventory.setItem(UnarmedSlot, UnarmedItem);
        // ---------- Woodcutting Skill ---------- \\
        // Get Config Stats \\
        int WoodcuttingSlot = main.getSkillsConfig().getInt("Skills-Gui.Woodcutting-Slot.Woodcutting-InventorySlot");
        String WoodcuttingSlotTitle = main.getSkillsConfig().getString("Skills-Gui.Woodcutting-Slot.Woodcutting-Title");
        Material WoodcuttingConfigItem = Material.valueOf(main.getSkillsConfig().getString("Skills-Gui.Woodcutting-Slot.Woodcutting-Item"));
        // Create Item \\
        ItemStack WoodcuttingItem = new ItemStack(WoodcuttingConfigItem);
        ItemMeta WoodcuttingMeta = WoodcuttingItem.getItemMeta();
        // Set Title \\
        String WoodcuttingDisplayName = Main.color(WoodcuttingSlotTitle).replace("{Player}", player.getName());
        String WoodcuttingDisplayNamePAPISet = main.setPlaceholders(player, WoodcuttingDisplayName);
        assert WoodcuttingMeta != null;
        WoodcuttingMeta.setDisplayName(Main.color(WoodcuttingDisplayNamePAPISet));
        // Set Lore \\
        ArrayList<String> WoodcuttingSlotLore = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Skills-Gui.Woodcutting-Slot.Woodcutting-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            WoodcuttingSlotLore.add(Main.color(withPAPISet)
                    .replace("{woodcuttingLevel}", String.valueOf(woodcuttingLevel))
                    .replace("{woodcuttingXp}", String.valueOf(woodcuttingXp))
                    .replace("{woodcuttingXpToNextLvl}", String.valueOf(woodcuttingXpToNextLvl)));
        }
        // Set all values \\
        WoodcuttingMeta.setLore(WoodcuttingSlotLore);
        WoodcuttingItem.setItemMeta(WoodcuttingMeta);
        // Place Item in correct slot \\
        inventory.setItem(WoodcuttingSlot, WoodcuttingItem);

        // ---------- GLASS FILLER Skill ---------- \\
        // Create Item \\
        String GlassTitle = main.getSkillsConfig().getString("Skills-Gui.Skills-Menu.Skills-Menu-Filler-Title");
        Material GlassConfigItem = Material.valueOf(main.getSkillsConfig().getString("Skills-Gui.Skills-Menu.Skills-Menu-Filler-Item"));
        ItemStack GlassItem = new ItemStack(GlassConfigItem);
        ItemMeta GlassItemMeta = GlassItem.getItemMeta();
        // Set Title \\
        String GlassItemDisplayName = Main.color(GlassTitle).replace("{Player}", player.getName());
        String GlassItemDisplayNamePAPISet = main.setPlaceholders(player, GlassItemDisplayName);
        assert GlassItemMeta != null;
        GlassItemMeta.setDisplayName(Main.color(GlassItemDisplayNamePAPISet));
        GlassItem.setItemMeta(GlassItemMeta);
        // Place Items in correct slots \\
        inventory.setItem(0, GlassItem);
        inventory.setItem(1, GlassItem);
        inventory.setItem(2, GlassItem);
        inventory.setItem(3, GlassItem);
        inventory.setItem(4, GlassItem);
        inventory.setItem(5, GlassItem);
        inventory.setItem(6, GlassItem);
        inventory.setItem(7, GlassItem);
        inventory.setItem(8, GlassItem);
        inventory.setItem(9, GlassItem);
        inventory.setItem(17, GlassItem);
        inventory.setItem(18, GlassItem);
        inventory.setItem(26, GlassItem);
        inventory.setItem(27, GlassItem);
        inventory.setItem(28, GlassItem);
        inventory.setItem(29, GlassItem);
        inventory.setItem(30, GlassItem);
        inventory.setItem(31, GlassItem);
        inventory.setItem(32, GlassItem);
        inventory.setItem(33, GlassItem);
        inventory.setItem(34, GlassItem);
        inventory.setItem(35, GlassItem);
    }
}
