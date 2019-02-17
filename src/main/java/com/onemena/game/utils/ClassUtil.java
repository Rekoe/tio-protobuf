package com.onemena.game.utils;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.nutz.resource.Scans;

/**
 * @author nonpool
 * @version 1.0
 * @since 2018/1/29
 */
public abstract class ClassUtil {

	/**
	 * 获取一个接口或者父类的所有子类(不含接口和抽象类)
	 *
	 * @param clazz 接口类或者父类
	 * @return
	 */
	public static List<Class<?>> getAllClassBySubClass(Class<?> clazz, String... packages) {
		return getAllClassBySubClass(clazz, false, packages);
	}

	/**
	 * 获取一个接口或者父类的所有子类(不含接口和抽象类)
	 *
	 * @param clazz     接口类或者父类
	 * @param findInJar 是否需要从jar包中查找
	 * @param packages  限定寻找的包名，前缀匹配模式 findInJar为true时建议一定要限制包名提升速度和避免出错！
	 * @return
	 */
	public static List<Class<?>> getAllClassBySubClass(Class<?> clazz, boolean findInJar, String... packages) {
		List<Class<?>> ret = getClasspathAllClass(findInJar, packages).stream().filter(c -> !c.isInterface()).filter(c -> !Modifier.isAbstract(c.getModifiers())).filter(c -> clazz.isAssignableFrom(c)).collect(Collectors.toList());
		return ret;
	}

	/**
	 * 获取所有classpath下所有全限定名以packages开头的的class
	 *
	 * @return
	 */
	private static List<Class<?>> getClasspathAllClass(boolean findInJar, String... packages) {
		List<Class<?>> classes = new ArrayList<>();
		Scans scans = Scans.me();
		Lang.each(packages, new Each<String>() {
			@Override
			public void invoke(int index, String pkg, int length) throws ExitLoop, ContinueLoop, LoopException {
				classes.addAll(scans.scanPackage(pkg));
			}
		});
		return classes;
	}

}