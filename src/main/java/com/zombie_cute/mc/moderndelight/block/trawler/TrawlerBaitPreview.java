package com.zombie_cute.mc.moderndelight.block.trawler;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.List;

/** A server-authored, informational page; outputs are alternatives, not a guaranteed haul. */
public record TrawlerBaitPreview(Identifier id, List<ItemStack> baits, List<ItemStack> outputs,
                                int intervalTicks, float consumeChance, boolean openWater,
                                boolean incomplete, int page, int pages) {
    public static final int OUTPUTS_PER_PAGE = 18;

    public TrawlerBaitPreview {
        baits = List.copyOf(baits);
        outputs = List.copyOf(outputs);
    }

    public void write(PacketByteBuf buf) {
        buf.writeIdentifier(id);
        buf.writeCollection(baits, PacketByteBuf::writeItemStack);
        buf.writeCollection(outputs, PacketByteBuf::writeItemStack);
        buf.writeVarInt(intervalTicks);
        buf.writeFloat(consumeChance);
        buf.writeBoolean(openWater);
        buf.writeBoolean(incomplete);
        buf.writeVarInt(page);
        buf.writeVarInt(pages);
    }

    public static TrawlerBaitPreview read(PacketByteBuf buf) {
        return new TrawlerBaitPreview(buf.readIdentifier(), buf.readList(PacketByteBuf::readItemStack),
                buf.readList(PacketByteBuf::readItemStack), buf.readVarInt(), buf.readFloat(),
                buf.readBoolean(), buf.readBoolean(), buf.readVarInt(), buf.readVarInt());
    }
}
