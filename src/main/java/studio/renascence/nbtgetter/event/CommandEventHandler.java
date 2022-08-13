package studio.renascence.nbtgetter.event;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import studio.renascence.nbtgetter.message.*;

@Mod.EventBusSubscriber
public class CommandEventHandler {
    @SubscribeEvent
    public static void onServerStaring(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();

        node(dispatcher, Commands.literal("hand").then(new DatapackInfo().executesMain()));
        node(dispatcher, Commands.literal("hand").then(new KubejsInfo().executesMain()));
        node(dispatcher, Commands.literal("hand").then(new CommonInfo().executesMain()));
        node(dispatcher, Commands.literal("hand").then(new CrTItemInfo().executesMain()));
        node(dispatcher, Commands.literal("hand").then(new MoreInfo().executesMain()));
        node(dispatcher, Commands.literal("hand").then(new ModderInfo().executesMain()));
        node(dispatcher, Commands.literal("hand").then(new ContainerInfo().executesMain()));

        node(dispatcher, Commands.literal("offhand").then(new DatapackInfo().executesOff()));
        node(dispatcher, Commands.literal("offhand").then(new KubejsInfo().executesOff()));
        node(dispatcher, Commands.literal("offhand").then(new CommonInfo().executesOff()));
        node(dispatcher, Commands.literal("offhand").then(new CrTItemInfo().executesOff()));
        node(dispatcher, Commands.literal("offhand").then(new MoreInfo().executesOff()));
        node(dispatcher, Commands.literal("offhand").then(new ModderInfo().executesOff()));
        node(dispatcher, Commands.literal("offhand").then(new ContainerInfo().executesOff()));

        node(dispatcher, Commands.literal("entity").then(entity(new DatapackInfo().executesSlot())));
        node(dispatcher, Commands.literal("entity").then(entity(new KubejsInfo().executesSlot())));
        node(dispatcher, Commands.literal("entity").then(entity(new CommonInfo().executesSlot())));
        node(dispatcher, Commands.literal("entity").then(entity(new CrTItemInfo().executesSlot())));
        node(dispatcher, Commands.literal("entity").then(entity(new MoreInfo().executesSlot())));
        node(dispatcher, Commands.literal("entity").then(entity(new ModderInfo().executesSlot())));
        node(dispatcher, Commands.literal("entity").then(entity(new ContainerInfo().executesSlot())));

        node(dispatcher, Commands.literal("item").then(item(new DatapackInfo().executesItem())));
        node(dispatcher, Commands.literal("item").then(item(new KubejsInfo().executesItem())));
        node(dispatcher, Commands.literal("item").then(item(new CommonInfo().executesItem())));
        node(dispatcher, Commands.literal("item").then(item(new CrTItemInfo().executesItem())));
        node(dispatcher, Commands.literal("item").then(item(new MoreInfo().executesItem())));
        node(dispatcher, Commands.literal("item").then(item(new ModderInfo().executesItem())));
        node(dispatcher, Commands.literal("item").then(item(new ContainerInfo().executesItem())));
    }


    public static RequiredArgumentBuilder<CommandSourceStack, ItemInput> item(LiteralArgumentBuilder<CommandSourceStack> node) {
        return Commands.argument("item", ItemArgument.item()).then(node);
    }

    public static RequiredArgumentBuilder<CommandSourceStack, EntitySelector> entity(LiteralArgumentBuilder<CommandSourceStack> node) {
        return Commands.argument("targets", EntityArgument.entities()).then(Commands.literal("slot").then(Commands.argument("slot", SlotArgument.slot()).then(node)));
    }

    public static void node(CommandDispatcher<CommandSourceStack> dispatcher, LiteralArgumentBuilder<CommandSourceStack> node) {
        dispatcher.register(Commands.literal("info").then(node));
    }
}
