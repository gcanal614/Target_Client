package love.target.other;

import com.utils.ObjectUtils;
import com.utils.file.FileUtils;

import java.util.List;

public class UpdateLog {
    public static List<String> getUpdateLog(String version) {
        return FileUtils.readLine(ObjectUtils.getResource("target_resources/update_logs/log" + version + ".txt"));
    }
}
