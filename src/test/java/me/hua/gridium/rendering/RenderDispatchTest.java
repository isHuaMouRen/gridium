package me.hua.gridium.rendering;

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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

	private static void assertBounds(Rect actual, Rect expected) {
		assertEquals(expected.getX(), actual.getX());
		assertEquals(expected.getY(), actual.getY());
		assertEquals(expected.getWidth(), actual.getWidth());
		assertEquals(expected.getHeight(), actual.getHeight());
	}
}
