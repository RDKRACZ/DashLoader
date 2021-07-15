package net.oskarstrom.dashloader.image;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import net.minecraft.client.texture.NativeImage;
import net.oskarstrom.dashloader.DashException;
import net.oskarstrom.dashloader.DashRegistry;
import net.oskarstrom.dashloader.Dashable;
import net.oskarstrom.dashloader.mixin.accessor.NativeImageAccessor;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.nio.ByteBuffer;

public class DashImage implements Dashable<NativeImage> {

    @Serialize(order = 0)
    public final byte[] image;

    @Serialize(order = 1)
    public final NativeImage.Format format;

    @Serialize(order = 2)
    public final boolean useSTB;

    @Serialize(order = 3)
    public final int width;

    @Serialize(order = 4)
    public final int height;


    public DashImage(NativeImage nativeImage) {
        try {
            NativeImageAccessor nativeImageAccess = (NativeImageAccessor) (Object) nativeImage;
            this.format = nativeImage.getFormat();
            this.width = nativeImage.getWidth();
            this.height = nativeImage.getHeight();
            this.image = nativeImage.getBytes();
            this.useSTB = nativeImageAccess.getIsStbImage();
        } catch (IOException e) {
            throw new DashException("Failed to create image. Reason: ", e);
        }
    }

    public DashImage(@Deserialize("image") byte[] image,
                     @Deserialize("format") NativeImage.Format format,
                     @Deserialize("useSTB") boolean useSTB,
                     @Deserialize("width") int width,
                     @Deserialize("height") int height) {
        this.image = image;
        this.format = format;
        this.useSTB = useSTB;
        this.width = width;
        this.height = height;
    }

    /**
     * <h2>I can bet that next dashloader version will change this again. This method needs some serious over engineering.</h2>
     *
     * @param registry da registry
     * @return da image
     */
    @Override
    public final NativeImage toUndash(final DashRegistry registry) {
//        try (MemoryStack stack = MemoryStack.stackPush()) {
        final ByteBuffer buf = ByteBuffer.allocateDirect(image.length);
        buf.put(image);
        buf.flip();
//            ByteBuffer buffer = STBImage.stbi_load_from_memory(
//                    buf,
//                    stack.mallocInt(1),
//                    stack.mallocInt(1),
//                    stack.mallocInt(1),
//                    format.getChannelCount());

        ByteBuffer buffer = STBImage.stbi_load_from_memory(
                buf,
                new int[1],
                new int[1],
                new int[1],
                format.getChannelCount());
        if (buffer == null) {
            throw new DashException("Could not load image: " + STBImage.stbi_failure_reason());
        }
            return NativeImageAccessor.init(format, this.width, this.height, useSTB, MemoryUtil.memAddress(buffer));
//        }
    }


}
