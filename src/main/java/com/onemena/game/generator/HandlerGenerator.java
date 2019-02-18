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

import org.nutz.lang.Strings;

import com.google.protobuf.MessageLite;
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
				Template handlerTemplate = cfg.getTemplate("handler_template.ftl");
				File file = new File(handlerFilePath + claz.getSimpleName() + "Handler.java");
				if (override || !file.exists()) {
					Writer out = new OutputStreamWriter(new FileOutputStream(file));
					handlerTemplate.process(dataModal, out);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

}
