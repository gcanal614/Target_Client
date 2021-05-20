package love.target.render.screen.altlogin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;

public class Alt {
    private final String account;
    private final String password;
    private final String userName;
    private final ResourceLocation head;
    private final ThreadDownloadImageData imageData;

    public Alt(String account, String password,String userName) {
        this.account = account;
        this.password = password;
        this.userName = userName;
        this.head = new ResourceLocation("heads/" + userName);
        this.imageData = new ThreadDownloadImageData(null,"https://minotar.net/avatar/" + userName,null,null);
    }

    public void loadHead() {
        Minecraft.getMinecraft().getTextureManager().loadTexture(this.head, imageData);
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public ResourceLocation getHead() {
        return head;
    }

    public ThreadDownloadImageData getImageData() {
        return imageData;
    }
}
