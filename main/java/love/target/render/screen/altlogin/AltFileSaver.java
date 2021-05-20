package love.target.render.screen.altlogin;

import com.utils.file.FileUtils;
import love.target.Wrapper;
import net.minecraft.util.StringUtils;

import java.io.FileInputStream;

public class AltFileSaver {
    public static void saveAlts() {
        StringBuilder altBuilder = new StringBuilder();
        for (Alt alt : GuiAltManager.getAlts()) {
            String a = StringUtils.isNullOrEmpty(alt.getAccount()) ? "NULL_ACCOUNT" : alt.getAccount();
            String p = StringUtils.isNullOrEmpty(alt.getPassword()) ? "NULL_PASSWORD" : alt.getPassword();
            String u = StringUtils.isNullOrEmpty(alt.getUserName()) ? "NULL_USERNAME" : alt.getUserName();
            altBuilder.append(a).append(":").append(p).append(":").append(u).append("\n");
        }

        FileUtils.writeStringTo(altBuilder.toString(), Wrapper.getClientFilePath(),"alts.txt");
    }

    public static void readAlts() {
        try {
            for (String str : FileUtils.readLine(new FileInputStream(Wrapper.getClientFilePath() + "alts.txt"))) {
                String account = str.split(":")[0];
                String password = str.split(":")[1];
                String userName = str.split(":")[2];

                GuiAltManager.getAlts().add(new Alt(account,password,userName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
