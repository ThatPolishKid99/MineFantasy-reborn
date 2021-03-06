package minefantasy.mfr.entity.mob.ai.hound;

import minefantasy.mfr.entity.mob.EntityHound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class AI_HoundFollow extends EntityAIBase {
    World theWorld;
    float maxDist;
    float minDist;
    private EntityHound thePet;
    private EntityLivingBase theOwner;
    private double field_75336_f;
    private PathNavigate petPathfinder;
    private int field_75343_h;
    private boolean field_75344_i;

    public AI_HoundFollow(EntityHound dog, double p_i1625_2_, float min, float max) {
        this.thePet = dog;
        this.theWorld = dog.world;
        this.field_75336_f = p_i1625_2_;
        this.petPathfinder = dog.getNavigator();
        this.minDist = min;
        this.maxDist = max;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = this.thePet.getOwner();

        if (entitylivingbase == null) {
            return false;
        } else if (this.thePet.isSitting()) {
            return false;
        } else if (this.thePet.getDistanceSq(entitylivingbase) < this.minDist * this.minDist) {
            return false;
        } else {
            this.theOwner = entitylivingbase;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting() {
        return !this.petPathfinder.noPath()
                && this.thePet.getDistanceSq(this.theOwner) > this.maxDist * this.maxDist
                && !this.thePet.isSitting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.field_75343_h = 0;
        this.field_75344_i = this.thePet.getNavigator().getAvoidsWater();
        this.thePet.getNavigator().setAvoidsWater(false);
    }

    /**
     * Resets the task
     */
    public void resetTask() {
        this.theOwner = null;
        this.petPathfinder.clearPathEntity();
        this.thePet.getNavigator().setAvoidsWater(this.field_75344_i);
    }

    /**
     * Updates the task
     */
    public void updateTask() {
        this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, this.thePet.getVerticalFaceSpeed());

        if (!this.thePet.isSitting() && this.thePet.shouldFollowOwner()) {
            if (--this.field_75343_h <= 0) {
                this.field_75343_h = 10;

                if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.field_75336_f)) {
                    if (!this.thePet.getLeashed()) {
                        if (this.thePet.getDistanceSq(this.theOwner) >= 144.0D)// Teleport
                        {
                            int i = MathHelper.floor(this.theOwner.posX) - 2;
                            int j = MathHelper.floor(this.theOwner.posZ) - 2;
                            int k = MathHelper.floor(this.theOwner.getCollisionBoundingBox().minY);

                            for (int l = 0; l <= 4; ++l) {
                                for (int i1 = 0; i1 <= 4; ++i1) {
                                    if ((l < 1 || i1 < 1 || l > 3 || i1 > 3)
                                            && World.doesBlockHaveSolidTopSurface(this.theWorld, i + l, k - 1, j + i1)
                                            && !this.theWorld.getBlock(i + l, k, j + i1).isNormalCube()
                                            && !this.theWorld.getBlock(i + l, k + 1, j + i1).isNormalCube()) {
                                        this.thePet.setLocationAndAngles(i + l + 0.5F, k, j + i1 + 0.5F,
                                                this.thePet.rotationYaw, this.thePet.rotationPitch);
                                        this.petPathfinder.clearPathEntity();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}