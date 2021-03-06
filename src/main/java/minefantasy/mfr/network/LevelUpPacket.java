package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.api.rpg.RPGElements;
import minefantasy.mfr.api.rpg.Skill;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class LevelUpPacket extends PacketMF {
    public static final String packetName = "MF2_levelup";
    private UUID username;
    private int level;
    private String skillName;
    private String name;
    private int skillLevel;

    public LevelUpPacket(EntityPlayer user, Skill skill, int level) {
        this.username = user.getUniqueID();
        this.skillName = skill.skillName;
        this.level = level;
    }

    public LevelUpPacket() {
    }

    @Override
    public void readFromStream(ByteBuf packet) {
        name = ByteBufUtils.readUTF8String(packet);
        skillLevel = packet.readInt();
        username = UUID.fromString(ByteBufUtils.readUTF8String(packet));

    }

    @Override
    public void writeToStream(ByteBuf packet) {
        ByteBufUtils.writeUTF8String(packet, skillName);
        packet.writeInt(level);
        ByteBufUtils.writeUTF8String(packet, username.toString());
    }

    @Override
    protected void execute(EntityPlayer player) {
        if (username != null && player.getUniqueID() == username) {
            Skill skill = RPGElements.getSkillByName(name);

            if (skill != null) {
                player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F, 0.5F);
                player.sendMessage(new TextComponentString(I18n.format("rpg.levelup", skill.getDisplayName().toLowerCase(), skillLevel)));
            }
        }
    }
}
