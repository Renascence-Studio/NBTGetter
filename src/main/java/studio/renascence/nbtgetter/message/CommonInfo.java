package studio.renascence.nbtgetter.message;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class CommonInfo extends BaseInfo {

    public CommonInfo() {
        super("common");
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

        player.sendMessage(colorText(name +" Info"+"(Common)"+": ", ChatFormatting.GREEN), uuid);

        player.sendMessage(copy("-ID ", itemRL.toString()), uuid);
        player.sendMessage(copy("-Namespace(Mod) ", itemRL.getNamespace()), uuid);
        player.sendMessage(copy("-RegisteredName ", itemRL.getPath()), uuid);
        player.sendMessage(copy("-Name ", name), uuid);
        player.sendMessage(copy("-TranslationKey ", stack.getItem().getDescriptionId()), uuid);
        player.sendMessage(copy("-LocalizedName ", new TranslatableComponent(stack.getItem().getDescriptionId()).getString()), uuid);
        player.sendMessage(copy("-Count ", String.valueOf(stack.getCount())), uuid);
        player.sendMessage(copy("-MaxStackSize ", String.valueOf(stack.getMaxStackSize())), uuid);

        return 0;
    }

    @Override
    public BaseInfo create() {
        return new CommonInfo();
    }
}
