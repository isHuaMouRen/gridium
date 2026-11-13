package me.hua.gridium.testutil;

import me.hua.gridium.rendering.GridRenderer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public final class RecordingGridRenderer implements InvocationHandler {
	private final List<Call> calls = new ArrayList<>();
	private final GridRenderer renderer = (GridRenderer) Proxy.newProxyInstance(
			GridRenderer.class.getClassLoader(),
			new Class<?>[]{GridRenderer.class},
			this
	);

	public GridRenderer renderer() {
		return renderer;
	}

	public List<Call> calls() {
		return List.copyOf(calls);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments) {
		calls.add(new Call(method.getName(), arguments == null ? new Object[0] : arguments.clone()));
		return null;
	}

	public record Call(String methodName, Object[] arguments) {
	}
}
