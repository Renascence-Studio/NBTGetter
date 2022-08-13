package studio.renascence.nbtgetter.message;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public abstract class BaseInfo {
    private final String name;

    public BaseInfo(String name) {
        this.name = name;
    }

    protected static Component copy(String head, String s) {
        return copy(head, s, ChatFormatting.AQUA);
    }

    protected static Component copy(String head, String s, ChatFormatting col) {
        var component = (TextComponent) colorText(head, ChatFormatting.GREEN);
        component.setStyle(component.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, s)));
        var copy = colorTrans("text.infogetter.copy", ChatFormatting.GREEN);
        component.setStyle(component.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, copy)));
        component.append(new TextComponent(s).withStyle(col));
        return component;
    }

    protected static Component show(String head, String s, ChatFormatting col, Component show) {
        var component = (TextComponent) colorText(head, ChatFormatting.GREEN);
        component.setStyle(component.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, show)));
        component.append(new TextComponent(s).withStyle(col));
        return component;
    }

    protected static Component colorTrans(String s, ChatFormatting co) {
        var component = new TranslatableComponent(s);
        component.setStyle(component.getStyle().withColor(co));
        return component;
    }

    protected static Component colorText(String s, ChatFormatting co) {
        var component = new TextComponent(s);
        component.setStyle(component.getStyle().withColor(co));
        return component;
    }

    public static String optimizeString(String str) {
        String[] strs = str.split("_");
        StringBuilder sb = new StringBuilder();
        for (String strTmp : strs) {
            char[] ch = strTmp.toCharArray();
            if (ch[0] >= 'a' && ch[0] <= 'z') {
                ch[0] = (char) (ch[0] - 32);
            }
            String strT = new String(ch);
            sb.append(strT).append(" ");
        }
        return sb.toString().trim();
    }

    public int send(ServerPlayer player, int slot, Collection<? extends Entity> collection) {
        for(Entity entity : collection) {
            SlotAccess access = entity.getSlot(slot);
            if (access != SlotAccess.NULL) {
                return send(player, access.get().copy());
            }
        }
        return 0;
    }

    public int send(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        return send(player, player.getMainHandItem());
    }

    public abstract int send(ServerPlayer player, ItemStack stack);
    public abstract BaseInfo create();

    public String getName() {
        return name;
    }

    public LiteralArgumentBuilder<CommandSourceStack> executesMain() {
        return Commands.literal(getName()).executes(context -> create().send(context));
    }

    public LiteralArgumentBuilder<CommandSourceStack> executesOff() {
        return Commands.literal(getName()).executes(context -> {
            var player = context.getSource().getPlayerOrException();
            return create().send(player, player.getOffhandItem());});
    }

    public LiteralArgumentBuilder<CommandSourceStack> executesItem() {
        return Commands.literal(getName()).executes(context -> {
            var player = context.getSource().getPlayerOrException();
            return create().send(player, ItemArgument.getItem(context, "item").getItem().getDefaultInstance());});
    }

    public LiteralArgumentBuilder<CommandSourceStack> executesSlot() {
        return Commands.literal(getName()).executes(context -> {
            var player = context.getSource().getPlayerOrException();
            return create().send(player, SlotArgument.getSlot(context, "slot"), EntityArgument.getEntities(context, "targets"));});
    }
}
