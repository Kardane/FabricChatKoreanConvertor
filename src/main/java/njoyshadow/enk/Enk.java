package njoyshadow.enk;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import njoyshadow.enk.command.EngTKor;


public class Enk implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
        {
            EngTKor.register(dispatcher);
            /*
            dispatcher.register(literal("enk").executes(EngTKor::Enk));

            dispatcher.register(literal("enklist").requires(source -> source.hasPermissionLevel(4)).executes(EnableList::enklist));

            dispatcher.register(literal("crimemode").requires(source -> source.hasPermissionLevel(4))
                    .then(literal("on").requires(source -> source.hasPermissionLevel(4))
                            .executes(CrimeMod::On))
                    .then(literal("off").requires(source -> source.hasPermissionLevel(4))
                            .executes(CrimeMod::Off))
                    .executes(CrimeMod::Info));
            */
        }
        );

    }

}
