package sy.rokz.mysterriaarchive.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;

public class MysterriaarchiveClient implements ClientModInitializer {

public static final ResourceReLoader CLIENT_DATA_LOADER = new ResourceReLoader();


    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES)
                .registerReloadListener(CLIENT_DATA_LOADER);
        ItemTooltipCallback.EVENT.register((stack, _, _, lines) -> {
          JsonObject ings = CLIENT_DATA_LOADER.getJson();
          if (ings != null) {
              if (stack.getCustomName() != null) {
                  JsonElement ing = ings.get(stack.getCustomName().getString());
                  if (ing != null) {
                      String path = ing.getAsJsonObject().get("pathname").getAsString();
                      String Seq = Integer.toString(ing.getAsJsonObject().get("seq").getAsInt());
                      boolean isSupplementary = ing.getAsJsonObject().get("isSupplementary").getAsBoolean();
                      lines.add(Component.literal(((isSupplementary) ? "Supplementary" : "Main") + " ingredient for sequence "+Seq+" of "+path+" pathway").withStyle(PathColors.valueOf(path.replaceAll(" ", "_")).toFormat()));
                  }
              }
          }
        });
    }
}
