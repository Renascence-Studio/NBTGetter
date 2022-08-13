package studio.renascence.nbtgetter.message;

import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class DatapackInfo extends BaseInfo {

    public DatapackInfo() {
        super("datapack");
    }

    @Override
    public int send(ServerPlayer player, ItemStack stack) {
        var uuid = player.getUUID();
        var itemRL = Objects.requireNonNull(stack.getItem().getRegistryName());
        var tagSet = stack.getTags().toList();
        var name = optimizeString(itemRL.getPath());

        if (stack.isEmpty()) {
            player.sendMessage(colorTrans("text.infogetter.air", ChatFormatting.RED), uuid);
            return 0;
        }

        player.sendMessage(colorText(name +" Info"+"(Datapack)"+": ", ChatFormatting.GREEN), uuid);

        player.sendMessage(copy("-Name ", itemRL.getPath()), uuid);
        player.sendMessage(copy("-UName ", "\""+itemRL.getPath()+"\""), uuid);
        player.sendMessage(copy("-ID ", formatting("item", itemRL.toString())), uuid);
        if (stack.getCount() != 1)
            player.sendMessage(copy("-Count ", formatting("count", String.valueOf(stack.getCount()))), uuid);

        if (tagSet.size() == 1)
            player.sendMessage(copy("-Tag ", formatting("tag", tagSet.get(0).location().toString())), uuid);
        else if (tagSet.size() != 0) {
            for (int i = 0; i < tagSet.size(); i++) {
                player.sendMessage(copy("-Tag " + (i+1) + " ", formatting("tag", tagSet.get(i).location().toString())), uuid);
            }
        }

        if (stack.getTag() != null)
            player.sendMessage(copy("-NBT ", formatting("nbt", stack.getTag().toString())), uuid);
        return 0;
    }

    @Override
    public BaseInfo create() {
        return new DatapackInfo();
    }

    protected String formatting(String head, String itemRL) {
        return "\"" + head + "\": " + "\"" + itemRL + "\"";
    }
}
