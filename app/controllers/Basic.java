package controllers;

import inteceptors.ConstantsInterceptor;
import inteceptors.TimeInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.Play;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;
import play.mvc.Finally;
import play.mvc.With;
import constants.Constants;

@With(value = { TimeInterceptor.class, ConstantsInterceptor.class })
public class Basic extends Controller {
	
	@Before
	public static void allowOrign(){
		response.setHeader("Access-Control-Allow-Origin", "*");
	}

	@Before(unless = { "Auth.index", "Auth.login", "Auth.logout" })
	static void checkAuthentification() {
		if (StringUtils.isEmpty(session.get(Constants.CURRENT_USERNAME))) {
			// flash.put("error", "You need login first");
			//Auth.index();
		}
		// TODO: refactor
		// Cache.safeSet(Constants.GLOBLE_FACILITIES, Facility.findAll(),
		// "365d");
	}

	@Catch(Exception.class)
	public static void exception(Throwable e) {
		Logger.error("error happend.", e);
	}

	@Finally
	public static void finalExecute() {

	}

	public static String uploadFile(File apkFile) {
		String filename = apkFile.getName();
		try {
			if(!StringUtils.containsIgnoreCase(filename, ".apk")){
				filename = filename + ".apk";
			}
			FileUtils.copyFile(apkFile, new File("public/versions/" + filename));
		} catch (IOException e) {
			Logger.error("upload file error", e);
		}
		return filename;
	}

	@Before(only = { "Users.index", "Employees.index", "Customers.index", "Suppliers.index","Appraisals.index" })
	public static void navigationAdd() {
		Map navigation = (Map) renderArgs.get("navigation");
		if (navigation == null)
			navigation = new HashMap();
		navigation.put("add", true);
		renderArgs.put("navigation", navigation);
	}

	@Before(only = { "Users.view", "Employees.view", "Customers.view", "Suppliers.view","Appraisals.view","Appraisals.add","Employees.add" })
	public static void navigationSave() {
		Map navigation = (Map) renderArgs.get("navigation");
		if (navigation == null)
			navigation = new HashMap();
		navigation.put("save", true);
		renderArgs.put("navigation", navigation);
	}

	@Before(only = { "Users.index", "Employees.index", "Customers.index", "Suppliers.index","Appraisals.index" })
	public static void navigationList() {
		Map navigation = (Map) renderArgs.get("navigation");
		if (navigation == null)
			navigation = new HashMap();
		navigation.put("list", true);
		renderArgs.put("navigation", navigation);
	}

	@Before(only = { "Home.index", "Auth.index", "Auth.login" })
	public static void navigationNoBack() {
		Map navigation = (Map) renderArgs.get("navigation");
		if (navigation == null)
			navigation = new HashMap();
		navigation.put("noback", true);
		renderArgs.put("navigation", navigation);
	}

}