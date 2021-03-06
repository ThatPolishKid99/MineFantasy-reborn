package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.item.ItemBandage;
import minefantasy.mfr.item.ItemComponentMFR;
import minefantasy.mfr.item.ItemResearchBook;
import minefantasy.mfr.item.ItemSkillBook;
import minefantasy.mfr.item.ItemWorldGenPlacer;
import minefantasy.mfr.item.gadget.ItemBomb;
import minefantasy.mfr.item.gadget.ItemClimbingPick;
import minefantasy.mfr.item.gadget.ItemCrossbow;
import minefantasy.mfr.item.gadget.ItemCrudeBomb;
import minefantasy.mfr.item.gadget.ItemExplodingArrow;
import minefantasy.mfr.item.gadget.ItemExplodingBolt;
import minefantasy.mfr.item.gadget.ItemLootSack;
import minefantasy.mfr.item.gadget.ItemMine;
import minefantasy.mfr.item.gadget.ItemParachute;
import minefantasy.mfr.item.gadget.ItemSpyglass;
import minefantasy.mfr.item.gadget.ItemSyringe;
import minefantasy.mfr.item.gadget.MobSpawnerMF;
import minefantasy.mfr.item.tool.ItemAxeMFR;
import minefantasy.mfr.item.tool.ItemHoeMF;
import minefantasy.mfr.item.tool.ItemLighterMF;
import minefantasy.mfr.item.tool.ItemPickMF;
import minefantasy.mfr.item.tool.ItemSpadeMF;
import minefantasy.mfr.item.tool.crafting.ItemEAnvilTools;
import minefantasy.mfr.item.tool.crafting.ItemHammer;
import minefantasy.mfr.item.tool.crafting.ItemKnifeMFR;
import minefantasy.mfr.item.tool.crafting.ItemNeedle;
import minefantasy.mfr.item.tool.crafting.ItemPaintBrush;
import minefantasy.mfr.item.tool.crafting.ItemTongs;
import minefantasy.mfr.item.weapon.ItemMace;
import minefantasy.mfr.item.weapon.ItemSpear;
import minefantasy.mfr.item.weapon.ItemSword;
import minefantasy.mfr.item.weapon.ItemWaraxe;
import minefantasy.mfr.material.BaseMaterialMFR;
import minefantasy.mfr.util.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;


@ObjectHolder(MineFantasyReborn.MOD_ID)
@Mod.EventBusSubscriber(modid = MineFantasyReborn.MOD_ID)
public class ToolListMFR {
    public static EnumRarity POOR;
    public static EnumRarity UNIQUE;
    public static EnumRarity RARE;

    public static EnumRarity[] RARITY;

    public static Item TRAINING_SWORD = Utils.nullValue();
    public static Item TRAINING_WARAXE = Utils.nullValue();
    public static Item TRAINING_MACE = Utils.nullValue();
    public static Item TRAINING_SPEAR = Utils.nullValue();

    public static Item STONE_KNIFE = Utils.nullValue();
    public static Item STONE_HAMMER = Utils.nullValue();
    public static Item STONE_TONGS = Utils.nullValue();
    public static Item BONE_NEEDLE = Utils.nullValue();
    public static Item STONE_PICK = Utils.nullValue();
    public static Item STONE_AXE = Utils.nullValue();
    public static Item STONE_SPADE = Utils.nullValue();
    public static Item STONE_HOE = Utils.nullValue();
    public static Item STONE_SWORD = Utils.nullValue();
    public static Item STONE_MACE = Utils.nullValue();
    public static Item STONE_WARAXE = Utils.nullValue();
    public static Item STONE_SPEAR = Utils.nullValue();

    public static Item BANDAGE_CRUDE = Utils.nullValue();
    public static Item BANDAGE_WOOL = Utils.nullValue();
    public static Item BANDAGE_TOUGH = Utils.nullValue();

    public static ItemCrudeBomb BOMB_CRUDE = Utils.nullValue();
    public static ItemBomb BOMB_CUSTOM = Utils.nullValue();
    public static ItemMine MINE_CUSTOM = Utils.nullValue();

    public static ItemResearchBook RESEARCH_BOOK = Utils.nullValue();

    public static Item DRY_ROCKS = Utils.nullValue();
    public static Item TINDERBOX = Utils.nullValue();

    public static Item SKILLBOOK_ARTISANRY = Utils.nullValue();
    public static Item SKILLBOOK_CONSTRUCTION = Utils.nullValue();
    public static Item SKILLBOOK_PROVISIONING = Utils.nullValue();
    public static Item SKILLBOOK_ENGINEERING = Utils.nullValue();
    public static Item SKILLBOOK_COMBAT = Utils.nullValue();

    public static Item SKILLBOOK_ARTISANRY_MAX = Utils.nullValue();
    public static Item SKILLBOOK_CONSTRUCTION_MAX = Utils.nullValue();
    public static Item SKILLBOOK_PROVISIONING_MAX = Utils.nullValue();
    public static Item SKILLBOOK_ENGINEERING_MAX = Utils.nullValue();
    public static Item SKILLBOOK_COMBAT_MAX = Utils.nullValue();

    public static Item ENGIN_ANVIL_TOOLS = Utils.nullValue();

    public static Item EXPLODING_ARROW = Utils.nullValue();
    public static Item SPYGLASS = Utils.nullValue();
    public static Item CLIMBING_PICK_BASIC = Utils.nullValue();
    public static Item PARACHUTE = Utils.nullValue();

    public static Item SYRINGE = Utils.nullValue();
    public static Item SYRINGE_EMPTY = Utils.nullValue();

    public static Item LOOT_SACK = Utils.nullValue();
    public static Item LOOT_SACK_UC = Utils.nullValue();
    public static Item LOOT_SACK_RARE = Utils.nullValue();

    public static ItemCrossbow CROSSBOW_CUSTOM = Utils.nullValue();
    public static Item EXPLODING_BOLT = Utils.nullValue();

    public static Item PAINT_BRUSH = Utils.nullValue();

    public static Item DEBUG_PLACE = Utils.nullValue();
    public static Item DEBUG_MOB = Utils.nullValue();

    public static void init() {
        TRAINING_SWORD = new ItemSword("training_sword", ToolMaterial.WOOD, -1, 0.8F);
        TRAINING_WARAXE = new ItemWaraxe("training_waraxe", ToolMaterial.WOOD, -1, 0.8F);
        TRAINING_MACE = new ItemMace("training_mace", ToolMaterial.WOOD, -1, 0.8F);
        TRAINING_SPEAR = new ItemSpear("training_spear", ToolMaterial.WOOD, -1, 0.8F);

        STONE_KNIFE = new ItemKnifeMFR("stone_knife", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 3.5F, 0);
        STONE_HAMMER = new ItemHammer("stone_hammer", BaseMaterialMFR.getMaterial("stone").getToolConversion(), false, -1, 0);
        STONE_TONGS = new ItemTongs("stone_tongs", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1);
        BONE_NEEDLE = new ItemNeedle("bone_needle", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 0);
        STONE_PICK = new ItemPickMF("stone_pick", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1);
        STONE_AXE = new ItemAxeMFR("stone_axe", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1);
        STONE_SPADE = new ItemSpadeMF("stone_spade", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1);
        STONE_HOE = new ItemHoeMF("stone_hoe", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1);
        STONE_SWORD = new ItemSword("stone_sword", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F);
        STONE_MACE = new ItemMace("stone_mace", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F);
        STONE_WARAXE = new ItemWaraxe("stone_waraxe", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F);
        STONE_SPEAR = new ItemSpear("stone_spear", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F);

        BANDAGE_CRUDE = new ItemBandage("bandage_crude", 5F);
        BANDAGE_WOOL = new ItemBandage("bandage_wool", 8F);
        BANDAGE_TOUGH = new ItemBandage("bandage_tough", 12F);

        BOMB_CRUDE = new ItemCrudeBomb("bomb_crude");
        BOMB_CUSTOM = new ItemCrudeBomb("bomb_custom");
        MINE_CUSTOM = new ItemMine("mine_custom");

        RESEARCH_BOOK = new ItemResearchBook();

        DRY_ROCKS = new ItemLighterMF("dryrocks", 0.1F, 16);
        TINDERBOX = new ItemLighterMF("tinderbox", 0.5F, 100);

        SKILLBOOK_ARTISANRY = new ItemSkillBook("skillbook_artisanry", SkillList.artisanry);
        SKILLBOOK_CONSTRUCTION = new ItemSkillBook("skillbook_construction", SkillList.construction);
        SKILLBOOK_PROVISIONING = new ItemSkillBook("skillbook_provisioning", SkillList.provisioning);
        SKILLBOOK_ENGINEERING = new ItemSkillBook("skillbook_engineering", SkillList.engineering);
        SKILLBOOK_COMBAT = new ItemSkillBook("skillbook_combat", SkillList.combat);

        SKILLBOOK_ARTISANRY_MAX = new ItemSkillBook("skillbook_artisanry_max", SkillList.artisanry).setMax();
        SKILLBOOK_CONSTRUCTION_MAX = new ItemSkillBook("skillbook_construction_max", SkillList.construction).setMax();
        SKILLBOOK_PROVISIONING_MAX = new ItemSkillBook("skillbook_provisioning_max", SkillList.provisioning).setMax();
        SKILLBOOK_ENGINEERING_MAX = new ItemSkillBook("skillbook_engineering_max", SkillList.engineering).setMax();
        SKILLBOOK_COMBAT_MAX = new ItemSkillBook("skillbook_combat_max", SkillList.combat).setMax();

        ENGIN_ANVIL_TOOLS = new ItemEAnvilTools("engin_anvil_tools", 64);

        EXPLODING_ARROW = new ItemExplodingArrow();
        SPYGLASS = new ItemSpyglass();
        CLIMBING_PICK_BASIC = new ItemClimbingPick("climbing_pick_basic", ToolMaterial.IRON, 0);
        PARACHUTE = new ItemParachute();

        SYRINGE = new ItemSyringe();
        SYRINGE_EMPTY = new ItemComponentMFR("syringe_empty").setCreativeTab(CreativeTabMFR.tabGadget);

        LOOT_SACK = new ItemLootSack("loot_sack", 8, 0);
        LOOT_SACK_UC = new ItemLootSack("loot_sack_uc", 8, 1);
        LOOT_SACK_RARE = new ItemLootSack("loot_sack_rare", 12, 2);

        CROSSBOW_CUSTOM = new ItemCrossbow();
        EXPLODING_BOLT = new ItemExplodingBolt();

        PAINT_BRUSH = new ItemPaintBrush("paint_brush", 256);

        DEBUG_PLACE = new ItemWorldGenPlacer();
        DEBUG_MOB = new MobSpawnerMF();
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(TRAINING_SWORD);
        registry.register(TRAINING_WARAXE);
        registry.register(TRAINING_MACE);
        registry.register(TRAINING_SPEAR);

        registry.register(STONE_KNIFE);
        registry.register(STONE_HAMMER);
        registry.register(STONE_TONGS);
        registry.register(BONE_NEEDLE);
        registry.register(STONE_PICK);
        registry.register(STONE_AXE);
        registry.register(STONE_SPADE);
        registry.register(STONE_HOE);
        registry.register(STONE_SWORD);
        registry.register(STONE_MACE);
        registry.register(STONE_WARAXE);
        registry.register(STONE_SPEAR);

        registry.register(BANDAGE_CRUDE);
        registry.register(BANDAGE_WOOL);
        registry.register(BANDAGE_TOUGH);

        registry.register(BOMB_CRUDE);
        registry.register(BOMB_CUSTOM);
        registry.register(MINE_CUSTOM);

        registry.register(RESEARCH_BOOK);

        registry.register(DRY_ROCKS);
        registry.register(TINDERBOX);

        registry.register(SKILLBOOK_ARTISANRY);
        registry.register(SKILLBOOK_CONSTRUCTION);
        registry.register(SKILLBOOK_PROVISIONING);
        registry.register(SKILLBOOK_ENGINEERING);
        registry.register(SKILLBOOK_COMBAT);

        registry.register(SKILLBOOK_ARTISANRY_MAX);
        registry.register(SKILLBOOK_CONSTRUCTION_MAX);
        registry.register(SKILLBOOK_PROVISIONING_MAX);
        registry.register(SKILLBOOK_ENGINEERING_MAX);
        registry.register(SKILLBOOK_COMBAT_MAX);

        registry.register(ENGIN_ANVIL_TOOLS);

        registry.register(EXPLODING_ARROW);
        registry.register(SPYGLASS);
        registry.register(CLIMBING_PICK_BASIC);
        registry.register(PARACHUTE);

        registry.register(SYRINGE);
        registry.register(SYRINGE_EMPTY);

        registry.register(LOOT_SACK);
        registry.register(LOOT_SACK_UC);
        registry.register(LOOT_SACK_RARE);

        registry.register(CROSSBOW_CUSTOM);
        registry.register(EXPLODING_BOLT);

        registry.register(PAINT_BRUSH);

        registry.register(DEBUG_PLACE);
        registry.register(DEBUG_MOB);
    }

    public static void load() {
        POOR = EnumHelper.addRarity("Poor", TextFormatting.DARK_GRAY, "poor");
        UNIQUE = EnumHelper.addRarity("Unique", TextFormatting.DARK_GREEN, "unique");
        RARE = EnumHelper.addRarity("Rare", TextFormatting.DARK_BLUE, "rare");

        RARITY = new EnumRarity[]{ToolListMFR.POOR, EnumRarity.COMMON, EnumRarity.UNCOMMON, EnumRarity.RARE, EnumRarity.EPIC};

        if (ConfigHardcore.HCCWeakItems) {
            weakenItems();
        }
    }

    private static void weakenItems() {
        weakenItem(Items.WOODEN_PICKAXE, 5);
        weakenItem(Items.WOODEN_AXE, 5);
        weakenItem(Items.WOODEN_SHOVEL, 5);
        weakenItem(Items.WOODEN_SWORD, 5);
        weakenItem(Items.WOODEN_HOE, 5);

        weakenItem(Items.LEATHER_HELMET);
        weakenItem(Items.LEATHER_CHESTPLATE);
        weakenItem(Items.LEATHER_LEGGINGS);
        weakenItem(Items.LEATHER_BOOTS);

        weakenItem(Items.STONE_PICKAXE, 10);
        weakenItem(Items.STONE_AXE, 10);
        weakenItem(Items.STONE_SHOVEL, 10);
        weakenItem(Items.STONE_SWORD, 10);
        weakenItem(Items.STONE_HOE, 10);

        weakenItem(Items.IRON_PICKAXE, 25);
        weakenItem(Items.IRON_AXE, 25);
        weakenItem(Items.IRON_SHOVEL, 25);
        weakenItem(Items.IRON_SWORD, 25);
        weakenItem(Items.IRON_HOE, 25);
        weakenItem(Items.IRON_HELMET);
        weakenItem(Items.IRON_CHESTPLATE);
        weakenItem(Items.IRON_LEGGINGS);
        weakenItem(Items.IRON_BOOTS);

        weakenItem(Items.GOLDEN_PICKAXE, 1);
        weakenItem(Items.GOLDEN_AXE, 1);
        weakenItem(Items.GOLDEN_SHOVEL, 1);
        weakenItem(Items.GOLDEN_SWORD, 1);
        weakenItem(Items.GOLDEN_HOE, 1);
        weakenItem(Items.GOLDEN_HELMET);
        weakenItem(Items.GOLDEN_CHESTPLATE);
        weakenItem(Items.GOLDEN_LEGGINGS);
        weakenItem(Items.GOLDEN_BOOTS);

        weakenItem(Items.DIAMOND_PICKAXE, 100);
        weakenItem(Items.DIAMOND_AXE, 100);
        weakenItem(Items.DIAMOND_SHOVEL, 100);
        weakenItem(Items.DIAMOND_SWORD, 100);
        weakenItem(Items.DIAMOND_HOE, 100);
        weakenItem(Items.DIAMOND_HELMET);
        weakenItem(Items.DIAMOND_CHESTPLATE);
        weakenItem(Items.DIAMOND_LEGGINGS);
        weakenItem(Items.DIAMOND_BOOTS);
    }

    private static void weakenItem(Item item) {
        weakenItem(item, (item.getMaxDamage() / 10) + 1);
    }

    private static void weakenItem(Item item, int hp) {
        if (item.isDamageable()) {
            item.setMaxDamage(hp);
        }
    }

}
