package com.supconit.common.web.startup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.StringUtils;

import com.supconit.common.utils.PropertiesLoader;

/**
 * Servlet implementation class StartupLoaderServlet
 */
//@WebServlet(loadOnStartup = 9, urlPatterns = { "/startupAfterSpring" })
public class StartupLoaderAfterSpringServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 顺序启动，同步启动，即一个插件启动之后才会启动另一个
	 */
	private List<IStartupPlugin> orderStartupPlugins = new ArrayList<IStartupPlugin>();
	/**
	 * 异步启动，多线程启动，多个插件同时启动
	 */
	private List<IStartupPlugin> startupPlugins = new ArrayList<IStartupPlugin>();

	public static final String PREFIX_ORDER = "order.startup.";
	public static final String PREFIX_NOORDER = "noorder.startup.";
	public static ThreadLocal<ServletConfig> threadLocal=new ThreadLocal<ServletConfig>();

	public StartupLoaderAfterSpringServlet() {
	}

	public void init(ServletConfig config) throws ServletException {
		threadLocal.set(config);
		PropertiesLoader loader = new PropertiesLoader(
				"classpath:startup-afterLoadSpring-plugins.properties");
		for (Object s : loader.getProperties().keySet()) {
			String key=StringUtils.trimToEmpty(s.toString()).toLowerCase();
			String value=StringUtils.trimToEmpty(loader.getProperty(key, ""));
			if(key.startsWith(PREFIX_ORDER)){
				instanceClass(orderStartupPlugins, value);
			}else if(key.startsWith(PREFIX_NOORDER)){
				instanceClass(startupPlugins, value);
			}
		}
		for (IStartupPlugin plugin : orderStartupPlugins) {
			plugin.startup();
		}
		if (startupPlugins.size() > 0) {
			ExecutorService executorService = Executors.newCachedThreadPool();
			for (final IStartupPlugin plugin : startupPlugins) {
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						plugin.startup();
					}
				});
			}
			executorService.shutdown();
		}
		// while(executorService.isTerminated()){
		// System.out.println("-------------");
		// executorService.shutdownNow();
		// }
	}

	private String instanceClass(List<IStartupPlugin> plugins, String className) {
		try {
			if (className == null) {
				return className;
			}
			className = className.trim();
			if (className.isEmpty()) {
				return className;
			}
			Class<?> clazz = Class.forName(className.trim());
			plugins.add((IStartupPlugin) clazz.newInstance());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return className;
	}
}
