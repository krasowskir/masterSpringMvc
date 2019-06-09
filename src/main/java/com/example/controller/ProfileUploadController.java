package com.example.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.Locale;

import javax.inject.Inject;
import javax.naming.SizeLimitExceededException;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.config.PicturesUploadProperties;
import com.example.profile.UserProfileSession;

@Controller
@SessionAttributes("picturePath")
public class ProfileUploadController {

    private final Resource picturesDir;
    private final Resource anonymousPicture;
    private final MessageSource messageSource;
    private final UserProfileSession userProfileSession;
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ProfileUploadController.class);

    @ModelAttribute("picturePath")
    public Resource picturePath() {
	return anonymousPicture;
    }

    @Inject
    public ProfileUploadController(PicturesUploadProperties picturesUploadProperties, MessageSource messageSource,
	    UserProfileSession userProfileSession) {
	picturesDir = picturesUploadProperties.getPicturePath();
	anonymousPicture = picturesUploadProperties.getAnonymousPicture();
	this.messageSource = messageSource;
	this.userProfileSession = userProfileSession;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profile", params = { "upload" })
    public String onUpload(@RequestParam MultipartFile multipartFile, RedirectAttributes redirectAttributes)
	    throws IOException {

	if (multipartFile.getSize() > 10000) {
	    redirectAttributes.addFlashAttribute("error", "File is too big");
	    LOGGER.error("File is too big", multipartFile.getSize());

	    return "redirect:/profile";
	}

	if (multipartFile.isEmpty() || !isImage(multipartFile)) {
	    redirectAttributes.addFlashAttribute("error", "Incorect File, Please upload a picture");
	    LOGGER.error("Incorrect File, please upload a picture", multipartFile.getContentType());
	    return "redirect:/profile";
	}

	Resource picturePath = copyFileToPictures(multipartFile);
	userProfileSession.setPicturePath(picturePath);
	return "redirect:profile";
    }

    @RequestMapping("/uploadedPicture")
    public void getUploadedPicture(HttpServletResponse response) throws IOException {
	Resource picturePath = userProfileSession.getPicturePath();
	if (picturePath == null) {
	    picturePath = anonymousPicture;
	}
	response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath.toString()));
	IOUtils.copy(picturePath.getInputStream(), response.getOutputStream());
    }

    private Resource copyFileToPictures(MultipartFile file) throws IOException {
	String fileExtension = getFileExtension(file.getOriginalFilename());
	String fileN = "pic";
	File tempFile = File.createTempFile(fileN, fileExtension, picturesDir.getFile());
	try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(tempFile)) {
	    IOUtils.copy(in, out);
	    LOGGER.info("new File directory: " + tempFile.getAbsolutePath());
	}
	return new FileSystemResource(tempFile);
    }

    @ExceptionHandler(IOException.class)
    public ModelAndView handleIoException(Locale locale) {
	ModelAndView modelAndView = new ModelAndView("profile/profilePage");
	modelAndView.addObject("error", messageSource.getMessage("upload.io.exception", null, locale));
	return modelAndView;
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ModelAndView handleSizeLimitExceedsException(SizeLimitExceededException sizeLimitExceededException) {
	ModelAndView modelAndView = new ModelAndView("profile/profilePage");
	modelAndView.addObject("error", sizeLimitExceededException.getMessage());
	return modelAndView;
    }

    @RequestMapping("uploadError")
    public ModelAndView onUploadError(Locale locale) {
	ModelAndView modelAndView = new ModelAndView("profile/profilePage");
	modelAndView.addObject("error", messageSource.getMessage("upload.file.too.big", null, locale));
	return modelAndView;
    }

    private boolean isImage(MultipartFile file) {
	return file.getContentType().startsWith("image");
    }

    private static String getFileExtension(String name) {
	return name.substring(name.lastIndexOf("."));
    }

    @RequestMapping("/highcharts")
    public ModelAndView getRechnungenView() {
	ModelAndView model = new ModelAndView("charts/highcharts");
	return model;
    }

}
