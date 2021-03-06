package minefantasy.mfr.block.refining;

import minefantasy.mfr.block.basic.BlockTileEntity;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.item.ItemFilledMould;
import minefantasy.mfr.tile.TileEntityCrucible;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCrucible extends BlockTileEntity<TileEntityCrucible> {

    public final boolean isActive;
    public int tier;
    public String type;
    public boolean isAuto;

    public BlockCrucible(String type, int tier, boolean isActive) {
        super(Material.ROCK);
        this.tier = tier;
        this.type = type;
        this.isActive = isActive;

        setRegistryName( "crucible_" + type + (isActive ? "Active" : ""));
        setUnlocalizedName("crucible_" + type);
        this.setSoundType(SoundType.STONE);
        this.setHardness(8F);
        this.setResistance(8F);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    @Override
    public TileEntity createTileEntity(final World world, final IBlockState state) {
        return new TileEntityCrucible();
    }

    @Override
    public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
    }

    @Override
    public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {

        TileEntityCrucible tile = (TileEntityCrucible) getTile(world, pos);
        if (tile != null) {
            ItemStack held = player.getHeldItem(hand);
            if (!held.isEmpty() && held.getItem() == ComponentListMFR.ARTEFACTS && held.getItemDamage() == 3) {
                if (tier == 2 && isActive) {
                    held.shrink(1);
                    if (held.getCount() <= 0) {
                        player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    }
                    if (!world.isRemote) {
                        world.spawnEntity(new EntityLightningBolt(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, true));
                        world.setBlockState(pos, (BlockListMFR.CRUCIBLE_MASTER).getDefaultState(), 2);
                    }
                }
                return true;
            }
            ItemStack out = tile.getInventory().getStackInSlot(tile.getInventory().getSlots() - 1);
            if (!held.isEmpty() && held.getItem() == ComponentListMFR.INGOT_MOULD
                    && !out.isEmpty()
                    && !(out.getItem() instanceof ItemBlock)) {
                ItemStack result = out.copy();
                result.setCount(1);
                tile.getInventory().extractItem(tile.getInventory().getSlots() - 1, 1, false);

                ItemStack mould = ItemFilledMould.createMould(result);
                if (held.getCount() == 1) {
                    player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, mould);
                } else {
                    held.shrink(1);
                    if (!world.isRemote) {
                        EntityItem drop = new EntityItem(world, player.posX, player.posY, player.posZ, mould);
                        drop.setPickupDelay(0);
                        world.spawnEntity(drop);
                    }
                }
                return true;
            }

        }

        if (!world.isRemote) {
            final TileEntityCrucible tileEntity = (TileEntityCrucible) getTile(world, pos);
            if (tileEntity != null) {
                tileEntity.openGUI(world, player);
            }
        }

        return true;
    }

    public BlockCrucible setAuto() {
        isAuto = true;
        return this;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> item) {
        if (!isActive) {
            super.getSubBlocks(tab, item);
        }
    }
}