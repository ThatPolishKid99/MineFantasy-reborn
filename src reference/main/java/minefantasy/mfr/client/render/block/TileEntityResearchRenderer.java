package minefantasy.mfr.client.render.block;

import minefantasy.mfr.api.helpers.TextureHelperMFR;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class TileEntityResearchRenderer extends TileEntitySpecialRenderer {
    private ModelResearch model;
    private Random random = new Random();

    public TileEntityResearchRenderer() {
        model = new ModelResearch();
    }

    public void renderAModelAt(TileEntity tile, double d, double d1, double d2, float f) {
        int i = 0;
        if (tile.getWorld() != null) {
            i = tile.getBlockMetadata();
        }
        this.renderModelAt("researchTable", i, d, d1, d2, f);
    }

    public void renderModelAt(String tex, int meta, double d, double d1, double d2, float f) {
        int i = meta;

        int j = 90 * i;

        if (i == 0) {
            j = 0;
        }

        if (i == 1) {
            j = 270;
        }

        if (i == 2) {
            j = 180;
        }

        if (i == 3) {
            j = 90;
        }
        if (i == 4) {
            j = 90;
        }

        bindTextureByName("textures/models/tileentity/" + tex + ".png"); // texture

        GL11.glPushMatrix(); // start
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1.25F, (float) d2 + 0.5F); // size
        GL11.glRotatef(j + 180F, 0.0F, 1.0F, 0.0F); // rotate based on metadata
        GL11.glScalef(1F, -1F, -1F); // if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        model.renderModel(0.0625F);

        GL11.glPopMatrix();
        GL11.glColor3f(255, 255, 255);
        GL11.glPopMatrix(); // end

    }

    private void bindTextureByName(String image) {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureHelperMFR.getResource(image));
    }


    private boolean shouldRender(TileEntity tile, int p) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer sp = mc.player;
        if (p == 1)// GRID
        {
            return false;
        }
        return true;
    }
}
