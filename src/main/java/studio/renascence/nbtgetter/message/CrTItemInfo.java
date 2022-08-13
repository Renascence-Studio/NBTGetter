package studio.renascence.nbtgetter.message;

import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class CrTItemInfo extends BaseInfo {

    public CrTItemInfo() {
        super("crt");
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

        player.sendMessage(colorText(name +" Info"+"(CraftTweaker)"+": ", ChatFormatting.GREEN), uuid);

        var str1 = formatting("item", itemRL.toString());
        if (stack.getTag() != null) {
            var nbt = stack.getTag().copy();
            if (stack.getTag().contains("Damage")) {
                var damage = stack.getTag().getInt("Damage");
                if (nbt.size() == 1) {
                    var str2 = stack.isDamaged() ? str1 + ".withDamage("+damage+")" : str1;
                    player.sendMessage(copy("-Stack ", str2), uuid);
                }else {
                    nbt.remove("Damage");
                    var str2 = str1 + ".withTag("+nbt+")";
                    var str3 = stack.isDamaged() ? str2 + ".withDamage("+damage+")" : str2;
                    player.sendMessage(copy("-Stack ", str3), uuid);
                }
            }else {
                var str = str1 + ".withTag("+nbt+")";
                player.sendMessage(copy("-Stack ", str), uuid);
            }
        } else if (stack.getCount() == 1) {
            player.sendMessage(copy("-Stack ", str1), uuid);
        } else {
            player.sendMessage(copy("-Stack ", str1 + " * " + stack.getCount()), uuid);
        }

        if (tagSet.size() == 1)
            player.sendMessage(copy("-Tag ", formatting("tag:items", tagSet.get(0).location().toString())), uuid);
        else if (tagSet.size() != 0) {
            for (int i = 0; i < tagSet.size(); i++) {
                player.sendMessage(copy("-Tag " + (i+1) + " ", formatting("tag:items", tagSet.get(i).location().toString())), uuid);
            }
        }

        return 0;
    }

    @Override
    public BaseInfo create() {
        return new CrTItemInfo();
    }

    protected String formatting(String head, String itemRL) {
        return "<"+ head + ":" + itemRL + ">";
    }
}
