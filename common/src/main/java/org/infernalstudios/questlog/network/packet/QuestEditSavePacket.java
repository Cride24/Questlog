package org.infernalstudios.questlog.network.packet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.infernalstudios.questlog.Questlog;
import org.infernalstudios.questlog.network.IPacketContext;
import org.infernalstudios.questlog.platform.Services;
import org.jetbrains.annotations.NotNull;


public record QuestEditSavePacket(ResourceLocation id, String json, boolean create) implements CustomPacketPayload {
    public QuestEditSavePacket(ResourceLocation id, String json) { this(id,json,false); }
    public static final Type<QuestEditSavePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Questlog.MODID, "edit_save"));
    public static final StreamCodec<RegistryFriendlyByteBuf, QuestEditSavePacket> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, QuestEditSavePacket::id,
            ByteBufCodecs.STRING_UTF8, QuestEditSavePacket::json,
            ByteBufCodecs.BOOL, QuestEditSavePacket::create,
            QuestEditSavePacket::new
    );
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void handle(QuestEditSavePacket packet, IPacketContext ctx) {
        if (!(ctx.getSender() instanceof ServerPlayer player) || !player.hasPermissions(2)) return;

        try {
            JsonObject definition = GSON.fromJson(packet.json, JsonObject.class);
            if (definition == null) return;
            definition.remove("_validation_error");
            ResourceLocation id = packet.create ? org.infernalstudios.questlog.core.QuestIdGenerator.next(
                    org.infernalstudios.questlog.core.validation.QuestDraftFields.text(definition,"chapter","main"),
                    new org.infernalstudios.questlog.core.QuestText.Draft(definition,java.util.Set.of("title")).firstText("title"),
                    org.infernalstudios.questlog.core.DefinitionUtil::hasCachedQuest) : packet.id;
            var report = org.infernalstudios.questlog.core.validation.QuestValidation.inspect(definition, QuestAuthorPacket.references(player));
            boolean requestedActive = definition.has("active") && definition.get("active").isJsonPrimitive()
                    && definition.getAsJsonPrimitive("active").isBoolean() && definition.get("active").getAsBoolean();
            definition.addProperty("active", requestedActive && report.functional());
            boolean success = org.infernalstudios.questlog.core.QuestDefinitionWriter.write(player, id, definition);
            if (success && org.infernalstudios.questlog.core.DefinitionUtil.hasCachedQuest(id))
                report = org.infernalstudios.questlog.core.validation.QuestValidation.inspect(org.infernalstudios.questlog.core.DefinitionUtil.getCachedQuest(id),QuestAuthorPacket.references(player));
            Services.PLATFORM.sendPacketToClient(player, new QuestAuthorResultPacket(id, requestedActive ? QuestAuthorPacket.SAVE : QuestAuthorPacket.SAVE_DRAFT, success,
                    QuestAuthorPacket.encodeReport(QuestAuthorPacket.report(id, report))));
        } catch (RuntimeException e) {
            Questlog.LOGGER.error("Invalid quest definition received", e);
        }
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
