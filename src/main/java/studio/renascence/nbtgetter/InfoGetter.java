package studio.renascence.nbtgetter;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(InfoGetter.MODID)
public class InfoGetter {
    public static final String MODID = "infogetter";

    public InfoGetter() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
