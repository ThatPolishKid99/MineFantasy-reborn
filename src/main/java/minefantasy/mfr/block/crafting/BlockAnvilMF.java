package minefantasy.mfr.block.crafting;

import minefantasy.mfr.api.heating.TongsHelper;
import minefantasy.mfr.block.basic.BlockTileEntity;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.item.tool.crafting.ItemTongs;
import minefantasy.mfr.material.BaseMaterialMFR;
import minefantasy.mfr.tile.TileEntityAnvilMFR;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAnvilMF extends BlockTileEntity<TileEntityAnvilMFR> {
    public BaseMaterialMFR material;
    private int tier;
    float height = 1.0F / 16F * 13F;
    AxisAlignedBB BlockBB = new AxisAlignedBB(0F, 0F, 0F, 1F, height, 1F);

    public BlockAnvilMF(BaseMaterialMFR material) {
        super(Material.ANVIL);
        this.material = material;
        String name = "anvil_" + material.name;
        this.tier = material.tier;

        setRegistryName(name);
        setUnlocalizedName(name);
        this.setSoundType(SoundType.METAL);
        this.setHardness(material.hardness + 1 / 2F);
        this.setResistance(material.hardness + 1);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityAnvilMFR();
    }

    @Override
    public AxisAlignedBB getBoundingBox (IBlockState state, IBlockAccess source, BlockPos pos){
        return BlockBB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack) {
        world.setBlockState(pos, state);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        TileEntityAnvilMFR tile = (TileEntityAnvilMFR) getTile(world, pos);
        if (tile != null) {
            if (facing == EnumFacing.NORTH && !held.isEmpty() && held.getItem() instanceof ItemTongs
                    && onUsedTongs(world, player, held, tile)) {
                return true;
            }
            if (facing == EnumFacing.NORTH || !tile.tryCraft(player, true) && !world.isRemote) {
                tile.openGUI(world, player);
            }
        }
        return true;
    }

    private boolean onUsedTongs(World world, EntityPlayer user, ItemStack held, TileEntityAnvilMFR tile) {
        ItemStack result = tile.getInventory().getStackInSlot(tile.getInventory().getSlots() - 1);
        ItemStack grabbed = TongsHelper.getHeldItem(held);

        // GRAB
        if (grabbed.isEmpty()) {
            if (!result.isEmpty() && result.getItem() == ComponentListMFR.HOT_ITEM) {
                if (TongsHelper.trySetHeldItem(held, result)) {
                    tile.getInventory().setStackInSlot(tile.getInventory().getSlots() - 1, ItemStack.EMPTY);
                    return true;
                }
            }
        } else {
            for (int s = 0; s < (tile.getInventory().getSlots() - 1); s++) {
                ItemStack slot = tile.getInventory().getStackInSlot(s);
                if (slot.isEmpty()) {
                    tile.getInventory().setStackInSlot(s, grabbed);
                    TongsHelper.clearHeldItem(held, user);
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer user) {
        {
            TileEntityAnvilMFR tile = (TileEntityAnvilMFR) getTile(world, pos);
            if (tile != null) {
                if (user.isSneaking()) {
                    tile.upset(user);
                } else {
                    tile.tryCraft(user, false);
                }
            }
        }
    }

    public int getTier() {
        return tier;
    }


}