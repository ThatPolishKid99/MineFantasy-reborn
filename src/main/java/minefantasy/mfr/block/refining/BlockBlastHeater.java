package minefantasy.mfr.block.refining;

import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.block.basic.BlockTileEntity;
import minefantasy.mfr.tile.blastfurnace.TileEntityBlastHeater;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.KnowledgeListMFR;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockBlastHeater extends BlockTileEntity<TileEntityBlastHeater> {
    private static final PropertyBool BURNING = PropertyBool.create("burning");

    public BlockBlastHeater() {
        super(Material.ANVIL);


        setRegistryName("blast_heater");
        setUnlocalizedName("blastfurnheater");
        this.setSoundType(SoundType.METAL);
        this.setHardness(10F);
        this.setResistance(10F);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BURNING);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityBlastHeater();
    }

    @Nonnull
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(BURNING, (meta == 1));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BURNING) ? 1 : 0;
    }

    @Nonnull
    @Override
    public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
        return getDefaultState().withProperty(BURNING, false);
    }

    public static void setActiveState(boolean active, World world, BlockPos pos){
        world.setBlockState(pos, world.getBlockState(pos).withProperty(BURNING, active));
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos){
        TileEntityBlastHeater tile = (TileEntityBlastHeater) getTile(world, pos);
        if (tile != null)
            tile.updateBuild();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        final TileEntityBlastHeater tile = (TileEntityBlastHeater) getTile(world, pos);
        if (tile != null) {
            if (ResearchLogic.hasInfoUnlocked(player, KnowledgeListMFR.blastfurn)) {
                if (world.isRemote)
                    player.sendMessage(new TextComponentString(I18n.format("knowledge.unknownUse")));
                return false;
            }
        }
        if (!world.isRemote) {
            TileEntityBlastHeater tileEntity = (TileEntityBlastHeater) getTile(world, pos);
            if (tileEntity != null) {
                tileEntity.openGUI(world, player);
            }
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {
        TileEntityBlastHeater tile = (TileEntityBlastHeater) getTile(world, pos);
        if (tile.isBurning() && rand.nextInt(20) == 0) {
            world.playSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.AMBIENT,1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
        }
    }
}
