package studio.renascence.nbtgetter.event;

import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import studio.renascence.nbtgetter.message.*;

@Mod.EventBusSubscriber
public class CommandEventHandler {
    @SubscribeEvent
    public static void onServerStaring(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();
        var command = dispatcher.register(Commands.literal("info")
                .then(Commands.literal("hand")
                        .then(Commands.literal("datapack")
                                .executes(context -> new DatapackInfo().send(context)))
                        .then(Commands.literal("kubejs")
                                .executes(context -> new KubejsInfo().send(context)))
                        .then(Commands.literal("common")
                                .executes(context -> new CommonInfo().send(context)))
                        .then(Commands.literal("more")
                                .executes(context -> new MoreInfo().send(context))
                        )
                        .then(Commands.literal("crt")
                                .executes(context -> new CrTItemInfo().send(context))
                        )
                )
                .then(Commands.literal("offhand")
                        .then(Commands.literal("datapack")
                                .executes(context -> new DatapackInfo().send(context.getSource().getPlayerOrException(), context.getSource().getPlayerOrException().getOffhandItem())))
                        .then(Commands.literal("kubejs")
                                .executes(context -> new KubejsInfo().send(context.getSource().getPlayerOrException(), context.getSource().getPlayerOrException().getOffhandItem())))
                        .then(Commands.literal("common")
                                .executes(context -> new CommonInfo().send(context.getSource().getPlayerOrException(), context.getSource().getPlayerOrException().getOffhandItem())))
                        .then(Commands.literal("more")
                                .executes(context -> new MoreInfo().send(context.getSource().getPlayerOrException(), context.getSource().getPlayerOrException().getOffhandItem())))
                        .then(Commands.literal("crt")
                                .executes(context -> new CrTItemInfo().send(context.getSource().getPlayerOrException(), context.getSource().getPlayerOrException().getOffhandItem())))
                )
                .then(Commands.literal("item")
                        .then(Commands.argument("item", ItemArgument.item())
                                .then(Commands.literal("datapack")
                                        .executes(context -> new DatapackInfo().send(context.getSource().getPlayerOrException(), ItemArgument.getItem(context, "item").getItem().getDefaultInstance())))
                                .then(Commands.literal("kubejs")
                                        .executes(context -> new KubejsInfo().send(context.getSource().getPlayerOrException(), ItemArgument.getItem(context, "item").getItem().getDefaultInstance())))
                                .then(Commands.literal("common")
                                        .executes(context -> new CommonInfo().send(context.getSource().getPlayerOrException(), ItemArgument.getItem(context, "item").getItem().getDefaultInstance())))
                                .then(Commands.literal("more")
                                        .executes(context -> new MoreInfo().send(context.getSource().getPlayerOrException(), ItemArgument.getItem(context, "item").getItem().getDefaultInstance())))
                                .then(Commands.literal("crt")
                                        .executes(context -> new CrTItemInfo().send(context.getSource().getPlayerOrException(), ItemArgument.getItem(context, "item").getItem().getDefaultInstance())))
                        )
                )
        );
    }
}
