package me.hua.gridium.rendering;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import me.hua.gridium.controls.BookPreview;
import me.hua.gridium.controls.BannerPatternPreview;
import me.hua.gridium.controls.EntityPreview;
import me.hua.gridium.controls.FakeItemIcon;
import me.hua.gridium.controls.GradientRectangle;
import me.hua.gridium.controls.HorizontalLine;
import me.hua.gridium.controls.ItemDecorations;
import me.hua.gridium.controls.ItemIcon;
import me.hua.gridium.controls.MapImage;
import me.hua.gridium.controls.ProfilerChart;
import me.hua.gridium.controls.Rectangle;
import me.hua.gridium.controls.RectangleOutline;
import me.hua.gridium.controls.SkinPreview;
import me.hua.gridium.controls.SpriteRectangle;
import me.hua.gridium.controls.Text;
import me.hua.gridium.controls.TextHighlight;
import me.hua.gridium.controls.TextureRectangle;
import me.hua.gridium.controls.VerticalLine;
import me.hua.gridium.core.Rect;
import me.hua.gridium.core.RenderTransform;
import me.hua.gridium.integration.minecraft.MinecraftWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class MinecraftGridRenderer implements GridRenderer {
	private final GuiGraphicsExtractor graphics;
	private final int mouseX;
	private final int mouseY;
	private final float partialTick;

	public MinecraftGridRenderer(GuiGraphicsExtractor graphics) {
		this(graphics, 0, 0, 0.0F);
	}

	public MinecraftGridRenderer(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
		this.graphics = Objects.requireNonNull(graphics, "graphics");
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.partialTick = partialTick;
	}

	@Override
	public void pushElementTransform(
			Rect bounds,
			double layoutScaleX,
			double layoutScaleY,
			RenderTransform renderTransform,
			double renderOriginX,
			double renderOriginY
	) {
		var pose = graphics.pose();
		pose.pushMatrix();
		double originX = bounds.getX() + bounds.getWidth() * layoutScaleX * renderOriginX;
		double originY = bounds.getY() + bounds.getHeight() * layoutScaleY * renderOriginY;
		pose.translate((float) renderTransform.translationX(), (float) renderTransform.translationY());
		pose.translate((float) originX, (float) originY);
		pose.rotate((float) Math.toRadians(renderTransform.rotationDegrees()));
		pose.scale((float) renderTransform.scaleX(), (float) renderTransform.scaleY());
		pose.translate((float) -originX, (float) -originY);
		pose.translate((float) bounds.getX(), (float) bounds.getY());
		pose.scale((float) layoutScaleX, (float) layoutScaleY);
		pose.translate((float) -bounds.getX(), (float) -bounds.getY());
	}

	@Override
	public void popElementTransform() {
		graphics.pose().popMatrix();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void render(Text text, @Nullable String string) {
		Rect bounds = text.getBounds();
		int x = PixelBounds.roundCoordinate(bounds.getX());
		int y = PixelBounds.roundCoordinate(bounds.getY());
		if (text.isCentered() && string != null) {
			int centerX = PixelBounds.roundCoordinate(bounds.getX() + bounds.getWidth() / 2.0);
			if (text.drawsShadow()) {
				x = centerX - text.getFont().width(string) / 2;
			} else {
				graphics.centeredText(text.getFont(), string, centerX, y, text.getColor());
				return;
			}
		}
		graphics.text(text.getFont(), string, x, y, text.getColor(), text.drawsShadow());
	}

	@Override
	@SuppressWarnings("deprecation")
	public void render(Text text, FormattedCharSequence formattedCharSequence) {
		Rect bounds = text.getBounds();
		int x = PixelBounds.roundCoordinate(bounds.getX());
		int y = PixelBounds.roundCoordinate(bounds.getY());
		if (text.isCentered()) {
			int centerX = PixelBounds.roundCoordinate(bounds.getX() + bounds.getWidth() / 2.0);
			if (text.drawsShadow()) {
				x = centerX - text.getFont().width(formattedCharSequence) / 2;
			} else {
				graphics.centeredText(text.getFont(), formattedCharSequence, centerX, y, text.getColor());
				return;
			}
		}
		graphics.text(text.getFont(), formattedCharSequence, x, y, text.getColor(), text.drawsShadow());
	}

	@Override
	@SuppressWarnings("deprecation")
	public void render(Text text, Component component) {
		Rect bounds = text.getBounds();
		int x = PixelBounds.roundCoordinate(bounds.getX());
		int y = PixelBounds.roundCoordinate(bounds.getY());
		if (text.isCentered()) {
			int centerX = PixelBounds.roundCoordinate(bounds.getX() + bounds.getWidth() / 2.0);
			if (text.drawsShadow()) {
				x = centerX - text.getFont().width(component) / 2;
			} else {
				graphics.centeredText(text.getFont(), component, centerX, y, text.getColor());
				return;
			}
		}
		graphics.text(text.getFont(), component, x, y, text.getColor(), text.drawsShadow());
	}

	@Override
	public void renderWrapped(Text text, FormattedText formattedText) {
		Rect bounds = text.getBounds();
		graphics.textWithWordWrap(
				text.getFont(),
				formattedText,
				PixelBounds.roundCoordinate(bounds.getX()),
				PixelBounds.roundCoordinate(bounds.getY()),
				text.getWrapWidth(),
				text.getColor(),
				text.drawsShadow()
		);
	}

	@Override
	public void renderWithBackdrop(Text text, Component component) {
		Rect bounds = text.getBounds();
		graphics.textWithBackdrop(
				text.getFont(),
				component,
				PixelBounds.roundCoordinate(bounds.getX() + 2.0),
				PixelBounds.roundCoordinate(bounds.getY() + 2.0),
				text.getBackdropWidth(),
				text.getColor()
		);
	}

	@Override
	public void render(TextHighlight highlight) {
		PixelBounds bounds = PixelBounds.from(highlight.getBounds());
		graphics.textHighlight(
				bounds.left(),
				bounds.top(),
				bounds.right(),
				bounds.bottom(),
				highlight.invertsText()
		);
	}

	@Override
	public void render(ItemIcon itemIcon) {
		Rect bounds = itemIcon.getBounds();
		int x = PixelBounds.roundCoordinate(bounds.getX());
		int y = PixelBounds.roundCoordinate(bounds.getY());
		LivingEntity owner = itemIcon.getOwner();
		if (owner != null) {
			graphics.item(owner, itemIcon.getItemStack(), x, y, itemIcon.getSeed());
		} else {
			graphics.item(itemIcon.getItemStack(), x, y, itemIcon.getSeed());
		}
	}

	@Override
	public void render(FakeItemIcon itemIcon) {
		Rect bounds = itemIcon.getBounds();
		graphics.fakeItem(
				itemIcon.getItemStack(),
				PixelBounds.roundCoordinate(bounds.getX()),
				PixelBounds.roundCoordinate(bounds.getY()),
				itemIcon.getSeed()
		);
	}

	@Override
	public void render(ItemDecorations itemDecorations) {
		Rect bounds = itemDecorations.getBounds();
		graphics.itemDecorations(
				itemDecorations.getFont(),
				itemDecorations.getItemStack(),
				PixelBounds.roundCoordinate(bounds.getX()),
				PixelBounds.roundCoordinate(bounds.getY()),
				itemDecorations.getCountText()
		);
	}

	@Override
	public void render(MinecraftWidget widget) {
		widget.getWidget().extractRenderState(graphics, mouseX, mouseY, partialTick);
	}

	@Override
	public void render(MapImage mapImage) {
		Rect bounds = mapImage.getBounds();
		var pose = graphics.pose();
		pose.pushMatrix();
		try {
			pose.translate((float) bounds.getX(), (float) bounds.getY());
			var mapRenderState = mapImage.getMapRenderState();
			Objects.requireNonNull(
					mapRenderState.texture,
					"MapRenderState must be extracted by MapRenderer before drawing"
			);
			graphics.map(mapRenderState);
		} finally {
			pose.popMatrix();
		}
	}

	@Override
	public void render(EntityPreview entityPreview) {
		PixelBounds bounds = PixelBounds.from(entityPreview.getBounds());
		graphics.entity(
				entityPreview.getRenderState(),
				entityPreview.getScale(),
				entityPreview.getTranslation(),
				entityPreview.getRotation(),
				entityPreview.getOverrideCameraAngle(),
				bounds.left(),
				bounds.top(),
				bounds.right(),
				bounds.bottom()
		);
	}

	@Override
	public void render(SkinPreview skinPreview) {
		PixelBounds bounds = PixelBounds.from(skinPreview.getBounds());
		graphics.skin(
				skinPreview.getPlayerModel(),
				skinPreview.getTexture(),
				skinPreview.getScale(),
				skinPreview.getRotationX(),
				skinPreview.getRotationY(),
				skinPreview.getPivotY(),
				bounds.left(),
				bounds.top(),
				bounds.right(),
				bounds.bottom()
		);
	}

	@Override
	public void render(BookPreview bookPreview) {
		PixelBounds bounds = PixelBounds.from(bookPreview.getBounds());
		graphics.book(
				bookPreview.getBookModel(),
				bookPreview.getTexture(),
				bookPreview.getScale(),
				bookPreview.getOpen(),
				bookPreview.getFlip(),
				bounds.left(),
				bounds.top(),
				bounds.right(),
				bounds.bottom()
		);
	}

	@Override
	public void render(BannerPatternPreview bannerPatternPreview) {
		PixelBounds bounds = PixelBounds.from(bannerPatternPreview.getBounds());
		graphics.bannerPattern(
				bannerPatternPreview.getFlagModel(),
				bannerPatternPreview.getBaseColor(),
				bannerPatternPreview.getPatterns(),
				bounds.left(),
				bounds.top(),
				bounds.right(),
				bounds.bottom()
		);
	}

	@Override
	public void render(ProfilerChart profilerChart) {
		PixelBounds bounds = PixelBounds.from(profilerChart.getBounds());
		graphics.profilerChart(
				profilerChart.getChartData(),
				bounds.left(),
				bounds.top(),
				bounds.right(),
				bounds.bottom()
		);
	}

	@Override
	public void render(Rectangle rectangle) {
		PixelBounds bounds = PixelBounds.from(rectangle.getBounds());
		int left = bounds.left();
		int top = bounds.top();
		int right = bounds.right();
		int bottom = bounds.bottom();
		RenderPipeline renderPipeline = rectangle.getRenderPipeline();
		TextureSetup textureSetup = rectangle.getTextureSetup();
		if (textureSetup != null) {
			graphics.fill(
					Objects.requireNonNull(renderPipeline, "renderPipeline"),
					textureSetup,
					left,
					top,
					right,
					bottom
			);
		} else if (renderPipeline != null) {
			graphics.fill(
					renderPipeline,
					left,
					top,
					right,
					bottom,
					Objects.requireNonNull(rectangle.getColor(), "color")
			);
		} else {
			graphics.fill(
					left,
					top,
					right,
					bottom,
					Objects.requireNonNull(rectangle.getColor(), "color")
			);
		}
	}

	@Override
	public void render(TextureRectangle rectangle) {
		PixelBounds bounds = PixelBounds.from(rectangle.getBounds());
		int left = bounds.left();
		int top = bounds.top();
		int right = bounds.right();
		int bottom = bounds.bottom();
		var textureView = rectangle.getTextureView();
		if (textureView != null) {
			TextureRectangle.UvRegion uvRegion = Objects.requireNonNull(rectangle.getUvRegion(), "uvRegion");
			graphics.blit(
					textureView,
					Objects.requireNonNull(rectangle.getSampler(), "sampler"),
					left,
					top,
					right,
					bottom,
					uvRegion.u0(),
					uvRegion.u1(),
					uvRegion.v0(),
					uvRegion.v1()
			);
			return;
		}

		Identifier texture = Objects.requireNonNull(rectangle.getTexture(), "texture");
		TextureRectangle.PixelRegion pixelRegion = rectangle.getPixelRegion();
		if (pixelRegion != null) {
			graphics.blit(
					Objects.requireNonNull(rectangle.getRenderPipeline(), "renderPipeline"),
					texture,
					left,
					top,
					pixelRegion.u(),
					pixelRegion.v(),
					bounds.width(),
					bounds.height(),
					pixelRegion.sourceWidth(),
					pixelRegion.sourceHeight(),
					pixelRegion.textureWidth(),
					pixelRegion.textureHeight(),
					rectangle.getColor()
			);
		} else {
			TextureRectangle.UvRegion uvRegion = Objects.requireNonNull(rectangle.getUvRegion(), "uvRegion");
			graphics.blit(
					texture,
					left,
					top,
					right,
					bottom,
					uvRegion.u0(),
					uvRegion.u1(),
					uvRegion.v0(),
					uvRegion.v1()
			);
		}
	}

	@Override
	public void render(SpriteRectangle spriteRectangle) {
		PixelBounds bounds = PixelBounds.from(spriteRectangle.getBounds());
		int left = bounds.left();
		int top = bounds.top();
		int width = bounds.width();
		int height = bounds.height();
		if (width <= 0 || height <= 0) {
			return;
		}

		var sprite = spriteRectangle.getSprite();
		if (sprite != null) {
			graphics.blitSprite(
					spriteRectangle.getRenderPipeline(),
					sprite,
					left,
					top,
					width,
					height,
					spriteRectangle.getColor()
			);
			return;
		}

		Identifier spriteId = Objects.requireNonNull(spriteRectangle.getSpriteId(), "spriteId");
		Float alpha = spriteRectangle.getAlpha();
		SpriteRectangle.SourceRegion sourceRegion = spriteRectangle.getSourceRegion();
		if (sourceRegion != null) {
			int sourceWidth = sourceRegion.sourceWidth();
			int sourceHeight = sourceRegion.sourceHeight();
			var pose = graphics.pose();
			pose.pushMatrix();
			try {
				pose.translate(left, top);
				pose.scale((float) width / sourceWidth, (float) height / sourceHeight);
				graphics.blitSprite(
						spriteRectangle.getRenderPipeline(),
						spriteId,
						sourceRegion.spriteWidth(),
						sourceRegion.spriteHeight(),
						sourceRegion.sourceX(),
						sourceRegion.sourceY(),
						0,
						0,
						sourceWidth,
						sourceHeight,
						spriteRectangle.getColor()
				);
			} finally {
				pose.popMatrix();
			}
		} else if (alpha != null) {
			graphics.blitSprite(
					spriteRectangle.getRenderPipeline(),
					spriteId,
					left,
					top,
					width,
					height,
					alpha
			);
		} else {
			graphics.blitSprite(
					spriteRectangle.getRenderPipeline(),
					spriteId,
					left,
					top,
					width,
					height,
					spriteRectangle.getColor()
			);
		}
	}

	@Override
	public void render(GradientRectangle rectangle) {
		PixelBounds bounds = PixelBounds.from(rectangle.getBounds());
		graphics.fillGradient(
				bounds.left(),
				bounds.top(),
				bounds.right(),
				bounds.bottom(),
				rectangle.getFirstColor(),
				rectangle.getSecondColor()
		);
	}

	@Override
	public void render(HorizontalLine line) {
		PixelBounds bounds = PixelBounds.from(line.getBounds());
		int length = bounds.width();
		if (length > 0) {
			graphics.horizontalLine(
					bounds.left(),
					bounds.right() - 1,
					bounds.top(),
					line.getColor()
			);
		}
	}

	@Override
	public void render(VerticalLine line) {
		PixelBounds bounds = PixelBounds.from(line.getBounds());
		int length = bounds.height();
		if (length > 0) {
			// The native verticalLine offsets its first Y by one and treats the second as exclusive.
			graphics.verticalLine(
					bounds.left(),
					bounds.top() - 1,
					bounds.bottom(),
					line.getColor()
			);
		}
	}

	@Override
	public void render(RectangleOutline outline) {
		PixelBounds bounds = PixelBounds.from(outline.getBounds());
		graphics.outline(
				bounds.left(),
				bounds.top(),
				bounds.width(),
				bounds.height(),
				outline.getColor()
		);
	}
}
