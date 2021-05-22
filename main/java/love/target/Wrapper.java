package love.target;

import com.utils.logger.Logger;
import com.utils.logger.LoggerCreator;
import love.target.command.CommandManager;
import love.target.config.Config;
import love.target.config.ConfigManager;
import love.target.mod.ModManager;
import love.target.other.object.Link;
import love.target.render.font.FontManager;
import love.target.render.screen.altlogin.AltFileSaver;
import love.target.utils.LaunchUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Wrapper {
    public static final Minecraft mc = Minecraft.getMinecraft();
    private static final Logger<Object> logger = LoggerCreator.getLoggerObject(Wrapper.class);
    private static final String CLIENT_NAME = "Target";
    private static final String CLIENT_VERSION = "Beta -> 210521 #0";
    private static final String CLIENT_FILE_PATH = Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/target/";
    private static final Config NORMAL_CONFIG = new Config("",true);
    private static final Wrapper Instance = new Wrapper();
    private static final List<Link> links = new CopyOnWriteArrayList<>();

    private static long runTicks;

    /**
     * 在初始化游戏最后一刻调用
     * @see Minecraft
     */
    public void onStartGame() {
        links.add(new Link(new ResourceLocation("textures/gui/icons/github.png"),"GitHub","https://github.com/yaskylan/Target_Client"));
        initializationManagers();
        ConfigManager.loadConfig(NORMAL_CONFIG);
        AltFileSaver.readAlts();
        Display.setTitle("Minecraft 1.8.9 -> Target " + getClientVersion());
        getLogger().info("Client的版本为" + getClientVersion());
        getLogger().warn("本次启动浪费了" + (LaunchUtils.getLaunchTime() / 1000L) + "秒的人生");
    }

    /**
     * 初始化 -> Manager
     * @see ModManager
     * @see CommandManager
     * @see ConfigManager
     * @see FontManager
     */
    private static void initializationManagers() {
        ModManager.init();
        CommandManager.init();
        ConfigManager.init();
        FontManager.init();
    }

    /**
     * 在Minecraft.runTick()调用
     * @see Minecraft
     */
    public void runTick() {
        runTicks++;

        if (mc.world != null) {
            ConfigManager.saveConfig(NORMAL_CONFIG);
        }

        AltFileSaver.saveAlts();
    }

    public static void sendMessage(Object message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(EnumChatFormatting.WHITE + "[" + EnumChatFormatting.YELLOW + getClientName() + EnumChatFormatting.WHITE + "] " + EnumChatFormatting.GRAY + message));
    }

    public static void sendMessageNoClientName(Object message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(String.valueOf(message)));
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < length; ++i) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }

        return sb.toString();
    }

    public static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static int speedBsInteger(Entity entity) {
        return (int) speedBsDouble(entity);
    }

    public static double speedBsDouble(Entity entity) {
        double xDif = entity.posX - entity.prevPosX;
        double zDif = entity.posZ - entity.prevPosZ;
        return Math.sqrt(xDif * xDif + zDif * zDif) * 20.0;
    }

    public static String speedBsString(Entity entity, int format) {
        double xDif = entity.posX - entity.prevPosX;
        double zDif = entity.posZ - entity.prevPosZ;
        double lastDist = Math.sqrt(xDif * xDif + zDif * zDif) * 20.0;
        return String.format("%." + format + "f", lastDist * mc.timer.timerSpeed);
    }

    public static String getClientName() {
        return CLIENT_NAME;
    }

    public static String getClientVersion() {
        return CLIENT_VERSION;
    }

    public static Wrapper getInstance() {
        return Instance;
    }

    public static long getRunTicks() {
        return runTicks;
    }

    public static String getClientFilePath() {
        return CLIENT_FILE_PATH;
    }

    public static Logger<Object> getLogger() {
        return logger;
    }

    public static List<Link> getLinks() {
        return links;
    }

    public static Config getNormalConfig() {
        return NORMAL_CONFIG;
    }
}
