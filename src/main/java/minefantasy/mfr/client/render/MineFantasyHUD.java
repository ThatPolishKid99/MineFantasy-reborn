package minefantasy.mfr.client.render;

import minefantasy.mfr.api.archery.AmmoMechanicsMFR;
import minefantasy.mfr.api.archery.IDisplayMFRAmmo;
import minefantasy.mfr.api.archery.IFirearm;
import minefantasy.mfr.api.crafting.IBasicMetre;
import minefantasy.mfr.api.crafting.IQualityBalance;
import minefantasy.mfr.api.helpers.ArmourCalculator;
import minefantasy.mfr.api.helpers.GuiHelper;
import minefantasy.mfr.api.helpers.PowerArmour;
import minefantasy.mfr.api.helpers.TextureHelperMFR;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.stamina.StaminaBar;
import minefantasy.mfr.config.ConfigClient;
import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.item.gadget.IScope;
import minefantasy.mfr.item.tool.advanced.ItemMattock;
import minefantasy.mfr.item.weapon.ItemWeaponMFR;
import minefantasy.mfr.tile.TileEntityAnvilMFR;
import minefantasy.mfr.tile.TileEntityCarpenterMFR;
import minefantasy.mfr.tile.TileEntityRoad;
import minefantasy.mfr.tile.TileEntityTanningRack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class MineFantasyHUD extends Gui {
    protected static final ResourceLocation pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
    private static Minecraft mc = Minecraft.getMinecraft();

    /**
     * Gets the 2 config values to get the X,Y orient
     */
    public static int[] getOrientsFor(int screenX, int screenY, int cfgX, int cfgY) {
        int xOrient = cfgX == -1 ? 0 : cfgX == 1 ? screenX : screenX / 2;
        int yOrient = cfgY == -1 ? 0 : cfgY == 1 ? screenY : screenY / 2;

        return new int[]{xOrient, yOrient};
    }

    public void renderViewport() {
        if (mc.player != null) {
            EntityPlayer player = mc.player;

            if (mc.gameSettings.thirdPersonView == 0) {
                if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() instanceof IScope) {
                    renderScope(player.getHeldItemMainhand());
                }
                if (player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityCogwork) {
                    renderPowerHelmet(player, (EntityCogwork) player.getRidingEntity());
                }
            }
        }
    }

    public void renderGameOverlay(float partialTicks, int mouseX, int mouseY) {
        if (mc.player != null) {
            EntityPlayer player = mc.player;

            if ((mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiContainerCreative)) {
                renderArmourRating(player);
            } else {
                renderAmmo(player);
            }
            if (StaminaBar.isSystemActive && !player.capabilities.isCreativeMode
                    && !PowerArmour.isWearingCogwork(player)) {
                renderStaminaBar(player);
            }
            Entity highlight = getClickedEntity(partialTicks, mouseX, mouseY);
            if (highlight instanceof EntityCogwork) {
                lookAtCogwork((EntityCogwork) highlight);
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            BlockPos coords = getClickedBlock(partialTicks, mouseX, mouseY);
            if (coords == null)
                return;

            World world = player.world;
            TileEntity tile = world.getTileEntity(coords);
            if (tile != null) {
                if (tile instanceof TileEntityAnvilMFR) {
                    this.renderCraftMetre(world, player, (TileEntityAnvilMFR) tile);
                }
                if (tile instanceof TileEntityCarpenterMFR) {
                    this.renderCraftMetre(world, player, (TileEntityCarpenterMFR) tile);
                }
                if (tile instanceof TileEntityTanningRack) {
                    this.renderCraftMetre(world, player, (TileEntityTanningRack) tile);
                }
                if (tile instanceof IBasicMetre) {
                    this.renderCraftMetre(world, player, (IBasicMetre) tile);
                }
                if (tile instanceof IQualityBalance) {
                    this.renderQualityBalance(world, player, (IQualityBalance) tile);
                }
                if (tile instanceof TileEntityRoad) {
                    renderRoad(world, player, ((TileEntityRoad) tile));
                }
            }
        }
    }

    protected void renderPowerHelmet() {
        GL11.glPushMatrix();
        ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();

        bindTexture("textures/gui/scopes/cogwork_helm.png");
        int x = (width / 2 - 256);
        int y = height - 256;

        this.drawTexturedModalRect(x, y, 0, 64, 128, 64);

        GL11.glPopMatrix();
    }

    private void renderScope(ItemStack item) {
        if (item.getItem() instanceof IFirearm) {
            float factor = ((IScope) item.getItem()).getZoom(item);
            if (factor > 0.1F) {
                GL11.glPushMatrix();
                ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
                int width = scaledresolution.getScaledWidth();
                int height = scaledresolution.getScaledHeight();

                bindTexture("textures/gui/scopes/scope_basic.png");
                int xPos = width / 2 - 128;
                int yPos = height / 2 - 128;
                this.drawTexturedModalRect(xPos, yPos, 0, 0, 256, 256);

                GL11.glPopMatrix();
            }
        }
    }

    private void lookAtCogwork(EntityCogwork suit) {
        GL11.glPushMatrix();
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();

        renderCogworkFuel(width, height, suit);

        GL11.glPopMatrix();
    }

    private void renderPowerHelmet(EntityPlayer user, EntityCogwork suit) {
        GL11.glPushMatrix();
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();

        renderHelmetBlur(width, height);
        renderCogworkFuel(width, height, suit);

        GL11.glPopMatrix();
    }

    private void renderCogworkFuel(int width, int height, EntityCogwork suit) {
        ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);

        bindTexture("textures/gui/hud_overlay.png");
        int[] orientationAR = getOrientsFor(width, height, ConfigClient.CF_xOrient, ConfigClient.CF_yOrient);
        int xPos = orientationAR[0] + ConfigClient.CF_xPos;
        int yPos = orientationAR[1] + ConfigClient.CF_yPos;

        this.drawTexturedModalRect(xPos, yPos, 84, 38, 172, 20);
        int i = suit.getMetreScaled(160);
        this.drawTexturedModalRect(xPos + 6 + (160 - i), yPos + 11, 90, 20, i, 3);
    }

    private void renderHelmetBlur(int width, int height) {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        bindTexture("textures/gui/scopes/cogwork_helm.png");
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(0.0D, height, -90.0D).tex(0.0D, 1.0D);
        bufferBuilder.pos(width, height, -90.0D).tex(1.0D, 1.0D);
        bufferBuilder.pos(width, 0.0D, -90.0D).tex(1.0D, 0.0D);
        bufferBuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D);
        bufferBuilder.finishDrawing();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public BlockPos getClickedBlock(float ticks, int mouseX, int mouseY) {
        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
            return new BlockPos(mc.objectMouseOver.getBlockPos());
        }
        return null;
    }

    public Entity getClickedEntity(float ticks, int mouseX, int mouseY) {
        if (mc.objectMouseOver != null
                && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY) {
            return mc.objectMouseOver.entityHit;
        }
        return null;
    }

    private void renderArmourRating(EntityPlayer player) {
        int base = getBaseRating(player);
        ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();

        int[] orientationAR = getOrientsFor(width, height, ConfigClient.AR_xOrient, ConfigClient.AR_yOrient);
        int xPosAR = orientationAR[0] + ConfigClient.AR_xPos;
        int yPosAR = orientationAR[1] + ConfigClient.AR_yPos;
        int y = 8;
        if (ArmourCalculator.advancedDamageTypes) {
            mc.fontRenderer.drawStringWithShadow(I18n.format("attribute.armour.protection"), xPosAR,
                    yPosAR, Color.WHITE.getRGB());
            displayTraitValue(xPosAR, yPosAR + 8, orientationAR, 0, player, base);
            displayTraitValue(xPosAR, yPosAR + 16, orientationAR, 2, player, base);
            displayTraitValue(xPosAR, yPosAR + 24, orientationAR, 1, player, base);
            y = 32;
        } else {
            displayGeneralAR(xPosAR, yPosAR, orientationAR, player, base);
        }

        float weight = getBaseWeight(player);

        Iterable<ItemStack> armour = mc.player.getArmorInventoryList();
        for (ItemStack stack: armour) {
            weight += ArmourCalculator.getPieceWeight(stack);
        }

        String massString = CustomMaterial.getWeightString(weight);
        mc.fontRenderer.drawStringWithShadow(massString, xPosAR, yPosAR + y, Color.WHITE.getRGB());
    }

    private int getBaseRating(EntityPlayer player) {
        if (player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityCogwork) {
            return ((EntityCogwork) player.getRidingEntity()).getArmourRating();
        }
        return 0;
    }

    private float getBaseWeight(EntityPlayer player) {
        if (player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityCogwork) {
            return ((EntityCogwork) player.getRidingEntity()).getWeight();
        }
        return 0;
    }

    private void renderAmmo(EntityPlayer player) {
        ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();

        int[] orientationAR = getOrientsFor(width, height, ConfigClient.AR_xOrient, ConfigClient.AR_yOrient);
        int xPosAR = orientationAR[0] + ConfigClient.AR_xPos;
        int yPosAR = orientationAR[1] + ConfigClient.AR_yPos;

        ItemStack held = player.getHeldItemMainhand();
        if (!held.isEmpty() && (held.getItem() instanceof IDisplayMFRAmmo)) {
            ItemStack arrow = AmmoMechanicsMFR.getAmmo(held);

            String text = I18n.format("info.bow.reload");
            if (!arrow.isEmpty()) {
                text = arrow.getDisplayName() + " x" + arrow.getCount();
            }
            int[] orientationAC = getOrientsFor(width, height, ConfigClient.AC_xOrient, ConfigClient.AC_yOrient);
            int xPosAC = orientationAR[0] + ConfigClient.AC_xPos;
            int yPosAC = orientationAR[1] + ConfigClient.AC_yPos;

            mc.fontRenderer.drawStringWithShadow(text, xPosAC, yPosAC, Color.WHITE.getRGB());

            int cap = ((IDisplayMFRAmmo) held.getItem()).getAmmoCapacity(held);

            ItemStack ammo = AmmoMechanicsMFR.getArrowOnBow(held);
            int ammocount = ammo == ItemStack.EMPTY ? 0 : ammo.getCount();
            if (cap > 1) {
                String ammostring = I18n.format("info.firearm.ammo", ammocount, cap);
                mc.fontRenderer.drawStringWithShadow(ammostring, xPosAC, yPosAC + 10, Color.WHITE.getRGB());
            }
        }
    }

    private void displayTraitValue(int xPosAR, int yPosAR, int[] orientationAR, int id, EntityPlayer player, int base) {
        float AR = (int) (ArmourCalculator.getDRDisplay(player, id) * 100F) + base;
        mc.fontRenderer.drawStringWithShadow(I18n.format("attribute.armour.rating." + id) + " "
                + ItemWeaponMFR.decimal_format.format(AR), xPosAR, yPosAR, Color.WHITE.getRGB());
    }

    private void displayGeneralAR(int xPosAR, int yPosAR, int[] orientationAR, EntityPlayer player, int base) {
        float AR = ((int) (ArmourCalculator.getDRDisplay(player, 0) * 100F) + base);

        mc.fontRenderer.drawStringWithShadow(I18n.format("attribute.armour.protection") + ": "
                + ItemWeaponMFR.decimal_format.format(AR), xPosAR, yPosAR, Color.WHITE.getRGB());
    }

    private void renderStaminaBar(EntityPlayer player) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        float staminaMax = StaminaBar.getTotalMaxStamina(player);
        float staminaAt = StaminaBar.getStaminaValue(player);

        float staminaPercentage = staminaMax > 0 ? Math.min(1.0F, staminaAt / staminaMax) : 0F;

        float flash = StaminaBar.getFlashTime(player);
        int stam = (int) Math.min(81F, (81F * staminaPercentage));
        ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture("textures/gui/hud_overlay.png");

        int[] orientation = getOrientsFor(width, height, ConfigClient.stam_xOrient, ConfigClient.stam_yOrient);
        int xPos = orientation[0] + ConfigClient.stam_xPos;
        int yPos = orientation[1] + ConfigClient.stam_yPos;
        this.drawTexturedModalRect(xPos, yPos, 0, 0, 81, 5);

        int modifier = modifyMetre(stam, 81, ConfigClient.stam_direction);
        this.drawTexturedModalRect(xPos + modifier, yPos, 0, 5, stam, 5);

        if (flash > 0 && player.ticksExisted % 10 < 5) {
            this.drawTexturedModalRect(xPos, yPos, 0, 10, 81, 5);
        }
        /*
         * else if(StaminaBar.getBonusStaminaRegenTicks(player) > 0 &&
         * player.ticksExisted % 10 < 5) { this.drawTexturedModalRect(xPos, yPos, 0, 15,
         * 81, 5); }
         */

        String stamTxt = (int) staminaAt + " / " + (int) staminaMax;
        boolean bonus = StaminaBar.getBonusStamina(player) > 0;

        if (mc.currentScreen instanceof GuiInventory) {
            mc.fontRenderer.drawStringWithShadow(stamTxt, xPos + 41 - (mc.fontRenderer.getStringWidth(stamTxt) / 2),
                    yPos - 2, bonus ? Color.CYAN.getRGB() : Color.WHITE.getRGB());
        }
        GL11.glDisable(GL11.GL_BLEND);
    }

    private int modifyMetre(int i, int max, int cfg) {
        if (cfg == 0) {
            if (max == i) {
                return (max - i) / 2;
            } else {
                return (max - i - 1) / 2;
            }
        }
        if (cfg == 1) {
            if (max == i) {
                return max - i;
            } else {
                return max - i - 1;
            }
        }
        return 0;
    }

    public void bindTexture(String image) {
        mc.renderEngine.bindTexture(TextureHelperMFR.getResource(image));
    }

    private void renderCraftMetre(World world, EntityPlayer player, TileEntityAnvilMFR tile) {
        boolean knowsCraft = tile.doesPlayerKnowCraft(player);
        GL11.glPushMatrix();
        ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();

        bindTexture("textures/gui/hud_overlay.png");
        int xPos = width / 2 + -86;
        int yPos = height - 69;

        this.drawTexturedModalRect(xPos, yPos, 84, 0, 172, 20);
        this.drawTexturedModalRect(xPos + 6, yPos + 12, 90, 20, tile.getProgressBar(160), 3);

        String s = knowsCraft ? tile.getResultName() : "????";
        mc.fontRenderer.drawString(s, xPos + 86 - (mc.fontRenderer.getStringWidth(s) / 2), yPos + 3, 0);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        if (knowsCraft && !tile.resName.equalsIgnoreCase("") && tile.getToolNeeded() != null) {
            boolean available = ToolHelper.isToolSufficient(player.getHeldItem(EnumHand.MAIN_HAND), tile.getToolNeeded(),
                    tile.getToolTierNeeded());
            GuiHelper.renderToolIcon(this, tile.getToolNeeded(), tile.getToolTierNeeded(), xPos - 20, yPos, available);
            if (tile.getAnvilTierNeeded() > -1) {
                GuiHelper.renderToolIcon(this, "anvil", tile.getAnvilTierNeeded(), xPos + 172, yPos,
                        tile.tier >= tile.getAnvilTierNeeded());
            }
        }

        GL11.glPopMatrix();
    }

    private void renderCraftMetre(World world, EntityPlayer player, TileEntityCarpenterMFR tile) {
        boolean knowsCraft = tile.doesPlayerKnowCraft(player);
        GL11.glPushMatrix();
        ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();

        bindTexture("textures/gui/hud_overlay.png");
        int xPos = width / 2 + -86;
        int yPos = height - 69;

        this.drawTexturedModalRect(xPos, yPos, 84, 0, 172, 20);
        this.drawTexturedModalRect(xPos + 6, yPos + 12, 90, 20, tile.getProgressBar(160), 3);

        String s = knowsCraft ? tile.getResultName() : "????";
        mc.fontRenderer.drawString(s, xPos + 86 - (mc.fontRenderer.getStringWidth(s) / 2), yPos + 3, 0);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        if (knowsCraft && !tile.resName.equalsIgnoreCase("") && tile.getToolNeeded() != null) {
            boolean available = ToolHelper.isToolSufficient(player.getHeldItem(EnumHand.MAIN_HAND), tile.getToolNeeded(),
                    tile.getToolTierNeeded());
            GuiHelper.renderToolIcon(this, tile.getToolNeeded(), tile.getToolTierNeeded(), xPos - 20, yPos, available);
        }

        GL11.glPopMatrix();
    }

    private void renderCraftMetre(World world, EntityPlayer player, TileEntityTanningRack tile) {
        boolean knowsCraft = tile.doesPlayerKnowCraft();
        GL11.glPushMatrix();
        ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();

        bindTexture("textures/gui/hud_overlay.png");
        int xPos = width / 2 + -86;
        int yPos = height - 69;

        this.drawTexturedModalRect(xPos, yPos, 84, 0, 172, 20);
        this.drawTexturedModalRect(xPos + 6, yPos + 12, 90, 20, tile.getProgressBar(160), 3);

        String s = knowsCraft ? tile.getResultName() : "????";
        ItemStack result = tile.getInventory().getStackInSlot(1);
        if (!result.isEmpty() && result.getCount() > 1) {
            s += " x" + result.getCount();
        }
        mc.fontRenderer.drawString(s, xPos + 86 - (mc.fontRenderer.getStringWidth(s) / 2), yPos + 3, 0);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        String resName = tile.getResultName();

        if (knowsCraft && !resName.equalsIgnoreCase("") && tile.toolType != null) {
            boolean available = ToolHelper.isToolSufficient(player.getHeldItem(EnumHand.MAIN_HAND), tile.toolType, -1);
            GuiHelper.renderToolIcon(this, tile.toolType, tile.tier, xPos - 20, yPos, available);
        }

        GL11.glPopMatrix();
    }

    private void renderCraftMetre(World world, EntityPlayer player, IBasicMetre tile) {
        if (!tile.shouldShowMetre()) {
            return;
        }
        GL11.glPushMatrix();
        ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();

        bindTexture("textures/gui/hud_overlay.png");
        int xPos = width / 2 + -86;
        int yPos = height - 69;

        this.drawTexturedModalRect(xPos, yPos, 84, 0, 172, 20);
        this.drawTexturedModalRect(xPos + 6, yPos + 12, 90, 20, tile.getMetreScale(160), 3);

        String s = tile.getLocalisedName();
        mc.fontRenderer.drawString(s, xPos + 86 - (mc.fontRenderer.getStringWidth(s) / 2), yPos + 3, 0);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    private void renderQualityBalance(World world, EntityPlayer player, IQualityBalance tile) {
        if (!tile.shouldShowMetre()) {
            return;
        }
        GL11.glPushMatrix();
        ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();

        bindTexture("textures/gui/hud_overlay.png");
        int xPos = width / 2 + -86;
        int yPos = height - 69 + 17;
        int barwidth = 160;
        int centre = xPos + 5 + (barwidth / 2);

        this.drawTexturedModalRect(xPos, yPos, 84, 23, 172, 10);
        int markerPos = (int) (centre + (tile.getMarkerPosition() * barwidth / 2F));
        this.drawTexturedModalRect(markerPos, yPos + 1, 84, 33, 3, 5);

        int offset = (int) (tile.getThresholdPosition() / 2F * barwidth);
        this.drawTexturedModalRect(centre - offset - 1, yPos + 1, 87, 33, 2, 4);
        this.drawTexturedModalRect(centre + offset, yPos + 1, 89, 33, 2, 4);

        int offset2 = (int) (tile.getSuperThresholdPosition() / 2F * barwidth);
        this.drawTexturedModalRect(centre - offset2, yPos + 1, 91, 33, 1, 4);
        this.drawTexturedModalRect(centre + offset2, yPos + 1, 91, 33, 1, 4);

        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    private void renderRoad(World world, EntityPlayer player, TileEntityRoad tile) {
        if (!mc.player.getHeldItemMainhand().isEmpty()) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof ItemMattock) {
                GL11.glPushMatrix();
                ScaledResolution scaledresolution = new ScaledResolution(MineFantasyHUD.mc);
                int width = scaledresolution.getScaledWidth();
                int height = scaledresolution.getScaledHeight();

                bindTexture("textures/gui/hud_overlay.png");
                int xPos = width / 2 + 12;
                int yPos = height / 2 - 6;
                ;

                this.drawTexturedModalRect(xPos, yPos, tile.isLocked ? 0 : 8, 20, 8, 12);
                GL11.glPopMatrix();
            }
        }
    }
}
