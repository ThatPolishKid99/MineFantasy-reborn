package minefantasy.mfr.block.decor;

import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.tile.decor.TileEntityTrough;
import minefantasy.mfr.tile.decor.TileEntityWoodDecor;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockTrough extends BlockWoodDecor {
    private static final PropertyInteger FILL_COUNT = PropertyInteger.create("fill_count", 0, 6);
    public static final String FILL_LEVEL = "fill_level";

    public BlockTrough(String name) {
        super(name);

        setRegistryName(name);
        setUnlocalizedName(name);
        this.setHardness(1F);
        this.setResistance(0.5F);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityTrough();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FILL_COUNT);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityTrough tile = (TileEntityTrough) getTile(world, pos);
        return state.withProperty(FILL_COUNT, tile.getFillCount());
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    public static void setActiveState(int fuelCount, World world, BlockPos pos){
        world.setBlockState(pos, world.getBlockState(pos).withProperty(FILL_COUNT, fuelCount));
    }

    @Nonnull
    @Override
    public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
        return getDefaultState().withProperty(FILL_COUNT, 0);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0F, 0F, 0F, 1.0F, (7F / 16F), 1.0F);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack item) {

        TileEntityTrough tile = (TileEntityTrough) getTile(world, pos);
        if (tile != null) {
            if (item.hasTagCompound() && item.getTagCompound().hasKey(FILL_LEVEL)) {
                tile.fill = item.getTagCompound().getInteger(FILL_LEVEL);
            }
            setActiveState(tile.getFillCount(), world, pos);
        }
        super.onBlockPlacedBy(world, pos, state, user, item);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = user.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityTrough) {
            if (((TileEntityTrough) tile).interact(user, held)) {
                world.playSound(user, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT, 0.125F + user.getRNG().nextFloat() / 4F, 0.5F + user.getRNG().nextFloat());
                ((TileEntityTrough) tile).syncData();
                return true;
            }
        }
        return false;
    }

    @Override
    protected ItemStack modifyDrop(TileEntityWoodDecor tile, ItemStack item) {
        return modifyFill((TileEntityTrough) tile, super.modifyDrop(tile, item));
    }

    private ItemStack modifyFill(TileEntityTrough tile, ItemStack item) {
        if (tile != null && !item.isEmpty()) {
            item.getTagCompound().setInteger(FILL_LEVEL, tile.fill);
        }
        return item;
    }
}
