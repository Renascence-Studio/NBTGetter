package studio.renascence.nbtgetter.message;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;

import java.util.Objects;

public class MoreInfo extends BaseInfo {

    public MoreInfo() {
        super("more");
    }

    @Override
    public int send(ServerPlayer player, ItemStack stack) {
        var uuid = player.getUUID();
        var flag = true;
        var itemRL = Objects.requireNonNull(stack.getItem().getRegistryName());
        var name = optimizeString(itemRL.getPath());

        if (stack.isEmpty()) {
            player.sendMessage(colorTrans("text.infogetter.air", ChatFormatting.RED), uuid);
            return 0;
        }

        player.sendMessage(colorText(name +" Info"+"(More)"+": ", ChatFormatting.GREEN), uuid);

        if (stack.getTag() != null) {
            player.sendMessage(copy("-NBT ", String.valueOf(stack.getTag())), uuid);
            flag = false;
        }
        if (stack.getItem() instanceof BlockItem blockItem && BlockItem.BY_BLOCK.get(blockItem.getBlock()) != null) {
            player.sendMessage(copy("-IsBlockItem ", String.valueOf(true)), uuid);
            flag = false;
        }
        if (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0) {
            player.sendMessage(copy("-IsFuel ", String.valueOf(true)), uuid);
            player.sendMessage(copy("-BurnTime ", String.valueOf(ForgeHooks.getBurnTime(stack, RecipeType.SMELTING))), uuid);
            if (stack.getCount() > 1) {
                player.sendMessage(copy("-TotalBurnTime ", String.valueOf(ForgeHooks.getBurnTime(stack, RecipeType.SMELTING)*stack.getCount())), uuid);
            }
            flag = false;
        }
        if (stack.isEnchanted()) {
            ListTag enchantments = stack.getEnchantmentTags();
            if (enchantments.size() == 1)
                player.sendMessage(copy("-Enchantment ", enchantments.get(0).getAsString()), uuid);
            else {
                player.sendMessage(colorText("-Enchantments ", ChatFormatting.GREEN), uuid);
                for (int i = 0; i <enchantments.size(); i++) {
                    player.sendMessage(copy("--Enchantment " + (i+1) + " ", enchantments.get(i).getAsString()), uuid);
                }
            }
            flag = false;
        }
        if (stack.getDamageValue() != 0) {
            player.sendMessage(copy("-Damage ", String.valueOf(stack.getDamageValue())), uuid);
            flag = false;
        }
        if (flag) {
            player.sendMessage(colorTrans("text.infogetter.more", ChatFormatting.RED), uuid);
        }

        return 0;
    }

    @Override
    public BaseInfo create() {
        return new MoreInfo();
    }
}
