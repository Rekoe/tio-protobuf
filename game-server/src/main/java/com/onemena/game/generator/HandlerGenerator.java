package com.onemena.game.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.nutz.lang.Strings;
import org.nutz.resource.Scans;

import com.google.common.collect.Lists;
import com.google.protobuf.MessageLite;
import com.onemena.game.annotation.HandlerMapping;
import com.onemena.game.proto.Frame;
import com.onemena.game.utils.ClassUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * handler层代码生成器
 */
public class HandlerGenerator {

	public static void main(String[] args) throws Exception {
		HandlerGenerator generator = new HandlerGenerator();
		generator.run("com.onemena.game.custom.handler", true);
	}

	private void run(String packagePath, boolean override) throws Exception {
		final List<Class<?>> handlers = Scans.me().scanPackage("com.onemena.game.custom.handler");
		final List<String> handersFinish = Lists.newArrayList();
		Lang.each(handlers, new Each<Class<?>>() {
			@Override
			public void invoke(int index, Class<?> cls, int length) throws ExitLoop, ContinueLoop, LoopException {
				if (Lang.isNotEmpty(cls.getAnnotation(HandlerMapping.class))) {
					handersFinish.add(cls.getName());
				}
			}
		});
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		URL resource = HandlerGenerator.class.getResource("/handler_template.ftl");
		cfg.setDirectoryForTemplateLoading(Paths.get(resource.toURI()).toFile().getParentFile());
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		Map<String, String> dataModal = new HashMap<>();
		String handlerFilePath = "src/main/java/" + packagePath.replace('.', '/') + "/";
		List<Class<?>> classes = ClassUtil.getAllClassBySubClass(MessageLite.class, true, "com.onemena.game.proto");
		classes.stream().filter(claz -> !Objects.equals(claz, Frame.class)).forEach(claz -> {
			try {
				dataModal.put("className", claz.getSimpleName());
				dataModal.put("lowerClassName", Strings.lowerFirst(claz.getSimpleName()));
				dataModal.put("packagePath", packagePath);
				String clzz = dataModal.get("packagePath") + "." + dataModal.get("className") + "Handler";
				if (handersFinish.contains(clzz)) {
					System.out.println("========" + true);
				} else {
					Template handlerTemplate = cfg.getTemplate("handler_template.ftl");
					File file = new File(handlerFilePath + claz.getSimpleName() + "Handler.java");
					if (override || !file.exists()) {
						Writer out = new OutputStreamWriter(new FileOutputStream(file));
						handlerTemplate.process(dataModal, out);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
}
