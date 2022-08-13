package studio.renascence.nbtgetter.message;

import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class KubejsInfo extends BaseInfo {

    public KubejsInfo() {
        super("kubejs");
    }

    @Override
    public int send(ServerPlayer player, ItemStack stack) {
        var uuid = player.getUUID();
        var itemRL = Objects.requireNonNull(stack.getItem().getRegistryName());
        var tagSet = stack.getTags().toList();
        var name = optimizeString(itemRL.getPath());
        var tab = stack.getItem().getItemCategory();

        if (stack.isEmpty()) {
            player.sendMessage(colorTrans("text.infogetter.air", ChatFormatting.RED), uuid);
            return 0;
        }

        player.sendMessage(colorText(name +" Info"+"(Kubejs)"+": ", ChatFormatting.GREEN), uuid);

        if (stack.getTag() != null)
            player.sendMessage(copy("-Stack ", "Item.of(" + formatting(itemRL.toString()) + ", " + formatting(String.valueOf(stack.getTag())) + ")"), uuid);
        else if (stack.getCount() == 1)
            player.sendMessage(copy("-Stack ", formatting(itemRL.toString())), uuid);
        else
            player.sendMessage(copy("-Stack ", formatting(stack.getCount()+ "x " + itemRL)), uuid);

        if (tagSet.size() == 1)
            player.sendMessage(copy("-Tag ", formatting("#", tagSet.get(0).location().toString())), uuid);
        else if (tagSet.size() != 0) {
            for (int i = 0; i < tagSet.size(); i++) {
                player.sendMessage(copy("-Tag " + (i+1) + " ", formatting("#", tagSet.get(i).location().toString())), uuid);
            }
        }

        player.sendMessage(copy("-Mod ", formatting("@", itemRL.getNamespace())), uuid);

        if (tab != null)
            player.sendMessage(copy("-Tab ", formatting("%", tab.getRecipeFolderName())), uuid);

        return 0;
    }

    @Override
    public BaseInfo create() {
        return new KubejsInfo();
    }

    protected String formatting(String head, String itemRL) {
        return "'"+ head + itemRL + "'";
    }

    protected String formatting(String itemRL) {
        return formatting("", itemRL);
    }
}
