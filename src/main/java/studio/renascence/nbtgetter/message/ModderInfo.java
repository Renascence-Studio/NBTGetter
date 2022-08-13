package studio.renascence.nbtgetter.message;

import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class ModderInfo extends BaseInfo {

    public ModderInfo() {
        super("modder");
    }

    @Override
    public int send(ServerPlayer player, ItemStack stack) {
        var uuid = player.getUUID();
        var itemRL = Objects.requireNonNull(stack.getItem().getRegistryName());
        var name = optimizeString(itemRL.getPath());

        if (stack.isEmpty()) {
            player.sendMessage(colorTrans("text.infogetter.air", ChatFormatting.RED), uuid);
            return 0;
        }

        player.sendMessage(colorText(name +" Info"+"(Modder)"+": ", ChatFormatting.GREEN), uuid);

        if (stack.getItem() instanceof BlockItem blockItem && BlockItem.BY_BLOCK.get(blockItem.getBlock()) != null) {
            player.sendMessage(copy("-ClassPathname(Item) ", stack.getItem().getClass().getName()), uuid);
            player.sendMessage(copy("-ClassPathname(Block) ", blockItem.getBlock().getClass().getName()), uuid);
        }else {
            player.sendMessage(copy("-ClassPathname ", stack.getItem().getClass().getName()), uuid);
        }

        return 0;
    }

    @Override
    public BaseInfo create() {
        return new ModderInfo();
    }
}
