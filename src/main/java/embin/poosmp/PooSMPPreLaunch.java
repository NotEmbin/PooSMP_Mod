package embin.poosmp;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.apache.logging.log4j.LogManager;

public final class PooSMPPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        java.util.logging.Logger.getLogger("").setFilter(PooSMPMod.filter);
        ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addFilter(PooSMPMod.filter);
    }
}
