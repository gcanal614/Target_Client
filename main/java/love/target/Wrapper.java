package love.target;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.utils.logger.Logger;
import com.utils.logger.LoggerCreator;
import love.target.command.CommandManager;
import love.target.config.Config;
import love.target.config.ConfigManager;
import love.target.events.EventPreUpdate;
import love.target.mod.ModManager;
import love.target.render.font.FontManager;
import love.target.utils.LaunchUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentText;

import java.util.Random;

public class Wrapper {
    public static final Minecraft mc = Minecraft.getMinecraft();
    private static final Logger<Object> logger = LoggerCreator.getLoggerObject(Wrapper.class);
    private static final String CLIENT_NAME = "Target";
    private static final String CLIENT_VERSION = "Beta -> 210518 #0";
    private static final String CLIENT_FILE_PATH = Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/target/";
    private static final Config NORMAL_CONFIG = new Config("",true);
    private static final Wrapper Instance = new Wrapper();

    private static long runTicks;

    /**
     * 在初始化游戏最后一刻调用
     * @see Minecraft
     */
    public void onStartGame() {
        initializationManagers();
        ConfigManager.loadConfig(NORMAL_CONFIG);

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
    }

    public static void sendMessage(Object message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(ChatFormatting.WHITE + "[" + ChatFormatting.YELLOW + getClientName() + ChatFormatting.WHITE + "] " + ChatFormatting.GRAY + message));
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
        return String.format("%." + format + "f", lastDist);
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
}
