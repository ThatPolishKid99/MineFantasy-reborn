package minefantasy.mfr.init;

import minefantasy.mfr.item.archery.ArrowType;
import minefantasy.mfr.item.archery.EnumBowType;
import minefantasy.mfr.item.archery.ItemArrowMFR;
import minefantasy.mfr.item.archery.ItemBowMFR;
import minefantasy.mfr.item.tool.*;
import minefantasy.mfr.item.tool.advanced.*;
import minefantasy.mfr.item.tool.crafting.*;
import minefantasy.mfr.item.weapon.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;

public class CustomToolListMFR {
    // STANDARD
    public static ItemWeaponMFR standard_sword, standard_waraxe, standard_mace, standard_dagger, standard_spear;
    public static ItemWeaponMFR standard_greatsword, standard_battleaxe, standard_warhammer, standard_katana, standard_halbeard, standard_lance;
    public static ItemPickMF standard_pick;
    public static ItemAxeMF standard_axe;
    public static ItemSpadeMF standard_spade;
    public static ItemHoeMF standard_hoe;
    public static ItemHvyPick standard_hvypick;
    public static ItemHvyShovel standard_hvyshovel;
    public static ItemHandpick standard_handpick;
    public static ItemTrowMF standard_trow;
    public static ItemScythe standard_scythe;
    public static ItemMattock standard_mattock;
    public static ItemLumberAxe standard_lumber;

    public static ItemHammer standard_hammer, standard_hvyhammer;
    public static ItemTongs standard_tongs;
    public static ItemShearsMFR standard_shears;
    public static ItemKnifeMFR standard_knife;
    public static ItemNeedle standard_needle;
    public static ItemSaw standard_saw;
    public static ItemSpanner standard_spanner;
    public static ItemBasicCraftTool standard_spoon, standard_mallet;

    public static ItemBowMFR standard_bow;
    public static ItemArrowMFR standard_arrow, standard_bolt, standard_arrow_bodkin, standard_arrow_broad;

    public static void load() {
        String design = "standard";
        CreativeTabs tab = CreativeTabMFR.tabWeapon;
        // Standard Weapons
        standard_dagger = new ItemDagger(design + "_dagger", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        standard_sword = new ItemSwordMF(design + "_sword", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        standard_waraxe = new ItemWaraxeMFR(design + "_waraxe", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        standard_mace = new ItemMaceMFR(design + "_mace", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        standard_spear = new ItemSpearMFR(design + "_spear", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        standard_katana = new ItemKatanaMFR(design + "_katana", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        standard_greatsword = new ItemGreatswordMFR(design + "_greatsword", ToolMaterial.IRON, 0, 1F).setCustom(design)
                .setTab(tab);
        standard_battleaxe = new ItemBattleaxeMFR(design + "_battleaxe", ToolMaterial.IRON, 0, 1F).setCustom(design)
                .setTab(tab);
        standard_warhammer = new ItemWarhammerMFR(design + "_warhammer", ToolMaterial.IRON, 0, 1F).setCustom(design)
                .setTab(tab);
        standard_halbeard = new ItemHalbeardMFR(design + "_halbeard", ToolMaterial.IRON, 0, 1F).setCustom(design)
                .setTab(tab);
        standard_lance = new ItemLance(design + "_lance", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);

        tab = CreativeTabMFR.tabArcher;
        standard_bow = (ItemBowMFR) new ItemBowMFR(design + "_bow", EnumBowType.SHORTBOW).setCustom(design)
                .setCreativeTab(tab);
        standard_bolt = (ItemArrowMFR) new ItemArrowMFR(design, ArrowType.BOLT, 20).setCustom(design).setAmmoType("bolt")
                .setCreativeTab(tab);
        standard_arrow = (ItemArrowMFR) new ItemArrowMFR(design, ArrowType.NORMAL, 16).setCustom(design)
                .setCreativeTab(tab);
        standard_arrow_bodkin = (ItemArrowMFR) new ItemArrowMFR(design, ArrowType.BODKIN, 16).setCustom(design)
                .setCreativeTab(tab);
        standard_arrow_broad = (ItemArrowMFR) new ItemArrowMFR(design, ArrowType.BROADHEAD, 16).setCustom(design)
                .setCreativeTab(tab);

        tab = CreativeTabMFR.tabTool;
        // Standard Tools
        standard_pick = (ItemPickMF) new ItemPickMF(design + "_pick", ToolMaterial.IRON, 0).setCustom(design)
                .setCreativeTab(tab);
        standard_axe = (ItemAxeMF) new ItemAxeMF(design + "_axe", ToolMaterial.IRON, 0).setCustom(design)
                .setCreativeTab(tab);
        standard_spade = (ItemSpadeMF) new ItemSpadeMF(design + "_spade", ToolMaterial.IRON, 0).setCustom(design)
                .setCreativeTab(tab);
        standard_hoe = (ItemHoeMF) new ItemHoeMF(design + "_hoe", ToolMaterial.IRON, 0).setCustom(design)
                .setCreativeTab(tab);

        tab = CreativeTabMFR.tabToolAdvanced;
        standard_handpick = (ItemHandpick) new ItemHandpick(design + "_handpick", ToolMaterial.IRON, 0)
                .setCustom(design).setCreativeTab(tab);
        standard_hvypick = (ItemHvyPick) new ItemHvyPick(design + "_hvypick", ToolMaterial.IRON, 0).setCustom(design)
                .setCreativeTab(tab);
        standard_trow = (ItemTrowMF) new ItemTrowMF(design + "_trow", ToolMaterial.IRON, 0).setCustom(design)
                .setCreativeTab(tab);
        standard_hvyshovel = (ItemHvyShovel) new ItemHvyShovel(design + "_hvyshovel", ToolMaterial.IRON, 0)
                .setCustom(design).setCreativeTab(tab);
        standard_scythe = (ItemScythe) new ItemScythe(design + "_scythe", ToolMaterial.IRON, 0).setCustom(design)
                .setCreativeTab(tab);
        standard_mattock = (ItemMattock) new ItemMattock(design + "_mattock", ToolMaterial.IRON, 0).setCustom(design)
                .setCreativeTab(tab);
        standard_lumber = (ItemLumberAxe) new ItemLumberAxe(design + "_lumber", ToolMaterial.IRON, 0).setCustom(design)
                .setCreativeTab(tab);
        // Standard Crafters
        tab = CreativeTabMFR.tabCraftTool;
        standard_hammer = (ItemHammer) new ItemHammer(design + "_hammer", ToolMaterial.IRON, false, 0, 0)
                .setCustom(design).setCreativeTab(tab);
        standard_hvyhammer = (ItemHammer) new ItemHammer(design + "_hvyhammer", ToolMaterial.IRON, true, 0, 0)
                .setCustom(design).setCreativeTab(tab);
        standard_shears = (ItemShearsMFR) new ItemShearsMFR(design + "_shears", ToolMaterial.IRON, 0, 0).setCustom(design)
                .setCreativeTab(tab);
        standard_knife = (ItemKnifeMFR) new ItemKnifeMFR(design + "_knife", ToolMaterial.IRON, 0, 1F, 0).setCustom(design)
                .setCreativeTab(tab);
        standard_needle = (ItemNeedle) new ItemNeedle(design + "_needle", ToolMaterial.IRON, 0, 0).setCustom(design)
                .setCreativeTab(tab);
        standard_saw = (ItemSaw) new ItemSaw(design + "_saw", ToolMaterial.IRON, 0, 0).setCustom(design)
                .setCreativeTab(tab);
        standard_tongs = (ItemTongs) new ItemTongs(design + "_tongs", ToolMaterial.IRON, 0).setCustom(design)
                .setCreativeTab(tab);
        standard_spanner = (ItemSpanner) new ItemSpanner(design + "_spanner", 0, 0).setCustom(design)
                .setCreativeTab(tab);

        standard_spoon = (ItemBasicCraftTool) new ItemBasicCraftTool(design + "_spoon", "spoon", 0, 64)
                .setCustom(design).setCreativeTab(tab);
        standard_mallet = (ItemBasicCraftTool) new ItemBasicCraftTool(design + "_mallet", "mallet", 0, 64)
                .setCustom(design).setCreativeTab(tab);

        DragonforgedStyle.load();
        OrnateStyle.load();
    }
}
