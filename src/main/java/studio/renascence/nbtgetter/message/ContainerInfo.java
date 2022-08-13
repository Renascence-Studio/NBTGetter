package studio.renascence.nbtgetter.message;

import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class ContainerInfo extends BaseInfo {

    public ContainerInfo() {
        super("container");
    }

    @Override
    public int send(ServerPlayer player, ItemStack stack) {
        var uuid = player.getUUID();
        var itemRL = Objects.requireNonNull(stack.getItem().getRegistryName());
        var name = optimizeString(itemRL.getPath());
        var flag = true;

        if (stack.isEmpty()) {
            player.sendMessage(colorTrans("text.infogetter.air", ChatFormatting.RED), uuid);
            return 0;
        }

        player.sendMessage(colorText(name +" Info"+"(Container)"+": ", ChatFormatting.GREEN), uuid);

        if (stack.getItem() instanceof BucketItem bucket) {
            var fluid = bucket.getFluid();
            var r = fluid.getRegistryName();
            var list = fluid.defaultFluidState().getTags().toList();
            assert r != null;
            player.sendMessage(copy("-FluidID ", r.toString()), uuid);
            if (list.size() == 1)
                player.sendMessage(copy("-FluidTag ", list.get(0).location().toString()), uuid);
            else if (list.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    player.sendMessage(copy("-FluidTag " + (i+1) + " ", list.get(i).location().toString()), uuid);
                }
            }
            flag = false;
        }
        if (flag) {
            player.sendMessage(colorTrans("text.infogetter.container", ChatFormatting.RED), uuid);
        }

        return 0;
    }

    @Override
    public BaseInfo create() {
        return new ContainerInfo();
    }
}
