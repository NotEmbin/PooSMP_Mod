package embin.poosmp.util;

import embin.poosmp.PooSMPMod;
import net.minecraft.util.Identifier;

public class ConvertNamespace {
    public static final ConvertNamespace cn = new ConvertNamespace();

    public Identifier convert(String namespace, String default_namespace) {
        String name = removeInvalidCharactersFromString(namespace);
        String[] splitted = name.split(String.valueOf(Identifier.NAMESPACE_SEPARATOR));
        if (splitted.length == 1) {
            return Identifier.of(default_namespace, namespace);
        }
        return Identifier.of(splitted[0], splitted[1]);
    }

    public Identifier convert(String namespace) {
        return convert(namespace, PooSMPMod.MOD_ID);
    }

    public Identifier convertVanilla(String namespace) {
        return convert(namespace, Identifier.DEFAULT_NAMESPACE);
    }

    public String removeInvalidCharactersFromString(String namespace) {
        return namespace.toLowerCase()
            .replace(" ", "_")
            .replace("!", "");
    }
}
