package me.hua.gridium.rendering;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import me.hua.gridium.controls.ItemDecorations;
import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.Text;
import me.hua.gridium.controls.TextureRectangle;
import me.hua.gridium.core.HorizontalAlignment;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.RenderTransform;
import me.hua.gridium.core.Size;
import me.hua.gridium.core.VerticalAlignment;
import me.hua.gridium.layout.Grid;
import me.hua.gridium.testutil.RecordingGridRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class RenderDispatchTest {
	@Test
	void layoutChangesTextureDestinationWithoutChangingUvRegion() {
		TextureRectangle texture = new TextureRectangle(
				Identifier.fromNamespaceAndPath("gridium", "test"),
				20.0,
				10.0,
				0.125F,
				0.875F,
				0.25F,
				0.75F
		).setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setVerticalAlignment(VerticalAlignment.BOTTOM);
		Grid grid = new Grid();
		grid.add(texture);
		grid.measure(new Size(100.0, 60.0));
		grid.arrange(new Rect(10.0, 20.0, 100.0, 60.0));

		RecordingGridRenderer recordingRenderer = new RecordingGridRenderer();
		texture.render(recordingRenderer.renderer());

		assertBounds(texture.getBounds(), new Rect(50.0, 70.0, 20.0, 10.0));
		assertEquals(new TextureRectangle.UvRegion(0.125F, 0.875F, 0.25F, 0.75F), texture.getUvRegion());
		RecordingGridRenderer.Call renderCall = recordingRenderer.calls().getFirst();
		assertEquals("render", renderCall.methodName());
		assertSame(texture, renderCall.arguments()[0]);
	}

	@Test
	void textureRectangleRendererDispatchesDestinationBoundsAndUvCoordinates() {
		Identifier textureId = Identifier.fromNamespaceAndPath("gridium", "uv-test");
		TextureRectangle texture = new TextureRectangle(
				textureId,
				14.0,
				9.0,
				0.125F,
				0.875F,
				0.25F,
				0.75F
		);
		texture.arrange(new Rect(10.6, 20.4, 14.4, 8.6));
		RecordingTextureBlitter recordingBlitter = new RecordingTextureBlitter();

		new TextureRectangleRenderer(recordingBlitter.blitter()).render(texture);

		assertBlitCall(
				recordingBlitter.calls().getFirst(),
				textureId,
				11,
				20,
				25,
				29,
				0.125F,
				0.875F,
				0.25F,
				0.75F
		);
	}

	@Test
	void textureRectangleRendererDispatchesPixelSourceRegionColorAndPipeline() {
		RenderPipeline pipeline = RenderPipelines.GUI_TEXTURED;
		Identifier textureId = Identifier.fromNamespaceAndPath("gridium", "pixel-test");
		TextureRectangle texture = new TextureRectangle(
				pipeline,
				textureId,
				31.0,
				19.0,
				7.5F,
				11.25F,
				13,
				17,
				128,
				96,
				0x7F12AB34
		);
		texture.arrange(new Rect(4.6, 7.4, 31.6, 18.6));
		RecordingTextureBlitter recordingBlitter = new RecordingTextureBlitter();

		new TextureRectangleRenderer(recordingBlitter.blitter()).render(texture);

		assertBlitCall(
				recordingBlitter.calls().getFirst(),
				pipeline,
				textureId,
				5,
				7,
				7.5F,
				11.25F,
				31,
				19,
				13,
				17,
				128,
				96,
				0x7F12AB34
		);
	}

	@Test
	void renderDispatchPreservesRectangleAndTextDrawingProperties() {
		Rectangle rectangle = new Rectangle(12.0, 8.0, 0x7F12AB34);
		rectangle.arrange(new Rect(3.0, 5.0, 12.0, 8.0));
		Text text = new Text(testFont(), "Gridium", 0xFFABCDEF, false);
		text.arrange(new Rect(21.0, 34.0, 40.0, 9.0));

		RecordingGridRenderer recordingRenderer = new RecordingGridRenderer();
		rectangle.render(recordingRenderer.renderer());
		text.render(recordingRenderer.renderer());

		assertEquals(2, recordingRenderer.calls().size());
		assertSame(rectangle, recordingRenderer.calls().get(0).arguments()[0]);
		assertEquals(0x7F12AB34, rectangle.getColor());
		assertSame(text, recordingRenderer.calls().get(1).arguments()[0]);
		assertEquals("Gridium", text.getString());
		assertEquals(0xFFABCDEF, text.getColor());
		assertFalse(text.drawsShadow());
		assertBounds(rectangle.getBounds(), new Rect(3.0, 5.0, 12.0, 8.0));
		assertBounds(text.getBounds(), new Rect(21.0, 34.0, 40.0, 9.0));
	}

	@Test
	void renderTransformIsScopedAroundTheElementRenderCall() {
		TextureRectangle texture = new TextureRectangle(
				Identifier.fromNamespaceAndPath("gridium", "test"),
				10.0,
				6.0
		).setLayoutScale(2.0, 3.0)
				.setRenderTransform(new RenderTransform(4.0, 5.0, 1.5, 2.0, 30.0))
				.setRenderTransformOrigin(0.25, 0.75);
		texture.arrange(new Rect(10.0, 20.0, 20.0, 18.0));
		RecordingGridRenderer recordingRenderer = new RecordingGridRenderer();

		texture.render(recordingRenderer.renderer());

		assertEquals(3, recordingRenderer.calls().size());
		assertEquals("pushElementTransform", recordingRenderer.calls().get(0).methodName());
		assertEquals("render", recordingRenderer.calls().get(1).methodName());
		assertEquals("popElementTransform", recordingRenderer.calls().get(2).methodName());
		assertSame(texture, recordingRenderer.calls().get(1).arguments()[0]);
		assertEquals(texture.getRenderTransform(), recordingRenderer.calls().get(0).arguments()[3]);
		assertEquals(0.25, recordingRenderer.calls().get(0).arguments()[4]);
		assertEquals(0.75, recordingRenderer.calls().get(0).arguments()[5]);
	}

	@Test
	void itemDecorationsCanUseMinecraftDefaultCountText() {
		ItemDecorations decorations = new ItemDecorations(testFont(), ItemStack.EMPTY);

		assertNull(decorations.getCountText());
	}

	private static void assertBlitCall(BlitCall call, Object... expectedArguments) {
		assertEquals("blit", call.methodName());
		assertArrayEquals(expectedArguments, call.arguments());
	}

	private static Font testFont() {
		return new Font(new Font.Provider() {
			@Override
			public net.minecraft.client.gui.GlyphSource glyphs(net.minecraft.network.chat.FontDescription description) {
				return null;
			}

			@Override
			public net.minecraft.client.gui.font.glyphs.EffectGlyph effect() {
				return null;
			}
		});
	}

	private static final class RecordingTextureBlitter implements InvocationHandler {
		private final List<BlitCall> calls = new ArrayList<>();
		private final TextureRectangleRenderer.TextureBlitter blitter =
				(TextureRectangleRenderer.TextureBlitter) Proxy.newProxyInstance(
						TextureRectangleRenderer.TextureBlitter.class.getClassLoader(),
						new Class<?>[]{TextureRectangleRenderer.TextureBlitter.class},
						this
				);

		TextureRectangleRenderer.TextureBlitter blitter() {
			return blitter;
		}

		List<BlitCall> calls() {
			return List.copyOf(calls);
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] arguments) {
			calls.add(new BlitCall(method.getName(), arguments.clone()));
			return null;
		}
	}

	private record BlitCall(String methodName, Object[] arguments) {
	}

	private static void assertBounds(Rect actual, Rect expected) {
		assertEquals(expected.getX(), actual.getX());
		assertEquals(expected.getY(), actual.getY());
		assertEquals(expected.getWidth(), actual.getWidth());
		assertEquals(expected.getHeight(), actual.getHeight());
	}
}
